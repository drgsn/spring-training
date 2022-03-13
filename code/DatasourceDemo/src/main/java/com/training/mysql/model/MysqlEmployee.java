package com.training.mysql.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Random;

import static javax.persistence.GenerationType.AUTO;

@Entity
//@Table(name = "mysql_employee") // in jpa you must have different name for each entity. both mysql and h2 use jpa!!!
public class MysqlEmployee {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String emailName;

    public MysqlEmployee() {
        this.firstName = "mysql first name";
        this.lastName = "mysql last name";
        this.emailName = "mysql@email.com" + new Random().nextInt();
    }

    public MysqlEmployee(String firstName, String lastName, String emailName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailName = emailName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailName() {
        return emailName;
    }

    public void setEmailName(String emailName) {
        this.emailName = emailName;
    }

    @Override
    public String toString() {
        return "MysqlEmployee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailName='" + emailName + '\'' +
                '}';
    }
}