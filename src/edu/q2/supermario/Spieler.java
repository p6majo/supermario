package src.edu.q2.supermario;

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



    /*
     **********************************************
     ****           Constructors         **********
     **********************************************
     */

    public Spieler(Spiel pSpiel, int pLebenszahl, int pMuenzen, int pGroesse) {
        super(pSpiel);
        lebenszahl = pLebenszahl;
        muenzen = pMuenzen;
        groesse = pGroesse;
        anfangsGroesse = pGroesse;
        punkte = 0;
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

    public void sammeln(Sammelbar pSammelbar){
        if (pSammelbar instanceof Muenze){
            muenzen++;
        }
        else if (pSammelbar instanceof Pilz) {
        }
        else if (pSammelbar instanceof Fragezeichen) {
        }
    }

    public void springe(){

    }

    public void nimmtSchaden(){
        groesse--;
        if (groesse<0){
            lebenszahl--;
            groesse = anfangsGroesse;
        }
    }

    public void ducken(){

    }

    public void sliden(){

    }

    public void ballern(){

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
        return "Spieler "+super.toString();
    }



}
