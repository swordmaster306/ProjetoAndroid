package com.example.alexy.redesocial.API;

import com.example.alexy.redesocial.models.*;



import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface RedeSocialService {


    @POST("/api/auth/cadastro")
    Call<Void> cadastrar(@Body User usuario);

    @POST("/api/auth/autenticar")
    Call<Token> autenticar(@Body User usuario);

    @GET("api/feed/feedprincipal/{id}")
    Call<List<Historia>> getFeedPrincipal(@Path("id") int id);

    @GET("api/feed/meusamigos/{id}")
    Call<List<User>> getMeusAmigos(@Path("id") int id);
}
