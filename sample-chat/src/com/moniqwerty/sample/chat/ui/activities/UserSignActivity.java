package com.moniqwerty.sample.chat.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.moniqwerty.sample.chat.core.ChatService;
import com.quickblox.core.QBEntityCallbackImpl;
import com.quickblox.sample.chat.R;
import com.quickblox.users.model.QBUser;

import java.util.List;

public class UserSignActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(240, 248, 255));
    }

    public void signUp(View view){
        EditText username = (EditText) findViewById(R.id.loginEdit);
        EditText password = (EditText) findViewById(R.id.passwordEdit);
        final QBUser user = new QBUser();
        user.setLogin(String.valueOf(username.getText()));
        user.setPassword(String.valueOf(password.getText()));

        ChatService.initIfNeed(this);

        ChatService.getInstance().signUp(user, new QBEntityCallbackImpl() {

            @Override
            public void onSuccess() {
                // Once user is signed up, go to login
                ChatService.getInstance().login(user, new QBEntityCallbackImpl() {

                    @Override
                    public void onSuccess() {
                        // Go to Dialogs screen
                        //
                        Intent intent = new Intent(UserSignActivity.this, DialogsActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(List errors) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(UserSignActivity.this);
                        dialog.setMessage("chat login errors: " + errors).create().show();
                    }
                });
            }

            @Override
            public void onError(List errors) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(UserSignActivity.this);
                dialog.setMessage("chat login errors: " + errors).create().show();
            }
        });
    }


    public void login(View view) {
        // Login to REST API
        EditText username = (EditText) findViewById(R.id.loginEdit);
        EditText password = (EditText) findViewById(R.id.passwordEdit);
        final QBUser user = new QBUser();
        user.setLogin(String.valueOf(username.getText()));
        user.setPassword(String.valueOf(password.getText()));

        ChatService.initIfNeed(this);

        ChatService.getInstance().login(user, new QBEntityCallbackImpl() {

            @Override
            public void onSuccess() {
                // Go to Dialogs screen
                //
                Intent intent = new Intent(UserSignActivity.this, DialogsActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(List errors) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(UserSignActivity.this);
                dialog.setMessage("chat login errors: " + errors).create().show();
            }
        });
    }
}