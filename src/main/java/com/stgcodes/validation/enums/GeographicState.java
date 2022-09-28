package com.stgcodes.validation.enums;

import com.stgcodes.utils.constants.CustomMatchers;

import java.util.HashMap;
import java.util.Map;

public enum GeographicState {

    ALABAMA("Alabama", "AL"), 
    ALASKA("Alaska", "AK"), 
    AMERICAN_SAMOA("American Samoa", "AS"), 
    ARIZONA("Arizona", "AZ"), 
    ARKANSAS("Arkansas", "AR"), 
    CALIFORNIA("California", "CA"), 
    COLORADO("Colorado", "CO"), 
    CONNECTICUT("Connecticut", "CT"), 
    DELAWARE("Delaware", "DE"), 
    DISTRICT_OF_COLUMBIA("District of Columbia", "DC"), 
    FLORIDA("Florida", "FL"), 
    GEORGIA("Georgia", "GA"), 
    GUAM("Guam", "GU"), 
    HAWAII("Hawaii", "HI"), 
    IDAHO("Idaho", "ID"), 
    ILLINOIS("Illinois", "IL"), 
    INDIANA("Indiana", "IN"), 
    IOWA("Iowa", "IA"),
    KANSAS("Kansas", "KS"), 
    KENTUCKY("Kentucky", "KY"), 
    LOUISIANA("Louisiana", "LA"),
    MAINE("Maine", "ME"), 
    MARYLAND("Maryland", "MD"), 
    MARSHALL_ISLANDS("Marshall Islands", "MH"), 
    MASSACHUSETTS("Massachusetts", "MA"), 
    MICHIGAN("Michigan", "MI"),
    MINNESOTA("Minnesota", "MN"), 
    MISSISSIPPI("Mississippi", "MS"), 
    MISSOURI("Missouri", "MO"), 
    MONTANA("Montana", "MT"), 
    NEBRASKA("Nebraska", "NE"), 
    NEVADA("Nevada", "NV"), 
    NEW_HAMPSHIRE("New Hampshire", "NH"), 
    NEW_JERSEY("New Jersey", "NJ"), 
    NEW_MEXICO("New Mexico", "NM"), 
    NEW_YORK("New York", "NY"), 
    NORTH_CAROLINA("North Carolina", "NC"), 
    NORTH_DAKOTA("North Dakota", "ND"), 
    NORTHERN_MARIANA_ISLANDS("Northern Mariana Islands", "MP"), 
    OHIO("Ohio", "OH"), 
    OKLAHOMA("Oklahoma", "OK"), 
    OREGON("Oregon", "OR"), 
    PALAU("Palau", "PW"), 
    PENNSYLVANIA("Pennsylvania", "PA"), 
    PUERTO_RICO("Puerto Rico", "PR"), 
    RHODE_ISLAND("Rhode Island", "RI"), 
    SOUTH_CAROLINA("South Carolina", "SC"), 
    SOUTH_DAKOTA("South Dakota", "SD"), 
    TENNESSEE("Tennessee", "TN"),
    TEXAS("Texas", "TX"), 
    UTAH("Utah", "UT"), 
    VERMONT("Vermont", "VT"), 
    VIRGIN_ISLANDS("Virgin Islands", "VI"), 
    VIRGINIA("Virginia", "VA"), 
    WASHINGTON("Washington", "WA"), 
    WEST_VIRGINIA("West Virginia", "WV"), WISCONSIN("Wisconsin", "WI"), WYOMING("Wyoming", "WY");

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
        String key = abbr.toUpperCase().replaceAll(CustomMatchers.WHITESPACE_DASH_SLASH_MATCHER, "");

        return GEOGRAPHIC_STATES_BY_ABBR.get(key);
    }

    public static GeographicState valueOfName(String name) {
        final String enumName = name.toUpperCase().trim().replaceAll(CustomMatchers.WHITESPACE_DASH_SLASH_MATCHER, "_");

        try {
            return valueOf(enumName);
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
