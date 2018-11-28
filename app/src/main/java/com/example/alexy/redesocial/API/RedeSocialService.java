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



    @GET("api/feed/perfil/{id}")
    Call<User> getPerfil(@Path("id") int id);

    @GET("api/feed/buscaramigos/{nome}/{id}")
    Call<List<User>> buscarAmigos(@Path("nome") String nome,@Path("id") int id);

    @GET("api/feed/historias/{id}")
    Call<List<Historia>> getPerfilHistorias(@Path("id") int id);

    @POST("api/addremove/criarhistoria")
    Call<Void> criarHistoria(@Body Historia historia);

    @POST("api/addremove/likedislike")
    Call<Void> darlikedislike(@Body LikeDislike ld);

    @HTTP(method = "DELETE", path = "api/addremove/deletarhistoria", hasBody = true)
    Call<Void> deletarHistoria(@Body Historia historia);



    @GET("api/feed/amizadespendentes/{id}")
    Call<List<User>> getAmizadesPendentes(@Path("id") int id);

    @GET("api/feed/statusAmizade/{id1}/{id2}")
    Call<Amizade> getstatusAmizade(@Path("id1") int id1,@Path("id2") int id2);

    @POST("api/addremove/aceitaramizade/{id1}/{id2}")
    Call<Void> aceitaramizade(@Path("id1") int id1,@Path("id2") int id2);

    @POST("api/addremove/rejeitaramizade/{id1}/{id2}")
    Call<Void> rejeitaramizade(@Path("id1") int id1,@Path("id2") int id2);

    @POST("api/addremove/adicionaramigo/{id1}/{id2}")
    Call<Void> adicionaramigo(@Path("id1") int id1,@Path("id2") int id2);

    @DELETE("api/addremove/deletaramigo/{id1}/{id2}")
    Call<Void> deletaramigo(@Path("id1") int id1,@Path("id2") int id2);


}
