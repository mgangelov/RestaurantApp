package com.bham.restaurantapp;

public abstract class Globals {
    public static final int DEFAULT_MIN_RATING = 3;
    public static final int DEFAULT_MAX_DISTANCE_LIMIT = 3;
    public static final int DEFAULT_BUSINESS_TYPE_POSITION = 0;
    public static final int DEFAULT_REGION_POSITION = 0;
    public static final int DEFAULT_AUTHORITY_POSITION = 0;
    public static final int DEFAULT_BUSINESS_TYPE_ID = -1;
    public static final int DEFAULT_REGION_ID = 99;
    public static final int DEFAULT_AUTHORITY_ID = 8999;
    public static final int DEFAULT_PAGE_NUMBER = 1;
    public static final int DEFAULT_PAGE_SIZE = 9;
    public static final int DEFAULT_SORT_OPTION_KEY = 2;
    public static final String DEFAULT_KEY_GENERATION_SCHEME = "PBKDF2WithHmacSHA1";
    public static final String DEFAULT_CIPHER_TYPE = "AES/GCM/NoPadding";
    public static final String DEFAULT_KEY_SPEC = "AES";
    public static final int DEFAULT_KEY_LENGTH = 128;
    public enum FAVOURITE_MODES {
        ADD_MODE,
        REMOVE_MODE,
        CHECK_MODE
    }
    public enum NOTE_MODES {
        VIEW_MODE,
        SAVE_MODE,
        DELETE_MODE
    }
}
