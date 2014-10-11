package io.noah.jpasandbox.config;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * Created by chanwook on 2014. 10. 1..
 */
@ContextConfiguration(classes = {JpaContextConfig.class})
public class BasicConfigTests extends AbstractTransactionalJUnit4SpringContextTests {

    @Test
    public void config() throws Exception {

    }
}
