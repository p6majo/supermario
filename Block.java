

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * The class Block
 *
 *
 *  *
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
    private BufferedImage img;





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
    public void setImg(BufferedImage pImg){img = pImg;}

    /*
     ***********************************************
     ***           Public methods       ************
     ***********************************************
     */

public void draw(Graphics2D pScreen,double pVerschiebeX, int pZellgroesse){

    if (getTyp()==1) {
        pScreen.setStroke(new BasicStroke(3.f));
        pScreen.setColor(Color.RED);
    }
    else if (isHighlighted()) {
        pScreen.setStroke(new BasicStroke(2.f));
        pScreen.setColor(Color.GREEN);
    }
    else {
        pScreen.setStroke(new BasicStroke(1.f));
        pScreen.setColor(Color.BLUE);
    }
    int xPos = (int) (x * pZellgroesse + pVerschiebeX);
    int yPos =y * pZellgroesse;

    if (img!=null){
        pScreen.drawImage(img,xPos,yPos,null);
    }
    else {
       // pScreen.drawRect(xPos, yPos, pZellgroesse, pZellgroesse);
       // pScreen.drawString(getTyp() + "", (int) ((x + 0.4) * pZellgroesse + pVerschiebeX), (int) ((y + 0.5) * pZellgroesse));

        //pScreen.drawString(block.getTyp()+"("+block.getX()+"|"+block.getY()+")",(int) ((spalte+0.1)*zellGroesse+verschiebeX),(int) ((zeile+0.5)*zellGroesse));
    }
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
