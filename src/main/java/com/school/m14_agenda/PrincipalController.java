package com.school.m14_agenda;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PrincipalController {
    @FXML
    private BorderPane borderPane;

    /**
     * Fecha a aplicação, caso o botão clicado seja o Sim
     * @param actionEvent evento disparado
     */
    public void menuExitApplication(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sair da aplicação");
        alert.setHeaderText("Deseja mesmo sair da apliação?");
        // Adiciona botões personalizados em português
        ButtonType botaoSim = new ButtonType("Sim");
        ButtonType botaoNao = new ButtonType("Não");
        alert.getButtonTypes().setAll(botaoSim, botaoNao);

        alert.showAndWait().ifPresent(response ->{
            if(response == botaoSim){
                Settings.getPrimaryStage().close();
            }
        });
    }

    /**
     * Abre a janela Acerca De...
     * @param actionEvent evento disparado
     * @throws Exception serve para ignorar todos o warnings de exceções. Caso contrário temos qe usar o try...catch
     */
    public void menuAbout(ActionEvent actionEvent) throws Exception {
        // Aquisição do controlo da cena (Scene) about FXML
        Parent scene = FXMLLoader.load(getClass().getResource("about.fxml"));

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

    public void menuAddContact(ActionEvent actionEvent) throws Exception{
        // Definição da Flag Action
        Settings.ACTION = Settings.ACTION_INSERT;
        // Abre a Scene numa nova Stage em modo Modal
        // Aquisição do controlo da cena pretendida (student.fxml)
        Parent scene = FXMLLoader.load(getClass().getResource("contacto.fxml"));

        // Nova janela (Stage)
        Stage addContact = new Stage();
        addContact.setTitle("Agenda - Inserir novo Contacto");

        // Associação da Scene à Stage
        addContact.setScene(new Scene(scene));

        // Abertura da janela addStudent em modo MODAL, em relação à primaryStage
        addContact.initOwner(Settings.getPrimaryStage());
        addContact.initModality(Modality.WINDOW_MODAL);

        // Abertura da janela
        addContact.show();
    }

    public void menuContactList(ActionEvent actionEvent) throws Exception{
        // Aquisição do controlo da cena da ListViewXml FXML (listviewxml.fxml) e
        // associar à zona central da BorderPane.
        // Aquisição do controlo do Scene pretendida
        Parent scene = FXMLLoader.load(getClass().getResource("contactolist.fxml"));

        // Atribuição da Scene à zona central da cena Principal, que é um BorderPane
        borderPane.setCenter(scene);
    }
}
