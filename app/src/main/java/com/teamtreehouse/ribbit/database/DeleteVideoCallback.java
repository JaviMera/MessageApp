package com.teamtreehouse.ribbit.database;

/**
 * Created by javie on 4/18/2017.
 */

public interface DeleteVideoCallback {

    void onSuccess();
    void onFailure(String message);
}
