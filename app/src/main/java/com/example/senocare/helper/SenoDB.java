package com.example.senocare.helper;

import android.content.Context;
import android.util.Log;

import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;

public final class SenoDB {
    private static Realm uiThreadRealm;
    private static App app;
    private final static String appID = "senocare-uzrlz";

    private static AtomicReference<User> user;

    public static void init(Context context) {
        Realm.init(context);

        app = new App(new AppConfiguration.Builder(appID)
                .build());
    }

    public static void register(String email, String password) {
        app.getEmailPassword().registerUserAsync(email, password, it -> {
            if (it.isSuccess()) {
                Log.i("EXAMPLE", "Successfully registered user.");
            } else {
                Log.e("EXAMPLE", "Failed to register user: " + it.getError().getErrorMessage());
            }
        });
    }

    public static void login(String email, String password) {
        Credentials emailPasswordCredentials = Credentials.emailPassword(email, password);

        user = new AtomicReference<User>();
        app.loginAsync(emailPasswordCredentials, it -> {
            if (it.isSuccess()) {
                Log.v("AUTH", "Successfully authenticated using an email and password.");
                user.set(app.currentUser());
            } else {
                Log.e("AUTH", it.getError().toString());
            }
        });
    }

    public static void logout() {
        user.get().logOutAsync( result -> {
            if (result.isSuccess()) {
                Log.v("AUTH", "Successfully logged out.");
            } else {
                Log.e("AUTH", result.getError().toString());
            }
        });
    }
}
