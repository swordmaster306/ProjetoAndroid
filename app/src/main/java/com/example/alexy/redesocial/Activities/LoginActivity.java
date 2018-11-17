package com.example.alexy.redesocial.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alexy.redesocial.R;
import com.example.alexy.redesocial.Singletons.RetrofitSingleton;
import com.example.alexy.redesocial.models.Token;
import com.example.alexy.redesocial.models.User;
import com.example.alexy.redesocial.utils.RegExValidation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            User user = new User();
            user.setSenha(pass);
            user.setEmail(email);
            Call<Token> autenticacao = RetrofitSingleton.getInstance().redesocialapi.autenticar(user);
            Callback<Token> autenticacaoCallback = new Callback<Token>() {
                @Override
                public void onResponse(Call<Token> call, Response<Token> response) {
                    Token token = response.body();
                    RetrofitSingleton.getInstance().token = token;
                    if(token.authenticated) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "Usuário não cadastrado", Toast.LENGTH_SHORT).show();

                    }
                }
                @Override
                public void onFailure(Call<Token> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Falha na requisição", Toast.LENGTH_SHORT).show();

                }
            };

            autenticacao.enqueue(autenticacaoCallback);
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
