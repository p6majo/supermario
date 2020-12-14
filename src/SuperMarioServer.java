package src;


import java.util.ArrayList;
import java.util.List;

/**
 * Server f&uuml;r das SuperMariospiel, der es erlaubt, bis zu vier Spieler in einem Spiel miteinander zu verbinden
 *
 * @author Mark Lob, p6majo
 * @version 2019-05-26
 */
public class SuperMarioServer extends Server {

    public static final String SEP = ",";

    public enum SERVER_STATUS {initialisieren, laeuft, ende}

    ;
    private SERVER_STATUS status;


    //Clients f&uuml;r Mitspieler
    private List<SuperMarioSocket> socketListe;
    private List<String> names;

    //Das Spiel
    //private SuperMarioSpiel spiel;

    //Datenbank
    private DatabaseConnector dbc;


    /**
     * Serverklasse f&uuml;r das SuperMariospiel
     *
     * @param pPort Port auf dem der Server auf Anfragen lauscht
     */
    public SuperMarioServer(int pPort) {
        super(pPort);

        socketListe = new ArrayList<>();
        names = new ArrayList<>();

        //spiel = new SuperMarioSpiel();
        System.out.println("SuperMarioServer: "+"SuperMarioServer gestartet");
        System.out.println("SuperMarioServer: "+"Server-Port: " + pPort);
        System.out.println("SuperMarioServer: "+"---------------------------------");

        status = SERVER_STATUS.initialisieren;

        //Verbinde zur Datenbank
        // Wichtig! Zur erfolgreichen Arbeit mit dem DatabaseConnector muss die Bibliothek
        //sqlite-jdbc.jar geladen sein
        //
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
       // URL url = loader.getResource("spielstandDB.sqlite");
        //if (url != null) {
         //   dbc = new DatabaseConnector("", 0, url.getPath(), "", "");
          //  System.out.println("SuperMarioServer: "+"Verbindung zur Datenbank: " + dbc.getErrorMessage());
       // }
    }

    /**
     * Gibt die Anzahl der registrierten Spieler zur&uuml;ck
     *
     * @return
     */
    private int gibAnzahlSpieler() {
        return socketListe.size();
    }


    /**
     * Neue SpielerSocket Verbindung aufbauen
     *
     * @param pClientIP   IP-Adresse des Client
     * @param pClientPort Port auf dem der Client lauscht
     */
    public void processNewConnection(String pClientIP, int pClientPort) {
        if (gibAnzahlSpieler() < 4 && status == SERVER_STATUS.initialisieren) {
            SuperMarioSocket mySocket = new SuperMarioSocket(pClientIP, pClientPort);
            // send(pClientIP, pClientPort, "+ CONNECT :"+mySocket);
            // send(pClientIP,pClientPort, "Willkommen beim SuperMario-Server");
            send(pClientIP, pClientPort, "INDEX " + gibAnzahlSpieler());
            socketListe.add(new SuperMarioSocket(pClientIP, pClientPort));
            System.out.println("SuperMarioServer: "+"[" + pClientIP + ":" + pClientPort + "] Verbindung akzepiert: (" + gibAnzahlSpieler() + " Verbindungen)");
        } else {
            send(pClientIP, pClientPort, "- CONNECTION DENIED MAX NUMBER REACHED");
            System.out.println("SuperMarioServer: "+"[" + pClientIP + ":" + pClientPort + "] Verbindung NICHT akzepiert");
        }
    }

    public void processMessage(String pClientIP, int pClientPort, String pMessage) {

        SuperMarioSocket socket = sucheNachIPundPort(pClientIP, pClientPort);
        if (socket == null) {
            System.out.println("SuperMarioServer: "+"Schwerer Ausnahmefehler! Socket nicht gefunden");
            return;
        }

        if (pMessage.startsWith("PING")) {
            send(socket, "PONG");
        } else {
            System.out.println("SuperMarioServer: "+"[" + socket + "]: ERROR - WRONG COMMAND");
        }

    }

    public void processClosingConnection(String pClientIP, int pClientPort) {
        send(pClientIP, pClientPort, "Bye, Bye...");
        System.out.println("SuperMarioServer: "+"[" + pClientIP + ":" + pClientPort + "] Socket geschlossen");
    }

    private SuperMarioSocket sucheNachIPundPort(String pClientIP, int pClientPort) {
        for (SuperMarioSocket socket : socketListe)
            if (socket.getIP().equals(pClientIP) && socket.getPort() == pClientPort) return socket;


        return null;
    }



    // Überschreibene Methode zum Senden an einen Socket
    private void send(SuperMarioSocket pSocket, String pMessage) {
        send(pSocket.getIP(), pSocket.getPort(), pMessage);
    }


}
