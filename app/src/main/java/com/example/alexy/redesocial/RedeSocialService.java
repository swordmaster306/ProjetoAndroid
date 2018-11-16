package com.example.alexy.redesocial;

import com.example.alexy.redesocial.models.User;

import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RedeSocialService {

    @POST("api/autenticar")
    Call<void> auth(@Body User user);
}
