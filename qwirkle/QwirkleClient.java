package qwirkle;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * Diese Klasse erzeugt einen Spieler, der sich automatisch mit einem gegebenen Qwirkleserver verbindet
 *
 * @author p6majo
 * @version 1.0
 * @date 2019-07-02
 */
public class QwirkleClient extends Client {


    /**********************/
    /***   attributes   ***/
    /**********************/


    private String serverIP;
    private int serverPort;
    private String name;
    private String vorname;
    private String geburtsdatum;

    private QwirkleGui gui;
    private int spielerIndex;


    /*********************/
    /***  constructors ***/
    /*********************/

     public QwirkleClient(String pServerIP, int pServerPort,String pVorname, String pName){
         this(pServerIP,pServerPort,pVorname,pName,null);
     }


    public QwirkleClient(String pServerIP, int pServerPort,String pVorname, String pName, String pGeburtsdatum) {
        super(pServerIP, pServerPort);

        this.serverIP = pServerIP;
        this.serverPort = pServerPort;
        this.name = pName;
        this.vorname =pVorname;
        this.geburtsdatum = pGeburtsdatum;

        this.gui = new QwirkleGui(this);
        String dataString = vorname+QwirkleServer.SEP+name;
        if (geburtsdatum!=null) dataString+=QwirkleServer.SEP+geburtsdatum;
        this.send("USER "+dataString);

    }

    /************************/
    /****       getter    ***/
    /************************/

    public int gibSpielerIndex(){ return this.spielerIndex;}


    /************************/
    /****       setter    ***/
    /************************/


    /******************************/
    /****     public methods    ***/
    /******************************/

    public void spielStarten(){
        this.send("START");
    }

    public void legeStein(Stein stein, int z, int s){
        stein.setzeZeile(z);
        stein.setzeSpalte(s);
        this.send("ADD "+stein.toString()+","+spielerIndex);
    }

    public void tauschen(List<Stein> steine, int s) {
        String steineTauschString = s+"";
        steine.toFirst();
        while(steine.hasAccess()){
            steineTauschString+=QwirkleServer.SEP+steine.getContent().toString();
            steine.next();
        }
        this.send("TAUSCH "+steineTauschString);
    }

    public void rundeBeenden(){
        this.send("NEXT "+spielerIndex);
    }

    public void holeHighScore(){
        this.send("HIGHSCORE "+spielerIndex);
    }

    /******************************/
    /****     private methods   ***/
    /******************************/


    /******************************/
    /****     overrides         ***/
    /******************************/

    @Override
    public void processMessage(String pMessage) {

        if (pMessage.startsWith("INDEX "))
            this.spielerIndex = Integer.parseInt(pMessage.substring(6));
    else if (pMessage.startsWith("TITLE "))
            gui.setzeTitle(pMessage.substring(6));
        else if(pMessage.startsWith("NAMES ")){
            String[] namen = pMessage.substring(6).split(QwirkleServer.SEP);
            if (gui!=null)
                this.gui.aktualisiereSpieler(namen);
        }
        else if(pMessage.startsWith("STATUS ")){
            this.gui.aktualisiereStatus(pMessage.substring(7));
        }
        else if(pMessage.startsWith("ENDE ")){
            this.gui.beendeSpiel(pMessage.substring(5));
        }
        else if(pMessage.startsWith("HIGHSCORE ")){
            //Erzeuge Highscoreeintraege und schicke diese ans Gui
            String[] parts = pMessage.substring(10).split(QwirkleServer.SEP);
            HighScoreEintrag[] eintraege = new HighScoreEintrag[parts.length/4];

            if (eintraege.length>0) {
                for (int i = 0; i < parts.length; i = i + 4) {
                    DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                    Date date = null;
                    try {
                        date = formatter.parse(parts[i + 3]);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    eintraege[i / 4] = new HighScoreEintrag(parts[i + 1], parts[i], Integer.parseInt(parts[i + 2]), date);
                }
            }
            gui.zeigeHighScore(eintraege);

        }
    }

    /******************************/
    /****     toString()        ***/
    /******************************/

    @Override
    public String toString() {

        return name+"verbunden mit: "+serverIP+":"+serverPort;
    }


}
