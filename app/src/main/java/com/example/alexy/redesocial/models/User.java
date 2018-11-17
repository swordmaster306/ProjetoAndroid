package com.example.alexy.redesocial.models;

public class User {
    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }

    public Integer getQtdAmigos() {
        return QtdAmigos;
    }

    public void setQtdAmigos(Integer qtdAmigos) {
        QtdAmigos = qtdAmigos;
    }

    public Integer getQtdHistoria() {
        return QtdHistoria;
    }

    public void setQtdHistoria(Integer qtdHistoria) {
        QtdHistoria = qtdHistoria;
    }

    public String getFotoPerfil() {
        return FotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        FotoPerfil = fotoPerfil;
    }

    public Integer UserId;
    public String Nome;
    public String Email;
    public String Senha;
    public Integer QtdAmigos;
    public Integer QtdHistoria;
    public String FotoPerfil;
}
