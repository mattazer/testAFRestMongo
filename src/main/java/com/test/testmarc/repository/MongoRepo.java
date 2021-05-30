package com.test.testmarc.repository;

import com.test.testmarc.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Optional;

/**
 * Repository mongo DB utilisé pour stocker les Utilisateurs, il s'agit de l'interface de la Base de donnée embarqué MongoDB
 */
@EnableMongoRepositories(basePackages = "com.test.testmarc.repository")
public interface MongoRepo extends MongoRepository<User,String> {
    Optional<User> findFirstByName(String name);
}
