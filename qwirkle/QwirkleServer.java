package qwirkle;


import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Server f&uuml;r das Qwirklespiel, der es erlaubt, bis zu vier Spieler in einem Spiel miteinander zu verbinden
 *
 * @author Mark Lob, p6majo
 * @version 2019-05-26
 */
public class QwirkleServer extends Server {

    public static final String SEP = ",";

    public enum SERVER_STATUS {initialisieren, laeuft, ende}

    ;
    private SERVER_STATUS status;


    //Clients f&uuml;r Mitspieler
    private List<QwirkleSocket> socketListe;
    private List<String> names;

    //Das Spiel
    private QwirkleSpiel spiel;

    //Datenbank
    private DatabaseConnector dbc;


    /**
     * Serverklasse f&uuml;r das Qwirklespiel
     *
     * @param pPort Port auf dem der Server auf Anfragen lauscht
     */
    public QwirkleServer(int pPort) {
        super(pPort);

        socketListe = new ArrayList<>();
        names = new ArrayList<>();

        spiel = new QwirkleSpiel();
        System.out.println("QwirkleServer: "+"QwirkleServer gestartet");
        System.out.println("QwirkleServer: "+"Server-Port: " + pPort);
        System.out.println("QwirkleServer: "+"---------------------------------");

        status = SERVER_STATUS.initialisieren;

        //Verbinde zur Datenbank
        // Wichtig! Zur erfolgreichen Arbeit mit dem DatabaseConnector muss die Bibliothek
        //sqlite-jdbc.jar geladen sein
        //
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource("spielstandDB.sqlite");
        if (url != null) {
            dbc = new DatabaseConnector("", 0, url.getPath(), "", "");
            System.out.println("QwirkleServer: "+"Verbindung zur Datenbank: " + dbc.getErrorMessage());
        }
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
            QwirkleSocket mySocket = new QwirkleSocket(pClientIP, pClientPort);
            // send(pClientIP, pClientPort, "+ CONNECT :"+mySocket);
            // send(pClientIP,pClientPort, "Willkommen beim Qwirkle-Server");
            send(pClientIP, pClientPort, "INDEX " + gibAnzahlSpieler());
            socketListe.add(new QwirkleSocket(pClientIP, pClientPort));
            System.out.println("QwirkleServer: "+"[" + pClientIP + ":" + pClientPort + "] Verbindung akzepiert: (" + gibAnzahlSpieler() + " Verbindungen)");
        } else {
            send(pClientIP, pClientPort, "- CONNECTION DENIED MAX NUMBER REACHED");
            System.out.println("QwirkleServer: "+"[" + pClientIP + ":" + pClientPort + "] Verbindung NICHT akzepiert");
        }
    }

    public void processMessage(String pClientIP, int pClientPort, String pMessage) {
        if (pMessage.equals("QUIT")) {
            closeConnection(pClientIP, pClientPort);
        }

        QwirkleSocket socket = sucheNachIPundPort(pClientIP, pClientPort);
        if (socket == null) {
            System.out.println("QwirkleServer: "+"Schwerer Ausnahmefehler! Socket nicht gefunden");
            return;
        }

        switch (socket.getZustand()) {
            case QwirkleSocket.ZUSTAND_USER:
                processUser(socket, pMessage);
                break;
            case QwirkleSocket.ZUSTAND_PASSIVE:
                processStart(socket, pMessage);
                break;
            case QwirkleSocket.ZUSTAND_ACTIVE:
                processPlay(socket, pMessage);
                break;
        }

    }

    public void processClosingConnection(String pClientIP, int pClientPort) {
        send(pClientIP, pClientPort, "Bye, Bye...");
        System.out.println("QwirkleServer: "+"[" + pClientIP + ":" + pClientPort + "] Socket geschlossen");
    }

    private QwirkleSocket sucheNachIPundPort(String pClientIP, int pClientPort) {
        for (QwirkleSocket socket : socketListe)
            if (socket.getIP().equals(pClientIP) && socket.getPort() == pClientPort) return socket;


        return null;
    }

    private String namenListenString() {

        String out = "";
        for (String name : names)
            out += name.split(SEP)[0] + SEP;

        return out.substring(0, out.length() - SEP.length()); //entferne letztes Komma
    }


    private void processStart(QwirkleSocket pSocket, String pMessage) {
        if (pMessage.startsWith("START")) {
            spiel.starteSpiel(gibAnzahlSpieler());
            status = SERVER_STATUS.laeuft;
            for (QwirkleSocket qwirkleSocket : socketListe)
                qwirkleSocket.setZustand(QwirkleSocket.ZUSTAND_ACTIVE);
            sendToAll("STATUS " + spiel.gibZustand());
        } else if (pMessage.startsWith("HIGHSCORE ")) {
            int spielerIndex = Integer.parseInt(pMessage.substring(10));
            sendHighScoreTo(spielerIndex);
        }

    }

    private void processUser(QwirkleSocket pSocket, String pMessage) {
        if (pMessage.startsWith("USER ")) {
            String name = pMessage.substring(5).trim();
            names.add(name);
            send(pSocket, "TITLE " + " Spieler " + (gibAnzahlSpieler()) + ": " + name.split(SEP)[0]);//nur der Vorname wird zum Gui geschickt.
            sendToAll("NAMES " + namenListenString());
            System.out.println("QwirkleServer: "+"[" + pSocket + "]: USER = " + name);
            pSocket.setZustand(QwirkleSocket.ZUSTAND_PASSIVE);
        } else if (pMessage.startsWith("START")) {

            spiel.starteSpiel(gibAnzahlSpieler());
        } else {
            send(pSocket, "- USER");
            System.out.println("QwirkleServer: "+"[" + pSocket + "]: ERROR - WRONG COMMAND");
        }
    }

    private void processPlay(QwirkleSocket pSocket, String pMessage) {
        if (pMessage.startsWith("ADD ")) {
            String[] strArray = pMessage.substring(4).split(SEP);
            Stein stein = new Stein(strArray[0]);

            spiel.addSteinZuSpielFeld(stein, Integer.parseInt(strArray[1]));
            sendToAll("STATUS " + spiel.gibZustand());
        } else if (pMessage.startsWith("NEXT ")) {
            int aktuellerSpieler = Integer.parseInt(pMessage.substring(5));
            if (spiel.naechsterSpieler(aktuellerSpieler))
                sendToAll("STATUS " + spiel.gibZustand());
            else{
                    sendToAll("ENDE " + spiel.gibZustand());
                    speicherePunkteStaende();
            }
        } else if (pMessage.startsWith("TAUSCH ")) {
            List<Stein> zuTauschen = new ArrayList<>();
            String[] strArray = pMessage.substring(7).split(SEP);
            int index = Integer.parseInt(strArray[0]);
            for (int i = 1; i < strArray.length; i++) {
                zuTauschen.add(new Stein(strArray[i]));
            }
            spiel.tauschen(index, zuTauschen);
            sendToAll("STATUS " + spiel.gibZustand());
        }else if (pMessage.startsWith("HIGHSCORE ")){
            sendHighScoreTo(Integer.parseInt(pMessage.substring(10)));
        }
        else {
            send(pSocket, "- ERR <UNKNOWN COMMAND> ");
        }
    }

    private void speicherePunkteStaende(){
        String[] parts = spiel.gibZustand().split(SEP);
        int[] punkte = new int[gibAnzahlSpieler()];
        for (int i = 3; i < gibAnzahlSpieler()+3; i++) {
            punkte[i-3]=Integer.parseInt(parts[i]);
        }

        for (int spieler = 0; spieler < gibAnzahlSpieler(); spieler++) {
            String[] data = names.get(spieler).split(SEP);
            String vorname = data[0];
            String nachname = data[1];
            String geburtsdatum = null;
            if (data.length>2) geburtsdatum = data[2];

            //untersuche, ob Nutzer existiert
            String sql = "SELECT id FROM spieler WHERE vorname = '"+vorname+"' AND name == '"+nachname+"'";
            if (geburtsdatum!=null)
                sql+=" AND geburtsdatum == '"+geburtsdatum+"'";

            dbc.executeStatement(sql);

            int spielerId = -1;
            QueryResult queryResult = dbc.getCurrentQueryResult();
            if (queryResult!=null && queryResult.getData().length>0) {//Spieler existiert in Datenbank
                spielerId = Integer.parseInt(queryResult.getData()[0][0]);
            }
            else { //Spieler anlegen
                if (geburtsdatum!=null)
                    sql = "INSERT INTO spieler (vorname,name,geburtsdatum) VALUES ('"+vorname+"','"+nachname+"','"+geburtsdatum+"')";
                else
                    sql = "INSERT INTO spieler (vorname,name) VALUES ('"+vorname+"','"+nachname+"')";
                dbc.executeStatement(sql);

                //erfrage spielerId;
                sql = "Select id From spieler Where vorname = '"+vorname+"' AND name == '"+nachname+"'";
                if (geburtsdatum!=null)
                    sql+=" AND geburtsdatum == '"+geburtsdatum+"'";
                dbc.executeStatement(sql);

                queryResult = dbc.getCurrentQueryResult();
                if (queryResult!=null &&queryResult.getData().length>0) {//Spieler existiert in Datenbank
                    spielerId = Integer.parseInt(queryResult.getData()[0][0]);
                }
                else{
                    System.out.println("QwirkelServer: " + "Fehler beim Anlegen des Spielers " + vorname + " " + nachname);
                }

            }

            if (spielerId !=-1){//Speichern des Punktestandes
                DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                String date = format.format(new Date(System.currentTimeMillis()));
                sql = "INSERT INTO ergebnisse (spielerId,punkte,datum) VALUES ("+spielerId+","+punkte[spieler]+",'"+date+"')";

                dbc.executeStatement(sql);
                System.out.println(" ");
            }
        }

    }

    // Überschreibene Methode zum Senden an einen Socket
    private void send(QwirkleSocket pSocket, String pMessage) {
        send(pSocket.getIP(), pSocket.getPort(), pMessage);
    }

    private void sendHighScoreTo(int spielerIndex) {
        String sql = "SELECT vorname,name,punkte,datum FROM spieler,ergebnisse WHERE ergebnisse.spielerId=spieler.id ORDER BY punkte DESC";
        String out = "";
        dbc.executeStatement(sql);
        System.out.println("QwirkleServer: "+"Datenbankanfrage: " + dbc.getErrorMessage());
        QueryResult res = dbc.getCurrentQueryResult();


        String[][] data = res.getData();
        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < data[i].length; j++)
                out += data[i][j] + SEP;


        send(socketListe.get(spielerIndex),"HIGHSCORE " + out);


    }
}
