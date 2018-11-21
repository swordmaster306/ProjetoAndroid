package com.example.alexy.redesocial.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alexy.redesocial.R;
import com.example.alexy.redesocial.Singletons.RetrofitSingleton;
import com.example.alexy.redesocial.models.Historia;
import com.example.alexy.redesocial.utils.ConversorBase64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PublishActivity extends AppCompatActivity {

    private ImageButton btnCamera;
    private ImageView imageView;
    // private TextView textView;

    private String fotoB64;
    private String Mensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);

        btnCamera = (ImageButton) findViewById(R.id.btnCamera);
        imageView = (ImageView) findViewById(R.id.imageView);
        //textView = (TextView) findViewById(R.id.textView);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int REQUEST_IMAGE_CAPTURE = 51;
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int REQUEST_IMAGE_CAPTURE = 51;
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            fotoB64 = ConversorBase64.bitmaptob64(imageBitmap);
        }
    }


    //Chamar esse método ao confirmar a postagem da história
    public void criarHistoria(){
        Historia hist = new Historia();
        hist.userId = RetrofitSingleton.getInstance().token.userid;
        //Daniel, substitua essa mensagem cravada pela mensagem que o usuario vai digitar
        hist.mensagem = "Test de historia via android";
        if(fotoB64 != null)
            hist.foto = fotoB64;

        Call<Void> criarhistoria = RetrofitSingleton.getInstance().redesocialapi.criarHistoria(hist);
        Callback<Void> callbackCriarHistoria =  new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(PublishActivity.this, "História criada com sucesso", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(PublishActivity.this, "Falha na criação da história", Toast.LENGTH_SHORT).show();
            }
        };
        criarhistoria.enqueue(callbackCriarHistoria);
    }
}
