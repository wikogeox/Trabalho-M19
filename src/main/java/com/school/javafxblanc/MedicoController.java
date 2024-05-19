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
import java.util.Objects;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class MedicoController implements Initializable {
    //Todas as variaveis
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtIdade;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtEspecialidade;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private TableView<Medico> tableView;
    @FXML
    private TableColumn<Medico,Integer> tableColumnID;
    @FXML
    private TableColumn <Medico,String> tableColumnNome;
    @FXML
    private TableColumn <Medico,Integer> tableColumnIdade;
    @FXML
    private TableColumn<Medico, String> tableColumnEspecialidade;
    @FXML
    private static ObservableList<Medico> Pesquisa = FXCollections.observableArrayList();
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnRemove;

    int lastID = 14;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableColumnID.setCellValueFactory(new PropertyValueFactory<Medico, Integer>("ID"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<Medico, String>("nome"));
        tableColumnIdade.setCellValueFactory(new PropertyValueFactory<Medico, Integer>("idade"));
        tableColumnEspecialidade.setCellValueFactory(new PropertyValueFactory<Medico, String>("especialidade"));
        tableColumnEspecialidade.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEspecialidade().getNome())
        );
        tableView.setItems(MedicoDAO.listMedicos());

        btnEdit.setDisable(true);
        btnRemove.setDisable(true);
    };


    public void btnAddMedico(ActionEvent actionEvent) {
        String nome = txtNome.getText();
        int idade = Integer.parseInt(txtIdade.getText());
        String especialidadeNome = txtEspecialidade.getText();
        int especialidadeId = MedicoDAO.getEspecialidadeIdByName(especialidadeNome);

        if (especialidadeId == -1) {
            // Tratar caso a especialidade não seja encontrada
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Especialidade não encontrada");
            alert.setContentText("A especialidade não existe.");
            alert.show();
            return;
        }

        Especialidade especialidade = new Especialidade(especialidadeNome, especialidadeId);
        Medico newMedico = new Medico(nome, idade, especialidade);

        int id = MedicoDAO.addMedico(newMedico);
        newMedico.setID(id);

        // Adiciona o novo Medico à TableView
        Settings.getMedico().add(newMedico);
        tableView.refresh();
    }


    //Permite editar um medico da lista
    public void editMedico(MouseEvent mouseEvent) {
        int i = tableView.getSelectionModel().getSelectedIndex();

        Medico medico = (Medico) tableView.getItems().get(i);

        txtID.setText(String.valueOf(medico.getID()));
        txtNome.setText(medico.getNome());
        txtIdade.setText(String.valueOf(medico.getIdade()));
        txtEspecialidade.setText(medico.getEspecialidade().getNome());

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
        // Se nenhum item for selecionado vai aparecer uma alert box
        if (tableView.getSelectionModel().getSelectedItem() == null) {
            // Criação de uma alert box
            Alert alert = new Alert(Alert.AlertType.WARNING);
            // Definição do título da alert box
            alert.setTitle("Item não selecionado");
            // Definição do texto que aparece na alert box
            alert.setHeaderText("Selecione um item, por favor");
            // Mostra a alert box
            alert.show();
            return;
        }

        int ID = parseInt(txtID.getText());
        String nome = txtNome.getText();
        int idade = parseInt(txtIdade.getText());
        String especialidadeNome = txtEspecialidade.getText();
        int especialidadeId = MedicoDAO.getEspecialidadeIdByName(especialidadeNome);

        if (especialidadeId == -1) {
            // Tratar caso a especialidade não seja encontrada
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Especialidade não encontrada");
            alert.setContentText("A especialidade não existe.");
            alert.show();
            return;
        }

        Especialidade especialidade = new Especialidade(especialidadeNome, especialidadeId);

        // Os itens editados vão ser colocados na lista
        for (Medico medico : Settings.getMedico()) {
            if (ID == medico.getID()) {
                medico.setNome(nome);
                medico.setIdade(idade);
                medico.setEspecialidade(especialidade);
                break;
            }
        }

        MedicoDAO.updateMedico(new Medico(ID, nome, idade, especialidade));
        // Atualiza a lista
        tableView.refresh();

        // Limpa os campos de texto
        limparCampos();
    }

    public void btnPesquisarMedico(ActionEvent actionEvent) {
        if (txtPesquisar.getText().isEmpty()) {
            tableView.setItems(Settings.getMedico());
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

        Medico medicoPesquisado = MedicoDAO.pesquisaID(idPesquisa);

        if (medicoPesquisado == null) {
            tableView.setItems(FXCollections.observableArrayList());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("Médico não encontrado");
            alert.setContentText("Por favor, tente novamente com um ID diferente.");
            alert.show();
        } else {
            tableView.setItems(FXCollections.observableArrayList(medicoPesquisado));
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
                Medico medicoSelecionado = tableView.getSelectionModel().getSelectedItem();
                if (medicoSelecionado != null) {
                    int id = medicoSelecionado.getID();
                    MedicoDAO.removeMedico(id);
                    Settings.getMedico().remove(medicoSelecionado);
                    tableView.refresh();
                    limparCampos();
                }
            }
        });
    }

    // Método para limpar os campos de texto após a remoção
    private void limparCampos() {
        txtID.setText("");
        txtNome.setText("");
        txtIdade.setText("");
        txtEspecialidade.setText("");
        btnEdit.setDisable(true);
        btnRemove.setDisable(true);
    }

    public void TextFieldNameKeyPressed(KeyEvent keyEvent) {
        Settings.checkMaxLength((TextField) keyEvent.getSource(), 80);
        Settings.isTextOnly((TextField) keyEvent.getSource());
    }

    public void TextFieldAgeKeyPressed(KeyEvent keyEvent) {
        Settings.checkMaxLength((TextField) keyEvent.getSource(), 5);
        Settings.isNumeric((TextField) keyEvent.getSource());
    }

    public void TextFieldEspecialidadeKeyPressed(KeyEvent keyEvent) {
        Settings.checkMaxLength((TextField) keyEvent.getSource(), 30);
    }

    public void TextFieldIdKeyPressed(KeyEvent keyEvent) {
        Settings.checkMaxLength((TextField) keyEvent.getSource(), 5);
        Settings.isNumeric((TextField) keyEvent.getSource());
    }
}
