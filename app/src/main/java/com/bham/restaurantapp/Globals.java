package com.bham.restaurantapp;

public abstract class Globals {
    public static final int DEFAULT_MIN_RATING = 3;
    public static final float DEFAULT_MAX_DISTANCE_LIMIT = 3;
    public static final int DEFAULT_BUSINESS_TYPE_POSITION = 0;
    public static final int DEFAULT_REGION_POSITION = 0;
    public static final int DEFAULT_AUTHORITY_POSITION = 0;
    public static final int DEFAULT_BUSINESS_TYPE_ID = -1;
    public static final int DEFAULT_REGION_ID = 99;
    public static final int DEFAULT_AUTHORITY_ID = 8999;
    public static final int DEFAULT_PAGE_NUMBER = 1;
    public static final int DEFAULT_PAGE_SIZE = 9;
    public enum MODES {
        ADD_MODE,
        REMOVE_MODE,
        CHECK_MODE
    }
}
