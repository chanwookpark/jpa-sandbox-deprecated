package io.noah.jpasandbox.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import net.sf.log4jdbc.tools.Log4JdbcCustomFormatter;
import net.sf.log4jdbc.tools.LoggingType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

/**
 * Created by chanwook on 2014. 8. 30..
 */
@Configuration
@ComponentScan(useDefaultFilters = true,
        basePackages = {"io.noah.jpasandbox"},
        includeFilters = {@ComponentScan.Filter(value = {Repository.class})})
@EnableTransactionManagement
public class JpaContextConfig {

    @Bean
    public DataSource dataSource() throws Exception {
        Log4jdbcProxyDataSource proxyDataSource = new Log4jdbcProxyDataSource(originalDataSource());

        Log4JdbcCustomFormatter formatter = getLog4jdbcFormatter();

        proxyDataSource.setLogFormatter(formatter);
        proxyDataSource.setLoginTimeout(1000);

        return proxyDataSource;
    }

    @Bean
    public Log4JdbcCustomFormatter getLog4jdbcFormatter() {
        Log4JdbcCustomFormatter formatter = new Log4JdbcCustomFormatter();
        formatter.setLoggingType(LoggingType.SINGLE_LINE);
//        formatter.setMargin(19);
        formatter.setSqlPrefix("SQL :: ");
        return formatter;
    }

    @Bean
    public DataSource originalDataSource() throws Exception {
        ComboPooledDataSource pool = new ComboPooledDataSource();
        pool.setDriverClass("com.mysql.jdbc.Driver");
        pool.setJdbcUrl("jdbc:mysql://localhost:3306/jpasandbox");
        pool.setUser("jpasandbox");
        pool.setPassword("jpasandbox");
        return pool;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() throws Exception {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);
        vendorAdapter.setDatabase(Database.MYSQL);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("io.noah.jpasandbox");
        factory.setDataSource(dataSource());
        factory.setPersistenceUnitName("pu-sandbox");

        HashMap<String, Object> jpaProperties = new HashMap<String, Object>();
        jpaProperties.put("hibernate.temp.use_jdbc_metadata_defaults", false);

        factory.setJpaPropertyMap(jpaProperties);
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    @Bean
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws Exception {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }
}
