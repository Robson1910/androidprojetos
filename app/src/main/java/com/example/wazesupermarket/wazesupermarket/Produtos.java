package com.example.wazesupermarket.wazesupermarket;

import java.util.Random;

public class Produtos {

    private String nome;
    private String valor;
    private String supermercado;
    private String local;
    private String marca;
    private String usuariouid;
    private String emailFeedback;
    Random randomico = new Random();
    int rand = randomico.nextInt(10000);
    private String mensagemFeedback;

    public Produtos() {
    }

    public int getRand () {return rand;}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getSupermercado () { return supermercado;}

    public void setSupermercado(String supermercado) { this.supermercado = supermercado;}

    public String getLocal () { return local;}

    public void setLocal(String local) { this.local = local;}

    public void setMensagemFeedback(String mensagemFeedback) { this.mensagemFeedback = mensagemFeedback; }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }


    public String getUsuariouid() {
        return usuariouid;
    }

    public void setUsuariouid(String usuariouid) {
        this.usuariouid = usuariouid;
    }

    public void setEmailFeedback(String emailFeedback) {
        this.emailFeedback = emailFeedback;
    }
}

