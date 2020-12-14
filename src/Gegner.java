

import java.awt.*;

/**
 * The class Gegner
 *
 * @author p6majo
 * @version 2020-11-25
 */
public class Gegner  extends Figur{

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

    public Gegner(Spiel pSpiel){
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
    public void draw(Graphics2D pScreen, int pZellGroesse,double pVerschiebeX) {
        super.draw(pScreen,pZellGroesse,pVerschiebeX);
    }
    /*
     ***********************************************
     ***           toString             ************
     ***********************************************
     */

    @Override
    public String toString() {
        return super.toString();
    }


}
