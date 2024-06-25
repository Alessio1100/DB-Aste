package it.uniroma2.dicii.bd.view;

import it.uniroma2.dicii.bd.model.domain.Credentials;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginView {
    public static Credentials authenticate() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(" ");
        System.out.println("\u001B[34m"+"*********      LOGIN     *********"+"\u001B[0m");
        System.out.println(" ");
        System.out.print("username: ");
        String username = reader.readLine();
        System.out.println(" ");
        System.out.print("password: ");
        String password = reader.readLine();

        return new Credentials(username, password, null);
    }
}
