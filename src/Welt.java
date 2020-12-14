package src;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * The class Welt
 *
 * 0 leer
 *   1 Stein
 *   2 Fragezeichen
 *   3 Roehre
 *
 *
 * @author p6majo
 * @version 2020-11-25
 */
public class Welt {

    /*
     *********************************************
     ***           Attributes           **********
     *********************************************
     */

    private int zeilen = 20;
    private int laenge= 2000;
    private Block[][] bloecke;

    private int typAnzahl=5 ;//leer, Stein, Fragezeichen, Roehre, muenze
    private BufferedImage[] images ;



    /*
     **********************************************
     ****           Constructors         **********
     **********************************************
     */

    public Welt(){
        images = new BufferedImage[typAnzahl];
        loadImages();
        bloecke = new Block[zeilen][laenge];
        //erzeuge einfache Welt mit sechs Steinbloecken am Boden und ansonsten zufaelligen Zahlen
        for (int l = 0; l < bloecke.length; l++) {
            for (int h = 0; h < bloecke[0].length; h++) {
                if (l>13) {
                    bloecke[l][h]=new Stein(h,l);
                    if (images[1]!=null) bloecke[l][h].setImg(images[1]);
                }
                else if (l==13){
                    if (Math.random()<0.2) {
                        bloecke[l][h]=new Muenze(h,l);
                        if (images[4]!=null) bloecke[l][h].setImg(images[4]);
                    }
                    else bloecke[l][h]=new Block(h,l,0);
                }
                else{
                    //setze in 3 Prozent der Faelle einen Stein, ansonsten Luft
                    if (Math.random()<0.03) {
                        bloecke[l][h]=new Stein(h,l);
                        if (images[1]!=null) bloecke[l][h].setImg(images[1]);
                    }
                    else bloecke[l][h]=new Block(h,l,0);
                }
            }
        }

    }

    /*
     ***********************************************
     ***           Getters              ************
     ***********************************************
     */

    public Block[][] gibBloecke(){
        return bloecke;
    }

    public int getHoehe(){
        return zeilen;
    }

    public int getBreite(){
        return laenge;
    }
    /**
     * Beachte, dass x der Spalte und y der Zeile entspricht
     *
     * @param x
     * @param y
     * @return
     */
    public Block gibBlock(double x, double y){
        if (x<0||y<0||x>=bloecke[0].length||y>=bloecke.length) return null;
        return bloecke[(int) y][(int) x];
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

    public void highlightBlock(double x,double y){
        bloecke[(int) y][(int) x].setHighlighted(true);
    }

    public void draw(Graphics2D pScreen, double pVerschiebeX, int pWidth, int pHeight,int pZellgroesse){
        if (pScreen!=null) {
            pScreen.setColor(Color.white);
            pScreen.fillRect(0, 0,  pWidth,pHeight);

            for (int zeile = 0; zeile < bloecke.length; zeile++ ) {
                for (int spalte = 0; spalte < bloecke[0].length; spalte++) {
                    Block block = bloecke[zeile][spalte];
                    block.draw(pScreen, pVerschiebeX, pZellgroesse);
                }
            }
        }
    }
    /*
     ***********************************************
     ***           Private methods      ************
     ***********************************************
     */

    private void loadImages(){
        images[1] = loadImage("stein.png");
        images[4]=loadImage("muenze.png");
    }

    public BufferedImage loadImage(String name){
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource("res/"+name );
        BufferedImage img = null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
    /*
     ***********************************************
     ***           Overrides            ************
     ***********************************************
     */


    @Override
    public String toString() {
        return "Welt";
    }


}
