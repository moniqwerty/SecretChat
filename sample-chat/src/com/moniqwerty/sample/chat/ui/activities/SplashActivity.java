package com.moniqwerty.sample.chat.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import com.quickblox.core.QBEntityCallbackImpl;
import com.moniqwerty.sample.chat.core.ChatService;
import com.quickblox.users.model.QBUser;
import com.quickblox.sample.chat.R;

import java.util.List;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(240, 248, 255));


    }
    public  void login(View view)
    {
        // Login to REST API
        //
        EditText username = (EditText)findViewById(R.id.loginEdit);
        EditText password = (EditText)findViewById(R.id.passwordEdit);
        final QBUser user = new QBUser();
        user.setLogin(String.valueOf(username.getText()));
        user.setPassword(String.valueOf(password.getText()));

        ChatService.initIfNeed(this);

        ChatService.getInstance().login(user, new QBEntityCallbackImpl() {

            @Override
            public void onSuccess() {
                // Go to Dialogs screen
                //
                Intent intent = new Intent(SplashActivity.this, DialogsActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(List errors) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(SplashActivity.this);
                dialog.setMessage("chat login errors: " + errors).create().show();
            }
        });
    }
}