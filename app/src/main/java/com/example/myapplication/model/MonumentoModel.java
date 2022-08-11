package com.example.myapplication.model;

import java.io.Serializable;
import java.time.LocalDate;

public class MonumentoModel implements Serializable {

    private Integer id_Monumento;
    private String descricao;
    private String nome;
    private LocalDate dataConstrucao;
    private Integer id_Cidade;
    private Integer id_TipoMonumento;

    public MonumentoModel() {
        super();
    }

    public MonumentoModel(Integer id_Monumento, String descricao, String nome, LocalDate dataConstrucao, Integer id_Cidade, Integer id_TipoMonumento) {
        super();
        this.id_Monumento = id_Monumento;
        this.descricao = descricao;
        this.nome = nome;
        this.dataConstrucao = dataConstrucao;
        this.id_Cidade = id_Cidade;
        this.id_TipoMonumento = id_TipoMonumento;
    }

    public Integer getId_Monumento() {
        return id_Monumento;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataConstrucao() {
        return dataConstrucao;
    }

    public Integer getId_Cidade() {
        return id_Cidade;
    }

    public Integer getId_TipoMonumento() {
        return id_TipoMonumento;
    }

    public void setId_Monumento(Integer id_Monumento) {
        this.id_Monumento = id_Monumento;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDataConstrucao(LocalDate dataConstrucao) {
        this.dataConstrucao = dataConstrucao;
    }

    public void setId_Cidade(Integer id_Cidade) {
        this.id_Cidade = id_Cidade;
    }

    public void setId_TipoMonumento(Integer id_TipoMonumento) {
        this.id_TipoMonumento = id_TipoMonumento;
    }
}
