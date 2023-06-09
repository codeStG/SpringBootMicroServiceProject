package com.stgcodes.utils.sorting;

import com.stgcodes.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class UserComparatorTests {

    List<User> users;

    @BeforeEach
    public void setUp() {
        users = new ArrayList<>();

        users.add(User.builder()
                .firstName("Lionel")
                .lastName("Montgomery")
                .username("limontgom")
                .email("limontgom@hotmail.com")
                .build());

        users.add(User.builder()
                .firstName("Zachary")
                .lastName("Still")
                .username("zastill")
                .email("zastill@gmail.com")
                .build());

        users.add(User.builder()
                .firstName("Andreas")
                .lastName("Muniz")
                .username("anmuniz")
                .email("anmuniz@hotmail.com")
                .build());

        users.add(User.builder()
                .firstName("Lionel")
                .lastName("Montgomery")
                .username("liomontgom")
                .email("liomontgom@hotmail.com")
                .build());
    }

    @Test
    void testUsersSortedByFirstNameAscending() {
        Collections.shuffle(users);
        users.sort(new UserComparator());

        for(int i = 0; i < users.size() - 1; i++) {
            User p1 = users.get(i);
            User p2 = users.get(i + 1);

            int result = p1.getLastName().compareTo(p2.getLastName());

            if(result == 0) {
                result = p1.getFirstName().compareTo(p2.getFirstName());

                if(result == 0) {
                    result = p1.getUsername().compareTo(p2.getUsername());
                }
            }

            Assertions.assertTrue(result < 0);
        }
    }
}