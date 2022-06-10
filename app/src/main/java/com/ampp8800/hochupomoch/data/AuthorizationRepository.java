package com.ampp8800.hochupomoch.data;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class AuthorizationRepository {
    private static AuthorizationRepository authorizationRepository;
    private final String LOGIN = "admin";
    private final String PASSWORD = "password";
    private final String SAVED_AUTHORIZATION = "saved_authorization";
    static SharedPreferences authorization;

    private AuthorizationRepository() {
    }

    public boolean isAuthorized(Context context) {
        authorization = context.getSharedPreferences(SAVED_AUTHORIZATION, Context.MODE_PRIVATE);
        return authorization.getBoolean(SAVED_AUTHORIZATION, false);
    }

    public String getLogin() {
        return LOGIN;
    }

    public String getPassword() {
        return PASSWORD;
    }

    public void setAuthorized(boolean value) {
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
