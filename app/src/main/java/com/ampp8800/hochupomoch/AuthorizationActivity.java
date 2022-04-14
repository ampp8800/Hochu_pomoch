package com.ampp8800.hochupomoch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class AuthorizationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceHelp) {
        super.onCreate(savedInstanceHelp);
        setContentView(R.layout.activity_authorization);
        // инициализация тулбара
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.toolbar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ImageView imageView = (ImageView) findViewById(R.id.iv_edit);
        imageView.setVisibility(View.GONE);
        TextView textView = (TextView) findViewById(R.id.tv_toolbar_name);
        textView.setText(R.string.help);
        // переход по URL
        View forgotYourPassword = (View) this.findViewById(R.id.tv_forgot_your_password);
        forgotYourPassword.setOnClickListener(clickedView -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))));
        View registration = (View) this.findViewById(R.id.tv_registration);
        registration.setOnClickListener(clickedView -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.apple.com"))));

    }

    public void login(View view) {
        ProfileRepository.setAuthorization(((EditText) findViewById(R.id.et_edit_e_mail)).getText().toString(), ((EditText) findViewById(R.id.et_edit_password)).getText().toString());
        if (ProfileRepository.getAuthorization(this)) {
            startActivity(new Intent(AuthorizationActivity.this, MainActivity.class));
        }else {
            Toast.makeText(this, R.string.wrong_login, Toast.LENGTH_SHORT).show();
        }
    }

    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);

    }
}
