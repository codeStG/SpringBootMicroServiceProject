package com.stgcodes.utils.constants;

public final class CustomMatchers {

    private CustomMatchers() {
    }

    public static final String WHITESPACE = "\\s+";
    public static final String WHITESPACE_COMMON_SEPARATORS = "[\\s-/,.*]+";
    public static final String LETTER = "[a-zA-Z]+";
    public static final String SOCIAL_SECURITY = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$";

    public static final String US_ZIP_CODE =  "\\d{5}(-\\d{4})?";
}
