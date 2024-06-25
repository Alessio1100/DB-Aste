package it.uniroma2.dicii.bd.view;

import it.uniroma2.dicii.bd.model.domain.Credentials;
import it.uniroma2.dicii.bd.model.domain.CreditCard;
import it.uniroma2.dicii.bd.model.domain.Role;
import it.uniroma2.dicii.bd.model.domain.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserRegistrationView {

    public static Credentials showUserMenu() throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(" ");
        System.out.println("\u001B[34m"+"****    USER REGISTRATION    ****"+"\u001B[0m");
        System.out.println(" ");
        System.out.print("username: ");
        String username = reader.readLine();
        System.out.println(" ");
        System.out.print("password: ");
        String password = reader.readLine();

        return new Credentials(username, password, Role.UTENTE);
    }
    public static User insertUserDetails(Credentials cred) throws IOException {

    // implementare inserimento dati in fase di registrazione
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // inserimento codice fiscale
        System.out.println("Please enter your Codice Fiscale: ");
        String cf = reader.readLine();

        // inserimento nome
        System.out.println("Please enter your name: ");
        String nome = reader.readLine();

        // inserimento cognome
        System.out.println("Please enter your surname: ");
        String cognome = reader.readLine();

        // inserimento data di nascita
        java.sql.Date dataDiNascita = null;
        try{
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            System.out.println("Please enter your birth date: [YYYY-MM-DD]");
            Date date = dateFormat.parse(reader.readLine());
            dataDiNascita = new java.sql.Date(date.getTime());
        } catch (ParseException e) {
            System.out.println(" Please enter a valid birth date !");
            e.printStackTrace();
            System.exit(1);
        }

        // inserimento citta di nascita
        System.out.println("Please enter your birth city: ");
        String cittaDiNascita = reader.readLine();

        // inserimento  via;
        System.out.println("Please enter your shipping address: ");
        String via = reader.readLine();

        // inserimento civico;
        System.out.println("Please enter your house number: ");
        String civico = reader.readLine();

        // inserimento comune;
        System.out.println("Please enter your town: ");
        String comune = reader.readLine();

        // inserimento cap
        System.out.println("Please enter your postal code: ");
        String cap = reader.readLine();

        System.out.println("** CREDIT CARD **");

        // numero carta
        System.out.println("Please enter the credit card number: ");
        String cartaDiCredito = reader.readLine();

        return new  User(cf,nome,cognome,dataDiNascita, cittaDiNascita, via, civico,comune,cap, cartaDiCredito, cred.getUsername(), cred.getPassword());


    }


    public static CreditCard insertCreditCard(User user) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // nome intestatario carta
        System.out.println("Please enter the cardholder name: ");
        String nomeIntestatario = reader.readLine();

        // cognome intestatario carta
        System.out.println("Please enter the cardholder surname: ");
        String cognomeIntestatario = reader.readLine();

        // scadenza carta
        String scadenzaCarta = null;
        try {
            SimpleDateFormat formato = new SimpleDateFormat("MM-yy");
            formato.setLenient(false);
            System.out.println("Please enter the card expire date [MM-YY]: ");
            scadenzaCarta = reader.readLine();
            formato.parse(scadenzaCarta);
        }
        catch (ParseException e){
            System.out.println(" Please enter a valid expire date !");
            e.printStackTrace();
            System.exit(1);
        }
        // cvv
        System.out.println("Please enter the card CVV: ");
        String cvv = reader.readLine();

        return new CreditCard(user.getCartaDiCredito(),nomeIntestatario,cognomeIntestatario,scadenzaCarta, cvv);
    }
}
