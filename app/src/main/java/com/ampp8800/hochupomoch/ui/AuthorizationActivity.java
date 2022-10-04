package com.ampp8800.hochupomoch.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import com.ampp8800.hochupomoch.R;
import com.ampp8800.hochupomoch.data.AuthorizationRepository;
import com.ampp8800.hochupomoch.mvp.AuthorizationView;

import moxy.MvpAppCompatActivity;

public class AuthorizationActivity extends MvpAppCompatActivity implements AuthorizationView {
    @NonNull
    private AuthorizationRepository authorizationRepository = AuthorizationRepository.getInstance();

    @NonNull
    View vForgotYourPassword;
    @NonNull
    View vRegistration;

    @NonNull
    private EditText editEMail;
    @NonNull
    private EditText editPassword;

    @NonNull
    private ImageView ivEdit;

    @NonNull
    private TextView tvToolbarName;

    @Override
    protected void onCreate(Bundle savedInstanceHelp) {
        super.onCreate(savedInstanceHelp);
        setContentView(R.layout.activity_authorization);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.toolbar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        editEMail = findViewById(R.id.et_edit_e_mail);
        editPassword = findViewById(R.id.et_edit_password);
        ivEdit = findViewById(R.id.iv_edit);
        ivEdit.setVisibility(View.GONE);
        findViewById(R.id.search_layout).setVisibility(View.GONE);
        tvToolbarName = findViewById(R.id.tv_toolbar_name);
        tvToolbarName.setText(R.string.log_in);
        // переход по URL
        vForgotYourPassword = this.findViewById(R.id.tv_forgot_your_password);
        goToWebsite(vForgotYourPassword, "http://www.google.com");
        vRegistration = this.findViewById(R.id.tv_registration);
        goToWebsite(vRegistration, "http://www.apple.com");
        //нажатие кнопки войти
        (findViewById(R.id.b_sign_in)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void goToWebsite(@NonNull View view, @NonNull String url) {
        view.setOnClickListener(clickedView -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url))));
    }

    private void login() {
        authorizationRepository.setAuthorized(authorization((editEMail).getText().toString(), (editPassword).getText().toString()));
        if (authorizationRepository.isAuthorized(this)) {
            startActivity(new Intent(AuthorizationActivity.this, MainActivity.class));
        }
    }

    private boolean authorization(@NonNull String login, @NonNull String password) {
        if (authorizationRepository.getLogin().equals(login)) {
            if (authorizationRepository.getPassword().equals(password)) {
                return true;
            } else {
                Toast.makeText(this, R.string.wrong_password, Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(this, R.string.wrong_login, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}
