



import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * The class QwirkleGui
 *
 * @author p6majo
 * @version 2019-06-20
 */
public class WeltView extends JFrame implements KeyListener {


    /*
     *********************************************
     ***           Attributes           **********
     *********************************************
     */



    private Spieler spieler;
    private Spiel spiel;



    //Panels zur Strukturierung des GUIs
    private JPanel hauptKomponente;
    private JLabel spielfeldLabel;


    //Attribute zur Konfiguration der Fensterdimensionen
    private int frameWidth =800;
    private int frameHeight = 650;
    private int rand = 10;

    //Attribute zur Konfiguration der Spielfelddimensionen
    private int sfWidth;
    private int sfHeight;


    //doppelt gepuffered Grafik, fuer schnelles Zeichnen des Spielfeldes
    private BufferedImage offscreenImage, onscreenImage;
    private Graphics2D offscreen, onscreen;
    private int zellGroesse=30;


    /*
     **********************************************
     ****           Constructors         **********
     **********************************************
     */

    public WeltView(Spiel pSpiel, Spieler pZentralerSpieler) {
        // Frame-Initialisierung
        super("TestView zur Darstellung der Super Mario Welt");
        addKeyListener(this);


        spieler = pZentralerSpieler;
        spiel = pSpiel;

        hauptKomponente = new JPanel();
        hauptKomponente.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-10084576)));

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(frameWidth, frameHeight);


        setResizable(true);
        Container cp = getContentPane();
        cp.add(hauptKomponente);
        show();



        //bereite Grafik vor, auf das Spielfeld zu zeichnen
        sfHeight = frameWidth-rand-50;
        sfWidth = frameHeight-rand;

        offscreenImage = new BufferedImage(sfWidth, sfHeight, BufferedImage.TYPE_INT_ARGB);
        onscreenImage  = new BufferedImage(sfWidth, sfHeight, BufferedImage.TYPE_INT_ARGB);
        offscreen = offscreenImage.createGraphics();
        onscreen  = onscreenImage.createGraphics();


        ImageIcon icon = new ImageIcon(onscreenImage);
        spielfeldLabel = new JLabel(icon);
        hauptKomponente.add(spielfeldLabel);
        spielfeldLabel.setBounds(0,0,sfWidth,sfHeight);

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

    public void draw() {
        double verschiebeX = 0;
        double x = spieler.getX();
        if (x*zellGroesse>sfWidth/2)
            verschiebeX = sfWidth/2-x*zellGroesse;

        //zeichne Welt
        spiel.getWelt().draw(offscreen,verschiebeX,sfWidth+rand,sfHeight+rand,zellGroesse);
        //zeichne Spieler
        spieler.draw(offscreen,zellGroesse,verschiebeX);

        //zeichne Gegner
        List<Gegner> gegner = spiel.getGegner();
        gegner.toFirst();
        while(gegner.hasAccess()){
            gegner.getContent().draw(offscreen,zellGroesse,verschiebeX);
            gegner.next();
        }

        onscreen.drawImage(offscreenImage, 0, 0, null);
        hauptKomponente.repaint();
    }
    /*
     ***********************************************
     ***           Private methods      ************
     ***********************************************
     */


    private void zeichneAusschnitt(double verschiebeX){




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



    /*
     ***********************************************
     ***           Overrrides KeyListener **********
     ***********************************************
     */

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()){
            case KeyEvent.VK_LEFT:
                spieler.setBewegung(Figur.Bewegung.links);
                break;
            case KeyEvent.VK_RIGHT:
                spieler.setBewegung(Figur.Bewegung.rechts);
                break;
            case KeyEvent.VK_SPACE:
                spieler.setBewegung(Figur.Bewegung.springen);
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
            default:
                System.out.println(keyEvent.getKeyCode());

        }

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

}
