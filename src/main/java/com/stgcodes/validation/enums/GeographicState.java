package com.stgcodes.validation.enums;

import java.util.HashMap;
import java.util.Map;

public enum GeographicState {

    AL("Alabama", "AL"),
    AK("Alaska", "AK"),
    AS("American Samoa", "AS"),
    AZ("Arizona", "AZ"),
    AR("Arkansas", "AR"),
    CA("California", "CA"),
    CO("Colorado", "CO"),
    CT("Connecticut", "CT"),
    DE("Delaware", "DE"),
    DC("District of Columbia", "DC"),
    FL("Florida", "FL"),
    GA("Georgia", "GA"),
    GU("Guam", "GU"),
    HI("Hawaii", "HI"),
    ID("Idaho", "ID"),
    IL("Illinois", "IL"),
    IN("Indiana", "IN"),
    IA("Iowa", "IA"),
    KS("Kansas", "KS"),
    KY("Kentucky", "KY"),
    LA("Louisiana", "LA"),
    ME("Maine", "ME"),
    MD("Maryland", "MD"),
    MH("Marshall Islands", "MH"),
    MA("Massachusetts", "MA"),
    MI("Michigan", "MI"),
    MN("Minnesota", "MN"),
    MS("Mississippi", "MS"),
    MO("Missouri", "MO"),
    MT("Montana", "MT"),
    NE("Nebraska", "NE"),
    NV("Nevada", "NV"),
    NH("New Hampshire", "NH"),
    NJ("New Jersey", "NJ"),
    NM("New Mexico", "NM"),
    NY("New York", "NY"),
    NC("North Carolina", "NC"),
    ND("North Dakota", "ND"),
    MP("Northern Mariana Islands", "MP"),
    OH("Ohio", "OH"),
    OK("Oklahoma", "OK"),
    OR("Oregon", "OR"),
    PW("Palau", "PW"),
    PA("Pennsylvania", "PA"),
    PR("Puerto Rico", "PR"),
    RI("Rhode Island", "RI"),
    SC("South Carolina", "SC"),
    SD("South Dakota", "SD"),
    TN("Tennessee", "TN"),
    TX("Texas", "TX"),
    UT("Utah", "UT"),
    VT("Vermont", "VT"),
    VI("Virgin Islands", "VI"),
    VA("Virginia", "VA"),
    WA("Washington", "WA"),
    WV("West Virginia", "WV"),
    WI("Wisconsin", "WI"),
    WY("Wyoming", "WY");

    private final String name;
    
    private final String abbreviation;

    private static final Map<String, GeographicState> GEOGRAPHIC_STATES_BY_ABBR = new HashMap<String, GeographicState>();

    /* static initializer */
    static {
        for (GeographicState geographicState : values()) {
            GEOGRAPHIC_STATES_BY_ABBR.put(geographicState.getAbbreviation(), geographicState);
        }
    }
    
    GeographicState(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
    }
    
    public String getAbbreviation() {
        return abbreviation;
    }
    
    public static GeographicState valueOfAbbreviation(String abbr) {
        return GEOGRAPHIC_STATES_BY_ABBR.get(abbr);
    }

    public static GeographicState valueOfName(String name) {
        try {
            return valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static boolean isAState(String abbrOrName) {
        return valueOfAbbreviation(abbrOrName) != null || valueOfName(abbrOrName) != null;
    }

    @Override
    public String toString() {
        return name;
    }
}
