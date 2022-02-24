package com.alphazer0.pesquisaexemplo.model;
//==================================================================================================

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//==================================================================================================
@Entity
public class Cliente implements Serializable {
//==================================================================================================
    //VARIAVEIS

    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    //DADOS PESSOAIS
    private String nomeCompleto;
    //CONTATO
    private String celular1;


    //ENDERECO
    private String rua;
//==================================================================================================
    //METODOS DE SOBRECARGA
    public Cliente(){}

    @Ignore
    public Cliente(int id, String nomeCompleto, String celular1,  String rua) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.celular1 = celular1;
        this.rua = rua;
    }

//==================================================================================================
    //SOBREESCREVENDO O TOSTRING
    @NonNull
    @Override
    public String toString(){
        return nomeCompleto;
    }
//==================================================================================================
    public boolean temIdValido(){
        return id >0;
    }
//==================================================================================================
    //GETTERS AND SETTERS

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCelular1() {
        return celular1;
    }

    public void setCelular1(String celular1) {
        this.celular1 = celular1;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    //==================================================================================================
}
