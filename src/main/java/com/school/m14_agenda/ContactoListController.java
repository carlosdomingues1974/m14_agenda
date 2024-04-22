package com.school.m14_agenda;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ContactoListController implements Initializable {
    //region Dados locais e Controlos XML

    // Atributo (Control) da tableView
    // 1º criá-los pelo método automático do XML: 1º criar o id no SceneBuilder, 2º no XML clicar para criar o atributo)
    // 2º acrescentar a diretiva @FXML e mudar para private: restringe o seu uso ao Controller e à file FXML associada.
    // 3º Associar a Classe que vai tratar.
    @FXML
    private TableView tableViewContact;
    @FXML
    private TableColumn tableColumnName;
    @FXML
    private TableColumn tableColumnID;
    @FXML
    private TableColumn tableColumnEmail;
    @FXML
    private TableColumn tableColumnBirthDate;
    @FXML
    private TableColumn tableColumnPhone;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnClose;
    @FXML
    public Button btnSearch;
    //endregion

    /**
     * Loader
     * INITIALIZE() - é executado assim que o construtor acaba de ativar todos os elementos da scene
     * Neste caso, vai efetivar a associação das colunas da tableView aos atributos da classe.
     * Ou seja, estamos a dizer aos atributos da TableView onde devem is buscar os dados.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Associação das colunas aos atributos da classe
        tableColumnID.setCellValueFactory(new PropertyValueFactory<Contactos, Integer>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<Contactos, String>("name"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<Contactos, String>("email"));
        tableColumnBirthDate.setCellValueFactory(new PropertyValueFactory<Contactos, LocalDate>("birthDate"));
        tableColumnPhone.setCellValueFactory(new PropertyValueFactory<Contactos, String>("phone"));

        Settings.getContacts().clear();
        tableViewContact.setItems(ContactosDAO.listContacts());

        //ContactosDAO.listContacts();
        //tableViewContact.setItems(Settings.getContacts());

        //Filtrar a lista para a pesquisa
        FilteredList<Contactos> searchData = new FilteredList<>(Settings.getContacts());

        txtSearch.textProperty().addListener((observable, oldValue, newValue) ->{
            searchData.setPredicate(contactos -> {
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }

                String searchName = newValue.toLowerCase();
                if(contactos.getName().toLowerCase().contains(searchName) ){
                    return true;
                }
                else if(String.valueOf(contactos.getId()).indexOf(searchName) != -1){
                    return true;
                }
                else{
                    return false;
                }
            });
        });

        SortedList<Contactos> sortedData = new SortedList<>(searchData);
        sortedData.comparatorProperty().bind(tableViewContact.comparatorProperty());
        tableViewContact.setItems(sortedData);
        //Fim do filtro de pesquisa

        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
    }

    /**
     * Editar/ Atualizar o item selecionado
     * 1º Verifica se há algum item selecionado
     * 2º Extrai o Objeto
     * 3º Atualiza Settings com a FLAG Action a 2 e o Objeto Entidade
     * @param actionEvent executa o evento
     * @throws Exception serve para ignorar todos o warnings de exceções. Caso contrário temos qe usar o try...catch
     */
    public void buttonEdit(ActionEvent actionEvent) throws Exception{
        // Caso não haja um item selecionado notifica o Utilizador e termina.
        if(tableViewContact.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Item não selecionado");
            alert.setHeaderText("Selecione um item, por favor!");
            alert.show();
            return;
        }
        // Se chegou aqui é porque há um item selecionado => Extrai-o
        // O método devolve um Object porque nunca sabe o que lá vem. => Cast para Aluno.
        Contactos selectedItem = (Contactos) tableViewContact.getSelectionModel().getSelectedItem();

        // Definição da Flag Ation e do objeto de Entidade de settings com Update
        Settings.ACTION = Settings.ACTION_UPDATE;
        Settings.setContactEdit(selectedItem);

        // Abre a Scene numa nova Stage em modo Modal
        // Aquisição do controlo da Scene pretendida
        Parent scene = FXMLLoader.load(getClass().getResource("contacto.fxml"));

        // Nova janela
        Stage contactEdit = new Stage();
        contactEdit.setTitle("Agenda de Contactos - Atualizar Contacto");

        // Associação da Scene à Stage
        contactEdit.setScene(new Scene(scene));

        // Abertura da janela edit Student em modo MODAL, em relação à primaryStage
        contactEdit.initOwner(Settings.getPrimaryStage());
        contactEdit.initModality(Modality.WINDOW_MODAL);

        // Abertura da Window
        contactEdit.show();
    }

    /**
     * Eliminar o Item Selecionado
     * 1º Verifica se há algum item selecionado
     * 2º Extrai o Objeto
     * 3º Atualiza Settings com a FLAG Action a 3 e o Objeto Entidade
     * @param actionEvent executa o evento
     * @throws Exception serve para ignorar todos o warnings de exceções. Caso contrário temos qe usar o try...catch
     */
    public void buttonDelete(ActionEvent actionEvent) throws Exception{
        // Caso não haja um item selecionado notifica o Utilizador e termina.
        if(tableViewContact.getSelectionModel().getSelectedItem() == null){

            // Notifica o utilizdor e termina
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Item não selecionado");
            alert.setHeaderText("Selecione um item, por favor!");
            alert.show();
            return;
        }
        // Se chegou aqui é porque há um item selecionado => Extrai-o
        // O método devolve um Object porque nunca sabe o que lá vem. => Cast para Aluno.
        Contactos selectedItem = (Contactos) tableViewContact.getSelectionModel().getSelectedItem();

        // Definição da Flag Ation e do objeto de Entidade de settings com Delete
        Settings.ACTION = Settings.ACTION_DELETE;
        Settings.setContactEdit(selectedItem);

        // Abre a Scene numa nova Stage em modo Modal
        // Aquisição do controlo da Scene pretendida
        Parent scene = FXMLLoader.load(getClass().getResource("contacto.fxml"));

        // Nova janela
        Stage contactDelete = new Stage();
        contactDelete.setTitle("Agenda de Contactos - Remover Contacto");

        // Associação da Scene à Stage
        contactDelete.setScene(new Scene(scene));

        // Abertura da janela edit Student em modo MODAL, em relação à primaryStage
        contactDelete.initOwner(Settings.getPrimaryStage());
        contactDelete.initModality(Modality.WINDOW_MODAL);

        // Abertura da Window
        contactDelete.show();
    }

    /**
     * permite sair da cena atual. Repôe a cena Principal na Stage
     * @param actionEvent executa o evento
     * @throws Exception erve para ignorar todos o warnings de exceções. Caso contrário temos qe usar o try...catch
     */
    public void buttonClose(ActionEvent actionEvent) throws Exception {
        // Aquisição do controlo da Scene pretendida
        Parent scene = FXMLLoader.load(getClass().getResource("principal.fxml"));

        // Voltar à cena principal da Stage
        Settings.getPrimaryStage().setScene(new Scene(scene));
    }

    public void selectedRow(MouseEvent mouseEvent) {
        if(tableViewContact.getSelectionModel().getSelectedItem() == null){
            btnEdit.setDisable(true);
            btnDelete.setDisable(true);
        }
        else{
            btnEdit.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    public void buttonSearch(ActionEvent actionEvent) {

    }
}
