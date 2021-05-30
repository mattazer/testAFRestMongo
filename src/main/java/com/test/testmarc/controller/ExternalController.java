package com.test.testmarc.controller;

import com.test.testmarc.customexception.TechnicalBDException;
import com.test.testmarc.customexception.UserCustomException;
import com.test.testmarc.model.User;
import com.test.testmarc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author marc rubin
 * Controleur externe exposant trois endpoints :
 * /registerUser et /displayUser/{name} sont les deux services REST demandés pour ce test
 * /displayAllUsers permet un affichage complet du contenu de la base Mongo embarquée fourni dans un but de debug
 *
 */
@RestController
public class ExternalController {
    /**
     * Service utilisé pour la gestion des utilisateurs
     */
    @Autowired
    UserService userService;

    /**
     * Endpoint d'enregistrement des utilisateurs
     * @param name   nom de famille de l'utilisateur
     * @param firstName prénom de l'utilisateur
     * @param age age de l'utilisateur
     * @param country pays de résidence de l'utilisateur
     * @param favoriteDestination destination favorite de l'utilisateur (par default Paris)
     * @param phoneNumber numéro de téléphone de l'utilisateur
     * @return un message de confirmation d'enregistrement ou d'explication de l'erreur
     */
    @PostMapping ("/registerUser")
    public ResponseEntity<String> registerUser(@RequestParam(required = true) String name,
                                             @RequestParam(required = true) String firstName,
                                             @RequestParam(required = true) int age,
                                             @RequestParam(required = true) String country,
                                             @RequestParam(required = false,defaultValue = "Paris") String favoriteDestination,
                                             @RequestParam(required = false) String phoneNumber){
        String ret="";
        try{
            ret = userService.addUser(name,firstName,age,country,favoriteDestination,phoneNumber);
        }catch(UserCustomException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.FORBIDDEN);
        }catch(TechnicalBDException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(ret,HttpStatus.OK);
    }

    /**
     * Endpoint de recherche d'un utilisateur existant par nom de famille
     * @param name nom de l'utilisateur
     * @return l'utilisateur si il existe ou une erreur 404
     */
    @GetMapping("/displayUser/{name}")
    public ResponseEntity<User> findByName(@PathVariable String name){
        User user = userService.findByName(name);
        if(user!=null){
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
        else return new ResponseEntity<User>((User) null,HttpStatus.NOT_FOUND);
    }

    /**
     * affiche tous les utilisateurs en base
     * @return la liste des utilisateurs
     */
    @GetMapping("/displayAllUsers")
    public List<User> displauUsers(){
        return userService.listUsers();
    }
}
