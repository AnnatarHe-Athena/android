package com.annatarhe.athena.ViewController;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.annatarhe.athena.MainActivity;
import com.annatarhe.athena.Model.Config;
import com.annatarhe.athena.R;
import com.annatarhe.athena.queries.AuthQuery;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import javax.annotation.Nonnull;

/**
 * Created by Annatarhe on 9/13/2017.
 *
 * @author AnnatarHe
 * @email iamhele1994@gmail.com
 */

public class AuthActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth);

        initAuthBtn();
    }
    private void initAuthBtn() {
        Button authBtn = (Button) findViewById(R.id.authBtn);
        final EditText email = (EditText) findViewById(R.id.auth_email);
        final EditText password = (EditText) findViewById(R.id.auth_password);
        authBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailTxt = email.getText().toString();
                String passwordTxt = password.getText().toString();

                if (!emailTxt.contains("@")) {
                    Toast.makeText(AuthActivity.this, "email is invalid", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (passwordTxt.length() < 6) {
                    Toast.makeText(AuthActivity.this, "password should more than 6", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 请求后端接口

                Toast.makeText(AuthActivity.this, "auth pass. StartActivity now", Toast.LENGTH_SHORT).show();

                Config.getApolloClient().query(
                        AuthQuery.builder().email(emailTxt).password(passwordTxt).build()
                ).enqueue(new ApolloCall.Callback<AuthQuery.Data>() {
                    @Override
                    public void onResponse(@Nonnull Response<AuthQuery.Data> response) {
                        Config.token = response.data().auth().token();
                        Config.userID = Integer.parseInt(response.data().auth().id());


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AuthActivity.this, "auth success", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });

                    }

                    @Override
                    public void onFailure(@Nonnull ApolloException e) {

                        Toast.makeText(AuthActivity.this, "auth fail", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
}
