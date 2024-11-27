package SalvataggioCaricamento;

import Gestione.BilancioPanel;
import Gestione.VoceBilancio;
import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * La classe "SalvaCSV", che estende la classe "SalvaCarica", implementa il salvataggio della tabella in un file ".csv" con la possibilità di
 * sovrascrivere file già esistenti. Il tutto sfruttando l'override del metodo "salva()" dalla classe padre<br>
 *<br>
 * @author Samuele Carpi
 */
public class SalvaCSV extends SalvaCarica{
    /**
     * costruttore del salvataggio con il tipo csv
     * @param bilancio pannello associato al salvataggio
     */
    public SalvaCSV(BilancioPanel bilancio){
        super(bilancio);
    }

    /**salva il bilancio con un x nome in un file .csv
     * @param nomeFileEsporta nome con cui salvare il file
     */
    @Override
    public void salva(String nomeFileEsporta){
        String tipo=".csv";
        boolean check=check(nomeFileEsporta,tipo);
        if(check){
            ArrayList<VoceBilancio> lista=getBilancio().getLista();
            try{
                FileWriter writer=new FileWriter(nomeFileEsporta+".csv");
                StringBuffer sb=new StringBuffer();
                sb.append("N°").append(",").append("Data").append(",").append("Descrizione").append(",").append("Ammontare").append(",").append("Tipo").append(System.lineSeparator());
                for(VoceBilancio vb : lista){
                    sb.append(vb.getId()).append(",").append(vb.getData()).append(",").append(vb.getDescrizione()).append(",").append(vb.getAmmontare()).append(",").append(vb.getTipo()).append(System.lineSeparator());
                }
                writer.write(sb.toString());
                writer.close();
            } catch(IOException e){
                JOptionPane.showMessageDialog(null, "Ops! Qualcosa è andato storto!");
            }
        }
    }
}
