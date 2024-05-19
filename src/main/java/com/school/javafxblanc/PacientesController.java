package com.school.javafxblanc;
//Importações
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;


public class PacientesController implements Initializable {
    //Todas as variaveis
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtIdade;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtNumUtente;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTelefone;
    @FXML
    private TextField txtMorada;
    @FXML
    private TableView <Paciente> tableView;
    @FXML
    private TableColumn <Paciente,Integer> tableColumnID;
    @FXML
    private TableColumn <Paciente,String> tableColumnNome;
    @FXML
    private TableColumn <Paciente,Integer> tableColumnIdade;
    @FXML
    private TableColumn <Paciente,String> tableColumnMorada;
    @FXML
    private TableColumn <Paciente,String> tableColumnTelefone;
    @FXML
    private TableColumn <Paciente,String> tableColumnEmail;
    @FXML
    private TableColumn <Paciente,Integer> tableColumnNumDeUtente;
    @FXML
    private static ObservableList<Paciente> Pesquisa = FXCollections.observableArrayList();
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnRemove;

    int lastID = 5;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableColumnID.setCellValueFactory(new PropertyValueFactory<Paciente, Integer>("ID"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<Paciente, String>("nome"));
        tableColumnIdade.setCellValueFactory(new PropertyValueFactory<Paciente, Integer>("idade"));
        tableColumnMorada.setCellValueFactory(new PropertyValueFactory<Paciente, String>("morada"));
        tableColumnTelefone.setCellValueFactory(new PropertyValueFactory<Paciente, String>("telefone"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<Paciente, String>("email"));
        tableColumnNumDeUtente.setCellValueFactory(new PropertyValueFactory<Paciente, Integer>("numUtente"));

        tableView.setItems(PacientesDAO.listPacientes());

        txtTelefone.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.startsWith("+351")) {
                txtTelefone.setText("+351");
            }
        });

        btnEdit.setDisable(true);
        btnRemove.setDisable(true);
    };


    public void btnAddPaciente(ActionEvent actionEvent) {
        String nome = txtNome.getText();
        int idade = Integer.parseInt(txtIdade.getText());
        String morada = txtMorada.getText();
        String telefone = txtTelefone.getText();
        String email = txtEmail.getText();
        int numUtente = Integer.parseInt(txtNumUtente.getText());
        Paciente newPaciente = new Paciente(nome,idade, morada, telefone, email, numUtente);

        int id = PacientesDAO.addPaciente(newPaciente);
        newPaciente.setID(id);
        //Adiciona o novo Aluno à TableView
        Settings.getPaciente().add(newPaciente);
        limparCampos();
    }

    //Permite editar um paciente da lista
    public void editPaciente(MouseEvent mouseEvent) {
        int i = tableView.getSelectionModel().getSelectedIndex();

        Paciente paciente = (Paciente) tableView.getItems().get(i);

        txtID.setText(String.valueOf(paciente.getID()));
        txtNome.setText(paciente.getNome());
        txtIdade.setText(String.valueOf(paciente.getIdade()));
        txtMorada.setText(paciente.getMorada());
        txtTelefone.setText(paciente.getTelefone());
        txtEmail.setText(paciente.getEmail());
        txtNumUtente.setText(String.valueOf(paciente.getNumUtente()));

        if(tableView.getSelectionModel().getSelectedItem() == null){
            btnEdit.setDisable(true);
            btnRemove.setDisable(true);
        }
        else{
            btnEdit.setDisable(false);
            btnRemove.setDisable(false);
        }
    }

    public void buttonEditar(ActionEvent actionEvent) {
        //Se nenhum item for selecionado vai aparecer uma alert box
        if (tableView.getSelectionModel().getSelectedItem() == null) {
            //Criação de uma alert box
            Alert alert = new Alert(Alert.AlertType.WARNING);
            //Definição do titulo da alert box
            alert.setTitle("Item não selecionado");
            //Definição da texto que aparece na alert box
            alert.setHeaderText("Selecione um item, por favor");
            //Mostra a alert box
            alert.show();
            return;
        }

        int ID = parseInt(txtID.getText());
        String nome = txtNome.getText();
        int idade = parseInt(txtIdade.getText());
        String morada = txtMorada.getText();
        String telefone = txtTelefone.getText();
        String email = txtEmail.getText();
        int numUtente = parseInt(txtNumUtente.getText());

        //Os items editados vão ser colocados na lista
        for (Paciente paciente : Settings.getPaciente()){
            if (ID == paciente.getID()){
                paciente.setNome(nome);
                paciente.setIdade(idade);
                paciente.setMorada(morada);
                paciente.setTelefone(telefone);
                paciente.setEmail(email);
                paciente.setNumUtente(numUtente);
                break;
            }
            limparCampos();
        }
        PacientesDAO.updatePaciente(Settings.getPacienteEdit());
        //Atualiza a lista
        tableView.refresh();
    }

    public void btnPesquisarPaciente(ActionEvent actionEvent) {
        if (txtPesquisar.getText().isEmpty()) {
            tableView.setItems(Settings.getPaciente());
            tableView.refresh();
            return;
        }

        int idPesquisa;
        try {
            idPesquisa = Integer.parseInt(txtPesquisar.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erro");
            alert.setHeaderText("ID inválido");
            alert.setContentText("Por favor, insira um número válido para o ID.");
            alert.show();
            return;
        }

        Paciente pacientePesquisado = PacientesDAO.pesquisaID(idPesquisa);

        if (pacientePesquisado == null) {
            tableView.setItems(FXCollections.observableArrayList());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("Paciente não encontrado");
            alert.setContentText("Por favor, tente novamente com um ID diferente.");
            alert.show();
        } else {
            tableView.setItems(FXCollections.observableArrayList(pacientePesquisado));
        }
        txtPesquisar.setText("");
    }


    public void ButtonRemove(ActionEvent actionEvent) {
        // Criação de uma alert box
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        // Definição do título da alert box
        alert.setTitle("Eliminar médico");
        // Definição do texto que aparece na alert box
        alert.setHeaderText("Deseja mesmo eliminar o médico?");
        // Criação de dois botões "Sim" e "Não"
        ButtonType botaoSim = new ButtonType("Sim");
        ButtonType botaoNao = new ButtonType("Não");
        // Adiciona os dois botões
        alert.getButtonTypes().setAll(botaoSim, botaoNao);
        // Se o botão "Sim" for clicado, remove um médico da lista
        alert.showAndWait().ifPresent(resposta -> {
            if (resposta == botaoSim) {
                Paciente pacienteSelecionado = tableView.getSelectionModel().getSelectedItem();
                if (pacienteSelecionado != null) {
                    int id = pacienteSelecionado.getID();
                    PacientesDAO.removePaciente(id);
                    Settings.getPaciente().remove(pacienteSelecionado);
                    tableView.refresh();
                    limparCampos();
                }
            }
        });
    }

    // Método para limpar os campos de texto
    private void limparCampos() {
        txtID.setText("");
        txtNome.setText("");
        txtIdade.setText("");
        txtMorada.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtNumUtente.setText("");
        btnEdit.setDisable(true);
        btnRemove.setDisable(true);
    }

    public void TextFieldNameKeyPressed(KeyEvent keyEvent) {
        Settings.checkMaxLength((TextField) keyEvent.getSource(), 80);
        Settings.isTextOnly((TextField) keyEvent.getSource());
    }

    public void TextFieldEmailKeyPressed(KeyEvent keyEvent) {
        Settings.checkMaxLength((TextField) keyEvent.getSource(), 255);
    }

    public void TextFieldMoradaKeyPressed(KeyEvent keyEvent) {
        Settings.checkMaxLength((TextField) keyEvent.getSource(), 120);
    }

    public void TextFieldPhoneKeyPressed(KeyEvent keyEvent) {
        Settings.checkMaxLength((TextField) keyEvent.getSource(), 13);
        Settings.isNumeric((TextField) keyEvent.getSource());
    }

    public void TextFieldAgeKeyPressed(KeyEvent keyEvent) {
        Settings.checkMaxLength((TextField) keyEvent.getSource(), 5);
        Settings.isNumeric((TextField) keyEvent.getSource());
    }

    public void TextFieldNumUtenteKeyPressed(KeyEvent keyEvent) {
        Settings.checkMaxLength((TextField) keyEvent.getSource(), 9);
        Settings.isNumeric((TextField) keyEvent.getSource());
    }

    public void TextFieldIdKeyPressed(KeyEvent keyEvent) {
        Settings.checkMaxLength((TextField) keyEvent.getSource(), 5);
        Settings.isNumeric((TextField) keyEvent.getSource());
    }
}
