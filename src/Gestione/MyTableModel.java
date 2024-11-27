package Gestione;
import javax.swing.table.DefaultTableModel;

/**
 * La classe "MyTableModel", che estende la classe "DefaultTableModel", implementa la gestione del modello della tabella all'interno del pannello.<br>
 * Setta l'impossibilità di editare le celle all'interno della tabella<br>
 *<br>
 * @author Samuele Carpi
 */
public class MyTableModel extends DefaultTableModel{

    /**
     * rende la tabella non editabile
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
