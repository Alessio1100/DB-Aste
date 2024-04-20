package it.uniroma2.dicii.bd.model.domain;

public class Offer {

    private String utente;

    private Float importo;

    private String oggetto;

    private Float valoreMassimoAutomatico;

    public Offer(){}

    public Offer(String utente, String oggetto, Float importo, Float valoreMassimoAutomatico){

        this.utente = utente;
        this.importo = importo;
        this.oggetto = oggetto;
        this.valoreMassimoAutomatico = valoreMassimoAutomatico;
    }

    public String getUtente() {
        return utente;
    }

    public Float getImporto() {
        return importo;
    }

    public String getOggetto() {
        return oggetto;
    }

    public Float getValoreMassimoAutomatico() {
        return valoreMassimoAutomatico;
    }

    public void setUtente(String utente) {
        this.utente = utente;
    }

    public void setImporto(Float importo) {
        this.importo = importo;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public void setValoreMassimoAutomatico(Float valoreMassimoAutomatico) {
        this.valoreMassimoAutomatico = valoreMassimoAutomatico;
    }

}
