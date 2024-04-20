package it.uniroma2.dicii.bd.model.domain;

public class Category {

    private String nome;

    private String categoriaGenitore;

    public Category(){}

    public Category(String nome, String categoriagenitore){
        this.nome = nome;
        this.categoriaGenitore = categoriagenitore;
    }

    public String getNome() {
        return nome;
    }
    public String getCategoriaGenitore(){
        return categoriaGenitore;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCategoriaGenitore(String categoriaGenitore) {
        this.categoriaGenitore = categoriaGenitore;
    }
}
