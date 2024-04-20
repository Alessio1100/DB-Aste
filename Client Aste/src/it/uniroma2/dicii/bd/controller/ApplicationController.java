package it.uniroma2.dicii.bd.controller;

import it.uniroma2.dicii.bd.model.domain.Admin;
import it.uniroma2.dicii.bd.model.domain.Credentials;
import it.uniroma2.dicii.bd.model.domain.User;
import it.uniroma2.dicii.bd.view.ApplicationView;
import it.uniroma2.dicii.bd.view.RegistrationView;

import java.io.IOException;

public class ApplicationController implements Controller {
    Credentials cred;
    User user;
    Admin admin;

    @Override
    public void start() {

        while(true){

            int choise;
            choise = ApplicationView.showMenu();
            switch (choise){

                case 1 -> registration();
                case 2 -> login();
                case 3 -> System.exit(0);
                default -> throw new RuntimeException("Invalid choise");
            }

        }

    }

    private void registration() {

        int choice;
        choice = RegistrationView.ChooseRegistrationType();
        switch (choice) {

            case 1 -> {
                System.out.println("chosen 1");
                AdminRegistrationController adminRegistrationController = new AdminRegistrationController();
                adminRegistrationController.start();
                admin = adminRegistrationController.getUser();
                cred = adminRegistrationController.getCred();
                AdminController adminController = new AdminController();
                adminController.start();

            }
            case 2 -> {
                System.out.println("chosen 2");
                UserRegistrationController userRegistrationController = new UserRegistrationController();
                userRegistrationController.start();
                user = userRegistrationController.getUser();
                cred = userRegistrationController.getCred();
                UserController userController = new UserController();
                userController.start(cred);

            }
            case 3 -> System.exit(0);
            default -> throw new RuntimeException("Invalid choice");
        }
    }
    public void login() {

        LoginController loginController = new LoginController();
        loginController.start();
        cred = loginController.getCred();

        if(cred.getRole() == null){
            throw new RuntimeException("Invalid Credentials");
        }

        switch (cred.getRole()){
            case UTENTE -> new UserController().start(cred);
            case AMMINISTRATORE -> new AdminController().start();
            default -> throw new RuntimeException("Invalid Credentials role ");
        }

    }

}
