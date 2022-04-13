package com.ampp8800.hochupomoch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class AuthorizationActivity extends AppCompatActivity {

    ProfileRepository profileRepository = ProfileRepository.getInstance();

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
    }

    public void login(View view) {
        profileRepository.setAuthorization(((EditText) findViewById(R.id.et_edit_e_mail)).getText().toString(), ((EditText) findViewById(R.id.et_edit_password)).getText().toString());
        if (profileRepository.getAuthorization()) {
            startActivity(new Intent(AuthorizationActivity.this, MainActivity.class));
        }else {
            Toast.makeText(this, R.string.wrong_login, Toast.LENGTH_SHORT).show();
        }
    }
}
