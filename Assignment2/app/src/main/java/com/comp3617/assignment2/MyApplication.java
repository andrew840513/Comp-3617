package com.comp3617.assignment2;

/**
 * Created by Andrew on 2017-06-28.
 */

import android.app.Application;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize Realm. Should only be done once when the application starts.
        Log.d("Andrew","I created Application");
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .initialData(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.createObject(TaskModel.class);
                    }})
                .build();
        Realm.deleteRealm(realmConfig);
        Realm.setDefaultConfiguration(realmConfig);
    }
}
