package com.example.myapplication.api;


import com.example.myapplication.model.UserModel;

public interface API {

    UserModel getUserByUsernamePassword(String username, String password);

    void addUser(String username, String email, String genero, String password, String dataNasc, String ntelemovel,String iconid);

    void getPerguntabyIdCidade( Integer idCidade);

    void getPontuacaoByIdCidadeIdUtilizador( Integer idCidade,   Integer idUtilizador );

}
