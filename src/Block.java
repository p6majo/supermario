package src;

/**
 * The class Block
 *
 * @author p6majo
 * @version 2020-11-25
 */
public class Block {

    /*
     *********************************************
     ***           Attributes           **********
     *********************************************
     */

    private int x;
    private int y;
    private int typ;
    private boolean highlighted;




    /*
     **********************************************
     ****           Constructors         **********
     **********************************************
     */

    public Block(int pX, int pY, int pTyp) {
        x = pX;
        y = pY;
        typ = pTyp;
        highlighted = false;
    }


    /*
     ***********************************************
     ***           Getters              ************
     ***********************************************
     */



    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public int getTyp() {
        return typ;
    }

    public boolean isHighlighted() {
        return highlighted;
    }



    /*
     ***********************************************
     ***           Setters              ************
     ***********************************************
     */

    public void setHighlighted(boolean pHighlighted) {
        highlighted = pHighlighted;
    }

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

    /*
     ***********************************************
     ***           toString             ************
     ***********************************************
     */

    @Override
    public String toString() {
        return "Block @("+x+","+y+")";
    }

}
