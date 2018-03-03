package com.example.caporal.tecnutriapp.domain.repository;

import com.example.caporal.tecnutriapp.domain.entity.Card;
import com.example.caporal.tecnutriapp.domain.entity.LikeEvent;
import com.example.caporal.tecnutriapp.utils.Constants;

import io.realm.Realm;

import static com.example.caporal.tecnutriapp.utils.Constants.FEEDHASH;

/**
 * Created by caporal on 03/03/18.
 */

public class LikePersistenceRepository {

    public static void saveOrUpdate(final LikeEvent likeEvent) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(likeEvent);
            }
        });
    }

    public static LikeEvent getIsLikedByHash(String feedHash) {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(LikeEvent.class).equalTo(FEEDHASH, feedHash).findFirst();
    }

}
