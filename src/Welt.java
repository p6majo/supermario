package src;

/**
 * The class Welt
 *
 * 0 leer
 * 1 Stein
 * 2 Fragezeichen
 * 3 Roehre
 *
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

    /*
     **********************************************
     ****           Constructors         **********
     **********************************************
     */

    public Welt(){
        bloecke = new Block[zeilen][laenge];

        //erzeuge einfache Welt mit sechs Steinbloecken am Boden und ansonsten zufaelligen Zahlen
        for (int l = 0; l < bloecke.length; l++) {
            for (int h = 0; h < bloecke[0].length; h++) {
                if (l>13) bloecke[l][h]=new Block(h,l,1);
                else{
                    //setze in 3 Prozent der Faelle einen Stein, ansonsten Luft
                    if (Math.random()<0.03) bloecke[l][h]=new Block(h,l,1);
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

    /**
     * Beachte, dass x der Spalte und y der Zeile entspricht
     *
     * @param x
     * @param y
     * @return
     */
    public Block gibBlock(double x, double y){
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
        return "Welt";
    }


}
