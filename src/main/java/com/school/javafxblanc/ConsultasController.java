package com.school.javafxblanc;

import javafx.beans.property.SimpleStringProperty;
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

public class ConsultasController implements Initializable {
    //Todas as variaveis
    @FXML
    private TableView<Consulta> tableView;
    @FXML
    private TableColumn<Consulta, Integer> tableColumnID;
    @FXML
    private TableColumn<Consulta, String> tableColumnMedico;
    @FXML
    private TableColumn <Consulta, String> tableColumnPaciente;
    @FXML
    private TableColumn <Consulta, LocalDate> tableColumnData;
    @FXML
    private TableColumn<Consulta, Time> tableColumnHora;
    @FXML
    private static ObservableList<Medico> Pesquisa = FXCollections.observableArrayList();
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnRemove;
    @FXML
    private ComboBox <String> comboBoxMedicos;
    @FXML
    private ComboBox <String> comboBoxPacientes;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField txtHora;
    @FXML
    private TextField txtPesquisar;

    int lastID = 14;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableColumnID.setCellValueFactory(new PropertyValueFactory<Consulta, Integer>("IDM"));
        tableColumnMedico.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMedico().getNome()));
        tableColumnPaciente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPaciente().getNome()));
        tableColumnData.setCellValueFactory(new PropertyValueFactory<Consulta, LocalDate>("dataConsulta"));
        tableColumnHora.setCellValueFactory(new PropertyValueFactory<Consulta, Time>("horaConsulta"));

        tableView.setItems(ConsultaDAO.lisConsulta());

        inserirMedicoNaComboBox();
        inserirPacienteNaComboBox();

        btnEdit.setDisable(true);
        btnRemove.setDisable(true);
    };


    public void btnAddConsulta(ActionEvent actionEvent) {
        String nomeMedico = comboBoxMedicos.getValue();
        String nomePaciente = comboBoxPacientes.getValue();
        LocalDate dataConsulta = datePicker.getValue();
        Time horaConsulta = Time.valueOf(txtHora.getText());

        if (nomeMedico == null || nomePaciente == null || dataConsulta == null || horaConsulta == null) {
            // Se algum campo estiver vazio, exiba uma mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Campos vazios");
            alert.setContentText("Por favor, preencha todos os campos.");
            alert.show();
            return;
        }

        // Cria uma nova consulta com os nomes do médico e do paciente selecionados e os outros dados fornecidos
        Consulta newConsulta = new Consulta(new Medico(nomeMedico, 0), new Paciente(nomePaciente, 0), dataConsulta, horaConsulta);

        // Adiciona a consulta ao banco de dados
        ConsultaDAO.addConsulta(newConsulta);
        tableView.setItems(ConsultaDAO.lisConsulta());
    }


    //Permite editar uma consulta da lista
    public void editConsulta(MouseEvent mouseEvent) {
        int i = tableView.getSelectionModel().getSelectedIndex();

        Consulta consulta = (Consulta) tableView.getItems().get(i);

        comboBoxMedicos.setValue(consulta.getMedico().getNome());
        comboBoxPacientes.setValue(consulta.getPaciente().getNome());
        datePicker.setValue(consulta.getDataConsulta());
        txtHora.setText(String.valueOf(consulta.getHoraConsulta()));

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
        Consulta consultaSelecionada = tableView.getSelectionModel().getSelectedItem();

        if (consultaSelecionada == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Item não selecionado");
            alert.setHeaderText("Selecione um item, por favor");
            alert.show();
            return;
        }

        String nomeMedico = comboBoxMedicos.getValue();
        String nomePaciente = comboBoxPacientes.getValue();
        LocalDate dataConsulta = datePicker.getValue();
        Time horaConsulta = Time.valueOf(txtHora.getText());

        if (nomeMedico == null || nomePaciente == null || dataConsulta == null || horaConsulta == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Campos vazios");
            alert.setContentText("Por favor, preencha todos os campos.");
            alert.show();
            return;
        }

        consultaSelecionada.getMedico().setNome(nomeMedico);
        consultaSelecionada.getPaciente().setNome(nomePaciente);
        consultaSelecionada.setDataConsulta(dataConsulta);
        consultaSelecionada.setHoraConsulta(horaConsulta);

        ConsultaDAO.updateConsulta(consultaSelecionada);

        tableView.setItems(ConsultaDAO.lisConsulta());
        tableView.refresh();
        limparCampos();
    }


    public void ButtonRemove(ActionEvent actionEvent) {
        // Criação de uma alert box
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        // Definição do título da alert box
        alert.setTitle("Eliminar consulta");
        // Definição do texto que aparece na alert box
        alert.setHeaderText("Deseja mesmo eliminar a consulta?");
        // Criação de dois botões "Sim" e "Não"
        ButtonType botaoSim = new ButtonType("Sim");
        ButtonType botaoNao = new ButtonType("Não");
        // Adiciona os dois botões
        alert.getButtonTypes().setAll(botaoSim, botaoNao);
        // Se o botão "Sim" for clicado, remove uma consulta da lista
        alert.showAndWait().ifPresent(resposta -> {
            if (resposta == botaoSim) {
                Consulta consultaSelecionada = tableView.getSelectionModel().getSelectedItem();
                if (consultaSelecionada != null) {
                    int id = consultaSelecionada.getIDM();
                    ConsultaDAO.removeConsulta(id);
                    Settings.getConsulta().remove(consultaSelecionada);
                    tableView.refresh();
                    limparCampos();
                }
            }
        });
    }

    // Método para limpar os campos de texto após a remoção
    private void limparCampos() {
        comboBoxMedicos.getSelectionModel().clearSelection();
        comboBoxPacientes.getSelectionModel().clearSelection();
        comboBoxMedicos.setValue(null);
        comboBoxPacientes.setValue(null);
        datePicker.setValue(null);
        txtHora.setText("");

        btnEdit.setDisable(true);
        btnRemove.setDisable(true);

        // Limpa o editor para forçar a exibição do prompt text
        comboBoxMedicos.setValue(null);
        comboBoxMedicos.getEditor().clear();
        comboBoxMedicos.setPromptText("Médicos");

        comboBoxPacientes.setValue(null);
        comboBoxPacientes.getEditor().clear();
        comboBoxPacientes.setPromptText("Pacientes");
    }

    //Método que permite adicionar o nome dos médicos na ComboBox
    private void inserirMedicoNaComboBox() {
        try (Connection connection = ConnectionDB.openDB();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT nome FROM medico")) {

            while (resultSet.next()) {
                String nomeMedico = resultSet.getString("nome");
                comboBoxMedicos.getItems().add(nomeMedico);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Método que permite adicionar o nome dos pacientes na ComboBox
    private void inserirPacienteNaComboBox() {
        try (Connection connection = ConnectionDB.openDB();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT nome FROM paciente")) {

            while (resultSet.next()) {
                String nomePaciente = resultSet.getString("nome");
                comboBoxPacientes.getItems().add(nomePaciente);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnPesquisarConsulta(ActionEvent actionEvent) {
        if (txtPesquisar.getText().isEmpty()) {
            tableView.setItems(Settings.getConsulta());
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

        Consulta consultaPesquisada = ConsultaDAO.pesquisaID(idPesquisa);

        if (consultaPesquisada == null) {
            tableView.setItems(FXCollections.observableArrayList());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("Consulta não encontrada");
            alert.setContentText("Por favor, tente novamente com um ID diferente.");
            alert.show();
        } else {
            tableView.setItems(FXCollections.observableArrayList(consultaPesquisada));
        }
        txtPesquisar.setText("");
    }

    public void TextFieldHoraKeyPressed(KeyEvent keyEvent) {
        Settings.checkMaxLength((TextField) keyEvent.getSource(), 8);
        Settings.isNumericForHora((TextField) keyEvent.getSource());
    }

    public void TextFieldIdKeyPressed(KeyEvent keyEvent) {
        Settings.checkMaxLength((TextField) keyEvent.getSource(), 5);
        Settings.isNumeric((TextField) keyEvent.getSource());
    }
}
