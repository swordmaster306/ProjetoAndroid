package com.example.alexy.redesocial.models;

public class User {


    public Integer userId;
    public String nome;
    public String email;
    public String senha;
    public Integer qtdAmigos;
    public Integer qtdHistorias;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getQtdAmigos() {
        return qtdAmigos;
    }

    public void setQtdAmigos(Integer qtdAmigos) {
        this.qtdAmigos = qtdAmigos;
    }

    public Integer getQtdHistorias() {
        return qtdHistorias;
    }

    public void setQtdHistorias(Integer qtdHistorias) {
        this.qtdHistorias = qtdHistorias;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String fotoPerfil;
}
