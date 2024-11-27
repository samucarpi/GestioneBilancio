package SalvataggioCaricamento;

import Gestione.*;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

/**
 * La classe "SalvaCarica" implementa il salvataggio e caricamento della tabella in un file ".dat" con la possibilità di
 * sovrascrivere file già esistenti.<br>
 * Inoltre salva ogni 30 secondi su un file temporaneo la tabella e le sue voci.<br>
 *<br>
 * @author Samuele Carpi
 */
public class SalvaCarica{
    private BilancioPanel bilancio;

    /**
     * costruttore del salvataggio/caricamento
     * @param bilancio pannello associato al salvataggio/caricamento
     */
    public SalvaCarica(BilancioPanel bilancio){
        this.bilancio=bilancio;
    }

    /**controlla se il file esiste e richiede se si vuole sovrascrivere il nuovo file
     * @param nomeFileSalva nome con cui salvare il file
     * @param tipo tipo di estensione del file
     * @return true se è da sovrascrivere
     */
    public boolean check(String nomeFileSalva,String tipo){
        boolean flag=false;
        File file=new File(nomeFileSalva+tipo);
        if(file.exists()){
            int dialog=JOptionPane.showConfirmDialog(null, "Sovrascrivere il bilancio '"+nomeFileSalva+"'?", "WARNING", JOptionPane.YES_NO_OPTION);
            if(dialog==JOptionPane.YES_OPTION) {
                flag=true;
            }
        }else{
            flag=true;
        }
        return flag;
    }

    /**salva il bilancio con un x nome in un file .dat
     * @param nomeFileSalva nome con cui salvare il file
     */
    public void salva(String nomeFileSalva){
        String tipo=".dat";
        boolean check=check(nomeFileSalva,tipo);
        if(check){
            ArrayList<VoceBilancio> lista=bilancio.getLista();
            try{
                FileOutputStream f=new FileOutputStream(nomeFileSalva + ".dat");
                ObjectOutputStream os=new ObjectOutputStream(f);
                os.writeObject(lista);
                os.close();
            } catch(IOException e){
                JOptionPane.showMessageDialog(null, "Ops! Qualcosa è andato storto!");
            }
        }
    }

    /**carica nella tabella uno specifico file .dat
     * @param nomeFileCarica nome del file da caricare
     */
    public void carica(String nomeFileCarica){
        File f=new File(nomeFileCarica+".dat");
        ArrayList<VoceBilancio> l=null;
        if(f.exists()){
            FileInputStream fin;
            ObjectInputStream is;
            try{
                fin=new FileInputStream(nomeFileCarica+".dat");
                is=new ObjectInputStream(fin);
                l=(ArrayList<VoceBilancio>)(is.readObject());
                is.close();
            }catch(IOException | ClassNotFoundException e){
                JOptionPane.showMessageDialog(null, "Ops! Qualcosa è andato storto!");
            }
            bilancio.setLista(l);
            bilancio.stampaRigheFiltrate(l);
        }else{
            JOptionPane.showMessageDialog(null, "File cercato non esistente!");
        }
    }

    /**crea un thread che chiama una funzione che salva il bilancio in un file temporaneo ogni 30 secondi
     */
    public void autoSalva() {
        Runnable r=new Runnable(){
            @Override
            public void run() {
                while(true){
                    try{
                        salvaTemporaneo();
                        Thread.sleep(30000);
                    }catch(InterruptedException e){
                        break;
                    }
                }
            }
        };
        Thread salvaThread=new Thread(r);
        salvaThread.start();
    }

    /**salva il bilancio in un file temporaneo sostituendo quello precedente
     */
    public void salvaTemporaneo() {
        ArrayList<VoceBilancio> lista=bilancio.getLista();
        try{
            FileOutputStream fos=new FileOutputStream("temporaneo.tmp");
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            oos.writeObject(lista);
            oos.close();
            fos.close();
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "Ops! Qualcosa è andato storto!");
        }
    }

    /**restituisce il pannello del bilancio
     * @return restituisce il pannello
     */
    public BilancioPanel getBilancio() {
        return bilancio;
    }
}
