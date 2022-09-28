package com.stgcodes.utils.constants;

public final class CustomMatchers {

    private CustomMatchers() {
    }

    public static final String WHITESPACE_DASH_SLASH_MATCHER = "[\\s-/]+";
    public static final String LETTER_MATCHER = "[a-zA-Z]+";
    public static final String SOCIAL_SECURITY_MATCHER = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$";
}
