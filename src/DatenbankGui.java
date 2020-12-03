package src;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JButton buttonErgebnisse;
    private JTable table1;
    private JPanel haupkomponente;

    //Datenbank
    private DatabaseConnector dbc;

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


    public DatenbankGui() {
        setupUI();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(800, 600);


        setResizable(true);
        Container cp = getContentPane();
        cp.add(haupkomponente);
        show();
        pack();

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource("res/spielstandDb.db");
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
        }
        else
            this.checkboxLogin.setSelected(false);
    }

    public void addNutzer(String pName, String pVorname, String pNickname){
        dbc.executeStatement("INSERT into Nutzer(nachname,vorname,nickname) Values('"+pName+"','"+pVorname+"','"+pNickname+"')");
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

    public static void main(String[] args) {
        new DatenbankGui();
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
        table1 = new JTable();
        scrollPane1.setViewportView(table1);
        buttonNutzer = new JButton();
        buttonNutzer.setText("Nutzer");
        buttonNutzer.addActionListener(actionEvent->listeNutzer());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        haupkomponente.add(buttonNutzer, gbc);
        buttonErgebnisse = new JButton();
        buttonErgebnisse.setText("Ergebnisse");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        haupkomponente.add(buttonErgebnisse, gbc);
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
