

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * The class Figur fürs SuperMario-Spiel
 *
 * @author p6majo
 * @version 2020-11-25
 */
public abstract class Figur {

    /*
     *************************************************
     ***           physikalische Parameter  **********
     *************************************************
     */


    private double g = 10; //Fallbeschleungigung, gibt an, wie schnell eine Figur nach unten faellt
    private double dt = 0.05;//Zeitintervall;
    private double sprungV = 7.; //Sprunggeschwindigkeit
    private double laufV = 3.; //Laufgeschwindigkeit
    private double daempfung =0.2; //Reibung in der Luft
    private BufferedImage img;

    /*
     *********************************************
     ***           Attributes           **********
     *********************************************
     */


    public enum Bewegung {rechts, links, springen, fallen, nichtfallen};

    private double x;
    private double y;
    private Bewegung bewegung;
    private Spiel spiel;

    private double vx;
    private double vy;
    protected boolean alive;

    /*
     **********************************************
     ****           Constructors         **********
     **********************************************
     */

    public Figur(Spiel pSpiel){
    spiel = pSpiel;
    alive= true;
}

    /*
     ***********************************************
     ***           Getters              ************
     ***********************************************
     */

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getVX(){return vx;}
    public double getVY(){return vy;}

    public Bewegung getBewegung(){ return bewegung;}
    public Spiel getSpiel() { return spiel; }
    public BufferedImage getImage(){return img;}

    /*
     ***********************************************
     ***           Setters              ************
     ***********************************************
     */



    public void setImg(String name){
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource("res/"+name );

        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setG(double g) {
        this.g = g;
    }

    public void setDt(double dt) {
        this.dt = dt;
    }

    public void setSprungV(double sprungV) {
        this.sprungV = sprungV;
    }

    public void setLaufV(double laufV) {
        this.laufV = laufV;
    }

    public void setDaempfung(double pDaempfung){daempfung = pDaempfung;}

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }


    public void setBewegung(Bewegung pBewegung) {
        switch(pBewegung){
            case rechts:
                vx = laufV;
                break;
            case links:
                vx = -laufV;
                break;
            case springen:
                if (bodenKontakt()) {
                    vy = -sprungV;
                    bewegung = Bewegung.fallen; //starte den freien Fall nach dem Sprung
                }
                break;
            case fallen:
                break;
            case nichtfallen:
                vy = 0;
                break;
        }
    }


    /*
     ***********************************************
     ***           Public methods       ************
     ***********************************************
     */



    public void sterbe(){
        alive = false;
        if (this instanceof Gegner)
            spiel.entferneGegner((Gegner) this);
    }

    public boolean bodenKontakt() {
        Welt welt = spiel.getWelt();
        //unterer Block ein Stein
        Block block = welt.gibBlock((int) (x + 0.5), (int) (y + 1)); //+0.5 um die Mitte des Blocks auf dem Stein zu platzieren
        if (block != null && block.getTyp() == 1) {
            return true;
        }
        return false;
    }
    /**
     * Hier wird die allgemeine Bewegung der Figuren festgelegt
     */
    public  void act(){
        if (alive) {
            x = x + vx * dt;
            vx = vx * (1. - daempfung); //Luftwiderstand, eventuell muss dass nur in der Spielerklasse implementiert
            //werden, damit die Pilze nicht abgebremst werden.
            if (bewegung == Bewegung.fallen) {
                y = y + vy * dt;
                vy = vy + g * dt;
            }
        }
    };

    /**
     * Diese Transformation bewegt die Figur in die andere Richtung
     *  und reduziert die Geschwindigkeit auf pAnteil
     * @param pAnteil
     */
    public void reflektiere(double pAnteil){
        vx = vx*(-pAnteil);
    }

    /**
     * Reflexion in vertikaler Richtung
     * @param pAnteil
     */
    public void reflektiereV(double pAnteil){
        vy = vy*(-pAnteil);
    }

    public void stoppeFall(){
        vy = 0;
        bewegung = Bewegung.nichtfallen;
    }

    public void starteFall(){
        bewegung = Bewegung.fallen;
    }

    public void draw(Graphics2D pScreen, int pZellGroesse,double pVerschiebeX){
        pScreen.setColor(Color.MAGENTA);
        pScreen.setStroke(new BasicStroke(4f));


        int x = (int) (getX() * pZellGroesse +pVerschiebeX);
        int y = (int) (getY() * pZellGroesse);
        pScreen.drawImage(getImage(),x,y,null);
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
        return "@("+x+"|"+y+") mit Bewegung: "+bewegung;
    }



}
