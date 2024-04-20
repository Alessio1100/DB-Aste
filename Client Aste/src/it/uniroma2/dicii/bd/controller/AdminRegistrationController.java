package it.uniroma2.dicii.bd.controller;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.dao.AdminRegistrationProcedureDAO;
import it.uniroma2.dicii.bd.model.domain.Admin;
import it.uniroma2.dicii.bd.model.domain.Credentials;
import it.uniroma2.dicii.bd.view.AdminRegistrationView;

import java.io.IOException;

public class AdminRegistrationController {
    Admin admin;
    Credentials cred;
    Boolean bool;
    public void start() {
        try {
        cred = AdminRegistrationView.showAdminRegMenu();
        admin = new Admin(cred.getUsername(), cred.getPassword());

    } catch (IOException e) {
        throw new RuntimeException(e);
    }

    try{

        AdminRegistrationProcedureDAO adminRegistrationProcedureDAO = AdminRegistrationProcedureDAO.getInstance();
        bool = adminRegistrationProcedureDAO.execute(admin);

        }catch (DAOException e){
            throw new RuntimeException(e);
        }

        if(bool){
            System.out.println("Admin Registration successful!");
        }

    }

    public Admin getUser() {
        return admin;
    }

    public Credentials getCred() {
        return cred;
    }
}
