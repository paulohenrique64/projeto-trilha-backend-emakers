package com.paulohenrique.library.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name="person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int personId;

    @Basic(optional = false)
    private String name;

    @Basic(optional = false)
    private String cep;

    public Person() { }

    public Person(String name, String cep) {
        this.name = name;
        this.cep = cep;
    }

    public int getPersonId() {
        return personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}
