package src;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * Diese Klasse erzeugt einen Spieler, der sich automatisch mit einem gegebenen SuperMarioserver verbindet
 *
 * @author p6majo
 * @version 1.0
 * @date 2019-07-02
 */
public class SuperMarioClient extends Client {


    /**********************/
    /***   attributes   ***/
    /**********************/


    private String serverIP;
    private int serverPort;
    private String name;
    private String vorname;
    private String geburtsdatum;

     private int spielerIndex;

    private int pingCount;
    private long pingStart;
    private int pings = 10000;
    private long pingEnd;


    /*********************/
    /***  constructors ***/
    /*********************/

     public SuperMarioClient(String pServerIP, int pServerPort,String pVorname, String pName){
         this(pServerIP,pServerPort,pVorname,pName,null);
     }


    public SuperMarioClient(String pServerIP, int pServerPort,String pVorname, String pName, String pGeburtsdatum) {
        super(pServerIP, pServerPort);

        this.serverIP = pServerIP;
        this.serverPort = pServerPort;
        this.name = pName;
        this.vorname =pVorname;
        this.geburtsdatum = pGeburtsdatum;

        //this.gui = new SuperMarioGui(this);
        //String dataString = vorname+SuperMarioServer.SEP+name;
        //if (geburtsdatum!=null) dataString+=SuperMarioServer.SEP+geburtsdatum;
        //this.send("USER "+dataString);

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

    public void ping(){
        pingCount = 0;
        pingStart = System.currentTimeMillis();

        send("PING");
    }





    /******************************/
    /****     private methods   ***/
    /******************************/



    /******************************/
    /****     overrides         ***/
    /******************************/

    @Override
    public void processMessage(String pMessage) {

        if (pMessage.startsWith("PONG")){
            if (pingCount>=pings){
                pingEnd= System.currentTimeMillis();
                System.out.println(pings+" mal Server-Client-Ping-Pong in "+(pingEnd-pingStart)+" ms.");
            }
            else{
                pingCount++;
                send("PING");
            }
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
