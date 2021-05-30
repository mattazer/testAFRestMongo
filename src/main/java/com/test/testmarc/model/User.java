package com.test.testmarc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.NumberFormat;

/**
 * Domain Object qui doit être persisté en base, comprend toutes les caractéristiques d'un utilisateur
 */
@Document
@Data
@NoArgsConstructor
public class User {
    @Id()
    private String id;
    private String name;
    private String firstName;
    private int age;
    private String country;
    private String favoriteDestination;
    @NumberFormat
    private String phoneNumber;

    public User(String name, String firstName,int age,String country,String favoriteDestination,String phoneNumber){
        this.name=name;
        this.firstName=firstName;
        this.age=age;
        this.country=country;
        this.favoriteDestination=favoriteDestination;
        this.phoneNumber=phoneNumber;
    }
}
