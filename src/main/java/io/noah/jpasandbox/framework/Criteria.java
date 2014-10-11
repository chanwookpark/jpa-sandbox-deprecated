package io.noah.jpasandbox.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by chanwook on 2014. 10. 6..
 */
public class Criteria {
    private final Logger logger = LoggerFactory.getLogger(Criteria.class);

    private Map<String, String> dataMap = new ConcurrentHashMap<String, String>();

    public Criteria eq(String key, String value) {
        if (dataMap.containsKey(key)) {
            logger.warn("동일한 키로 데이터 저장을 요청받았습니다 => key: " + key + ", current: " + dataMap.get(key) + ", request: " + value);
        }
        return this;
    }

    public static Criteria create() {
        return new Criteria();
    }

    public String get(String key) {
        return dataMap.get(key);
    }
}
