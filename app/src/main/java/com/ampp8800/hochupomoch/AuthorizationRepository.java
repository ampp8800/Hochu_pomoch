package com.ampp8800.hochupomoch;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class AuthorizationRepository {
    private static AuthorizationRepository authorizationRepository;
    private static final String LOGIN = "admin";
    private static final String PASSWORD = "password";
    private static final String SAVED_AUTHORIZATION = "saved_authorization";
    static SharedPreferences authorization;

    private AuthorizationRepository() {
    }

    public static boolean getAuthorization(Context context) {
        authorization = context.getSharedPreferences(SAVED_AUTHORIZATION, Context.MODE_PRIVATE);
        return authorization.getBoolean(SAVED_AUTHORIZATION, false);
    }

    public static String getLogin() {
        return LOGIN;
    }

    public static String getPassword() {
        return PASSWORD;
    }

    public static void isAuthorized(boolean value) {
        SharedPreferences.Editor editor = authorization.edit();
        editor.putBoolean(SAVED_AUTHORIZATION, value);
        editor.commit();
    }

    @NonNull
    public static AuthorizationRepository getInstance() {
        if (authorizationRepository == null) {
            authorizationRepository = new AuthorizationRepository();
        }
        return authorizationRepository;
    }

}
