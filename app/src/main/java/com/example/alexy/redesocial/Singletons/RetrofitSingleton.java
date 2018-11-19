package com.example.alexy.redesocial.Singletons;


import com.example.alexy.redesocial.API.RedeSocialService;
import com.example.alexy.redesocial.models.Token;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {


    public Retrofit retrofit;
    public Token token;
    public RedeSocialService redesocialapi;
    private static final RetrofitSingleton INSTANCE = new RetrofitSingleton();

    private RetrofitSingleton(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://189.121.26.170:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        redesocialapi = retrofit.create(RedeSocialService.class);
    }

    public static RetrofitSingleton getInstance() {
        return INSTANCE;
    }
}