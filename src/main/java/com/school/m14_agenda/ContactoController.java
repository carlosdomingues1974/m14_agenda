package com.school.m14_agenda;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ContactoController implements Initializable {
    //region dados locais e controlos dos elementos FXML
    //Controlos para os elementos FXML
    @FXML
    private Label lblTitle;
    @FXML
    private TextField txtNumber;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtEmail;
    @FXML
    private DatePicker datePickerBirthDate;
    @FXML
    private TextField txtPhone;
    @FXML
    private Button btnAction;

    // Controlo da Stage. Obtém-se a partir de qualquer Controlo dos elementos FXML.
    @FXML
    private Stage thisStage;
    //endregion

    /**
     * Método LOADER
     * Este é o primeiro método a ser executado, após a construção da Scene
     * Neste caso vai preparar a Scene, em função da FLAG Action da classe
     * Settings. Esta FLAG é definida no momento em que a Scene é chamada:
     * - Da opção de menu Inserir: passa o valor 1
     * - Da opção alterar da Scene contactList: passa o valor 2
     * - Da opção eliminar da Scene contactList: passa o valor 3
     * - -1 é o valor indefinido. caso chegue aqui, o utilizador é notificado.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Se flag Action não está definida => notifica e termina
        if (Settings.ACTION == -1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Sair da aplicação");
            alert.setHeaderText("A flag Action não está definida");
            alert.show();
        }
        //Preparação da cena
        switch (Settings.ACTION) {
            case Settings.ACTION_INSERT:
                // Altera o texto do título e do botão Action
                // Os campos são apresentados abertos e vazios
                lblTitle.setText("Adicionar Novo Contacto");
                btnAction.setText("Adicionar");
                txtNumber.setDisable(true);
                break;
            case Settings.ACTION_UPDATE:
                // Altera o texto do título e do botão Action
                lblTitle.setText("Atualização do Contacto");
                btnAction.setText("Atualizar");

                //Campo referente ao número de processo deve estar disable
                txtNumber.setDisable(true);
                //Preencher os campos com os dados do objeto Contacto
                //que pretendemos editar/ atualizar
                txtNumber.setText(String.valueOf(Settings.getContactEdit().getId()));
                txtName.setText(Settings.getContactEdit().getName());
                txtEmail.setText(Settings.getContactEdit().getEmail());
                datePickerBirthDate.setValue(Settings.getContactEdit().getBirthDate());
                txtPhone.setText(Settings.getContactEdit().getPhone());
                break;
            case Settings.ACTION_DELETE:
                // Altera o texto do título e do botão Action
                lblTitle.setText("Remover Contacto");
                btnAction.setText("Remover");

                //Preencher os campos com os dados do objeto Aluno
                //que pretendemos eliminar
                txtNumber.setText(String.valueOf(Settings.getContactEdit().getId()));
                txtName.setText(Settings.getContactEdit().getName());
                txtEmail.setText(Settings.getContactEdit().getEmail());
                datePickerBirthDate.setValue(Settings.getContactEdit().getBirthDate());
                txtPhone.setText(Settings.getContactEdit().getPhone());

                //Campos que devem estar disable
                txtNumber.setDisable(true);
                txtName.setDisable(true);
                txtEmail.setDisable(true);
                datePickerBirthDate.setDisable(true);
                txtPhone.setDisable(true);
                break;
        }
    }


    public void buttonAction(ActionEvent actionEvent) {
        switch (Settings.ACTION) {
            case Settings.ACTION_INSERT:
                // Recolha dos dados existentes nos Controlos (objetos gráficos da janela)
                // para um novo objeto.
                String name = txtName.getText();
                String email = txtEmail.getText();
                LocalDate birth = datePickerBirthDate.getValue();
                String phone = txtPhone.getText();
                Contactos newContact = new Contactos(name, email, birth, phone);

                int id = ContactosDAO.addContact(newContact);
                newContact.setId(id);
                //Adiciona o novo Aluno à TableView
                Settings.getContacts().add(newContact);
                break;
            case Settings.ACTION_UPDATE:
                //Recolha dos dados existentes nos Controlos (objetos gráficos da janela)
                // para o mesmo objeto.
                Settings.getContactEdit().setName(txtName.getText());
                Settings.getContactEdit().setEmail(txtEmail.getText());
                Settings.getContactEdit().setBirthDate(datePickerBirthDate.getValue());
                Settings.getContactEdit().setPhone(txtPhone.getText());
                ContactosDAO.updateContact(Settings.getContactEdit());
                int position = Settings.getContacts().indexOf(Settings.getContactEdit());
                Settings.getContacts().set(position, Settings.getContactEdit());
                break;
            case Settings.ACTION_DELETE:
                // Procura e elimina o objeto da Lista geral
                int ids = Integer.parseInt(txtNumber.getText());
                ContactosDAO.removeContact(ids);
                Settings.getContacts().remove(Settings.getContactEdit());
                /*for (Contactos contacto : Settings.getContacts())
                {
                    if (contacto.getId() == Settings.getContactEdit().getId())
                    {
                        ContactosDAO.removeContact(contacto);
                        Settings.getContacts().remove(Settings.getContactEdit());
                        // Sai do ciclo
                        break;
                    }
                }*/
                break;
        }
        // Reposição da Flag e do Objeto Entidade da classe Settings e encerramento da Stage
        Settings.ACTION = -1;
        Settings.setContactEdit(null);
        thisStage = (Stage) btnAction.getScene().getWindow();
        thisStage.close();
    }

    /**
     * Button Cancelar
     * Termina a Stage com a reposição dos valores nulls da flag Action e objeto Entidade
     * @param actionEvent executa o evento
     */
    public void buttonCancel(ActionEvent actionEvent) {
        // Reposição da Flag e Objeto Entidade em Settings e encerramento da Stage
        Settings.ACTION = -1;
        Settings.setContactEdit(null);
        thisStage = (Stage) btnAction.getScene().getWindow();
        thisStage.close();
    }

    //region Validação de dados
    public void TextFieldNameKeyPressed(KeyEvent keyEvent) {
        Settings.checkMaxLength((TextField) keyEvent.getSource(), 100);
        Settings.isTextOnly((TextField) keyEvent.getSource());
    }

    public void TextFieldEmailKeyPressed(KeyEvent keyEvent) {
        Settings.checkMaxLength((TextField) keyEvent.getSource(), 255);
    }


    public void TextFieldPhoneKeyPressed(KeyEvent keyEvent) {
        Settings.checkMaxLength((TextField) keyEvent.getSource(), 9);
        Settings.isNumeric((TextField) keyEvent.getSource());
    }
    //endregion
}
