

import java.awt.*;

/**
 * The class Pilz
 *
 * @author p6majo
 * @version 2020-11-25
 */
public class Pilz extends Figur implements Sammelbar {


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

    public Pilz(Spiel pSpiel) {
        super(pSpiel);
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

    /**
     * Hier wird festgelegt, wie die Figur gezeichnet werden soll.
     * @param pScreen
     * @param pZellGroesse
     */
    @Override
    public void draw(Graphics2D pScreen, int pZellGroesse, double pVerschiebeX) {

    }


    @Override
    public String toString() {
        return "Pilz";
    }


}
