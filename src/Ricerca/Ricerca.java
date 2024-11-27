package Ricerca;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * La classe "Ricerca" implementa la ricerca di un valore all'interno di una tabella
 * con la possibilità di evidenziare man mano le celle successive che rispondono ai
 * requisiti<br>
 *<br>
 * @author Samuele Carpi
 */
public class Ricerca{
    private JTable t;
    private int currentRow,nextColumn;
    private String testo;
    private final Color coloreSelezione=new JTable().getSelectionBackground();

    /**
     * costruttore della ricerca
     * @param t tabella su cui effettuare la ricerca
     * @param testo testo da cercare
     */
    public Ricerca(JTable t,String testo){
        this.t=t;
        this.testo=testo;
        t.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent evt){
                t.setSelectionBackground(coloreSelezione);
                t.setColumnSelectionAllowed(false);
            }
        });
        currentRow=-1;
        nextColumn=0;
        t.setSelectionBackground(Color.yellow);
        t.setColumnSelectionAllowed(true);
    }

    /**cerca il valore in input nel bilancio e restituisce la prima cella in cui esso compare salvando la posizione
     */
    public void cerca(){
        if(!testo.equals("")){
            for(int r=0;r<t.getRowCount();r++){
                for(int c=0;c<t.getColumnCount();c++){
                    if(t.getValueAt(r,c).toString().contains(testo)){
                        t.getSelectionModel().setSelectionInterval(r,r);
                        t.getColumnModel().getSelectionModel().setSelectionInterval(c, c);
                        currentRow=r;
                        nextColumn=c+1;
                        return;
                    }
                }
            }
            t.setSelectionBackground(coloreSelezione);
            t.setColumnSelectionAllowed(false);
            JOptionPane.showMessageDialog(null, "Testo cercato non trovato in bilancio!");
        }else{
            JOptionPane.showMessageDialog(null, "Impossibile cercare un valore vuoto!");
        }
    }

    /**continua la ricerca salvando sempre la posizione della cella
     */
    public void next() {
        if(currentRow!=-1){
            for(int r=currentRow;r<t.getRowCount();r++){
                for(int c=nextColumn;c<t.getColumnCount();c++){
                    if(t.getValueAt(r,c).toString().contains(testo)) {
                        t.getSelectionModel().setSelectionInterval(r, r);
                        t.getColumnModel().getSelectionModel().setSelectionInterval(c, c);
                        currentRow=r;
                        nextColumn=c+1;
                        return;
                    }
                }
                nextColumn=0;
            }
            t.setSelectionBackground(coloreSelezione);
            t.setColumnSelectionAllowed(false);
            currentRow=-1;
        }
    }
}


