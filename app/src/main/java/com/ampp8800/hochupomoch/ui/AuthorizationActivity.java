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
import androidx.appcompat.app.AppCompatActivity;

import com.ampp8800.hochupomoch.R;
import com.ampp8800.hochupomoch.data.AuthorizationRepository;

public class AuthorizationActivity extends AppCompatActivity {
    private AuthorizationRepository authorizationRepository = AuthorizationRepository.getInstance();
    private EditText editEMail;
    private EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceHelp) {
        super.onCreate(savedInstanceHelp);
        setContentView(R.layout.activity_authorization);
        editEMail = (EditText) findViewById(R.id.et_edit_e_mail);
        editPassword = (EditText) findViewById(R.id.et_edit_password);
        // инициализация тулбара
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.toolbar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ImageView imageView = (ImageView) findViewById(R.id.iv_edit);
        imageView.setVisibility(View.GONE);
        findViewById(R.id.search_layout).setVisibility(View.GONE);
        TextView textView = (TextView) findViewById(R.id.tv_toolbar_name);
        textView.setText(R.string.log_in);
        // переход по URL
        View forgotYourPassword = (View) this.findViewById(R.id.tv_forgot_your_password);
        goToWebsite(forgotYourPassword, "http://www.google.com");
        View registration = (View) this.findViewById(R.id.tv_registration);
        goToWebsite(registration, "http://www.apple.com");
        //нажатие кнопки войти
        ((View) findViewById(R.id.b_sign_in)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(view);
            }
        });
    }

    private void goToWebsite(View view, @NonNull String url) {
        view.setOnClickListener(clickedView -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url))));
    }

    private void login(View view) {
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
