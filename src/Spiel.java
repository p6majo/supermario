package src;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The class Spiel
 *
 * @author p6majo
 * @version 2020-11-25
 */
public class Spiel {

    /*
     *********************************************
     ***           Attributes           **********
     *********************************************
     */

    private List<Spieler> spielerListe;
    private List<SuperMarioGui> guiListe;
    private List<Gegner> gegnerListe;
    private int maxSpielerZahl ;
    private int spielerZahl;
    private Uhr uhr;
    private int zeit;
    private Timer timer;
    private boolean laeuft;
    private Welt welt;
    private HighScore highScore;
    private Random rnd;


    /*
     **********************************************
     ****           Constructors         **********
     **********************************************
     */

    public Spiel(int pMaxSpielerZahl){
        maxSpielerZahl = pMaxSpielerZahl;
        spielerZahl = 0;
        spielerListe = new List<>();
        guiListe = new List<>();
        gegnerListe = new List<>();

        rnd = new Random();

        for (int s = 0; s < maxSpielerZahl; s++) {
            spielerListe.append(new Spieler(this, 5, 0, 1));
        }

        //erzeuge Welt
        welt = new Welt();


        //erzeuge 10 Gumbas

        for (int i = 0; i < 100; i++) {
            Gumba gumba = new Gumba(this);

          gumba.setX(rnd.nextInt(welt.getBreite()));
          gumba.setY(rnd.nextInt(welt.getHoehe()-6));
            gegnerListe.append(gumba);
        }

        laeuft = false;
        highScore = ladeHighScore();

    }

    /*
     ***********************************************
     ***           Getters              ************
     ***********************************************
     */
    public Welt getWelt() {
        return welt;
    }

    public HighScore getHighScore(){
        return highScore;
    }

    public List<Gegner> getGegner(){
        return gegnerListe;
    }

    public List<Spieler> getSpieler(){
        return spielerListe;
    }

    public Spieler getSpieler(int pNr){
        int zaehler = 0;
        spielerListe.toFirst();
        while(spielerListe.hasAccess()){
            if (zaehler<pNr){
                spielerListe.next();
                zaehler++;
            }
            else
                return spielerListe.getContent();
        }
        return null;
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

    public void entferneGegner(Gegner gegner){
        gegnerListe.toFirst();
        while(gegnerListe.hasAccess()){
            if (gegner.equals(gegnerListe.getContent())){
                gegnerListe.remove();
            }
            gegnerListe.next();
        }
    }

    public void spielerAnmelden(Spieler pSpieler){
        if (spielerZahl<maxSpielerZahl){
            spielerZahl++;
            spielerListe.append(pSpieler);
        }
    }

    public void spielStarten(){
        zeit = 0;
        timer = new Timer();
        laeuft = true;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (laeuft)
                    zeitSchritt();
            }
        }, 0, 33);
        System.out.println("timer started");
    }

    public void spielPausieren(){
        laeuft = false;
    }

    public void spielFortsetzen(){
        if (timer!=null)
            laeuft = true;
    }

    public void spielBeenden() {
        timer.cancel();
        timer =null;
    }

    public void aktualisiereHighScore(){

    }

    public void meldeGuiAn(SuperMarioGui pGui){
        guiListe.append(pGui);
    }


    /*
     ***********************************************
     ***           Private methods      ************
     ***********************************************
     */

    /**
     * TODO
     * hier muss programmiert werden,
     * wie sich das Spiel pro Zeitschritt ändert
     */
    private void zeitSchritt(){
        zeit++;
        //System.out.println(zeit);

        //aktualisiere alle Spieler
        spielerListe.toFirst();
        while(spielerListe.hasAccess()){
            spielerListe.getContent().act();
            spielerListe.next();
        }

        gegnerListe.toFirst();
        while(gegnerListe.hasAccess()){
            gegnerListe.getContent().act();
            gegnerListe.next();
        }

        //aktualisiere alle Guis
        guiListe.toFirst();
        while(guiListe.hasAccess()){
            guiListe.getContent().draw();
            guiListe.next();
        }
    }

    private HighScore ladeHighScore(){
        return null;
    }
    /*
     ***********************************************
     ***           Overrides            ************
     ***********************************************
     */


    @Override
    public String toString() {
        return "Spiel";
    }



}
