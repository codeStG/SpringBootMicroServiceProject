package com.stgcodes.utils.constants;

public final class CustomMatchers {

    private CustomMatchers() {
    }
    public static final String LETTER = "[a-zA-Z]+";
    public static final String USERNAME = "^[a-zA-Z0-9._-]{6,25}$";
    public static final String SOCIAL_SECURITY = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$";
    public static final String US_ZIP_CODE =  "\\d{5}(-\\d{4})?";
    public static final String US_PHONE = "^[2-9]\\d{2}-\\d{3}-\\d{4}$";
}
