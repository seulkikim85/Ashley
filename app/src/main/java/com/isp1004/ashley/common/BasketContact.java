package com.isp1004.ashley.common;

/**
 * Created by h06 on 2017-04-07.
 */

public class BasketContact {

    public static abstract class DBList {
        public static final String TABLE_NAME = "Basket";

        public static final String BASKET_ID    = "basket_id";
        public static final String EMAIL        = "email";
        public static final String BRAND_NAME   = "brand_name";
        public static final String PRODUCT_ID   = "product_id";
        public static final String PRODUCT_NAME = "product_name";
        public static final String QTY          = "qty";
        public static final String PRICE        = "price";
        public static final String IS_ORDERED   = "is_ordered";
        public static final String IS_PAID      = "is_paid";
    }

}
