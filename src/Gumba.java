package src;

/**
 * The class Koopa
 *
 * @author p6majo
 * @version 2020-11-25
 */
public class Gumba extends Gegner {

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

    public Gumba(Spiel pSpiel){

        super(pSpiel);
        setImg("goomba.png");

        setDaempfung(0.);
        if (Math.random()<0.5)
            setBewegung(Bewegung.rechts);
        else
            setBewegung(Bewegung.links);
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

    /**
     * Ueberpruefe, ob die bestehende Bewegung fortgesetzt werden kann, oder
     * Anpassungen erfolgen muessen
     */
    private void checkKollisionen(){
        Welt welt = getSpiel().getWelt();

        double x = getX();
        double y = getY();


        //ueberpruefe horizontale Bewegung
        if (getVX()>0){
            //Nachbarblock ein Stein
            Block block = welt.gibBlock((int) (x + 1), (int) y);
            if (block!=null && block.getTyp()==1){
                reflektiere(1.);
            }
        }
        else if (getVX()<0){
            //Nachbarblock ein Stein
            Block block = welt.gibBlock((int) (x - 1), (int) y);
            if (block!=null && block.getTyp()==1){
                //reflektiere mit abgeschw"achter Geschwindigkeit
                reflektiere(1.);
            }
        }
        //TODO
        //teste Hindernisse auf der Diagonalen

        //ueberpruefe vertikale Bewegung
        if (getVY()>=0) {
            //nach unten
            //unterer Block ein Stein
            Block block = welt.gibBlock((int) (x+0.5), (int) (y + 1)); //+0.5 um die Mitte des Blocks auf dem Stein zu platzieren
            if (block!=null && block.getTyp() ==1){
                //reflektiere mit abgeschw"achter Geschwindigkeit
                stoppeFall();
            }
            else{
                starteFall();
            }
        }
    }

    private void checkKollisionenMitSpieler(){
        List<Spieler> spielerListe = getSpiel().getSpieler();
        spielerListe.toFirst();
        while (spielerListe.hasAccess()){
            Spieler sp = spielerListe.getContent();
            double sx = sp.getX();
            double sy = sp.getY();
            if ((sx-getX())*(sx-getX())+(sy-getY())*(sy-getY())<=1) {
                if (sp.getBewegung()==Bewegung.fallen && sy-getY()<-0.5){
                    sp.erhoehePunkte();
                    sterbe();
                }
                else {
                    sp.nimmtSchaden();
                    reflektiere(1);
                    sp.reflektiere(0.3);
                }
            }
            spielerListe.next();
        }
    }

    /*
     ***********************************************
     ***           Overrides            ************
     ***********************************************
     */

    @Override
    public void act() {
        checkKollisionen();
        checkKollisionenMitSpieler();
        super.act();
    }

    @Override
    public String toString() {
        return "Gumba"+super.toString();
    }


}
