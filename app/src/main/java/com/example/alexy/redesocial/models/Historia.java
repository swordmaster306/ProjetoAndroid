package com.example.alexy.redesocial.models;

import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Historia {
    public int deulike;
    public String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int id;
    public int userId;
    public String mensagem;
    public String foto;
    public int likes;
    public int dislikes;
    public int qtdComentarios;
    public String data;

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getDeulike() {
        return deulike;
    }

    public void setDeulike(int deulike) {
        this.deulike = deulike;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public int getQtdComentarios() {
        return qtdComentarios;
    }

    public void setQtdComentarios(int qtdComentarios) {
        this.qtdComentarios = qtdComentarios;
    }



}
