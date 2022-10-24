package com.stgcodes.utils.sorting;

public class PersonSortChooser {

    public static PersonSorter getSorter(String sorter) {
        switch(sorter.toLowerCase()) {
            case "firstname":
                return new SortByFirstName();
            case "username":
                return new SortByUsername();
            default:
                return new SortByLastName();
        }
    }
}
