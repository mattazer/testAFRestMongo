package com.test.testmarc;


import com.test.testmarc.customexception.TechnicalBDException;
import com.test.testmarc.customexception.UserCustomException;
import com.test.testmarc.logtime.LogExecutionTime;
import com.test.testmarc.model.User;
import com.test.testmarc.repository.MongoRepo;
import com.test.testmarc.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServicesTests {
    @Mock
    MongoRepo mongorepo;
    @InjectMocks
    UserService userservice;

    @Test(expected = UserCustomException.class)
    public void wrongAge() throws UserCustomException, TechnicalBDException {

        String str = userservice.addUser("name","first",15,"france",null,null);
    }
    @Test(expected = UserCustomException.class)
    public void wrongCountry() throws UserCustomException, TechnicalBDException {
            String str = userservice.addUser("name","first",18,"autriche",null,null);
    }
    @Test
    public void passRegistration() throws UserCustomException, TechnicalBDException {
        User mockUser = new User();
        mockUser.setName("name");
        when(mongorepo.save(any(User.class))).thenReturn(mockUser);
        String str = userservice.addUser("name","first",18,"france",null,null);
        assertTrue(str.equals("User Created with name: "+"name"+"."));
    }
    @Test(expected = TechnicalBDException.class)
    public void technicalException() throws UserCustomException, TechnicalBDException {
        String str = userservice.addUser("name","first",18,"france",null,null);
    }

    @Test
    public void findByNameFound() {
        User mockUser = new User();
        mockUser.setName("name");
        when(mongorepo.findFirstByName(any(String.class))).thenReturn(Optional.of(mockUser));

        User usr = userservice.findByName("name");
        assertTrue(usr.getName().equals("name"));
    }
    @Test
    public void findByNameNotFound() {
        when(mongorepo.findFirstByName(any(String.class))).thenReturn(Optional.empty());
        User usr = userservice.findByName("name");
        assertTrue(usr==null);
    }

}