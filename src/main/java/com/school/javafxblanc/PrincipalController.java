package com.school.javafxblanc;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class PrincipalController {
    //Atributos
    @FXML
    private Label labelDia;
    @FXML
    private TimerTask timerTask;
    @FXML
    private Timer timer;
    @FXML
    private BorderPane borderPane;

    public void initialize()throws IOException {

        Parent scene = FXMLLoader.load(getClass().getResource("pacientes.fxml"));
        borderPane.setCenter(scene);

        // Obter a data e hora atuais
        LocalDateTime agora = LocalDateTime.now();

        // Formatar a data e hora conforme desejado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataHoraFormatada = agora.format(formatter);

        // Definir o texto da Label
        labelDia.setText(dataHoraFormatada);

        timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> atualizarHora());
            }
        };
        timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, 0, 1000); // Atualiza a cada segundo
    }
    private void atualizarHora() {
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataHoraFormatada = agora.format(formatter);
        labelDia.setText(dataHoraFormatada);
    }

    public void buttonPacientes(ActionEvent actionEvent) throws IOException {
        Parent scene = FXMLLoader.load(getClass().getResource("pacientes.fxml"));
        borderPane.setCenter(scene);
    }

    public void buttonMedicos(ActionEvent actionEvent) throws IOException {
        Parent scene = FXMLLoader.load(getClass().getResource("medicos.fxml"));
        borderPane.setCenter(scene);
    }

    public void buttonConsultas(ActionEvent actionEvent) throws IOException {
        Parent scene = FXMLLoader.load(getClass().getResource("consultas.fxml"));
        borderPane.setCenter(scene);
    }

    public void buttonAcercaDe(ActionEvent actionEvent) throws IOException {
        // Aquisição do controlo da cena (Scene) about FXML
        Parent scene = FXMLLoader.load(getClass().getResource("acercaDe.fxml"));

        //Nova janela (Stage)
        Stage about = new Stage();

        //Definições da Stage
        about.setTitle("Acerca de");
        about.setResizable(false);

        // Associação da Scene à Stage
        about.setScene(new Scene(scene));

        // Abertura da janela About em modo MODAL, em relação à primaryStage
        about.initOwner(Settings.getPrimaryStage());
        about.initModality(Modality.WINDOW_MODAL);

        //Abertura da janela About
        about.show();
    }

    public void buttonClose(ActionEvent actionEvent) {
        //Criação de uma alert box
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        //Definição do titulo da alert box
        alert.setTitle("Sair da aplicação");
        //Definição da texto que aparece na alert box
        alert.setHeaderText("Deseja mesmo sair da aplicação?");
        //Criação de dois botões "Sim" e "Não"
        ButtonType botaoSim = new ButtonType("Sim");
        ButtonType botaoNao = new ButtonType("Não");
        //Adiciona os dois botões
        alert.getButtonTypes().setAll(botaoSim, botaoNao);

        Optional<ButtonType> choose = alert.showAndWait();
        //Se o botão "Sim" for clicado fecha a aplicação
        if(choose.get() == botaoSim)
            Platform.exit();
    }
}
