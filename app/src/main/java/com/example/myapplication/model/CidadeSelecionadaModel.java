package com.example.myapplication.model;

import java.io.Serializable;

public class CidadeSelecionadaModel implements Serializable {

    private Integer id_Cidade;
    private Integer id_Regiao;
    private Integer nrespostaCorreta;


    public Integer getId_Cidade() {
        return id_Cidade;
    }

    public void setId_Cidade(Integer id_Cidade) {
        this.id_Cidade = id_Cidade;
    }

    public Integer getId_Regiao() {
        return id_Regiao;
    }

    public void setId_Regiao(Integer id_Regiao) {
        this.id_Regiao = id_Regiao;
    }

    public Integer getNrespostaCorreta() {
        return nrespostaCorreta;
    }

    public void setNrespostaCorreta(Integer nrespostaCorreta) {
        this.nrespostaCorreta = nrespostaCorreta;
    }
}
