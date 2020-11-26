package src.edu.q2.supermario;

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

    private List<Spieler> spieler;
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
        spieler = new List<>();
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
            spieler.append(pSpieler);
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
        }, 0, 1000);
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
        System.out.println(zeit);
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
