package website.marcioheleno.service;

import website.marcioheleno.model.bloco.pagina.Pagina;

public class Lru {

    Pagina[] lru;

    public Lru(int tam) {
        this.lru = new Pagina[tam];
    }

    public Pagina[] getLru() {
        return lru;
    }

    public void setLru(Pagina[] lru) {
        this.lru = lru;
    }

}
