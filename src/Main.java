
import Gestione.BilancioPanel;

import javax.swing.*;
/**
 * Programma che permette di eseguire le seguenti azioni su un bilancio:<br>
 * • Gestione (inserimento,modifica,eliminazioni delle voci)<br>
 * • Salvataggio e caricamento<br>
 * • Ricerca d'informazioni<br>
 * • Esportazione (in formato .csv,.txt,.xlsx,.ods)<br>
 * • Stampa<br>
 * <br>
 * @author Samuele Carpi
 */
public class Main {
    /**
     * metodo main
     * @param args
     */
    public static void main(String[] args) {
        JFrame f=new JFrame("Bilancio");
        BilancioPanel b=new BilancioPanel();
        f.add(b);
        f.pack();
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}