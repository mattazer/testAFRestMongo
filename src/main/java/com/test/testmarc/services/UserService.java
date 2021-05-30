package com.test.testmarc.services;

import com.test.testmarc.customexception.TechnicalBDException;
import com.test.testmarc.customexception.UserCustomException;
import com.test.testmarc.logtime.LogExecutionTime;
import com.test.testmarc.model.User;
import com.test.testmarc.repository.MongoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service principale de l'application qui gère les utilisateurs
 */
@Service
public class UserService {
    /**
     * Repo mongoDB pour les interactions avec la base de donnée
     */
    @Autowired
    private MongoRepo repo;

    /**
     * methode de validation des données utilisateur entrées lors de l'appel d'enregistrement
     * @param name  nom de famille
     * @param firstName prenom
     * @param age age
     * @param country lieu de résidence
     * @param favoriteDestination destination favorite
     * @param phoneNumber numéro de téléphone
     * @return true si la validation s'est bien passée, sinon lève un exception:
     * @throws UserCustomException
     */
    private boolean checkValidity(String name, String firstName, int age, String country, String favoriteDestination, String phoneNumber) throws UserCustomException {
        if(!country.equalsIgnoreCase("FRANCE")){
            throw new UserCustomException("You must live in france to create an account");
        }
        if(!(age>=18)){
            throw new UserCustomException("You must be an adult to create an account");
        }
        if(name.trim().isEmpty()||firstName.trim().isEmpty()){
            throw new UserCustomException("You must fill name and firstname fields");
        }
        return true;
    }

    /**
     * méthode d'ajout d'un utilisateur (voir classe User pour les paramêtres
     * @param name
     * @param firstName
     * @param age
     * @param country
     * @param favoriteDestination
     * @param phoneNumber
     * @return une chaine de caractère de validation en cas de réussite avec le nom du compte créé
     * @throws UserCustomException  exception levée en cas d'erreur lors de la validation
     * @throws TechnicalBDException exception levée en cas de problème pour enregistrer l'utilisateur en base
     */
    @LogExecutionTime
    public String addUser(String name, String firstName, int age, String country, String favoriteDestination, String phoneNumber) throws UserCustomException, TechnicalBDException {

        boolean validInput=false;
        User userSaved = null;
        validInput = checkValidity(name,firstName,age,country,favoriteDestination,phoneNumber);

        if(validInput){
            //createUSer
            User userToAdd = new User(name,firstName,age,country,favoriteDestination,phoneNumber);

            userSaved = repo.save(userToAdd);
        }
        if(userSaved!=null)
            return "User Created with name: "+name+".";
        else
            throw new TechnicalBDException("Technical error related to DB storage");


    }

    /**
     * méthode utilisée pour exposer l'intégralité du contenu de la base
     * @return la liste des utilisateurs en base
     */
    @LogExecutionTime
    public List<User> listUsers(){
        return repo.findAll();
    }

    /**
     *
     * @param name nom utilisé pour la recherche d'utilisateur
     *             (on peux utiliser un ou plusieurs paramêtres plus judicieux dans le cas d'utilisateurs pouvant avoir le même nom, à définir dans la spécification)
     * @return le premier utilisateur trouvé avec le nom utilisé pour la recherche ou null si l'utilisateur n'a pas été trouvé en base
     */
    @LogExecutionTime
    public User findByName(String name) {
        Optional<User> user = repo.findFirstByName(name);
        return user.isPresent()?user.get(): null;
    }
}
