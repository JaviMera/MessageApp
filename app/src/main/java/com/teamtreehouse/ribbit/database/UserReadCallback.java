package com.teamtreehouse.ribbit.database;

import com.teamtreehouse.ribbit.models.User;

import java.util.List;

/**
 * Created by javie on 4/4/2017.
 */

public interface UserReadCallback {

    void onUserRead(List<User> user);
}
