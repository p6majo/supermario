package qwirkle;


import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TreeMap;

/**
 * The class QwirkleGui
 *
 * @author p6majo
 * @version 2019-06-20
 */
public class QwirkleGui extends JFrame implements MouseListener, MouseMotionListener {


    /*
     *********************************************
     ***           Attributes           **********
     *********************************************
     */


    //Panels zur Strukturierung des GUIs
    private JPanel hauptKomponente;
    private JPanel spielerPanelContainer;
    private JPanel vorratPanel;
    private JPanel controlPanel;
    private JPanel feldPanel;

    //Aktionsbutton
    private JButton startBtn;
    private JButton tauscheBtn;
    private JButton fertigButton;
    private JButton highScoreButton;


    private SpielerPanel[] spielerPanel;
    private QwirkleLabel[] vorratLabel;

    private JLabel spielfeldLabel;
    private JLabel titleLabel;
    private JLabel beutelLabel;

    //mit der Maus bewegtes Bild
    private BufferedImage floatingImage;
    private int floatingImageX,floatingImageY;


    //Attribute zur Konfiguration der Fensterdimensionen
    private int frameWidth = 820;
    private int frameHeight = 700;
    private int rand = 10;

    //Attribute zur Konfiguration der Spielfelddimensionen
    private int sfWidth;
    private int sfHeight;

    //Dimensionierung des sichtbaren Spielfelds,
    //starte mit Zeilen {-1,0,1} und
    //Spalten {-1,0,1}
    //Wird ein Stein in eine Randspalte oder -zeile abgelegt,
    //wird das Spielfeld automatisch erweitert
    private int minz = -1;
    private int maxz = 1;
    private int mins = -1;
    private int maxs = 1;

    //doppelt gepuffered Grafik, fuer schnelles Zeichnen des Spielfeldes
    private BufferedImage offscreenImage, onscreenImage;
    private Graphics2D offscreen, onscreen;
    private int feldgroesse;

    private List<Stein> spielfeld;
    private Stein bewegterStein;

    private boolean starteTausch =false;

    private Beutel tmpBeutel;

    private TreeMap<Symbol,Image> symbolImageMapOriginale;
    private TreeMap<Symbol,Image> symbolImageMap;


    private QwirkleClient client;
    private boolean aktiv;


    /*
     **********************************************
     ****           Constructors         **********
     **********************************************
     */

    public QwirkleGui(QwirkleClient pSpieler) {
        // Frame-Initialisierung
        super("Abschlussprojekt (E. Karan, M. Lob, J. Martin, J. Pickshaus)");

        this.client = pSpieler;

        erzeugeUI();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(frameWidth, frameHeight);


        setResizable(true);
        Container cp = getContentPane();
        cp.add(hauptKomponente);
        show();



        //bereite Grafik vor, auf das Spielfeld zu zeichnen
        sfHeight = feldPanel.getHeight()-rand;
        sfWidth = feldPanel.getWidth()-rand;

        offscreenImage = new BufferedImage(sfWidth, sfHeight, BufferedImage.TYPE_INT_ARGB);
        onscreenImage  = new BufferedImage(sfWidth, sfHeight, BufferedImage.TYPE_INT_ARGB);
        offscreen = offscreenImage.createGraphics();
        onscreen  = onscreenImage.createGraphics();


        ImageIcon icon = new ImageIcon(onscreenImage);
        spielfeldLabel = new JLabel(icon);
        feldPanel.add(spielfeldLabel);
        spielfeldLabel.setBounds(0,0,sfWidth,sfHeight);

        spielfeld = new List<>();

        feldPanel.addMouseListener(this);
        feldPanel.addMouseMotionListener(this);

        this.aktiv = false;
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

    /**
     *
     * @param status
     */
    public void aktualisiereStatus(String status){
        startBtn.setEnabled(false);
        String[] parts = status.split(QwirkleServer.SEP);

        int index = this.client.gibSpielerIndex(); //zu welchem Spieler gehoert das Gui
        int pos = 0;
        int anzahl = Integer.parseInt(parts[pos++]); //Anzahl der Spieler

        //aktualisiere Steine im Beutel
        this.aktualisereBeutelSteine(Integer.parseInt(parts[pos++])); //Steine im Beutel

        //aktualisiere aktiven Spieler
        int aktiverSpieler = Integer.parseInt(parts[pos++]);
        for (int i = 0; i < anzahl; i++) {
            spielerPanel[i].setzeAktiv(false);
        }
        if (aktiverSpieler>-1)
            spielerPanel[aktiverSpieler].setzeAktiv(true);
        if (aktiverSpieler!=index){
            aktiv = false;
            fertigButton.setEnabled(false);
            tauscheBtn.setEnabled(false);
        }else{
            aktiv = true;
            fertigButton.setEnabled(true);
            tauscheBtn.setEnabled(true);
        }

        //aktualisiere Punktest"ande
        for (int i = 0; i < anzahl; i++)
            spielerPanel[i].setzePunkte(Integer.parseInt(parts[pos++]));

        //aktualisere Spielersteine
        String[] steine=null;
        for (int i = 0; i < anzahl; i++) {
            if (i==index){
                String steineString = parts[pos++];
                if (steineString.length()>0)
                    steine = steineString.split(Spieler.SEP);
            }
            else pos++;
        }

        for (int i = 0; i < 6; i++)
            vorratLabel[i].loesche();

        if (steine!=null) {
            for (int i = 0; i < steine.length; i++)
                vorratLabel[i].setzeStein(new Stein(steine[i]));

            if (steine.length < 6) //wenn Stein gelegt wurde, kann nicht mehr getauscht werden.
                tauscheBtn.setEnabled(false);
        }

        //aktualisiere Spielfeld
        spielfeld = new List<Stein>();
        for (int i=pos;i<parts.length;i++){
            Stein stein = new Stein(parts[i]);
            //Passe Spielfeldgroesse an, falls Stein an den Rand gelegt wird
            int zeile = stein.gibZeile();
            int spalte = stein.gibSpalte();

            if (zeile==minz){
                minz--;
            }
            if (zeile==maxz) {
                maxz++;
            }
            if(spalte==mins) {
                mins--;
            }
            if (spalte==maxs) {
                maxs++;
            }

            spielfeld.append(stein);
            zeichneSpielfeld();
        }




    }


    public void aktualisiereSpieler(String[] namen){
        for (int i = 0; i < namen.length; i++) {
            spielerPanel[i].setzeLeer(false);
            spielerPanel[i].setzeSpielerName(namen[i]);
        }
        for (int i = namen.length; i < 4; i++) {
            spielerPanel[i].setzeLeer(true);
        }
    }



    /**
     * Die erste Zahl zeigt den naechsten aktiven Spieler.
     * Die folgenden Zahlen sind die Punktest&auml;nde der Spieler der Reihe nach.
     * @param punkte
     */
    public void aktualisierePunktestand(int[] punkte) {

        for (int i = 0; i < 4; i++) {
            spielerPanel[i].setzeAktiv(false);
        }

        spielerPanel[punkte[0]].setzeAktiv(true);

        for (int i = 1; i < punkte.length; i++)
            spielerPanel[i-1].setzePunkte(punkte[i]);
    }


    public void setzeTitle(String titlePart){
        this.titleLabel.setText("Qwirkle "+titlePart);
    }

    public void zeigeHighScore(HighScoreEintrag... highscore){

        JTable table = new JTable();

        DefaultTableModel model = new DefaultTableModel(new String[]{"","","",""},0);

        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        model.addRow(new String[]{"Punkte","Vorname","Name","Datum"});
        for (HighScoreEintrag highScoreEintrag : highscore) {
            model.addRow(new String[]{highScoreEintrag.getPunkte()+"",highScoreEintrag.getVorname(),highScoreEintrag.getName(),format.format(highScoreEintrag.getDatum())});
        }

        table.setModel(model);
        JOptionPane.showMessageDialog(this.hauptKomponente,table,"Ewige Qwirkle-Bestenliste",JOptionPane.CLOSED_OPTION);
    }

    public void beendeSpiel(String status){
        aktualisiereStatus(status);
        holeHighScore();
    }
    /*
     ***********************************************
     ***           Private methods      ************
     ***********************************************
     */


    private void aktualisereBeutelSteine(int anzahl) {
        beutelLabel.setText(anzahl+" Steine im Beutel!");
    }


    private void zeichneSpielfeld(){

        int b = sfWidth/(maxs-mins+1);
        int h = sfHeight/(maxz-minz+1);

        feldgroesse = Math.min(b,h)-4;//-4 damit der letzte Rand auch sichtbar ist.

        if (offscreen!=null) {

            offscreen.setColor(Color.white);
            offscreen.fillRect(0, 0, sfWidth + rand, sfHeight + rand);

            offscreen.setStroke(new BasicStroke(4.f));
            offscreen.setColor(Color.blue);

            //zeichne Gitter
            for (int z = minz; z <= maxz + 10; z++) {
                double x0 = rand;
                double x1 = sfWidth + rand;
                double y0 = rand + feldgroesse * (z - minz);
                offscreen.draw(new Line2D.Double(x0, y0, x1, y0));
            }

            for (int s = mins; s <= maxs + 10; s++) {
                double y0 = rand;
                double y1 = rand + sfHeight;
                double x0 = rand + feldgroesse * (s - mins);
                offscreen.draw(new Line2D.Double(x0, y0, x0, y1));
            }

            spielfeld.toFirst();
            while (spielfeld.hasAccess()) {
                Stein stein = spielfeld.getContent();
                int posZ = stein.gibZeile();
                int posS = stein.gibSpalte();
                int x = (posS - mins) * feldgroesse;
                int y = (posZ - minz) * feldgroesse;
                stein.erzeugeGraphik(offscreen, feldgroesse, x, y, rand);
                spielfeld.next();
            }

            if (floatingImage != null) {

                offscreen.setStroke(new BasicStroke(8.f));
                offscreen.setColor(Color.black);

                int[] zeilenSpalten = zeileSpalteVonKoordinaten(floatingImageX + feldgroesse / 2, floatingImageY + feldgroesse / 2);
                int z = zeilenSpalten[0];
                int s = zeilenSpalten[1];
                int[] koordinaten = koordinatenVonZeileSpalte(z, s);
                int x0 = koordinaten[0];
                int y0 = koordinaten[1];
                //rahmen fuer aktive Zelle
                offscreen.draw(new Line2D.Double(x0, y0, x0, y0 + feldgroesse));
                offscreen.draw(new Line2D.Double(x0 + feldgroesse, y0, x0 + feldgroesse, y0 + feldgroesse));
                offscreen.draw(new Line2D.Double(x0, y0, x0 + feldgroesse, y0));
                offscreen.draw(new Line2D.Double(x0, y0 + feldgroesse, x0 + feldgroesse, y0 + feldgroesse));
                offscreen.drawImage(floatingImage, floatingImageX, floatingImageY, null);
            }
            onscreen.drawImage(offscreenImage, 0, 0, null);
            hauptKomponente.repaint();
        }
    }


    private void legeStein(Stein stein) {
        int zeile = stein.gibZeile();
        int spalte = stein.gibSpalte();
        stein.setzeZeile(zeile);
        stein.setzeSpalte(spalte);
        spielfeld.append(stein);
        floatingImage = null;


        zeichneSpielfeld();
    }

    private void tauschen(){
        List<Stein> zutauschen = new List<>();
        for (int i = 0; i < 6; i++) {
            if (vorratLabel[i].istTauschbar())
                zutauschen.append((vorratLabel[i].gibStein()));
        }
        client.tauschen(zutauschen, client.gibSpielerIndex());
    }

    private void spielStarten(){
        this.client.spielStarten();
    }

    private void holeHighScore(){
        this.client.holeHighScore();
    }

    private void setzeFloatingImage(Stein stein){
        if (floatingImage==null)
            floatingImage = new BufferedImage(feldgroesse,feldgroesse,BufferedImage.TYPE_INT_ARGB);
        Graphics g = floatingImage.getGraphics();
        if (g!=null)
            stein.erzeugeGraphik(g,feldgroesse,0,0,0);
    }


    /**
     * Erzeugung der GUI-Komponenten
     */
    private void erzeugeUI() {
        spielerPanel = new SpielerPanel[4];
        for (int i = 0; i < 4; i++) {
            spielerPanel[i] = new SpielerPanel();
            spielerPanel[i].setzeSpielerName("Spieler "+(i+1));
            spielerPanel[i].setzePunkte(0);
            spielerPanel[i].setzeAktiv(false);
            spielerPanel[i].setVisible(false);
        }

        vorratLabel = new QwirkleLabel[6];
        for (int i = 0; i < 6; i++) {
            vorratLabel[i] = new QwirkleLabel(this);
            vorratLabel[i].setBorder(BorderFactory.createRaisedBevelBorder());
            vorratLabel[i].setBackground(Color.blue);
            vorratLabel[i].addMouseListener(this);
        }

        hauptKomponente = new JPanel();
        hauptKomponente.setLayout(new GridBagLayout());
        hauptKomponente.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-10084576)));

        //titelzeiel
        titleLabel = new JLabel();
        Font label1Font = this.erzeugeSchrift("Open Sans Extrabold", Font.BOLD, 36, titleLabel.getFont());
        if (label1Font != null) titleLabel.setFont(label1Font);
        titleLabel.setText("Qwirkle");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        hauptKomponente.add(titleLabel, gbc);

        beutelLabel = new JLabel();
        beutelLabel.setText("108 Steine im Beutel");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        hauptKomponente.add(beutelLabel, gbc);


        spielerPanelContainer = new JPanel();
        spielerPanelContainer.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.4;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        hauptKomponente.add(spielerPanelContainer, gbc);

        for (int i = 0; i < 4; i++) {
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            spielerPanelContainer.add(spielerPanel[i].gibHauptKomponente(), gbc);
        }

        vorratPanel = new JPanel();
        vorratPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.6;
        gbc.fill = GridBagConstraints.BOTH;
        hauptKomponente.add(vorratPanel, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Linke Taste: Spielstein aufs Feld legen, Rechte Taste: Fuer Tausch markieren");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 7;
        gbc.anchor = GridBagConstraints.WEST;
        vorratPanel.add(label2, gbc);

        for (int i = 0; i < 6 ; i++) {
            vorratLabel[i].setHorizontalAlignment(0);
            vorratLabel[i].setText("");
            gbc = new GridBagConstraints();
            gbc.gridx = i;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.ipadx = 10;
            gbc.ipady = 10;
            vorratPanel.add(vorratLabel[i], gbc);
        }

        controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.4;
        gbc.fill = GridBagConstraints.BOTH;
        hauptKomponente.add(controlPanel, gbc);



        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        startBtn = new JButton();
        startBtn.setText("Start");
        startBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                spielStarten();
            }
        });
        controlPanel.add(startBtn, gbc);


        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        fertigButton = new JButton();
        fertigButton.setText("Fertig");
        fertigButton.setEnabled(false);
        fertigButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                client.rundeBeenden();
            }
        });
        controlPanel.add(fertigButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        tauscheBtn = new JButton();
        tauscheBtn.setText("Tausch");
        tauscheBtn.setEnabled(false);
        tauscheBtn.addActionListener(e -> {
            tauschen();
        });
        controlPanel.add(tauscheBtn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        highScoreButton = new JButton();
        highScoreButton.setText("HighScore");
        highScoreButton.addActionListener(e -> {
            holeHighScore();
        });
        controlPanel.add(highScoreButton, gbc);




        feldPanel = new JPanel();
        feldPanel.setLayout(null);//erlaubt absolute Platzierung des SpielfeldLabels
        feldPanel.addComponentListener(new ComponentListener(){
            @Override
            public void componentResized(ComponentEvent e) {
                sfWidth = feldPanel.getWidth()-rand;
                sfHeight = feldPanel.getHeight()-rand;
                zeichneSpielfeld();
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.6;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        hauptKomponente.add(feldPanel, gbc);
        //feldPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), null));
    }

    private Font erzeugeSchrift(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    private int[] zeileSpalteVonKoordinaten(int pX, int pY){
        int[] zeileSpalte = new int[2];
        zeileSpalte[0] = minz+(pY-rand)/feldgroesse;
        zeileSpalte[1] = mins+(pX-rand)/feldgroesse;

        return zeileSpalte;
    }

    private int[] koordinatenVonZeileSpalte(int pZ, int pS){
        int[] koordinaten = new int[2];
        koordinaten[1] = (pZ-minz)*feldgroesse+rand;
        koordinaten[0] =(pS-mins)*feldgroesse+rand;
        return koordinaten;
    }


    /*
     ***********************************************
     **** Overrides  MouseMotionListener   *********
     ***********************************************
     */


    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(floatingImage !=null && e.getSource()==feldPanel) {
            floatingImageX = e.getX() - feldgroesse / 2;
            floatingImageY = e.getY() - feldgroesse / 2;
            zeichneSpielfeld();
        }
        int[] pos = zeileSpalteVonKoordinaten(e.getX(),e.getY());
    }


    /*
     ***********************************************
     **** Overrides  MousListener          *********
     ***********************************************
     */

    @Override
    public void mouseClicked(MouseEvent e) {


        QwirkleLabel label = null;
        if (aktiv && e.getSource() instanceof QwirkleLabel){
            //deselektiere alle Label
            if (e.getButton()==MouseEvent.BUTTON1){
                starteTausch  = false;
                for (int i = 0; i < 6; i++) {
                    vorratLabel[i].deselektiere();
                }
            }
            else if (e.getButton()==MouseEvent.BUTTON3){
                if (!starteTausch){
                    starteTausch = true;
                    for (int i = 0; i < 6; i++) {
                        vorratLabel[i].deselektiere();
                    }
                }
            }

            label = (QwirkleLabel) e.getSource();
            label.mouseEvent(e);
            bewegterStein = label.gibStein();
        }
        else if (aktiv && e.getSource()==feldPanel && floatingImage !=null && bewegterStein!=null){
            int[] zeileSpalte = zeileSpalteVonKoordinaten(floatingImageX+feldgroesse/2,floatingImageY+feldgroesse/2);
            int z = zeileSpalte[0];
            int s = zeileSpalte[1];
            client.legeStein(bewegterStein,z,s);
            bewegterStein = null;
            floatingImage = null;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

        if (e.getSource()==feldPanel){
            QwirkleLabel selektiert = null;
            for (int i = 0; i < 6; i++) {
                if( vorratLabel[i].istLegbar() ) {
                    selektiert = vorratLabel[i];
                    break;
                }
            }

            if (selektiert!=null){
                setzeFloatingImage(selektiert.gibStein());
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource()==feldPanel && floatingImage !=null) {
            floatingImage = null;
            zeichneSpielfeld();
        }
    }


    /*
     ***********************************************
     ***           toString             ************
     ***********************************************
     */

    @Override
    public String toString() {
        return "QwirkleGui zur Darstellung des Spielfeldes fuer einen Client";
    }



}
