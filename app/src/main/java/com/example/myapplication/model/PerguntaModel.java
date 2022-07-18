package com.example.myapplication.model;


import java.io.Serializable;
import java.util.List;

public class PerguntaModel implements Serializable {

    private Integer id_Cidade;
    private Integer id_Pergunta;
    private String descricao;
    private List<OpcaoModel> opcaoModels;

    public PerguntaModel() {
    }

    public PerguntaModel(Integer id_Cidade, Integer id_Pergunta, String descricao, List<OpcaoModel> opcaoModels) {
        this.id_Cidade = id_Cidade;
        this.id_Pergunta = id_Pergunta;
        this.descricao = descricao;
        this.opcaoModels = opcaoModels;
    }

    public Integer getId_Cidade() {
        return id_Cidade;
    }

    public void setId_Cidade(Integer id_Cidade) {
        this.id_Cidade = id_Cidade;
    }

    public Integer getId_Pergunta() {
        return id_Pergunta;
    }

    public void setId_Pergunta(Integer id_Pergunta) {
        this.id_Pergunta = id_Pergunta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<OpcaoModel> getOpcaoModels() {
        return opcaoModels;
    }

    public void setOpcoes(List<OpcaoModel> opcaoModels) {
        this.opcaoModels = opcaoModels;
    }
}
