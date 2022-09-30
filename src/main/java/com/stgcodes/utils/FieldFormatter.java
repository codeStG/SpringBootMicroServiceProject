package com.stgcodes.utils;

import org.apache.commons.lang3.StringUtils;

import static com.stgcodes.utils.constants.CustomMatchers.*;

public class FieldFormatter {

    public String separateBy(String date, String delimiter) {
        return cleanWhitespaceAndSeparators(date, delimiter);
    }

    public String formatAsEnum(String enumStr) {
        return cleanWhitespace(enumStr, "_").toUpperCase();
    }

    public String cleanWhitespace(String str) {
        return str.trim().replaceAll(WHITESPACE, StringUtils.EMPTY);
    }

    private String cleanWhitespace(String str, String delimiter) {
        return str.trim().replaceAll(WHITESPACE, delimiter);
    }

    private String cleanWhitespaceAndSeparators(String str, String delimiter) {
        return str.trim().replaceAll(WHITESPACE_COMMON_SEPARATORS, delimiter);
    }
}