package com.annatarhe.athena.ViewController;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.annatarhe.athena.MainActivity;
import com.annatarhe.athena.Model.Config;
import com.annatarhe.athena.R;
import com.anntarhe.athena.queries.AuthQuery;
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
            public void onClick(final View view) {
                String emailTxt = email.getText().toString();
                String passwordTxt = password.getText().toString();

                Log.i("i", "click auth buuton");

                if (!emailTxt.contains("@")) {
                    Snackbar.make(view, "email is invalid", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (passwordTxt.length() < 6) {
                    Snackbar.make(view, "password should more than 6", Snackbar.LENGTH_LONG).show();
                    return;
                }


                Log.i("i", "send request to backend: " + emailTxt.toString() + "   " + passwordTxt.toString());

                Config.getApolloClient().query(
                        AuthQuery.builder().email(emailTxt).password(passwordTxt).build()
                ).enqueue(new ApolloCall.Callback<AuthQuery.Data>() {
                    @Override
                    public void onResponse(@Nonnull Response<AuthQuery.Data> response) {

                        if (response.data().auth() == null) {
                            Log.i("auth", response.data().toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Snackbar.make(view, "backend error", Snackbar.LENGTH_LONG).show();
                                }
                            });
                            return;
                        }

                        Config.token = response.data().auth().token();
                        Config.userID = Integer.parseInt(response.data().auth().id());

                        Log.i("token", response.data().auth().token());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Snackbar.make(view, "auth success", Snackbar.LENGTH_LONG).show();
                                finish();
                            }
                        });

                    }

                    @Override
                    public void onFailure(@Nonnull ApolloException e) {
                        Log.i("http", e.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Snackbar.make(view, "backend error", Snackbar.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        });
    }
}
