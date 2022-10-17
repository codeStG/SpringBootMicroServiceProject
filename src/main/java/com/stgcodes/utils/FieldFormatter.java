package com.stgcodes.utils;

import com.stgcodes.validation.enums.GeographicState;
import lombok.extern.slf4j.Slf4j;

import static com.stgcodes.utils.constants.CustomMatchers.WHITESPACE;
import static com.stgcodes.utils.constants.CustomMatchers.WHITESPACE_COMMON_SEPARATORS;

@Slf4j
public class FieldFormatter {

    public String separateBy(String str, String delimiter) {
        return str.trim().replaceAll(WHITESPACE, delimiter);
    }

    public String formatAsEnum(String enumStr) {
        return enumStr.trim().replaceAll(WHITESPACE, "_").toUpperCase();
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
}