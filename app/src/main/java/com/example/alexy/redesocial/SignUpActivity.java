package com.example.alexy.redesocial;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.widget.EditText;

import com.example.alexy.redesocial.utils.RegExValidation;

public class SignUpActivity extends Activity {

    private EditText name;
    private EditText email;
    private EditText password;

    public Boolean validateForm() {
        String name = this.name.getText().toString();
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            if (name.isEmpty()) this.name.setError("Campo obrigatório");
            if (email.isEmpty()) this.email.setError("Campo obrigatório");
            if (password.isEmpty()) this.password.setError("Campo obrigatório");
            return false;
        }

        if (!email.matches(RegExValidation.email)) {
            this.email.setError("Email inválido");
            return false;
        }
        return true;
    }

    public void registerButtonClickHandler() {
        if (this.validateForm()) {
            // chamar servico de cadastro
            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.name = findViewById(R.id.nameInput);
        this.email = findViewById(R.id.emailInput);
        this.password = findViewById(R.id.passwordInput);
    }

}
