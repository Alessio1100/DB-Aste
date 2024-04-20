package it.uniroma2.dicii.bd.model.domain;

public class CreditCard {

    private String numero;
    private String nomeIntestatario;
    private String cognomeIntestatario;
    private String dataDiScadenza;
    private String cvv;

    public CreditCard(){}

    public CreditCard(String numero, String nomeIntestatario, String cognomeIntestatario, String dataDiScadenza, String cvv){

        this.numero = numero;
        this.nomeIntestatario = nomeIntestatario;
        this.cognomeIntestatario = cognomeIntestatario;
        this.dataDiScadenza = dataDiScadenza;
        this.cvv = cvv;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNomeIntestatario() {
        return nomeIntestatario;
    }

    public void setNomeIntestatario(String nomeIntestatario) {
        this.nomeIntestatario = nomeIntestatario;
    }

    public String getCognomeIntestatario() {
        return cognomeIntestatario;
    }

    public void setCognomeIntestatario(String cognomeIntestatario) {
        this.cognomeIntestatario = cognomeIntestatario;
    }

    public String getDataDiScadenza() {
        return dataDiScadenza;
    }

    public void setDataDiScadenza(String dataDiScadenza) {
        this.dataDiScadenza = dataDiScadenza;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
