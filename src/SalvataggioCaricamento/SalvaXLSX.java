package SalvataggioCaricamento;

import Gestione.BilancioPanel;
import Gestione.VoceBilancio;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;

/**
 * La classe "SalvaXLSX", che estende la classe "SalvaCarica", implementa il salvataggio della tabella in un file ".xlsx" con la possibilità di
 * sovrascrivere file già esistenti. Il tutto sfruttando l'override del metodo "salva()" dalla classe padre<br>
 *<br>
 * @author Samuele Carpi
 */
public class SalvaXLSX extends SalvaCarica{
    /**
     * costruttore del salvataggio con il tipo xlsx
     * @param bilancio pannello associato al salvataggio
     */
    public SalvaXLSX(BilancioPanel bilancio){
        super(bilancio);
    }

    /**salva il bilancio con un x nome in un file .xlsx
     * @param nomeFileEsporta nome con cui salvare il file
     */
    @Override
    public void salva(String nomeFileEsporta){
        String tipo=".xlsx";
        boolean check=check(nomeFileEsporta,tipo);
        if(check){
            int nRiga=0,nColonna=0;
            Row riga;
            Cell cella;
            ArrayList<VoceBilancio> lista=getBilancio().getLista();
            XSSFWorkbook workbook=new XSSFWorkbook();
            XSSFSheet sheet=workbook.createSheet(nomeFileEsporta);
            riga=sheet.createRow(nRiga++);
            cella=riga.createCell(nColonna++);
            cella.setCellValue("N°");
            cella=riga.createCell(nColonna++);
            cella.setCellValue("Data");
            cella=riga.createCell(nColonna++);
            cella.setCellValue("Descrizione");
            cella=riga.createCell(nColonna++);
            cella.setCellValue("Ammontare");
            cella=riga.createCell(nColonna++);
            cella.setCellValue("Tipo");
            for(VoceBilancio vb : lista){
                riga=sheet.createRow(nRiga++);
                nColonna=0;
                cella=riga.createCell(nColonna++);
                cella.setCellValue(vb.getId());
                cella=riga.createCell(nColonna++);
                cella.setCellValue(vb.getData());
                cella=riga.createCell(nColonna++);
                cella.setCellValue(vb.getDescrizione());
                cella=riga.createCell(nColonna++);
                cella.setCellValue(vb.getAmmontare());
                cella=riga.createCell(nColonna++);
                cella.setCellValue(vb.getTipo());
            }
            try{
                FileOutputStream os=new FileOutputStream(nomeFileEsporta+".xlsx");
                workbook.write(os);
                workbook.close();
            }catch(IOException e){
                JOptionPane.showMessageDialog(null, "Ops! Qualcosa è andato storto!");
            }
        }
    }
}
