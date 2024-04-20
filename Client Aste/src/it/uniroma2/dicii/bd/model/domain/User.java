package it.uniroma2.dicii.bd.model.domain;

import javax.xml.crypto.Data;
import java.util.Date;

public class User {

    private String cf;
    private String nome;
    private String cognome;
    private Date dataDiNascita;
    private String cittaDiNascita;
    private String via;
    private String civico;
    private String comune;
    private String cap;
    private String cartaDiCredito;
    private String username;
    private String password;

    public User(){}

    public User(String cf, String nome, String cognome, Date dataDiNascita, String cittaDiNascita, String via, String civico, String comune, String cap, String cartaDiCredito, String username, String password ){

        this.cf = cf;
        this.nome = nome;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;
        this.cittaDiNascita = cittaDiNascita;
        this.via = via;
        this.civico = civico;
        this.comune = comune;
        this.cap = cap;
        this.cartaDiCredito = cartaDiCredito;
        this.username=username;
        this.password =password;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Date getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(Date dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public String getCittaDiNascita() {
        return cittaDiNascita;
    }

    public void setCittaDiNascita(String cittaDiNascita) {
        this.cittaDiNascita = cittaDiNascita;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getCivico() {
        return civico;
    }

    public void setCivico(String civico) {
        this.civico = civico;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    public String getCartaDiCredito() {
        return cartaDiCredito;
    }

    public void setCartaDiCredito(String cartaDiCredito) {
        this.cartaDiCredito = cartaDiCredito;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }
}
