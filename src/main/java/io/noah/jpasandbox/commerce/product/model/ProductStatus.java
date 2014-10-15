package io.noah.jpasandbox.commerce.product.model;

/**
 * 직접 상태를 변경하는 값을 의미. 예를 들어, 상품판매가능일시 같은 조건은 별도로 체크
 * <p/>
 * Created by chanwook on 2014. 10. 13..
 */
public enum ProductStatus {
    OP/*OPEN*/, TC/*TEMP_CLOSE*/, SO/*SOLD_OUT*/;
}
