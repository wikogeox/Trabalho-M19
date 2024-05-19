package com.school.javafxblanc;

import javafx.scene.control.RadioButton;

public class Pessoa {
    //Atributos
    private String nome;
    private int idade;

    //Metodos construtores
    public Pessoa() {

    }

    public Pessoa(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    //Metodos getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
}
