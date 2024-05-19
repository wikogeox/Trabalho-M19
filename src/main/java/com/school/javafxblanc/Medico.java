package com.school.javafxblanc;

import javafx.scene.control.DatePicker;

public class Medico extends Pessoa{
    //Atributos
    private int ID;
    private Especialidade especialidade;;

    //Metodos construtores
    public Medico() {
        super();
    }

    public Medico(int ID, String nome, int idade, Especialidade especialidade) {
        super(nome, idade);
        this.ID = ID;
        this.especialidade = especialidade;
    }

    public Medico(String nome, int idade, Especialidade especialidade) {
        super(nome, idade);
        this.especialidade = especialidade;
    }

    public Medico(String nome, int idade) {
        super(nome, idade);
    }

    //Metodos getters e setters
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }
}
