package it.uniroma2.dicii.bd.controller;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.dao.*;
import it.uniroma2.dicii.bd.model.domain.*;
import it.uniroma2.dicii.bd.view.UserView;
import java.io.IOException;
import java.sql.SQLException;

public class UserController implements ControllerLogged {

    private User user;

    @Override
    public void start(Credentials cred) {
        try{
            ConnectionFactory.changeRole(Role.UTENTE);
            UserDashboardDAO userDashboardDAO = UserDashboardDAO.getInstance();
            user = userDashboardDAO.execute(cred.getUsername());

        }catch (SQLException | DAOException e){
            throw new RuntimeException(e);
        }
        userChoice();
    }

    public void userChoice() {

        while(true) {
            int choice;
            try {
                choice = UserView.showMenu();
            }
            catch(IOException e) {
                throw new RuntimeException(e);
            }

            switch(choice) {
                case 1 -> makeOffer();                     // U1 e U2
                case 2 -> showOfferedItems();              // U3
                case 3 -> showPurchasedItems();            // U4
                case 4 -> showItemsCurrentlyInAuction();   // U5
                case 5 -> System.exit(0);            // exit choice
                default -> throw new RuntimeException("\u001B[31m"+"Invalid choice"+"\u001B[0m");
            }
        }
    }

    private void showItemsCurrentlyInAuction() {
        ItemList itemList = null;
        try {
            ItemListDAO itemListDAO = ItemListDAO.getInstance();
            itemList = itemListDAO.execute();
        }
        catch (DAOException e){
            System.out.println(e.getMessage());
        }
        UserView.showItemsCurrentlyInAuctionView(itemList);
    }
    private void showPurchasedItems() {
        PurchasedItemProcedureDAO purchasedItemProcedureDAO = PurchasedItemProcedureDAO.getInstance();
        ItemList itemList = null;

        try{
            itemList = purchasedItemProcedureDAO.execute(user);
        }
        catch(DAOException e){
            System.out.println(e.getMessage());
        }

        if(itemList != null){
            UserView.showPurchasedItemsView(itemList);
        }
    }
    private void showOfferedItems() {

        OfferedItemsProcedureDAO offeredItemsProcedureDAO = OfferedItemsProcedureDAO.getInstance();
        ItemList itemList= null;

        try{
            itemList = offeredItemsProcedureDAO.execute(user);
        }
        catch (DAOException e){
            System.out.println(e.getMessage());
        }

        if(itemList != null){
            UserView.showOfferedItemsView(itemList);
        }

    }
    private void makeOffer() {
       boolean bool = false;
       Offer offer = null;

       try {
           offer = UserView.makeOfferView(user);
       }
       catch (Exception e){
           System.out.println(e.getMessage());
       }
       try {
           MakeOfferProcedureDAO makeOfferProcedureDAO = MakeOfferProcedureDAO.getInstance();
           bool = makeOfferProcedureDAO.execute(offer);
       }
       catch (DAOException e){
           System.out.println(e.getMessage());
       }

       if (bool){
           System.out.println("\u001B[32m"+" The offer was successfully added! "+"\u001B[0m");
       }
    }
}
