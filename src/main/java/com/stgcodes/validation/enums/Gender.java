package com.stgcodes.validation.enums;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum Gender {
    MALE,
    FEMALE,
    REFUSE,
    @JsonEnumDefaultValue UNKNOWN;
}