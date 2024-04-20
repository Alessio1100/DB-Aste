package it.uniroma2.dicii.bd.model.domain;

public class Admin {

    private String username;
    private String password;

    public Admin(){

    }

    public Admin(String usernme, String password){
        this.username = usernme;
        this.password = password;

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
