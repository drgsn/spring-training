package com.training.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "mongo_employee")
public class MongoEmployee {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String emailName;

    public MongoEmployee() {
        this.firstName = "mongo first name";
        this.lastName = "mongo last name";
        this.emailName = "mongo@email.com";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        return "Mongo Employee{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailName='" + emailName + '\'' +
                '}';
    }
}
