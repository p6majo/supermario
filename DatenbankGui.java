


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;

/**
 * The class DatenbankTestGui
 *
 * @author p6majo
 * @version 2020-12-03
 */
public class DatenbankGui extends JFrame {
    private JTextField textFieldName;
    private JTextField textFieldVorname;
    private JTextField textFieldNick;
    private JButton buttonRegist;
    private JButton buttonLogin;
    private JCheckBox checkboxLogin;
    private JButton buttonNutzer;
    private JButton buttonLevel;
    private JTable table;
    private JPanel haupkomponente;

    //Datenbank
    private DatabaseConnector dbc;
    private SuperMarioGui superMarioGui;

    /*
     *********************************************
     ***           Attributes           **********
     *********************************************
     */



    /*
     **********************************************
     ****           Constructors         **********
     **********************************************
     */


    public DatenbankGui(SuperMarioGui pSuperMarioGui) {
        setupUI();
        superMarioGui = pSuperMarioGui;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(800, 600);


        setResizable(true);
        Container cp = getContentPane();
        cp.add(haupkomponente);
        show();
        pack();

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource("spielstandDb.db");
        if (url != null) {
            dbc = new DatabaseConnector("", 0, url.getPath(), "", "");
            System.out.println("DatenbankGui: "+"Verbindung zur Datenbank: " + dbc.getErrorMessage());
        }
    }


    /*
     ***********************************************
     ***           Getters              ************
     ***********************************************
     */



    /*
     ***********************************************
     ***           Setters              ************
     ***********************************************
     */



    /*
     ***********************************************
     ***           Public methods       ************
     ***********************************************
     */


    public Nutzer getNutzer(String pName, String pVorname){
        dbc.executeStatement("Select nutzerId,nickname From Nutzer WHERE nachname='"+pName+"' AND vorname ='"+pVorname+"'");
        if (dbc.getErrorMessage()!=null)
            System.out.println("Fehlermeldung: "+dbc.getErrorMessage());
        else {
            QueryResult res = dbc.getCurrentQueryResult();

            if (res!=null) {
                String[][] data = res.getData();
                if (data.length > 0) {
                    int nutzerId = Integer.parseInt(data[0][0]);
                    String nickname = data[0][1];

                    Nutzer nutzer = new Nutzer(nutzerId, pName, pVorname, nickname);
                    return nutzer;
                }
            }
        }
        return null;
    }

    public void login(){
        String name = this.textFieldName.getText();
        String vorname = this.textFieldVorname.getText();
        Nutzer nutzer = getNutzer(name,vorname);
        if (nutzer!=null){
            this.checkboxLogin.setSelected(true);
            textFieldNick.setText(nutzer.getNickname());
            textFieldName.setEnabled(false);
            textFieldVorname.setEnabled(false);
            textFieldNick.setEnabled(false);
            superMarioGui.loginNutzer(nutzer);
        }
        else
            this.checkboxLogin.setSelected(false);
    }

    public void addNutzer(String pName, String pVorname, String pNickname){
        //check, whether user has been created already
        Nutzer nutzer  = getNutzer(pName,pVorname);
        if (nutzer==null) {
            dbc.executeStatement("INSERT into Nutzer(nachname,vorname,nickname) Values('" + pName + "','" + pVorname + "','" + pNickname + "')");
            login();
        }
        else {
            System.out.println("Nutzer existiert schon");
            login();
        }
        if (dbc.getErrorMessage()!=null)
            System.out.println("Fehlermeldung: "+dbc.getErrorMessage());
    }


    public void listeNutzer(){
        String out = "";
        dbc.executeStatement("SELECT * FROM 'Nutzer'");
        if (dbc.getErrorMessage()!=null)
            System.out.println("Fehlermeldung: "+dbc.getErrorMessage());
        else {
            QueryResult res = dbc.getCurrentQueryResult();


            if (res!=null) {

                DefaultTableModel model = new DefaultTableModel(new String[]{"", "", "", ""}, 0);

                model.addRow(new String[]{res.getColumnNames()[0], res.getColumnNames()[1], res.getColumnNames()[2], res.getColumnNames()[3]});


                for (String[] row: res.getData())
                    model.addRow(new String[]{row[0] , row[1], row[2], row[3]});


                table.setModel(model);
                table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent listSelectionEvent) {
                        int row = listSelectionEvent.getFirstIndex();
                        textFieldVorname.setText((String) model.getValueAt(row,2));
                        textFieldName.setText((String) model.getValueAt(row,1));
                        login();
                    }
                });

                for (int i = 0; i < res.getColumnCount(); i++) {
                    out += res.getColumnNames()[i] + "\t";
                }

                out += "\n";
                out += "_________________________________________________________\n";

                String[][] data = res.getData();
                for (int i = 0; i < data.length; i++) {
                    for (int j = 0; j < data[i].length; j++) {
                        out += data[i][j] + "\t";
                    }
                    out += "\n";
                }

                System.out.println(out);
            }
        }
    }

    public void listeLevel(){
        String out = "";
        dbc.executeStatement("SELECT * FROM 'Level'");
        if (dbc.getErrorMessage()!=null)
            System.out.println("Fehlermeldung: "+dbc.getErrorMessage());
        else {
            QueryResult res = dbc.getCurrentQueryResult();


            if (res!=null) {

                DefaultTableModel model = new DefaultTableModel(new String[]{"", "", ""}, 0);

                model.addRow(new String[]{res.getColumnNames()[0], res.getColumnNames()[1], res.getColumnNames()[2]});


                for (String[] row: res.getData())
                    model.addRow(new String[]{row[0] , row[1], row[2]});


                table.setModel(model);


                for (int i = 0; i < res.getColumnCount(); i++) {
                    out += res.getColumnNames()[i] + "\t";
                }

                out += "\n";
                out += "_________________________________________________________\n";

                String[][] data = res.getData();
                for (int i = 0; i < data.length; i++) {
                    for (int j = 0; j < data[i].length; j++) {
                        out += data[i][j] + "\t";
                    }
                    out += "\n";
                }

                System.out.println(out);
            }
        }
    }

    /*
     ***********************************************
     ***           Private methods      ************
     ***********************************************
     */


    /*
     ***********************************************
     ***           Overrides            ************
     ***********************************************
     */


    @Override
    public String toString() {
        return "DatenbankTestGui";
    }


    private void setupUI() {
        haupkomponente = new JPanel();
        haupkomponente.setLayout(new GridBagLayout());
        final JLabel label1 = new JLabel();
        label1.setText("Name");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        haupkomponente.add(label1, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        haupkomponente.add(spacer1, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Vorname");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        haupkomponente.add(label2, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("Nickname");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        haupkomponente.add(label3, gbc);
        textFieldName = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        haupkomponente.add(textFieldName, gbc);
        textFieldVorname = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        haupkomponente.add(textFieldVorname, gbc);
        textFieldNick = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        haupkomponente.add(textFieldNick, gbc);
        buttonRegist = new JButton();
        buttonRegist.setText("Registrieren");
        buttonRegist.addActionListener(actionEvent -> addNutzer(textFieldName.getText(),textFieldVorname.getText(),textFieldNick.getText()));
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        haupkomponente.add(buttonRegist, gbc);
        buttonLogin = new JButton();
        buttonLogin.setText("Login");
        buttonLogin.addActionListener(actionEvent -> login());
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        haupkomponente.add(buttonLogin, gbc);
        checkboxLogin = new JCheckBox();
        checkboxLogin.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        haupkomponente.add(checkboxLogin, gbc);
        final JScrollPane scrollPane1 = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.BOTH;
        haupkomponente.add(scrollPane1, gbc);
        table = new JTable();
        scrollPane1.setViewportView(table);
        buttonNutzer = new JButton();
        buttonNutzer.setText("Nutzer");
        buttonNutzer.addActionListener(actionEvent->listeNutzer());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        haupkomponente.add(buttonNutzer, gbc);
        buttonLevel = new JButton();
        buttonLevel.setText("Level");
        buttonLevel.addActionListener(actionEvent -> listeLevel());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        haupkomponente.add(buttonLevel, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        haupkomponente.add(spacer2, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        haupkomponente.add(spacer3, gbc);
    }

  
}
