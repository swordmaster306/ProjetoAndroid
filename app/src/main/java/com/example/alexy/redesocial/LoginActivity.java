package com.example.alexy.redesocial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alexy.redesocial.utils.RegExValidation;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;

    public void onForgetPassClickHandler(View v) {
        System.out.println("onForgetPassClickHandler");
    }

    public void onSignUpClickHandler(View v) {
        this.changeActivity(SignUpActivity.class);
    }

    private void changeActivity(Class activity) {
        Intent intent = new Intent(LoginActivity.this, activity);
        startActivity(intent);
    }

    public Boolean validateForm() {
        String pass = this.password.getText().toString();
        String email = this.email.getText().toString();
        if (pass.isEmpty() || email.isEmpty()) {
            if (pass.isEmpty()) this.password.setError("Campo obrigatório");
            if (email.isEmpty() || email.matches("")) this.email.setError("Campo obrigatório");
            return false;
        }
        if (!email.matches(RegExValidation.email)) {
            this.email.setError("Email inválido");
            return false;
        }
        return true;
    }

    public void onLoginButtonClickHandler(View v) {
        if (this.validateForm()) {
            String pass = this.password.getText().toString();
            String email = this.email.getText().toString();

            this.changeActivity(MainActivity.class);
        }
    }

    private void setUpBinds() {
        this.email = findViewById(R.id.emailInput);
        this.password = findViewById(R.id.passwordInput);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setUpBinds();
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .build();*/
    }
}
