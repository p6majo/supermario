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

    private Welt welt;
    private Spiel spiel;



    /*
     **********************************************
     ****           Constructors         **********
     **********************************************
     */

    public Spieler(Spiel pSpiel, int pLebenszahl, int pMuenzen, int pGroesse) {
        super(pSpiel);
        spiel = pSpiel;
        lebenszahl = pLebenszahl;
        muenzen = pMuenzen;
        groesse = pGroesse;
        anfangsGroesse = pGroesse;
        punkte = 0;
        welt = pSpiel.getWelt();

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

    public void sammeln(){

        Block block = welt.gibBlock(getX()+0.5,getY()+0.5);

        if (block instanceof  Sammelbar) {

            if (block instanceof Muenze) {
                muenzen++;
                erhoehePunkte();
                welt.remove(block);
            }
            else if (block instanceof Fragezeichen) {
            }
        }
    }

    public void springe(){

    }

    public void nimmtSchaden(){
        if (alive) {
            groesse--;
            if (groesse <= 0) {
                lebenszahl--;
                System.out.println("Schaden genommen. Neue Lebenszahl: " + lebenszahl);
                spiel.update();
                groesse = anfangsGroesse;
            }
            if (lebenszahl == 0) {
                sterbe();
            }
        }
    }

    public void ducken(){

    }

    public void sliden(){

    }

    public void ballern(){

    }

    public void sterbe(){
        setImg("tot.png");
        alive=false;
        spiel.update();
    }

    public void erhoehePunkte(){
        punkte++;
        System.out.println("neuer Punktestand: "+punkte);
        spiel.update();
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
        welt.highlightBlock(x,y);

        //ueberpruefe horizontale Bewegung
        if (getVX()>0){
            //Nachbarblock ein Stein
            Block block = welt.gibBlock((int) (x + 1), (int) y);
            if (block!=null && block.getTyp()==1){
                //reflektiere mit abgeschw"achter Geschwindigkeit
                reflektiere(0.3);
            }
        }
        else if (getVX()<0){
            //Nachbarblock ein Stein
            Block block = welt.gibBlock((int) (x - 1), (int) y);
            if (block!=null && block.getTyp()==1){
                //reflektiere mit abgeschw"achter Geschwindigkeit
                reflektiere(0.3);
            }
        }
        //TODO
        //teste Hindernisse auf der Diagonalen

        //ueberpruefe vertikale Bewegung
        if (getVY()>=0) {
            //nach unten
            if (bodenKontakt())
                stoppeFall();
            else{
                starteFall();
            }
        }
        else {
            //nach oben
            Block block = welt.gibBlock((int) (x + 0.5), (int) (y - 1)); //+0.5 um die Mitte des Blocks auf dem Stein zu platzieren
            if (block != null && block.getTyp() == 1) {
                reflektiereV(0.3);
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
        if (alive) {
            checkKollisionen();
            sammeln();
            super.act();
        }
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
