package com.example.caporal.tecnutriapp.domain.repository;

import com.example.caporal.tecnutriapp.domain.entity.Card;
import com.example.caporal.tecnutriapp.utils.Constants;

import io.realm.Realm;

import static com.example.caporal.tecnutriapp.utils.Constants.FEEDHASH;

/**
 * Created by caporal on 03/03/18.
 */

public class LikePersistenceRepository {

    public static void saveOrUpdate(final Card card) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(card);
            }
        });
    }

    public static Card getCardByHash(String feedHash) {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Card.class).equalTo(FEEDHASH, feedHash).findFirst();
    }


}
