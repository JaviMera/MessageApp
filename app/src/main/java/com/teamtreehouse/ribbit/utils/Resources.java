package com.teamtreehouse.ribbit.utils;

import com.teamtreehouse.ribbit.R;

/**
 * Created by javie on 3/25/2017.
 */

public class Resources {

    private static int[] themeColors = new int[] {
        R.color.swipeRefresh1,
        R.color.swipeRefresh2,
        R.color.swipeRefresh3,
        R.color.swipeRefresh4
    };

    public static int[] getRefresherColors() {

        return themeColors;
    }
}
