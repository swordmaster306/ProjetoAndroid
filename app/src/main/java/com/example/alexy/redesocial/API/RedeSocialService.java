package com.example.alexy.redesocial.API;

import com.example.alexy.redesocial.models.*;



import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
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

    @GET("api/feed/buscaramigos/{nome}")
    Call<List<User>> buscarAmigos(@Path("nome") String nome);

    @GET("api/feed/historias/{id}")
    Call<List<Historia>> getPerfilHistorias(@Path("id") int id);

    @POST("api/addremove/criarhistoria")
    Call<Void> criarHistoria(@Body Historia historia);

    @POST("api/addremove/likedislike")
    Call<Void> darlikedislike(@Body LikeDislike ld);

    @HTTP(method = "DELETE", path = "api/addremove/deletarhistoria", hasBody = true)
    Call<Void> deletarHistoria(@Body Historia historia);
}
