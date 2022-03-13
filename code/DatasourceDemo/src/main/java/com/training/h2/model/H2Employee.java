package com.training.h2.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.Random;

import static javax.persistence.GenerationType.AUTO;

@Entity
public class H2Employee {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String emailName;

    public H2Employee() {
        this.firstName = "h2 first name";
        this.lastName = "h2 last name";
        this.emailName = "h2email@email.com" + new Random().nextInt();
    }

    public H2Employee(String firstName, String lastName, String emailName) {
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
        return "H2 Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailName='" + emailName + '\'' +
                '}';
    }
}
