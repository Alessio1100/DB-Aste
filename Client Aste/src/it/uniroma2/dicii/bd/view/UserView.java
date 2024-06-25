package it.uniroma2.dicii.bd.view;

import it.uniroma2.dicii.bd.model.domain.ItemList;
import it.uniroma2.dicii.bd.model.domain.Offer;
import it.uniroma2.dicii.bd.model.domain.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserView {

    public static int showMenu() throws IOException {
        System.out.println(" ");
        System.out.println("\u001B[34m"+"**********************************"+"\u001B[0m");
        System.out.println("\u001B[34m"+"*****     USER DASHBOARD     *****"+"\u001B[0m");
        System.out.println("\u001B[34m"+"**********************************\n"+"\u001B[0m");
        System.out.println("***  What should I do for you? ***\n");
        System.out.println("1) Make a bid for an item");
        System.out.println("2) View the unfinished auctions you bid on");
        System.out.println("3) Display items from won auctions ");
        System.out.println("4) View the currently open auctions");
        System.out.println("5) Quit");
        System.out.println("");


        Scanner input = new Scanner(System.in);
        int choice = 0;
        while (true) {
            System.out.print("Please enter your choice: ");
            System.out.println("");
            choice = input.nextInt();
            if (choice >= 1 && choice <= 5) {
                break;
            }
            System.out.println("Invalid option");
        }

        return choice;
    }


    public static void showItemsCurrentlyInAuctionView(ItemList itemList) {

        List<String> fieldsToPrint = new ArrayList<>();
        fieldsToPrint.add("id");
        fieldsToPrint.add("descrizione");
        fieldsToPrint.add( "stato");
        fieldsToPrint.add("baseAsta");
        fieldsToPrint.add("dimensioni");
        fieldsToPrint.add("inizioAsta");
        fieldsToPrint.add("termineAsta");
        fieldsToPrint.add("numeroOfferte");
        fieldsToPrint.add("valoreMaxOfferta");
        fieldsToPrint.add("categoria");

        ApplicationView.printObjectTable(itemList.getList(), System.out, fieldsToPrint);

    }

    public static void showPurchasedItemsView(ItemList itemList) {
        List<String> fieldsToPrint = new ArrayList<>();
        fieldsToPrint.add("descrizione");
        fieldsToPrint.add("stato");
        fieldsToPrint.add("dimensioni");
        fieldsToPrint.add("categoria");
        fieldsToPrint.add("prezzoDiVendita");

        ApplicationView.printObjectTable(itemList.getList(), System.out, fieldsToPrint);
    }

    public static void showOfferedItemsView(ItemList itemList) {

        List<String> fieldsToPrint = new ArrayList<>();
        fieldsToPrint.add("id");
        fieldsToPrint.add("descrizione");
        fieldsToPrint.add("stato");
        fieldsToPrint.add("baseAsta");
        fieldsToPrint.add("dimensioni");
        fieldsToPrint.add("inizioAsta");
        fieldsToPrint.add("termineAsta");
        fieldsToPrint.add("valoreMaxOfferta");

        ApplicationView.printObjectTable(itemList.getList(), System.out, fieldsToPrint);

    }

    public static Offer makeOfferView(User user) throws IOException {
        Scanner bid = new Scanner(System.in);
        BufferedReader id = new BufferedReader(new InputStreamReader(System.in));
        Offer offer = new Offer();
        // set User making offer
        offer.setUtente(user.getCf());

        int choice;
        // set Item to bid on
        System.out.println("");
        System.out.println("Please provide the ID of the object you want to bid on: ");
        offer.setOggetto(id.readLine());
        // set amount of the offer
        System.out.println("Enter the amount of the Offer: ");
        offer.setImporto(bid.nextFloat());
        boolean check;
        // choose for automatic counter-offer
        do{
            System.out.println("Do you want to set the automatic counter-offer on this object?");
            System.out.println("1) Yes");
            System.out.println("2) No");
            choice = bid.nextInt();

            if (choice < 1 || choice > 2 ){
                System.out.println("");
                check = true;
            }
            else{
                check = false;
            }
        }
        while (check);{
            if (choice==1){
                // set automatic counter-offer amount
                System.out.println("Provide the maximum amount for the automatic counter-offer: ");
                offer.setValoreMassimoAutomatico(bid.nextFloat());
            }
        }
        return offer;
    }
}
