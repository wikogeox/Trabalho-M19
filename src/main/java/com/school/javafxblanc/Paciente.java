package com.school.javafxblanc;

public class Paciente extends Pessoa {
    //Atributos
    private String morada;
    private String telefone;
    private String email;
    private int numUtente;
    private int ID;

    //Metodos construtores
    public Paciente() {
        super();
    }

    public Paciente(int ID, String nome, int idade, String morada, String telefone, String email, int numUtente) {
        super(nome, idade);
        this.morada = morada;
        this.telefone = telefone;
        this. email = email;
        this.numUtente = numUtente;
        this.ID = ID;
    }

    public Paciente(String nome, int idade, String morada, String telefone, String email, int numUtente) {
        super(nome, idade);
        this.morada = morada;
        this.telefone = telefone;
        this. email = email;
        this.numUtente = numUtente;
    }

    public Paciente(String nome, int idade) {
        super(nome, idade);
    }

    //Metodos getters e setters
    public int getNumUtente() {
        return numUtente;
    }

    public void setNumUtente(int numUtente) {
        this.numUtente = numUtente;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
