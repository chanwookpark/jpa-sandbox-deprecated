package io.noah.jpasandbox.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by chanwook on 2014. 10. 6..
 */
public class Criteria {
    public static final int NON_PAGING_NUMBER = -1;

    private final Logger logger = LoggerFactory.getLogger(Criteria.class);

    private Map<String, String> fieldMap = new ConcurrentHashMap<String, String>();
    private Map<String, ORDERING> orderMap = new ConcurrentHashMap<String, ORDERING>();

    private int pageNumber = NON_PAGING_NUMBER;
    private int itemSize = NON_PAGING_NUMBER;

    public Criteria eq(String key, String value) {
        if (fieldMap.containsKey(key)) {
            logger.warn("동일한 키로 데이터 저장을 요청받았습니다. 기존 데이터는 삭제! => key: " + key + "가 " + fieldMap.get(key) + "에서 request: " + value + "로 변경됨.");
        }
        this.fieldMap.put(key, value);
        return this;
    }

    public static Criteria create() {
        return new Criteria();
    }

    public String get(String key) {
        return fieldMap.get(key);
    }

    public Map<String, String> toFieldMap() {
        return this.fieldMap;
    }

    public Criteria page(int pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public int getPage() {
        return pageNumber;
    }

    public Criteria item(int itemSize) {
        this.itemSize = itemSize;
        return this;
    }

    public int getItemSize() {
        return itemSize;
    }

    /**
     * 페이징을 원하는지 여부 확인
     *
     * @return
     */
    public boolean hasPaging() {
        return this.pageNumber > NON_PAGING_NUMBER && this.itemSize > NON_PAGING_NUMBER;
    }

    public Criteria order(String field, ORDERING ordering) {
        this.orderMap.put(field, ordering);
        return this;
    }

    public boolean hasOrdering() {
        return this.orderMap.size() > 0;
    }

    public Map<String, ORDERING> toOrderMap() {
        return this.orderMap;
    }

    public enum ORDERING {DESC}
}
