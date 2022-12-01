package com.stgcodes.criteria;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonCriteria {
	@Builder.Default
    private String firstName = "";
	@Builder.Default
    private String lastName = "";
	@Builder.Default
    private int age = 0;
    @Builder.Default
    private String gender = "UNKNOWN";
}
