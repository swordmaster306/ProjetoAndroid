package com.example.alexy.redesocial.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alexy.redesocial.R;
import com.example.alexy.redesocial.Singletons.RetrofitSingleton;
import com.example.alexy.redesocial.models.User;
import com.example.alexy.redesocial.utils.ConversorBase64;
import com.example.alexy.redesocial.utils.RegExValidation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends Activity {

    private EditText name;
    private EditText email;
    private EditText password;
    private String fotoPerfil;

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

    public void registerButtonClickHandler(View v) {
        boolean valida = false;
        if (this.validateForm()) {
            valida = true;
        }else{
            Toast.makeText(SignUpActivity.this, "Dados invalidos", Toast.LENGTH_SHORT).show();
        }

        if(valida){
            cadastrar();
        }
    }


    public void capturarFotoClickHandler(View v){
        int REQUEST_IMAGE_CAPTURE = 51;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int REQUEST_IMAGE_CAPTURE = 51;
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            fotoPerfil = ConversorBase64.bitmaptob64(imageBitmap);
        }
    }


    public void cadastrar(){
        User user = new User();
        user.setEmail(email.getText().toString());
        user.setNome(name.getText().toString());
        user.setSenha(password.getText().toString());
        if(fotoPerfil != null){
            user.setFotoPerfil(fotoPerfil);
        }
        Call<Void> cadastro = RetrofitSingleton.getInstance().redesocialapi.cadastrar(user);
        Callback<Void> callbackcadastro = new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(SignUpActivity.this, "Success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        };
        cadastro.enqueue(callbackcadastro);
    }

}
