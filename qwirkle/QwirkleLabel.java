package qwirkle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * The class QwirkleLabel
 *
 * @author p6majo
 * @version 2019-06-21
 */
public class QwirkleLabel extends JLabel {

    /*
     *********************************************
     ***           Attributes           **********
     *********************************************
     */

    private boolean tauschbar;
    private boolean legbar;

    private Stein stein;

    //spaeter wieder loeschen
    private QwirkleGui gui;

    /*
     **********************************************
     ****           Constructors         **********
     **********************************************
     */


    public QwirkleLabel(QwirkleGui pGui){
        gui = pGui;
        stein = null;
        tauschbar = false;
        legbar = false;
        zeichneSymbol();
    }


    /*
     ***********************************************
     ***           Getters              ************
     ***********************************************
     */

    public Symbol getSymbol() {
        return stein.gibSymbol();
    }

    public Stein gibStein(){
        return this.stein;
    }

    public boolean istTauschbar() {
        return tauschbar;
    }

    public boolean istLegbar(){
        return legbar;
    }

    /*
     ***********************************************
     ***           Setters              ************
     ***********************************************
     */



    public void setzeStein(Stein pStein) {
        this.stein = pStein;
        zeichneSymbol();
    }



    /*
     ***********************************************
     ***           Public methods       ************
     ***********************************************
     */

    public void loesche(){
        this.tauschbar = false;
        this.legbar = false;
        this.stein = null;
        zeichneSymbol();
    }

    public void deselektiere(){
        this.tauschbar = false;
        this.legbar = false;
        this.setBackground(Color.blue);
    }

    public void mouseEvent(MouseEvent e){
        //rechte Maustaste

        if (e.getButton()==MouseEvent.BUTTON3){
            if (this.tauschbar) {
                this.setBackground(Color.blue);
                this.tauschbar = false;
                this.legbar = false;
            }
            else{
                this.setBackground(Color.red);
                this.tauschbar = true;
                this.legbar = false;
            }

        }
        else if(e.getButton()==MouseEvent.BUTTON1){
            if (!legbar) {
                this.setBackground(Color.green);
                this.tauschbar = false;
                this.legbar = true;
            }
            else{
                this.legbar = false;
                this.tauschbar = false;
                this.setBackground(Color.blue);
            }
        }

    }

    /*
     ***********************************************
     ***           Private methods      ************
     ***********************************************
     */


    private void zeichneSymbol(){

        ImageIcon icon = null;

        int groesse = 70;
        BufferedImage img = new BufferedImage(groesse,groesse,BufferedImage.TYPE_INT_ARGB);


        if (stein!=null)
            stein.erzeugeGraphik(img.getGraphics(),groesse,0,0,0);
        else {
            img.getGraphics().setColor(Color.BLACK);
            img.getGraphics().fillRect(0, 0, groesse, groesse);
        }

        icon = new ImageIcon(img);
        setIcon(icon);

    }
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
        return super.toString();
    }




}
