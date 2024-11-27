package Gestione;

import Ricerca.Ricerca;
import SalvataggioCaricamento.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.math.RoundingMode;
import java.text.*;
import java.time.LocalDate;
import java.time.format.*;
import java.util.ArrayList;

/**
 * La classe BilancioPanel implementa il pannello dove si trova la tabella di bilancio e tutti gli elementi che permettono:<br>
 * • La gestione (inserimento,modifica,eliminazioni delle voci nella tabella)<br>
 * • Il salvataggio e il caricamento<br>
 * • La ricerca di un determinato caratterre/parola<br>
 * • L'esportazione (in formato .csv,.txt,.xlsx,.ods)<br>
 * • La stampa tramite una delle stampanti configurate dal sistema operativo<br>
 * Ogni voce viene salvata in un arraylist di tipo "VoceBilancio" in modo ordinato<br>
 *<br>
 * @author Samuele Carpi
 */
public class BilancioPanel extends JPanel implements ActionListener{
    private JTable t;
    private JLabel lInserisci,lData,lDescrizione,lAmmontare,lTipo,lSomma,lGiorno,lMese,lAnno,lDataIniziale,lDataFinale,lFiltro,lFiltroDAA,lSalvaCarica,lNomeFileSalva,lNomeFileEsporta,lTipoFileEsporta,lNomeFileCarica,lRicerca,lStampa;
    private JTextField tData,tDescrizione,tAmmontare,tDataIniziale,tDataFinale,tNomeFileSalva,tNomeFileCarica,tNomeFileEsporta,tRicerca;
    private JComboBox selectGG,selectMM,selectAA,selectTipo,selectEsporta;
    private JButton bAdd,bModifica,bRemove,bFiltra,bFiltraDaA,bReset,bSalva,bCarica,bEsporta,bCerca,bSuccessivo,bStampa;
    private MyTableModel dataModel;
    private int rigaSelezionata;
    private String formattedString;
    private DateTimeFormatter f;
    private ArrayList<VoceBilancio> lista;
    private SalvaCarica salvaCarica;
    private Ricerca ricerca;

    /** Costruttore del pannello dove si inizializzano tutti gli oggetti/variabili
     */
    public BilancioPanel() {
        f=DateTimeFormatter.ofPattern("dd/MM/yyyy");
        formattedString=LocalDate.now().format(f);

        lista=new ArrayList<VoceBilancio>();

        dataModel=new MyTableModel();
        dataModel.addColumn("N°");
        dataModel.addColumn("Data");
        dataModel.addColumn("Descrizione");
        dataModel.addColumn("Ammontare");
        dataModel.addColumn("Tipo");
        t=new JTable(dataModel);
        salvaCarica=new SalvaCarica(this);
        dataModel=(MyTableModel) t.getModel();

        //fields
        lSomma=new JLabel("Somma algebrica delle voci: "+"0.00€");

        //1° riga
        lInserisci=new JLabel("INSERIMENTO/MODIFICA");
        lData=new JLabel("Data");
        tData=new JTextField(formattedString,10);
        lDescrizione=new JLabel("Descrizione");
        tDescrizione=new JTextField(10);
        lAmmontare=new JLabel("Ammontare (€)");
        tAmmontare=new JTextField("0.00",10);
        lTipo=new JLabel("Tipo");
        selectTipo=new JComboBox();
        selectTipo.addItem("Spesa");
        selectTipo.addItem("Entrata");
        bAdd=new JButton("AGGIUNGI");
        bAdd.addActionListener(this);
        bModifica=new JButton("MODIFICA");
        bModifica.addActionListener(this);
        bModifica.setEnabled(false);
        bRemove=new JButton("CANCELLA");
        bRemove.addActionListener(this);
        bRemove.setEnabled(false);

        //2° riga
        lFiltro=new JLabel("FILTRO PER GG/MM/AA");
        lGiorno=new JLabel("Giorno");
        selectGG=new JComboBox();
        selectGG.addItem("-");
        for(int gg=1;gg<=31;gg++){
            if(gg<10)
                selectGG.addItem("0"+gg);
            else
                selectGG.addItem(gg);
        }
        lMese=new JLabel("Mese");
        selectMM = new JComboBox();
        selectMM.addItem("-");
        for(int mm=1;mm<=12;mm++){
            if(mm<10)
                selectMM.addItem("0"+mm);
            else
                selectMM.addItem(mm);
        }
        lAnno=new JLabel("Anno");
        selectAA = new JComboBox();
        selectAA.addItem("-");
        for(int aa=2023;aa>=1970;aa--){
            selectAA.addItem(aa);
        }
        bFiltra=new JButton("FILTRA");
        bFiltra.addActionListener(this);

        //3° riga
        lFiltroDAA=new JLabel("FILTRO DA/A");
        lDataIniziale=new JLabel("Da");
        tDataIniziale=new JTextField("--/--/----",10);
        lDataFinale=new JLabel("A");
        tDataFinale=new JTextField(formattedString,10);
        bFiltraDaA=new JButton("FILTRA");
        bFiltraDaA.setActionCommand("FILTRA2");
        bFiltraDaA.addActionListener(this);
        bReset=new JButton("RESET");
        bReset.addActionListener(this);

        //4° riga
        lSalvaCarica=new JLabel("SALVA/CARICA");
        lNomeFileSalva=new JLabel("Nome file");
        tNomeFileSalva=new JTextField(10);
        bSalva=new JButton("SALVA");
        bSalva.addActionListener(this);
        lNomeFileCarica=new JLabel("Nome file");
        tNomeFileCarica=new JTextField(10);
        bCarica=new JButton("CARICA");
        bCarica.addActionListener(this);
        lNomeFileEsporta=new JLabel("Nome file");
        tNomeFileEsporta=new JTextField(10);
        lTipoFileEsporta=new JLabel("Tipo file");
        selectEsporta=new JComboBox();
        selectEsporta.addItem(".csv");
        selectEsporta.addItem(".txt");
        selectEsporta.addItem(".xlsx");
        selectEsporta.addItem(".ods");
        bEsporta=new JButton("ESPORTA");
        bEsporta.addActionListener(this);

        //5° riga
        lRicerca=new JLabel("RICERCA");
        tRicerca=new JTextField(10);
        bCerca=new JButton("CERCA");
        bCerca.addActionListener(this);
        bSuccessivo=new JButton("NEXT");
        bSuccessivo.addActionListener(this);

        //6°riga
        lStampa=new JLabel("STAMPA");
        bStampa=new JButton("STAMPA");
        bStampa.addActionListener(this);

        salvaCarica.autoSalva();
        setLayout();

        //listener evento di cambio riga per settare i campi per la modifica
        t.getSelectionModel().addListSelectionListener(e->{
            bModifica.setEnabled(true);
            bRemove.setEnabled(true);
            rigaSelezionata=t.getSelectedRow();
            if(rigaSelezionata!=-1){
                String data=t.getValueAt(rigaSelezionata, 1).toString();
                String descrizione=t.getValueAt(rigaSelezionata, 2).toString();
                String ammontare=t.getValueAt(rigaSelezionata, 3).toString();
                int tipo=0;
                if(t.getValueAt(rigaSelezionata, 4).toString().equals("Entrata"))
                    tipo=1;
                tData.setText(data);
                tDescrizione.setText(descrizione);
                tAmmontare.setText(ammontare);
                selectTipo.setSelectedIndex(tipo);
            }
        });
    }

    /**gestione delle chiamate delle funzioni per le azioni sulla tabella
     * @param e nome dell'azione
     * */
    public void actionPerformed(ActionEvent e) {
        String data=tData.getText();
        float ammontare=arrotonda(Float.parseFloat(tAmmontare.getText()));
        String descrizione=tDescrizione.getText();
        String tipo=selectTipo.getSelectedItem().toString();
        String nomeFileSalva=tNomeFileSalva.getText();
        String nomeFileCarica=tNomeFileCarica.getText();
        String nomeFileEsporta=tNomeFileEsporta.getText();
        String extFile=selectEsporta.getSelectedItem().toString();
        String testoRicerca=tRicerca.getText();
        try{
            switch(e.getActionCommand()){
                case "AGGIUNGI": aggiungi(data,descrizione,ammontare,tipo);
                    break;
                case "MODIFICA": modifica(data,descrizione,ammontare,tipo);
                    break;
                case "CANCELLA": cancella();
                    break;
                case "FILTRA": filtraGiornoMeseAnno();
                    break;
                case "FILTRA2": filtraDaA();
                    break;
                case "RESET": resetData();
                    break;
                case "SALVA": salvaCarica.salva(nomeFileSalva);
                    break;
                case "CARICA": salvaCarica.carica(nomeFileCarica);
                    break;
                case "CERCA": ricerca(testoRicerca);
                    break;
                case "NEXT": ricerca.next();
                    break;
                case "ESPORTA": esporta(nomeFileEsporta,extFile);
                    break;
                case "STAMPA": stampa();
                    break;
            }
            lSomma.setText("Somma algebrica delle voci: "+sommaVoci()+"€");
        }catch(NumberFormatException error){
            JOptionPane.showMessageDialog(null, "Ammontare inserito non corretto!");
        }
    }

    /**restituisce la somma algebrica dell'ammontare del bilancio
     * @return la somma
     */
    private float sommaVoci(){
        float somma=0;
        for (VoceBilancio voceBilancio : lista) {
            float ammontare=arrotonda(voceBilancio.getAmmontare());
            if (voceBilancio.getTipo().equals("Spesa"))
                somma -= ammontare;
            else
                somma += ammontare;
        }
        return somma;
    }

    /**aggiunge una riga al bilancio
     * @param data data della voce
     * @param descrizione descrizione della voce
     * @param ammontare ammontare della voce
     * @param tipo tipo della voce
     */
    private void aggiungi(String data,String descrizione,float ammontare,String tipo){
        if(!controllaData(data))
            JOptionPane.showMessageDialog(null, "Data inserita non corretta!\n (si ricordi che il formato corretto è 'gg/mm/aaaa')");
        else if(ammontare<0){
            JOptionPane.showMessageDialog(null, "Ammontare inserito non corretto!");
        }else if(descrizione.equals("")){
            JOptionPane.showMessageDialog(null, "Inserire una descrizione");
        }else{
            int id=0;
            VoceBilancio vb=new VoceBilancio(id,data,descrizione,ammontare,tipo);
            vb.setId(lista.size()+1);
            lista.add(vb);
            Object[] rowData =new Object[5];
            rowData[0]=vb.getId();
            rowData[1]=vb.getData();
            rowData[2]=vb.getDescrizione();
            rowData[3]=vb.getAmmontare();
            rowData[4]=vb.getTipo();
            dataModel.addRow(rowData);
            }
        }

    /**modifica una riga al bilancio
     * @param data data della voce
     * @param descrizione descrizione della voce
     * @param ammontare ammontare della voce
     * @param tipo tipo della voce
     */
    private void modifica(String data,String descrizione,float ammontare,String tipo){
        if(!controllaData(data))
            JOptionPane.showMessageDialog(null, "Data inserita non corretta!\n (si ricordi che il formato corretto è 'gg/mm/aaaa')");
        else if(ammontare<0){
            JOptionPane.showMessageDialog(null, "Ammontare inserito non corretto!");
        }else if(descrizione.equals("")){
            JOptionPane.showMessageDialog(null, "Inserire una descrizione");
        }else{
            int id=(int) t.getValueAt(rigaSelezionata, 0);
            VoceBilancio vb=lista.get(id-1);
            vb.setData(data);
            vb.setDescrizione(descrizione);
            vb.setAmmontare(ammontare);
            vb.setTipo(tipo);
            t.setValueAt(vb.getData(),rigaSelezionata,1);
            t.setValueAt(vb.getDescrizione(),rigaSelezionata,2);
            t.setValueAt(vb.getAmmontare(),rigaSelezionata,3);
            t.setValueAt(vb.getTipo(),rigaSelezionata,4);
        }
    }

    /**cancella le righe selezionate della tabella
     */
    private void cancella(){
        int[] rows=t.getSelectedRows();
        for(int i=0;i<rows.length;i++){
            dataModel.removeRow(rows[i]-i);
            lista.remove(rows[i]-i);
        }
        for(int i=0;i<lista.size();i++){
            t.setValueAt(i+1,i,0);
            lista.get(i).setId(i+1);
        }
    }

    /**riempie la tabella con una lista in input (usata per i filtri e il caricamento)
     * @param l lista da stampare
     */
    public void stampaRigheFiltrate(ArrayList<VoceBilancio> l){
        clear();
        for (VoceBilancio vb : l) {
            Object[] rowData=new Object[5];
            rowData[0]=vb.getId();
            rowData[1]=vb.getData();
            rowData[2]=vb.getDescrizione();
            rowData[3]=vb.getAmmontare();
            rowData[4]=vb.getTipo();
            dataModel.addRow(rowData);
        }
    }

    /**elimina tutte le righe della tabella
     */
    private void clear(){
        dataModel.setRowCount(0);
    }

    /**primo filtro con JComboBox per filtrare giorno/mese/anno
     */
    private void filtraGiornoMeseAnno(){
        String gg=selectGG.getSelectedItem().toString();
        String mm=selectMM.getSelectedItem().toString();
        String aa=selectAA.getSelectedItem().toString();
        String dataString=gg+"/"+mm+"/"+aa;
        if(!controllaData(dataString) && !formatoJComboBox(gg,mm,aa)){
            JOptionPane.showMessageDialog(null, "Data inserita non corretta!");
        }else{
            ArrayList<VoceBilancio> l=new ArrayList<>();
            for (VoceBilancio voceBilancio : lista) {
                String data=voceBilancio.getData();
                String[] s=data.split("/");
                String giorno=s[0];
                String mese=s[1];
                String anno=s[2];
                if (gg.equals("-") && mm.equals("-") && aa.equals("-")) {
                    l.add(voceBilancio);
                } else if (gg.equals("-") && mm.equals("-") && aa.equals(anno)) {
                    l.add(voceBilancio);
                } else if (gg.equals("-") && mm.equals(mese) && aa.equals(anno)) {
                    l.add(voceBilancio);
                } else if (gg.equals(giorno) && mm.equals(mese) && aa.equals(anno)) {
                    l.add(voceBilancio);
                }
            }
            stampaRigheFiltrate(l);
        }
    }

    /**secondo filtro per filtrare le voci comprese tra due date
     */
    private void filtraDaA(){
        String dataIniziale=tDataIniziale.getText();
        String dataFinale=tDataFinale.getText();
        if(controllaData(dataIniziale) && controllaData(dataFinale)){
            LocalDate da=LocalDate.parse(dataIniziale,f);
            LocalDate a=LocalDate.parse(dataFinale,f);
            if(!da.isAfter(a)){
                ArrayList<VoceBilancio> l=new ArrayList<>();
                for (VoceBilancio voceBilancio : lista) {
                    String data=voceBilancio.getData();
                    LocalDate dataDate=LocalDate.parse(data, f);
                    if (!dataDate.isBefore(da) && !dataDate.isAfter(a)) {
                        l.add(voceBilancio);
                    }
                }
                stampaRigheFiltrate(l);
            }else{
                JOptionPane.showMessageDialog(null, "Data iniziale maggiore di quella finale!");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Data inserita non corretta!");
        }
    }

    /**resetta i valori dei JTextField a default e le righe della tabella
     */
    private void resetData(){
        tDataIniziale.setText("--/--/----");
        tDataFinale.setText(formattedString);
        stampaRigheFiltrate(lista);
    }

    /**crea una nuova istanza di ricerca e chiama il metodo
     * @param testoRicerca valore da cercare nella tabella
     */
    public void ricerca(String testoRicerca){
        ricerca=new Ricerca(t,testoRicerca);
        ricerca.cerca();
    }

    /**crea una nuova istanza in base al tipo di file e chiama il metodo salva() sfruttando il polimorfismo
     * @param nomeFileEsporta nome del file da esportare
     * @param extFile tipo di estensione che si vuole dare al file
     */
    private void esporta(String nomeFileEsporta,String extFile){
        switch(extFile){
            case ".csv": SalvaCSV salvaCSV=new SalvaCSV(this);
                         salvaCSV.salva(nomeFileEsporta);
                break;
            case ".txt": SalvaTXT salvaTXT=new SalvaTXT(this);
                         salvaTXT.salva(nomeFileEsporta);
                break;
            case ".xlsx": SalvaXLSX salvaXLSX=new SalvaXLSX(this);
                          salvaXLSX.salva(nomeFileEsporta);
                break;
            case ".ods": SalvaODS salvaODS=new SalvaODS(this);
                         salvaODS.salva(nomeFileEsporta);
                break;
        }
    }

    /**stampa la tabella tramite una delle stampanti configurate dal sistema operativo
     */
    private void stampa(){
        try{
            t.print();
        }catch(PrinterException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    //controlli & utilities

    /**arrotonda a due cifre la parte decimale di un numero
     * @param x numero da arrotondare
     * @return numero arrotondato
     */
    private float arrotonda(float x){
        DecimalFormat newFormat=new DecimalFormat("#.##");
        newFormat.setRoundingMode(RoundingMode.CEILING);
        DecimalFormatSymbols formatSymbol=new DecimalFormatSymbols();
        formatSymbol.setDecimalSeparator('.');
        newFormat.setDecimalFormatSymbols(formatSymbol);
        return Float.parseFloat(newFormat.format(x));
    }

    /**controlla che le combinazioni dei JComboBox siano corrette
     * @param gg valore del giorno
     * @param mm valore del mese
     * @param aa valore dell'anno
     * @return true se la combinazione è corretta
     */
    private boolean formatoJComboBox(String gg,String mm,String aa){
        if(gg.equals("-") && mm.equals("-") && !aa.equals("-"))
            return true;
        else if(gg.equals("-") && !mm.equals("-") && !aa.equals("-"))
            return true;
        else if(!gg.equals("-") && !mm.equals("-") && !aa.equals("-"))
            return true;
        else if(gg.equals("-") && mm.equals("-") && aa.equals("-"))
            return true;
        return false;
    }

    /**controlla che la data esista e sia nel formato corretto
     * @param data data da controllare
     * @return true se è corretta
     */
    private boolean controllaData(String data) {
        DateFormat f1=new SimpleDateFormat("dd/MM/yyyy");
        f1.setLenient(false);
        try{
            f1.parse(data);
            LocalDate.parse(data,f);
            int annoOra=LocalDate.now().getYear();
            String[] s=data.split("/");
            int anno=Integer.parseInt(s[2]);
            if(anno>annoOra){
                return false;
            }
        }catch(NumberFormatException | ParseException | DateTimeParseException e){
            return false;
        }
        return true;
    }

    //layout

    /**imposta il layout del pannello con GridBagLayout
     */
    public void setLayout(){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=10;

        gbc.insets=new Insets(0,0,30,0);
        add(t,gbc);
        add(new JScrollPane(t),gbc);

        gbc.gridy=1;
        gbc.insets=new Insets(0,0,30,0);
        add(lSomma,gbc);

        gbc.gridwidth=1;
        gbc.insets=new Insets(1,5,1,5);
        gbc.gridx=1;
        gbc.gridy=2;
        add(lData,gbc);
        gbc.gridx=2;
        add(lDescrizione,gbc);
        gbc.gridx=3;
        add(lAmmontare,gbc);
        gbc.gridx=4;
        add(lTipo,gbc);
        gbc.gridy=3;
        gbc.gridx=0;
        gbc.ipady=5;
        add(lInserisci,gbc);
        gbc.gridx=1;
        add(tData,gbc);
        gbc.gridx=2;
        add(tDescrizione,gbc);
        gbc.gridx=3;
        add(tAmmontare,gbc);
        gbc.gridx=4;
        add(selectTipo,gbc);
        gbc.gridx=5;
        gbc.ipady=15;
        add(bAdd,gbc);
        gbc.gridx=6;
        add(bModifica,gbc);
        gbc.gridx=7;
        add(bRemove,gbc);

        gbc.insets=new Insets(30,0,0,0);
        gbc.ipady=0;
        gbc.gridy=4;
        gbc.gridx=1;
        add(lGiorno,gbc);
        gbc.gridx=2;
        add(lMese,gbc);
        gbc.gridx=3;
        add(lAnno,gbc);
        gbc.insets=new Insets(0,5,0,5);
        gbc.fill= GridBagConstraints.HORIZONTAL;
        gbc.ipady=5;
        gbc.gridy=5;
        gbc.gridx=0;
        add(lFiltro,gbc);
        gbc.gridx=1;
        add(selectGG,gbc);
        gbc.gridx=2;
        add(selectMM,gbc);
        gbc.gridx=3;
        add(selectAA,gbc);
        gbc.ipady=15;
        gbc.gridx=4;
        add(bFiltra,gbc);

        gbc.insets=new Insets(30,0,0,0);
        gbc.fill= GridBagConstraints.NONE;
        gbc.ipady=0;
        gbc.gridx=1;
        gbc.gridy=6;
        add(lDataIniziale,gbc);
        gbc.gridx=2;
        add(lDataFinale,gbc);
        gbc.insets=new Insets(0,5,0,5);
        gbc.fill= GridBagConstraints.HORIZONTAL;
        gbc.ipady=5;
        gbc.gridy=7;
        gbc.gridx=0;
        add(lFiltroDAA,gbc);
        gbc.gridx=1;
        add(tDataIniziale,gbc);
        gbc.gridx=2;
        add(tDataFinale,gbc);
        gbc.fill= GridBagConstraints.NONE;
        gbc.gridx=3;
        gbc.ipady=15;
        add(bFiltraDaA,gbc);
        gbc.gridx=4;
        add(bReset,gbc);

        gbc.insets=new Insets(30,0,0,0);
        gbc.fill=GridBagConstraints.NONE;
        gbc.ipady=0;
        gbc.gridx=1;
        gbc.gridy=8;
        add(lNomeFileSalva,gbc);
        gbc.gridx=3;
        add(lNomeFileCarica,gbc);
        gbc.gridx=5;
        add(lNomeFileEsporta,gbc);
        gbc.gridx=6;
        add(lTipoFileEsporta,gbc);
        gbc.insets=new Insets(0,5,0,5);
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.ipady=5;
        gbc.gridy=9;
        gbc.gridx=0;
        add(lSalvaCarica,gbc);
        gbc.gridx=1;
        add(tNomeFileSalva,gbc);
        gbc.gridx=3;
        add(tNomeFileCarica,gbc);
        gbc.gridx=5;
        add(tNomeFileEsporta,gbc);
        gbc.gridx=6;
        add(selectEsporta,gbc);
        gbc.fill=GridBagConstraints.NONE;
        gbc.gridx=2;
        gbc.ipady=15;
        add(bSalva,gbc);
        gbc.gridx=4;
        add(bCarica,gbc);
        gbc.gridx=7;
        add(bEsporta,gbc);

        gbc.insets=new Insets(30,5,0,5);
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.ipady=5;
        gbc.gridy=10;
        gbc.gridx=0;
        add(lRicerca,gbc);
        gbc.gridx=1;
        add(tRicerca,gbc);
        gbc.gridx=2;
        gbc.ipady=15;
        gbc.fill=GridBagConstraints.NONE;
        add(bCerca,gbc);
        gbc.anchor=GridBagConstraints.WEST;
        gbc.gridx=3;
        add(bSuccessivo,gbc);

        gbc.insets=new Insets(30,5,0,5);
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.ipady=5;
        gbc.gridy=11;
        gbc.gridx=0;
        add(lStampa,gbc);
        gbc.ipady=15;
        gbc.gridx=1;
        add(bStampa,gbc);
    }

    //metodi get e set

    /** restituisce la lista relativa al bilancio
     * @return la lista
     */
    public ArrayList<VoceBilancio> getLista() {
        return lista;
    }

    /** setta la lista con dei valori nuovo
     * @param lista valori nuovi
     */
    public void setLista(ArrayList<VoceBilancio> lista) {
        this.lista = lista;
    }

    /** restituisce il modello della tabella
     * @return il modello
     */
    public MyTableModel getDataModel() {
        return dataModel;
    }
}
