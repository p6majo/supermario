package edu.q2.supermario;

/**
 * The class Figur fürs SuperMario-Spiel
 *
 * @author p6majo
 * @version 2020-11-25
 */
public class Figur {

    /*
     *********************************************
     ***           Attributes           **********
     *********************************************
     */

    private double x;
    private double y;
    private double v;
    private Spiel spiel;


    /*
     **********************************************
     ****           Constructors         **********
     **********************************************
     */

public Figur(Spiel pSpiel){
    spiel = pSpiel;
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


    public double getV() {
        return v;
    }

    public Spiel getSpiel() { return spiel; }

    /*
     ***********************************************
     ***           Setters              ************
     ***********************************************
     */

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setV(double v) {
        this.v = v;
    }

    /*
     ***********************************************
     ***           Public methods       ************
     ***********************************************
     */

    public void bewegeRechts(){
        x=x+1;
    }

    public void bewegeLinks(){
        x=x-1;
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
        return "@("+x+"|"+y+") mit v="+v;
    }



}
