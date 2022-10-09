package com.ampp8800.hochupomoch.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;


import com.ampp8800.hochupomoch.R;
import com.ampp8800.hochupomoch.mvp.AuthorizationView;
import com.ampp8800.hochupomoch.mvp.AuthorizationPresenter;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;

public class AuthorizationActivity extends MvpAppCompatActivity implements AuthorizationView {
    @NonNull
    private View vForgotYourPassword;
    @NonNull
    private View vRegistration;
    @NonNull
    private View vSearchLayout;

    @NonNull
    private EditText editEMail;
    @NonNull
    private EditText editPassword;

    @NonNull
    private ImageView ivEdit;

    @NonNull
    private TextView tvToolbarName;

    @NonNull
    private Button bSignIn;

    @InjectPresenter
    AuthorizationPresenter authorizationPresenter;

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
        vSearchLayout = findViewById(R.id.search_layout);
        vSearchLayout.setVisibility(View.GONE);
        vForgotYourPassword = this.findViewById(R.id.tv_forgot_your_password);
        vRegistration = this.findViewById(R.id.tv_registration);
        tvToolbarName = findViewById(R.id.tv_toolbar_name);
        tvToolbarName.setText(R.string.log_in);
        bSignIn = findViewById(R.id.b_sign_in);
    }

    @Override
    public void loadingInteractiveFunctionality() {
        goToWebsite(vForgotYourPassword, "http://www.google.com");
        goToWebsite(vRegistration, "http://www.apple.com");
        bSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authorizationPresenter.login((editEMail).getText().toString(), (editPassword).getText().toString());
            }
        });
    }

    private void goToWebsite(@NonNull View view, @NonNull String url) {
        view.setOnClickListener(clickedView -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url))));
    }

    @Override
    public void startAuthorization() {
        startActivity(new Intent(AuthorizationActivity.this, MainActivity.class));
    }

    @Override
    public void showToastWrongPassword() {
        Toast.makeText(this, R.string.wrong_password, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToastWrongLogin() {
        Toast.makeText(this, R.string.wrong_login, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        authorizationPresenter.hideApplication();
    }

    @Override
    public void hideApplication(){
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

}
