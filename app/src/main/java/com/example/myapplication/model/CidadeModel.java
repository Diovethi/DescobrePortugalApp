package com.example.myapplication.model;

public class CidadeModel {

    private Integer id_Cidade;
    private String nome;
    private String descricao;
    private Integer id_Regiao;

    public CidadeModel() {
    }

    public CidadeModel(Integer id_Cidade, String nome, String descricao, Integer id_Regiao) {
        this.id_Cidade = id_Cidade;
        this.nome = nome;
        this.descricao = descricao;
        this.id_Regiao = id_Regiao;
    }

    public Integer getId_Cidade() {
        return id_Cidade;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getId_Regiao() {
        return id_Regiao;
    }

    public void setId_Cidade(Integer id_Cidade) {
        this.id_Cidade = id_Cidade;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setId_Regiao(Integer id_Regiao) {
        this.id_Regiao = id_Regiao;
    }
}
