package com.stgcodes.utils.sorting;

import java.util.Comparator;

import com.stgcodes.model.User;

public class UserComparator implements Comparator<User> {

    @Override
    public int compare(User u1, User u2) {
        int result = sortByLastName(u1, u2);

        if(result == 0) {
            result = sortByFirstName(u1, u2);

            if(result == 0) {
                result = sortByUsername(u1, u2);
            }
        }

        return result;
    }

    private int sortByFirstName(User userOne, User userTwo) {
        return userOne.getFirstName().compareTo(userTwo.getFirstName());
    }

    private int sortByLastName(User userOne, User userTwo) {
        return userOne.getLastName().compareTo(userTwo.getLastName());
    }

    private int sortByUsername(User userOne, User userTwo) {
        return userOne.getUsername().compareTo(userTwo.getUsername());
    }
}
