package it.uniroma2.dicii.bd.model.domain;

import java.util.Date;
import java.time.LocalDateTime;

public class Item {

    private String id;
    private String descrizione;
    private String stato;
    private Float baseAsta;
    private String dimensioni;
    private LocalDateTime inizioAsta;
    private Integer durataAsta;
    private Date termineAsta;
    private String tipo;
    private Float prezzoDiVendita;
    private Integer numeroOfferte;
    private Float valoreMaxOfferta;
    private String categoria;
    private User utente;

    public Item(){}

    public Item(String id, String descrizione, String stato, Float baseAsta, String dimensioni, LocalDateTime inizioAsta, Integer durataAsta, Date termineAsta, String tipo, Float prezzoDiVendita, Integer numeroOfferte, Float valoreMaxOfferta, String categoria, User utente){

        this.id=id;
        this.descrizione=descrizione;
        this.stato=stato;
        this.baseAsta=baseAsta;
        this.dimensioni=dimensioni;
        this.inizioAsta = inizioAsta;
        this.durataAsta=durataAsta;
        this.termineAsta=termineAsta;
        this.tipo=tipo;
        this.prezzoDiVendita=prezzoDiVendita;
        this.numeroOfferte=numeroOfferte;
        this.valoreMaxOfferta = valoreMaxOfferta;
        this.categoria=categoria;
        this.utente=utente;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public Float getBaseAsta() {
        return baseAsta;
    }

    public void setBaseAsta(Float baseAsta) {
        this.baseAsta = baseAsta;
    }

    public LocalDateTime getInizioAsta() {
        return inizioAsta;
    }

    public void setInizioAsta(LocalDateTime inizioAsta) {
        this.inizioAsta = inizioAsta;
    }

    public String getDimensioni() {
        return dimensioni;
    }

    public void setDimensioni(String dimensioni) {
        this.dimensioni = dimensioni;
    }

    public Integer getDurataAsta() {
        return durataAsta;
    }

    public void setDurataAsta(Integer durataAsta) {
        this.durataAsta = durataAsta;
    }

    public Date getTermineAsta() {
        return termineAsta;
    }

    public void setTermineAsta(Date termineAsta) {
        this.termineAsta = termineAsta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Float getPrezzoDiVendita() {
        return prezzoDiVendita;
    }

    public void setPrezzoDiVendita(Float prezzoDiVendita) {
        this.prezzoDiVendita = prezzoDiVendita;
    }

    public Float getValoreMaxOfferta() {
        return valoreMaxOfferta;
    }

    public void setValoreMaxOfferta(Float valoreMaxOfferta) {
        this.valoreMaxOfferta = valoreMaxOfferta;
    }

    public Integer getNumeroOfferte() {
        return numeroOfferte;
    }

    public void setNumeroOfferte(Integer numeroOfferte) {
        this.numeroOfferte = numeroOfferte;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public User getUtente() {
        return utente;
    }

    public void setUtente(User utente) {
        this.utente = utente;
    }


}
