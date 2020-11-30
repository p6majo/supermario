package src;

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
    private int maxSpielerZahl ;
    private int spielerZahl;
    private Uhr uhr;
    private int zeit;
    private Timer timer;
    private boolean laeuft;
    private Welt welt;
    private HighScore highScore;


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

        for (int s = 0; s < maxSpielerZahl; s++) {
            spielerListe.append(new Spieler(this, 10, 0, 1));
        }
        laeuft = false;
        highScore = ladeHighScore();
        welt = new Welt();
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
