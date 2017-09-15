package com.annatarhe.athena.ViewController;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.annatarhe.athena.R;

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
                if (password.length() < 6) {
                    Toast.makeText(AuthActivity.this, "password should more than 6", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 请求后端接口

                Toast.makeText(AuthActivity.this, "auth pass. StartActivity now", Toast.LENGTH_SHORT).show();

                // 跳新的profile页面
                finish();

            }
        });
    }
}
