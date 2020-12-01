package src;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * The class Spieler
 *
 * @author p6majo
 * @version 2020-11-25
 */
public class Spieler extends Figur{

    /*
     *********************************************
     ***           Attributes           **********
     *********************************************
     */

    private int lebenszahl;
    private int muenzen;
    private int groesse;
    private int anfangsGroesse;
    private int punkte;



    /*
     **********************************************
     ****           Constructors         **********
     **********************************************
     */

    public Spieler(Spiel pSpiel, int pLebenszahl, int pMuenzen, int pGroesse) {
        super(pSpiel);
        lebenszahl = pLebenszahl;
        muenzen = pMuenzen;
        groesse = pGroesse;
        anfangsGroesse = pGroesse;
        punkte = 0;

       setImg("mario.png");
    }

    /*
     ***********************************************
     ***           Getters              ************
     ***********************************************
     */

    public int getLebenszahl() {
        return lebenszahl;
    }

    public int getMuenzen() {
        return muenzen;
    }

    public int getGroesse() {
        return groesse;
    }

    public int getPunkte() {
        return punkte;
    }

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

    public void sammeln(Sammelbar pSammelbar){
        if (pSammelbar instanceof Muenze){
            muenzen++;
        }
        else if (pSammelbar instanceof Pilz) {
        }
        else if (pSammelbar instanceof Fragezeichen) {
        }
    }

    public void springe(){

    }

    public void nimmtSchaden(){
        groesse--;
        if (groesse<0){
            lebenszahl--;
            groesse = anfangsGroesse;
        }
    }

    public void ducken(){

    }

    public void sliden(){

    }

    public void ballern(){

    }
    /*
     ***********************************************
     ***           Private methods      ************
     ***********************************************
     */

    /**
     * Ueberpruefe, ob die bestehende Bewegung fortgesetzt werden kann, oder
     * Anpassungen erfolgen muessen
     */
    private void checkKollisionen(){
        Welt welt = getSpiel().getWelt();

        double x = getX();
        double y = getY();
        welt.gibBlock(x,y).setHighlighted(true);
        welt.highlightBlock(x,y);

        //ueberpruefe horizontale Bewegung
        if (getVX()>0){
           //Nachbarblock ein Stein
            Block block = welt.gibBlock((int) (x + 1), (int) y);
            if (block.getTyp()==1){
                System.out.println(block.getTyp()+" ("+block.getX()+","+block.getY()+")");
                //reflektiere mit abgeschw"achter Geschwindigkeit
                reflektiere(0.3);
            }
        }
        else if (getVX()<0){
            //Nachbarblock ein Stein
            if (welt.gibBlock((int) (x-1),(int) y).getTyp()==1){
                //reflektiere mit abgeschw"achter Geschwindigkeit
                reflektiere(0.3);
            }
        }
        //TODO
        //teste Hindernisse auf der Diagonalen

        //ueberpruefe vertikale Bewegung
        if (getVY()>=0) {
            //nach unten
            //unterer Block ein Stein
             Block block = welt.gibBlock((int) (x+0.5), (int) (y + 1)); //+0.5 um die Mitte des Blocks auf dem Stein zu platzieren
            if (block.getTyp() ==1){
                //reflektiere mit abgeschw"achter Geschwindigkeit
                stoppeFall();
            }
            else{
                starteFall();
            }
        }
        //TODO
        //teste Hindernisse nach oben
        //Fragezeichen, etc.
    }

    /*
     ***********************************************
     ***           Overrides            ************
     ***********************************************
     */


    @Override
    public void act() {
        checkKollisionen();
        super.act();
    }

    /**
     * Hier koennen die Bilder fuer die Figuren geladen werden.
     * @param pScreen
     * @param pZellGroesse
     */
    @Override
    public void draw(Graphics2D pScreen, int pZellGroesse,double verschiebeX) {
        pScreen.setColor(Color.MAGENTA);
        pScreen.setStroke(new BasicStroke(4f));


        int x = (int) (getX() * pZellGroesse + verschiebeX);
        int y = (int) (getY() * pZellGroesse);
       // pScreen.drawRect(x, y,pZellGroesse,pZellGroesse);
        pScreen.drawImage(getImage(),x,y,null);
    }

    /*
     ***********************************************
     ***           toString             ************
     ***********************************************
     */



    @Override
    public String toString() {
        return "Spieler "+super.toString();
    }



}
