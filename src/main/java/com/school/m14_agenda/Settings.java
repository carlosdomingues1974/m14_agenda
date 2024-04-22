package com.school.m14_agenda;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class Settings {
    //region Stage
    /**
     * Stage da aplicação, guardada nesta classe de controlo, permite que
     * seja acedida por qualquer outra classe de trabalho, e com isso
     * tem várias vantagens.
     *  - Permitir manipular o tamanho da stage
     *  - Permitir defini-la Parent de outras stages Modais, etc.
     */
    private static Stage primaryStage;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        Settings.primaryStage = primaryStage;
    }
    //endregion

    //region das Flag Action
    /** FLAG de Sinalização da Ação das Scenes Edit.
     * Trabalha com as Constantes,a seguir definidas. 4 estados:
     *-1    - Não está a ser utilizado
     * 1    - INSERT
     * 2    - UPDATE
     * 3    - DELETE
     */
    public static int ACTION = -1;

    // Constantes para definir as operações da FLAG ACTION
    public static final int ACTION_INSERT = 1;
    public static final int ACTION_UPDATE = 2;
    public static final int ACTION_DELETE = 3;
    //endregion

    //region objetos das Entidades (Contactos)
    private static Contactos contactEdit;

    public static Contactos getContactEdit() {
        return contactEdit;
    }

    public static void setContactEdit(Contactos contactEdit) {
        Settings.contactEdit = contactEdit;
    }
    //endregion

    //region ObservableList
    /**
     * ObservableList de Alunos
     */
    private static ObservableList<Contactos> contacts = FXCollections.observableArrayList();

    public static ObservableList<Contactos> getContacts() {
        return contacts;
    }

    public static void setContacts(ObservableList<Contactos> contacts) {
        Settings.contacts = contacts;
    }
    //endregion
}
