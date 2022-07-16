package com.example.myapplication.model;

import java.io.Serializable;

public class OpcaoModel implements Serializable {
    private Integer id_Opcao;
    private Integer id_Pegunta;
    private String descricao;
    private Boolean opcaoCorreta;

    public OpcaoModel() {
    }

    public OpcaoModel(Integer id_Opcao, Integer id_Pegunta, String descricao, Boolean opcaoCorreta) {
        this.id_Opcao = id_Opcao;
        this.id_Pegunta = id_Pegunta;
        this.descricao = descricao;
        this.opcaoCorreta = opcaoCorreta;
    }

    public Integer getId_Opcao() {
        return id_Opcao;
    }

    public void setId_Opcao(Integer id_Opcao) {
        this.id_Opcao = id_Opcao;
    }

    public Integer getId_Pegunta() {
        return id_Pegunta;
    }

    public void setId_Pegunta(Integer id_Pegunta) {
        this.id_Pegunta = id_Pegunta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getOpcaoCorreta() {
        return opcaoCorreta;
    }

    public void setOpcaoCorreta(Boolean opcaoCorreta) {
        this.opcaoCorreta = opcaoCorreta;
    }
}
