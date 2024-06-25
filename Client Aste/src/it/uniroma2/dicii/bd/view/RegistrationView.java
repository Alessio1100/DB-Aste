package it.uniroma2.dicii.bd.view;

import java.io.IOException;
import java.util.Scanner;

public class RegistrationView {

    public static int ChooseRegistrationType() {
        System.out.println(" ");
        System.out.println("\u001B[34m"+"*********************************"+"\u001B[0m");
        System.out.println("\u001B[34m"+"*    REGISTRATION DASHBOARD    *"+"\u001B[0m");
        System.out.println("\u001B[34m"+"*********************************\n"+"\u001B[0m");
        System.out.println("*** Choose how to register ***\n");
        System.out.println("1) Admin");
        System.out.println("2) User");
        System.out.println("3) Quit");


        Scanner input = new Scanner(System.in);
        int choice = 0;
        while (true) {
            System.out.print("Please enter your choice: ");
            choice = input.nextInt();
            if (choice >= 1 && choice <= 3) {
                break;
            }
            System.out.println("Invalid option");
        }

        return choice;
    }
}
