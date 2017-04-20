package com.teamtreehouse.ribbit.database;

import com.teamtreehouse.ribbit.models.users.User;

/**
 * Created by javie on 4/4/2017.
 */

public interface UserInsertCallback {

    void onSuccess(User user);
    void onFailure(String message);
}