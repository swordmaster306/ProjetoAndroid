package com.example.alexy.redesocial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button login;

    private EditText email;
    private EditText password;

    public void onForgetPassClickHandler(View v) {
        System.out.println("onForgetPassClickHandler");
    }

    public void onSignUpClickHandler(View v) {
        System.out.println("onSignUpClickHandler");
    }

    // On Button Click Handler
    public void onClick(View v) {
        Intent i = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(i);
    }

    private void setUpBinds() {
        this.email = findViewById(R.id.emailInput);
        this.password = findViewById(R.id.passwordInput);
        this.login = findViewById(R.id.login);
        this.login.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setUpBinds();
    }
}
