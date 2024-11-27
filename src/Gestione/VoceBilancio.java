package Gestione;

import java.io.Serializable;

/**
 * La classe "VoceBilancio", che implementa la classe "Serializable" per rendere possibile il salvataggio, implementa la creazione di una voce.<br>
 * All'interno di essa sono presenti tutti i metodi get/set sulle categorie della voce: id,data,descrizione,ammontare,tipo<br>
 *<br>
 * @author Samuele Carpi
 */
public class VoceBilancio implements Serializable {
    private int id;
    private String data;
    private String descrizione;
    private float ammontare;
    private String tipo;


    /**
     * costruttore della voce
     * @param id numero di riga
     * @param data data della voce
     * @param descrizione descrizione della voce
     * @param ammontare ammontare della voce
     * @param tipo tipo della voce
     */
    public VoceBilancio(int id,String data,String descrizione,float ammontare,String tipo){
        this.id=id;
        this.data=data;
        this.descrizione=descrizione;
        this.ammontare=ammontare;
        this.tipo=tipo;
    }

    //metodi get

    /**
     * restituisce l'id della voce
     * @return restituisce l'id
     */
    public int getId() {
        return id;
    }
    /**
     * restituisce l'id della voce
     * @return restituisce l'id
     */
    public String getData() {
        return data;
    }
    /**
     * restituisce la descrizione della voce
     * @return restituisce la descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }
    /**
     * restituisce l'ammontare della voce
     * @return restituisce l'ammontare
     */
    public float getAmmontare() {
        return ammontare;
    }
    /**
     * restituisce il tipo della voce
     * @return restituisce il tipo
     */
    public String getTipo() {
        return tipo;
    }

    //metodi set

    /** setta la variabile id con un valore nuovo
     * @param id valore nuovo
     */
    public void setId(int id) {
        this.id = id;
    }

    /** setta la variabile data con un valore nuovo
     * @param data valore nuovo
     */
    public void setData(String data) {
        this.data = data;
    }

    /** setta la variabile descrizione con un valore nuovo
     * @param descrizione valore nuovo
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /** setta la variabile ammontare con un valore nuovo
     * @param ammontare valore nuovo
     */
    public void setAmmontare(float ammontare) {
        this.ammontare = ammontare;
    }

    /** setta la variabile tipo con un valore nuovo
     * @param tipo valore nuovo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
