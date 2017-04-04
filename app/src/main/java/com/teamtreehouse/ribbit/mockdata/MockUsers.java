package com.teamtreehouse.ribbit.mockdata;

import com.teamtreehouse.ribbit.models.purgatory.ObsoleteUser;

import java.util.ArrayList;

/**
 * Created by benjakuben on 10/12/16.
 */

public class MockUsers {

    public static final String TAG = MockUsers.class.getSimpleName();

    public static ArrayList<ObsoleteUser> testUsers;

    public static void initialize() {
        testUsers = new ArrayList<ObsoleteUser>();
        testUsers.add(new ObsoleteUser("Ben", "test", "ben@teamtreehouse.com"));
        testUsers.add(new ObsoleteUser("Sam", "test", "test@teamtreehouse.com"));
        testUsers.add(new ObsoleteUser("Rachel", "test", "test@teamtreehouse.com"));
        testUsers.add(new ObsoleteUser("Mike", "test", "test@teamtreehouse.com"));
        testUsers.add(new ObsoleteUser("Pasan", "test", "pasan@teamtreehouse.com"));
        testUsers.add(new ObsoleteUser("Jamele", "test", "test@teamtreehouse.com"));
        testUsers.add(new ObsoleteUser("Muhammed", "test", "test@teamtreehouse.com"));
        testUsers.add(new ObsoleteUser("Lisa", "test", "test@teamtreehouse.com"));
        testUsers.add(new ObsoleteUser("Sun", "test", "test@teamtreehouse.com"));
        testUsers.add(new ObsoleteUser("Maria", "test", "test@teamtreehouse.com"));
        testUsers.add(new ObsoleteUser("bdeitch", "test", "bdeitch@teamtreehouse.com"));
    }

    public static void add(ObsoleteUser user) {
        testUsers.add(user);
    }

    public static ObsoleteUser get(int position) {

        return testUsers.get(position);
    }
}
