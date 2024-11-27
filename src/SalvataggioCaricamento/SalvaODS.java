package SalvataggioCaricamento;

import Gestione.BilancioPanel;
import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * La classe "SalvaODS", che estende la classe "SalvaCarica", implementa il salvataggio della tabella in un file ".ods" con la possibilità di
 * sovrascrivere file già esistenti. Il tutto sfruttando l'override del metodo "salva()" dalla classe padre<br>
 *<br>
 * @author Samuele Carpi
 */
public class SalvaODS extends SalvaCarica{
    /**
     * costruttore del salvataggio con il tipo ods
     * @param bilancio pannello associato al salvataggio
     */
    public SalvaODS(BilancioPanel bilancio){
        super(bilancio);
    }

    /**salva il bilancio con un x nome in un file .ods
     * @param nomeFileEsporta nome con cui salvare il file
     */
    @Override
    public void salva(String nomeFileEsporta){
        String tipo=".ods";
        boolean check=check(nomeFileEsporta,tipo);
        if(check){
            try {
                final File f=new File(nomeFileEsporta+".ods");
                SpreadSheet.createEmpty(getBilancio().getDataModel()).saveAs(f);
                OOUtils.open(f);
            }catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Ops! Qualcosa è andato storto!");
            }

        }
    }
}
