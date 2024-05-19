package com.school.javafxblanc;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class SampleController {

    //Botão Começar - Quando clicado abre uma nova janela com o programa principal
    public void buttonComecar(ActionEvent actionEvent) throws IOException {
        Parent scene = FXMLLoader.load(getClass().getResource("principal.fxml"));

        //Nova janela (Stage)
        Stage about = new Stage();

        //Definições da Stage
        about.setTitle("Hospital");

        // Associação da Scene à Stage
        about.setScene(new Scene(scene));

        // Abertura da janela About em modo MODAL, em relação à primaryStage
        about.initOwner(Settings.getPrimaryStage());
        about.initModality(Modality.WINDOW_MODAL);

        //Abertura da janela About
        about.show();
    }

    //Botão fechar - Quando clicado fecha o trabalho
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
