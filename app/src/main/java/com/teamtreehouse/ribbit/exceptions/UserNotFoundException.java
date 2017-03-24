package com.teamtreehouse.ribbit.exceptions;

/**
 * Created by javie on 3/24/2017.
 */

public class UserNotFoundException extends Exception {

    @Override
    public String getMessage() {

        return "The username or password you entered is incorrect.";
    }
}
