package com.school.m14_agenda;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.JavaFXBuilderFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContactosDAO {
    public static int addContact(Contactos c){
        Connection conn = ConnectionDB.openDB();
        PreparedStatement stmt = null;
        int id = 0;

        String sql = "INSERT INTO contactos(nome, email, dataNascimento, telemovel) VALUES (?, ?, ?, ?);";

        try{
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, c.getName());
            stmt.setString(2,c.getEmail());
            stmt.setDate(3,Date.valueOf(c.getBirthDate()));
            stmt.setString(4, c.getPhone());
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                id = rs.getInt(1);
            }
            System.out.println("Contacto adicionado com sucesso");
        }
        catch(SQLException ex){
            System.out.println("Erro ao adicionar novo contacto: "+ex);

        }
        finally {
            ConnectionDB.closeDB(stmt);
        }
        return id;
    }

    public static ObservableList<Contactos> listContacts(){
        Connection conn = ConnectionDB.openDB();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ObservableList<Contactos> contacts = Settings.getContacts();
        String sql = "SELECT * FROM contactos;";

        try{
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("nome");
                String email = rs.getString("email");
                LocalDate birthDate = rs.getDate("dataNascimento").toLocalDate();
                String phone = rs.getString("telemovel");
                Contactos c = new Contactos(id, name, email, birthDate, phone);
                contacts.add(c);
            }
        }
        catch (SQLException ex){
            System.out.println("Erro ao listar os contactos: "+ex);
        }
        finally {
            ConnectionDB.closeDB(stmt, rs);
        }
        return contacts;
    }

    public static Contactos searchForID(int num){
        Connection conn = ConnectionDB.openDB();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Contactos c = null;

        String sql = "SELECT * FROM contactos WHERE id = ?;";

        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            if(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("nome");
                String email = rs.getString("email");
                LocalDate birthDate = rs.getDate("dataNascimento").toLocalDate();
                String phone = rs.getString("telemovel");
                c = new Contactos(id, name, email, birthDate, phone);
            }
        }
        catch (SQLException ex){
            System.out.println("Erro ao pesquisar o contacto: "+ex);
        }
        finally {
            ConnectionDB.closeDB(stmt, rs);
        }
        return c;
    }
    public static void removeContact(int id){
        Connection conn = ConnectionDB.openDB();
        PreparedStatement stmt = null;

        String sql = "DELETE FROM contactos WHERE id = ?;";

        try{
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            System.out.println("Contacto eliminado com sucesso");
        }
        catch (SQLException ex) {
            System.out.println("Erro ao eliminar contacto: "+ex);
        }
        finally {
            ConnectionDB.closeDB(stmt);
        }
    }

    public static void updateContact(Contactos c){
        Connection conn = ConnectionDB.openDB();
        PreparedStatement stmt = null;

        String sql = "UPDATE contactos SET nome = ?, email = ?, dataNascimento = ?, telemovel = ? WHERE id = ?;";

        try{
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, c.getName());
            stmt.setString(2, c.getEmail());
            stmt.setDate(3,Date.valueOf(c.getBirthDate()));
            stmt.setString(4, c.getPhone());
            stmt.setInt(5, c.getId());
            stmt.execute();
            System.out.println("Contacto atualizado com sucesso");
        }
        catch(SQLException ex){
            System.out.println("Erro ao atualizar contacto: "+ex);
        }
        finally {
            ConnectionDB.closeDB(stmt);
        }
    }
}
