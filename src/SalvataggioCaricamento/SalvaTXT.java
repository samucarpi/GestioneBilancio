package SalvataggioCaricamento;

import Gestione.BilancioPanel;
import Gestione.VoceBilancio;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * La classe "SalvaTXT", che estende la classe "SalvaCarica", implementa il salvataggio della tabella in un file ".txt" con la possibilità di
 * sovrascrivere file già esistenti. Il tutto sfruttando l'override del metodo "salva()" dalla classe padre<br>
 *<br>
 * @author Samuele Carpi
 */
public class SalvaTXT extends SalvaCarica{

    /**
     * costruttore del salvataggio con il tipo txt
     * @param bilancio pannello associato al salvataggio
     */
    public SalvaTXT(BilancioPanel bilancio){
        super(bilancio);
    }

    /**salva il bilancio con un x nome in un file .txt
     * @param nomeFileEsporta nome con cui salvare il file
     */
    @Override
    public void salva(String nomeFileEsporta){
        String tipo=".txt";
        boolean check=check(nomeFileEsporta,tipo);
        if(check){
            ArrayList<VoceBilancio> lista=getBilancio().getLista();
            try{
                FileWriter writer=new FileWriter(nomeFileEsporta+".txt");
                StringBuffer sb=new StringBuffer();
                for(VoceBilancio vb : lista){
                    sb.append(vb.getId()).append(" ").append(vb.getData()).append(" ").append(vb.getDescrizione()).append(" ").append(vb.getAmmontare()).append(" ").append(vb.getTipo()).append(System.lineSeparator());
                }
                writer.write(sb.toString());
                writer.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Ops! Qualcosa è andato storto!");
            }
        }
    }
}
