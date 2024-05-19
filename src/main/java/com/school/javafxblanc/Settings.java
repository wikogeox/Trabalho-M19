package com.school.javafxblanc;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Settings {
    private static Stage primaryStage;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        Settings.primaryStage = primaryStage;
    }


    private static ObservableList<Paciente> pacientes = FXCollections.observableArrayList();

    public static ObservableList<Paciente> getPaciente() {
        return pacientes;
    }

    public static void setPacientes(ObservableList<Paciente> pacientes) {
        Settings.pacientes = pacientes;
    }


    private static Paciente pacienteEdit;

    public static Paciente getPacienteEdit() {
        return pacienteEdit;
    }

    public static void setPacienteEdit(Paciente pacienteEdit) {
        Settings.pacienteEdit = pacienteEdit;
    }


    private static ObservableList<Medico> medicos = FXCollections.observableArrayList();

    public static ObservableList<Medico> getMedico() {
        return medicos;
    }

    public static void setMedicos(ObservableList<Medico> medicos) {
        Settings.medicos = medicos;
    }


    private static Medico medicosEdit;

    public static Medico getMedicosEdit() {
        return medicosEdit;
    }

    public static void setMedicosEdit(Medico medicosEdit) {
        Settings.medicosEdit = medicosEdit;
    }




    private static ObservableList<Consulta> consulta = FXCollections.observableArrayList();

    public static ObservableList<Consulta> getConsulta() {
        return consulta;
    }

    public static void setConsulta(ObservableList<Consulta> consulta) {
        Settings.consulta = consulta;
    }


    private static Consulta consultaEdit;

    public static Consulta getConsultaEdit() {
        return consultaEdit;
    }

    public static void setConsultaEdit(Consulta consultaEdit) {
        Settings.consultaEdit = consultaEdit;
    }


    /**
     * Método para limitar o preenchimento de qualquer TextField a um número máximo de carateres
     * @param textField mensagem a tratar
     * @param maxLength tamanho máximo a impor
     */
    public static void checkMaxLength(TextField textField, int maxLength) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (textField.getText().length() > maxLength) {
                    String s = textField.getText().substring(0, maxLength);
                    textField.setText(s);
                }
            }
        });
    }

    /**
     * Método que apenas permiti apenas algarismos nas TextFields
     * @param textField mensagem a tratar
     */
    public static void isNumeric(TextField textField){
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("[0-9+]*")) {
                    textField.setText(newValue.replaceAll("[^0-9+]", ""));
                }
            }
        });
    }

    /**
     * Método que apenas permiti apenas permite letras do alfabeto e a tecla espaço nas TextFields
     * @param textField mensagem a tratar
     */
    public static void isTextOnly(TextField textField){
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if(!newValue.matches("[a-zA-z ]")){
                    textField.setText(newValue.replaceAll("[^a-zA-Z ]", ""));
                }
            }
        });
    }

    public static void isNumericForHora(TextField textField){
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("[0-9:]*")) {
                    textField.setText(newValue.replaceAll("[^0-9:]", ""));
                }
            }
        });
    }
}
