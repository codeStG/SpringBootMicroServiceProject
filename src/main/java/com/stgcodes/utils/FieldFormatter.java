package com.stgcodes.utils;

import com.stgcodes.exceptions.GeographicStateNotFoundException;
import com.stgcodes.validation.enums.GeographicState;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import static com.stgcodes.utils.constants.CustomMatchers.WHITESPACE;
import static com.stgcodes.utils.constants.CustomMatchers.WHITESPACE_COMMON_SEPARATORS;

@Slf4j
public class FieldFormatter {

    public String separateBy(String str, String delimiter) {
        return cleanWhitespace(str, delimiter);
    }

    public String formatAsEnum(String enumStr) {
        return cleanWhitespace(enumStr, "_").toUpperCase();
    }

    public String formatAsState(String state) {
        GeographicState geographicState;
        String enumState = formatAsEnum(state);

        geographicState = enumState.length() > 2 ? GeographicState.valueOf(enumState) : GeographicState.valueOfAbbreviation(enumState);
        return geographicState.getAbbreviation();
    }

    public String formatAsDate(String date) {
        return date.trim().replaceAll(WHITESPACE_COMMON_SEPARATORS, "/");
    }

    public String cleanWhitespace(String str) {
        return str.trim().replaceAll(WHITESPACE, StringUtils.EMPTY);
    }

    private String cleanWhitespace(String str, String delimiter) {
        return str.trim().replaceAll(WHITESPACE, delimiter);
    }
}