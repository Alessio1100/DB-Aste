package it.uniroma2.dicii.bd.controller;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.dao.UserRegistrationProcedureDAO;
import it.uniroma2.dicii.bd.model.domain.Credentials;
import it.uniroma2.dicii.bd.model.domain.CreditCard;
import it.uniroma2.dicii.bd.model.domain.User;
import it.uniroma2.dicii.bd.view.UserRegistrationView;

import java.io.IOException;

public class UserRegistrationController {
    User user;
    Credentials cred;
    Boolean bool;

    CreditCard card;
    public void start() {
        try {
            cred = UserRegistrationView.showUserMenu();
            user = UserRegistrationView.insertUserDetails(cred);
            card = UserRegistrationView.insertCreditCard(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            UserRegistrationProcedureDAO registrationProcedureDAO = UserRegistrationProcedureDAO.getInstance();
            bool = registrationProcedureDAO.execute(user,card);
        }catch (DAOException e){
            throw new RuntimeException(e);
        }

        if(bool){
            System.out.println("\u001B[32m"+"Registration successful!"+"\u001B[0m");
        }

    }

    public Credentials getCred(){
        return cred;
    }

    public User getUser(){
        return user;
    }
}
