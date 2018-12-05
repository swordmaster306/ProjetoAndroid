package com.example.alexy.redesocial.Singletons;


import android.widget.Toast;

import com.example.alexy.redesocial.API.RedeSocialService;
import com.example.alexy.redesocial.Activities.MainActivity;
import com.example.alexy.redesocial.models.Token;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {


    public static Retrofit retrofit;
    public static OkHttpClient client;
    public static Token token;
    public static RedeSocialService redesocialapi;
    private static RetrofitSingleton  INSTANCE = new RetrofitSingleton();

    private RetrofitSingleton(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-18-228-227-199.sa-east-1.compute.amazonaws.com:5001/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        redesocialapi = retrofit.create(RedeSocialService.class);
    }

    public static RetrofitSingleton getInstance() {
        return INSTANCE;
    }

    public static void ReSetupRetrofit(){
        client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization","Bearer " +token.accessToken)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-18-228-227-199.sa-east-1.compute.amazonaws.com:5001/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        redesocialapi = retrofit.create(RedeSocialService.class);
    }
}
