package com.example.myapplication.model;


import java.io.Serializable;

public class PontuacaoModel implements Serializable {


private Integer id_utilizador;
private Integer nrespostasCertas;

    public PontuacaoModel(Integer id_utilizador, Integer nrespostasCertas) {
        this.id_utilizador = id_utilizador;
        this.nrespostasCertas = nrespostasCertas;
    }

    public PontuacaoModel() {
    }

    public Integer getId_utilizador() {
        return id_utilizador;
    }

    public void setId_utilizador(Integer id_utilizador) {
        this.id_utilizador = id_utilizador;
    }

    public Integer getNrespostasCertas() {
        return nrespostasCertas;
    }

    public void setNrespostasCertas(Integer nrespostasCertas) {
        this.nrespostasCertas = nrespostasCertas;
    }
}
