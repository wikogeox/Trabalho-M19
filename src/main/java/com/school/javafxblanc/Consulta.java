package com.school.javafxblanc;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

public class Consulta {
    //Atributos
    private int IDM;
    private LocalDate dataConsulta;
    private Time horaConsulta;
    private Paciente paciente;
    private Medico medico;

    //Metodo construtor
    public Consulta(int IDM) {
        this.IDM = IDM;
    }

    //Agregação - Metodo construtor
    public Consulta(int IDM, Medico medico, Paciente paciente, LocalDate dataConsulta, Time horaConsulta) {
        this.IDM = IDM;
        this.dataConsulta = dataConsulta;
        this.horaConsulta = horaConsulta;
        this.paciente = paciente;
        this.medico = medico;
    }

    public Consulta(Medico medico, Paciente paciente, LocalDate dataConsulta, Time horaConsulta) {
        this.dataConsulta = dataConsulta;
        this.horaConsulta = horaConsulta;
        this.paciente = paciente;
        this.medico = medico;
    }

    //Metodos getters e setters
    public LocalDate getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(LocalDate dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public Time getHoraConsulta() {
        return horaConsulta;
    }

    public void setHoraConsulta(Time horaConsulta) {
        this.horaConsulta = horaConsulta;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public int getIDM() {
        return IDM;
    }

    public void setIDM(int IDM) {
        this.IDM = IDM;
    }
}
