package com.teamtreehouse.ribbit.mockdata;

import com.teamtreehouse.ribbit.models.purgatory.ObsoleteUser;
import com.teamtreehouse.ribbit.models.purgatory.Relation;

import java.util.Random;

public class MockRelations {

    private static Relation<ObsoleteUser> friendRelation;

    public static Relation getUserRelations(String id) {
        if (friendRelation == null) {
            int numberOfRelations = new Random().nextInt(MockUsers.testUsers.size());
            if (numberOfRelations <= 1) {
                numberOfRelations++;
            }

            // Return a relation with a random number of users
            friendRelation = new Relation<ObsoleteUser>();
            for (int i = 0; i < numberOfRelations; i++) {
                friendRelation.add(MockUsers.testUsers.get(i));
            }
        }

        return friendRelation;
    }
}
