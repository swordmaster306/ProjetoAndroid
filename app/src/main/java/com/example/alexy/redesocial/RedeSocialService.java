package com.example.alexy.redesocial;

import com.example.alexy.redesocial.models.Token;
import com.example.alexy.redesocial.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface RedeSocialService {


    @POST("/api/auth/cadastro")
    Call<Void> cadastrar(@Body User usuario);

    @POST("/api/auth/autenticar")
    Call<Token> autenticar(@Body User usuario);
}
