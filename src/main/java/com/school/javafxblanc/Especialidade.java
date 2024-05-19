package com.school.javafxblanc;

public class Especialidade {
    //Atributos
    private String nome;
    private int ID;

    //Metodos construtores
    public Especialidade(String nome, int ID) {
        this.nome = nome;
        this.ID = ID;
    }

    public Especialidade(String nome) {
        this.nome = nome;
    }

    //Metodos getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
