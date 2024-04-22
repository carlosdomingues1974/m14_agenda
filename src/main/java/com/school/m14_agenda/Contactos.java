package com.school.m14_agenda;

import java.time.LocalDate;

public class Contactos {
    //region Atributos
    private int id;     //identificador do contacto
    private String name;    //nome do contacto
    private String email;   //e-mail do contacto
    private LocalDate birthDate;    //data de nascimento do contacto
    private String phone;   //contacto telefónico
    //endregion

    //region Construtores

    /**
     * Construtor completo
     * @param id identificador do contacto
     * @param name  nome do contacto
     * @param email e-mail do contacto
     * @param birthDate data de nascimento do contacto
     * @param phone contacto telefónico
     */
    public Contactos(int id, String name, String email, LocalDate birthDate, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.phone = phone;
    }

    /**
     * Construtor com 4 parâmetros
     * @param name nome do contacto
     * @param email e-mail do contacto
     * @param birthDate data de nascimento do contacto
     * @param phone contacto telefónico
     */
    public Contactos(String name, String email, LocalDate birthDate, String phone) {
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.phone = phone;
    }
    //endregion

    //region Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
