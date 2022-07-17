package com.example.myapplication.model;

import android.widget.EditText;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

public class UserModel  implements Serializable {

    private Integer id_utilizador;

    private String username;

    private String password;

    private String email;

    private LocalDate dataNascimento;

    private Integer id_genero;

    private Integer nTelemovel;

    private Integer id_icon;


    public UserModel(Integer id_utilizador, String username, String password, String email, LocalDate dataNascimento, Integer id_genero, Integer nTelemovel, Integer id_icon) {
        this.id_utilizador = id_utilizador;
        this.username = username;
        this.password = password;
        this.email = email;
        this.dataNascimento = dataNascimento;
        if (id_genero!= null)
            this.id_genero = id_genero;

        this.nTelemovel = nTelemovel;
        this.id_icon = id_icon;
    }

    public UserModel() {
    }

    public Integer getId_utilizador() {
        return id_utilizador;
    }

    public void setId_utilizador(Integer id_utilizador) {
        this.id_utilizador = id_utilizador;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Integer getId_genero() {
        return id_genero;
    }

    public void setId_genero(Integer id_genero) {
        this.id_genero = id_genero;
    }

    public Integer getnTelemovel() {
        return nTelemovel;
    }

    public void setnTelemovel(Integer nTelemovel) {
        this.nTelemovel = nTelemovel;
    }

    public Integer getId_icon() {
        return id_icon;
    }

    public void setId_icon(Integer id_icon) {
        this.id_icon = id_icon;
    }
}
