package qwirkle;




import java.util.ArrayList;
import java.util.TreeMap;


/**
 * The class QwirkleSpiel
 *
 * @author Jan Pickshaus, p6majo
 * @version 2019-07-02
 */
public class QwirkleSpiel {

    /*
     *********************************************
     ***           Attributes           **********
     *********************************************
     */

    private Beutel beutel;
    private List<Stein> spielfeld;
    private java.util.List<Stein>  rote, blaue, gruene, gelbe, violette, orangene, kreise, kleeblaetter, quadrate, karos, kreuze, sterne;

    //Zuordnungen zwischen Symbolen und Unterlisten
    private TreeMap<Symbol,java.util.List<Stein>> farbenMap;
    private TreeMap<Symbol,java.util.List<Stein>> symbolMap;

    private Ring<Spieler> spielerRing;
    private int anzahl = 0;

    private enum Richtung {oben,links,unten,rechts};

    //Variablen, die erzwingen, dass alle Steine nur in einer Reihe oder Spalte waehrend eines Zuges abgelegt werden koennen
    private Integer aktiveZeile = null;
    private Integer aktiveSpalte = null;
    private String aktiveFarbe = null;
    private String aktivesSymbol = null;

    private boolean spielEnde = false;

    /*
     **********************************************
     ****           Constructors         **********
     **********************************************
     */

    /**
     * Konstruktor f&uuml;r das Qwirklespiel
     */
    public QwirkleSpiel()
    {
        beutel=new Beutel(3); //erzeuge Beutel mit 3 Steinfamilien
        spielfeld=new List<Stein>();

        rote=new ArrayList<Stein>(); //Unterlisten, die zum leichteren Durchsuchen des Spielfelds angelegt werden
        blaue=new ArrayList<Stein>();
        gruene=new ArrayList<Stein>();
        gelbe=new ArrayList<Stein>();
        violette=new ArrayList<Stein>();
        orangene=new ArrayList<Stein>();
        kreise=new ArrayList<Stein>();
        kleeblaetter=new ArrayList<Stein>();
        quadrate=new ArrayList<Stein>();
        karos=new ArrayList<Stein>();
        kreuze=new ArrayList<Stein>();
        sterne=new ArrayList<Stein>();

        erstelleListenMap();
    }

    /*
     ***********************************************
     ***           Getters              ************
     ***********************************************
     */

    public int gibAnzahlBeutelSteine() { return beutel.gibAnzahl(); }

    public Beutel gibBeutel()
    {
        return beutel;
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

    /**
     * Starte Spiel mit vorgegebener Anzahl von Spielern
     *
     * @param pAnzahl
     */
    public void starteSpiel(int pAnzahl){

        this.anzahl = pAnzahl;

        spielerRing = new Ring<>();
        for (int i = 0; i < pAnzahl; i++) {
            spielerRing.insert(new Spieler(i));
        }

        Spieler startSpieler = null;
        while (startSpieler==null) {

            //verteile Steine
            int count = 0;
            while (count < pAnzahl) {
                Spieler spieler = this.spielerRing.getContent();
                spieler.loescheAlleSteine();
                for (int i = 0; i < 6; i++) {
                    spieler.addStein(beutel.gibStein());
                }
                this.spielerRing.next();
                count++;
            }

            //bestimme Startspieler
            int startWert = 0;
            count=0;
            while (count < pAnzahl) {
                Spieler spieler = this.spielerRing.getContent();
                int wert = spieler.gibStartWert();
                if (wert>startWert){
                    startWert = wert;
                    if (wert>2)
                        startSpieler = spieler;
                }
                this.spielerRing.next();
                count++;
            }

            if (startSpieler!=null)
                startSpieler.setzeAktiv(true);
        }
    }

    public void addSteinZuSpielFeld(Stein pStein, int spielerIndex)
    {
        //long start = System.currentTimeMillis();
        if (!checkLegbarkeit(pStein))
            return;
        //System.out.println("QuirkelSpiel: "+(System.currentTimeMillis() - start) + " ms for Legbarkeitscheck!");



        if (aktiveZeile ==null && aktiveSpalte ==null){
            aktiveZeile = pStein.gibZeile();
            aktiveSpalte = pStein.gibSpalte();
        }
        else if (aktiveZeile!=null && aktiveSpalte!=null){
            if (pStein.gibZeile()!=aktiveZeile && pStein.gibSpalte()!=aktiveSpalte) //Stein muss in gleiche Zeile oder Spalte gelegt werden
                return;
            else if (pStein.gibZeile()==aktiveZeile)
                aktiveSpalte = null;
            else
                aktiveZeile =null;
        }
        else if (aktiveZeile!=null){
            if (pStein.gibZeile()!=aktiveZeile) //Stein muss in die gleiche Zeile gelegt werden
                return;
        }
        else
            if (pStein.gibSpalte()!=aktiveSpalte) //Stein muss in die gleiche Spalte gelegt werden
                return;

        if (aktiveFarbe ==null && aktivesSymbol ==null){
            aktiveFarbe = pStein.gibFarbString();
            aktivesSymbol = pStein.gibSymbolString();
        }
        else if (aktiveFarbe!=null && aktivesSymbol!=null){
            if (!pStein.gibFarbString().equals(aktiveFarbe) && !pStein.gibSymbolString().equals(aktivesSymbol)) //Stein muss gleiches Symbol oder gleiche Farbe haben
                return;
            else if (pStein.gibFarbString().equals(aktiveFarbe))
                aktivesSymbol = null;
            else
                aktiveFarbe =null;
        }
        else if (aktiveFarbe!=null){
            if (!pStein.gibFarbString().equals(aktiveFarbe)) //Stein muss gleiche Farbe haben
                return;
        }
        else
        if (!pStein.gibSymbolString().equals(aktivesSymbol)) //Stein muss gleiches Symbol haben
            return;


        spielfeld.append(pStein); // legt ihn auf das Spielfeld
        symbolMap.get(pStein.gibSymbol()).add(pStein);
        farbenMap.get(pStein.gibSymbol()).add(pStein);

        int punkte=0;
        int zwischenPunkte =horizontalePunkte(pStein);
        if (zwischenPunkte==6)
            zwischenPunkte+=6;//Qwirkle
        punkte+=zwischenPunkte;
        zwischenPunkte =senkrechtePunkte(pStein);
        if (zwischenPunkte==6)
            zwischenPunkte+=6;//Qwirkle
        punkte+=zwischenPunkte;

        if (punkte==0)
            punkte = 1; //gib auf alle Faelle einen Punkt

        while (spielerRing.getContent().gibIndex()!=spielerIndex)
            spielerRing.next();

        Spieler spieler = this.spielerRing.getContent();
        spieler.legeStein(pStein,punkte);
        System.out.println("QwirkleSpiel: "+"Spieler " + spieler.gibIndex() + " hat " + pStein.toString() + " gelegt.");
        System.out.println("QwirkleSpiel: "+"Spieler " + spieler.gibIndex() + " hat " + punkte + " Punkte bekommen.");


        //Wiederauffuellen in der gleichen Runde, wenn alle Steine abgelegt worden sind
        if (spieler.gibAnzahlSteine()==0 && beutel.gibAnzahl()>0){
            while (spieler.gibAnzahlSteine()<6 && beutel.gibAnzahl()>0){
                spieler.addStein(beutel.gibStein());
            }
        }

    }

    public boolean naechsterSpieler(int aktuellerSpieler) {

        while (spielerRing.getContent().gibIndex()!=aktuellerSpieler)
            spielerRing.next();

        //fuelle Steine auf
        Spieler spieler = spielerRing.getContent();
        while(spieler.gibAnzahlSteine()<6 && beutel.gibAnzahl()>0) {
            spieler.addStein(beutel.gibStein());
        }

        spielerRing.getContent().setzeAktiv(false);
        spielerRing.next();
        spielerRing.getContent().setzeAktiv(true);

        aktiveZeile = null;
        aktiveSpalte = null;
        aktiveFarbe = null;
        aktivesSymbol = null;

        if (spielerRing.getContent().gibAnzahlSteine()==0) {//Runde ist rum, nachdem der erste Spieler seinen letzten Stein ausgespielt hat
            spielEnde = true;
            return false;
        }
        return true;
    }

    /**
     * Der Zustand ist ein String mit folgender Signatur:
     * AnzahlSpieler, SteineImBeutel, aktiverSpieler, punkte1, punkte2, ... , stein-stein-stein-stein-stein (von Spieler 1), stein-stein-stein-stein-stein(von Spieler 2), ..., stein(x,y), stein(x,y), ....
     * @return
     */
    public String gibZustand(){
        String out = anzahl+QwirkleServer.SEP;
        String aktiv = "";
        int[] punkte = new int[anzahl];
        String[] spielerSteine = new String[anzahl];
        String spielfeldString = "";

        //sammle Daten
        int count = 0;
        while (count<anzahl){
            Spieler spieler = this.spielerRing.getContent();
            if (spieler.istAktiv()) aktiv = spieler.gibIndex()+"";
            punkte[spieler.gibIndex()] = spieler.gibPunkteStand();
            spielerSteine[spieler.gibIndex()]=spieler.gibSteineString();
            this.spielerRing.next();
            count++;
        }

        if (spielEnde) aktiv = "-1";

        out += beutel.gibAnzahl()+QwirkleServer.SEP;
        out += aktiv;
        for (int i = 0; i < anzahl; i++)
            out+=QwirkleServer.SEP+punkte[i];

        for (int i = 0; i < anzahl; i++)
            out+=QwirkleServer.SEP+spielerSteine[i];


        spielfeld.toFirst();
        while(spielfeld.hasAccess()) {
            Stein stein = spielfeld.getContent();
            out += QwirkleServer.SEP + stein.toString();
            spielfeld.next();
        }

        return out;
    }

    public void tauschen(int index, java.util.List<Stein> zuTauschen){
        while (spielerRing.getContent().gibIndex()!=index)
            spielerRing.next();

        Spieler spieler = spielerRing.getContent();
        for (Stein stein : zuTauschen) {
            spieler.loescheStein(stein);
            beutel.addStein(stein);
        }

        while(spieler.gibAnzahlSteine()<6 && beutel.gibAnzahl()>0) {
            spieler.addStein(beutel.gibStein());
        }

        naechsterSpieler(index);
    }

    /*
     ***********************************************
     ***           Private methods      ************
     ***********************************************
     */

    /**
     * Untersucht, ob ein Stein an einer betreffenden Stelle gelegt werden kann
     * @param pStein
     * @return
     */
    private boolean checkLegbarkeit(Stein pStein){

        if (spielfeld.isEmpty()) //erster Stein, alles ist moeglich
            return true;

        //liegt schon ein Stein im Feld?
        spielfeld.toFirst();
        while(spielfeld.hasAccess()){
            Stein stein = spielfeld.getContent();
                if (pStein.gibZeile()==stein.gibZeile() && pStein.gibSpalte()==stein.gibSpalte())
                    return false;
            spielfeld.next();
        }

        //bestimme alle Nachbarsteine
        java.util.List<Stein> oben = new ArrayList<>();
        java.util.List<Stein> rechts = new ArrayList<>();
        java.util.List<Stein> links = new ArrayList<>();
        java.util.List<Stein> unten = new ArrayList<>();

        findeNachbarSteine(pStein,oben, Richtung.oben);
        findeNachbarSteine(pStein,rechts, Richtung.rechts);
        findeNachbarSteine(pStein,unten, Richtung.unten);
        findeNachbarSteine(pStein,links, Richtung.links);

        if (oben.size()==0 && rechts.size()==0 && links.size()==0 && unten.size()==0) //keine Nachbar, Stein ins Nirvana gelegt
            return false;

        if(pruefeAufKonflikt(pStein,oben)) return false;
        if(pruefeAufKonflikt(pStein,rechts)) return false;
        if(pruefeAufKonflikt(pStein,unten)) return false;
        if(pruefeAufKonflikt(pStein,links)) return false;

        return true;
    }

    /**
     * Gibt wahr zur&uuml;ck, wenn es beim Legen des Steins einen Konflikt ergibt
     * @param pStein
     * @param lst
     * @return
     */
    private boolean pruefeAufKonflikt(Stein pStein, java.util.List<Stein> lst){
        if (lst.size()==0) return false;


       String pFarbe = pStein.gibSymbol().toString().substring(3);
       String pSymbol = pStein.gibSymbol().toString().substring(0,2);

       //farbTest
        boolean farbtest = true;
        for (Stein stein : lst) {
            if (!stein.gibSymbol().toString().substring(3).equals(pFarbe)){
                farbtest = false;
                break;
            }
        }

        //symbolTest
        boolean symboltest = true;
        for (Stein stein : lst) {
            if (!stein.gibSymbol().toString().substring(0,2).equals(pSymbol)){
                symboltest = false;
                break;
            }
        }

        //Identitaetstest
        boolean identitytest = false;
        for (Stein stein : lst) {
            if (stein.gibSymbol().equals(pStein.gibSymbol())){
               identitytest = true;
               break;
            }
        }

        boolean erfolg = (farbtest||symboltest)&&!identitytest; //Stein kann gelegt werden, wenn ein Test erfolgreich war

        return !erfolg;
    }

    /**
     * Rekursive Methode, die alle benachbarten Steine eines Steines in eine Richtung in einer Liste zur&uuml;ck gibt
     * @param pStein - Stein, dessen Nachbarsteine bestimmt werden sollen
     * @param pSteinListe - Liste, in der die Nachbarsteine gespeichert werden
     * @param pRichtung -Richtung, in der nach Nachbarsteinen gesucht wird
     */
    private void findeNachbarSteine(Stein pStein, java.util.List<Stein> pSteinListe, Richtung pRichtung){
        spielfeld.toFirst();
        while(spielfeld.hasAccess()){  //Schleife ueber alle Steine des Spielfelds
            Stein stein = spielfeld.getContent();
            switch(pRichtung){
                case oben:
                    if (pStein.gibZeile()==stein.gibZeile()+1 && pStein.gibSpalte()==stein.gibSpalte()) {
                        pSteinListe.add(stein);
                        findeNachbarSteine(stein,pSteinListe,pRichtung);
                    }
                    break;
                case links:
                    if (pStein.gibZeile()==stein.gibZeile() && pStein.gibSpalte()==stein.gibSpalte()+1) {
                        pSteinListe.add(stein);
                        findeNachbarSteine(stein,pSteinListe,pRichtung);
                    }
                    break;
                case unten:
                    if (pStein.gibZeile()==stein.gibZeile()-1 && pStein.gibSpalte()==stein.gibSpalte()) {
                        pSteinListe.add(stein);
                        findeNachbarSteine(stein,pSteinListe,pRichtung);
                    }
                    break;
                case rechts:
                    if (pStein.gibZeile()==stein.gibZeile() && pStein.gibSpalte()==stein.gibSpalte()-1) {
                        pSteinListe.add(stein);
                        findeNachbarSteine(stein,pSteinListe,pRichtung);
                    }
                    break;
            }
            spielfeld.next();
        }
    }


    private int senkrechtePunkte(Stein pStein){
        int punkte =0;
        punkte =punkte+nachOben(pStein);
        punkte=punkte+nachUnten(pStein);
        if (punkte>0)
            punkte++; //addiere neu angelegten Stein zur Summe
        return punkte;
    }

    private int horizontalePunkte(Stein pStein){
        int punkte =0;
        punkte =punkte+nachLinks(pStein);
        punkte=punkte+nachRechts(pStein);
        if (punkte>0)
            punkte++; //addiere neu angelegten Stein zur Summe
        return punkte;
    }

    private int nachOben(Stein pStein){
        //farbentest
        java.util.List<Stein> farbenListe = farbenMap.get(pStein.gibSymbol());
        for (Stein stein : farbenListe)
            if (stein.gibZeile()==pStein.gibZeile()-1 && stein.gibSpalte()==pStein.gibSpalte())
                return nachOben(stein)+1;

        //symboltest
        java.util.List<Stein> symbolListe =symbolMap.get(pStein.gibSymbol());
        for (Stein stein : symbolListe)
            if (stein.gibZeile()==pStein.gibZeile()-1 && stein.gibSpalte()==pStein.gibSpalte())
                return (nachOben(stein)+1);

        return 0;
    }


    private int nachUnten(Stein pStein){
        //farbentest
        java.util.List<Stein> farbenListe = farbenMap.get(pStein.gibSymbol());
        for (Stein stein : farbenListe)
            if (stein.gibZeile()==pStein.gibZeile()+1 && stein.gibSpalte()==pStein.gibSpalte())
                return nachUnten(stein)+1;

        //symboltest
        java.util.List<Stein> symbolListe =symbolMap.get(pStein.gibSymbol());
        for (Stein stein : symbolListe)
            if (stein.gibZeile()==pStein.gibZeile()+1 && stein.gibSpalte()==pStein.gibSpalte())
                return (nachUnten(stein)+1);

        return 0;
    }
    private int nachLinks(Stein pStein){
        //farbentest
        java.util.List<Stein> farbenListe = farbenMap.get(pStein.gibSymbol());
        for (Stein stein : farbenListe)
            if (stein.gibZeile()==pStein.gibZeile() && stein.gibSpalte()==pStein.gibSpalte()-1)
                return nachLinks(stein)+1;

        //symboltest
        java.util.List<Stein> symbolListe =symbolMap.get(pStein.gibSymbol());
        for (Stein stein : symbolListe)
            if (stein.gibZeile()==pStein.gibZeile()&& stein.gibSpalte()==pStein.gibSpalte()-1)
                return (nachLinks(stein)+1);

        return 0;
    }

    private int nachRechts(Stein pStein){
        //farbentest
        java.util.List<Stein> farbenListe = farbenMap.get(pStein.gibSymbol());
        for (Stein stein : farbenListe)
            if (stein.gibZeile()==pStein.gibZeile() && stein.gibSpalte()==pStein.gibSpalte()+1)
                return nachRechts(stein)+1;

        //symboltest
        java.util.List<Stein> symbolListe =symbolMap.get(pStein.gibSymbol());
        for (Stein stein : symbolListe)
            if (stein.gibZeile()==pStein.gibZeile()&& stein.gibSpalte()==pStein.gibSpalte()+1)
                return (nachRechts(stein)+1);

        return 0;
    }

    /**
     * Erstellt die Zuordnungen zwischen Symbolen und Unterlisten
     */
    private void erstelleListenMap(){
        farbenMap = new TreeMap<>();
        symbolMap = new TreeMap<>();

        for(Symbol symbol:Symbol.values()){

            switch (symbol) // sortiert ihn in die entsprechende Liste ein
            {
                case ka_blau:
                    farbenMap.put(symbol,blaue);
                    symbolMap.put(symbol,karos);
                    break;
                case ks_blau:
                    farbenMap.put(symbol,blaue);
                    symbolMap.put(symbol,kreise);
                    break;
                case kr_blau:
                    farbenMap.put(symbol,blaue);
                    symbolMap.put(symbol,kreuze);
                    break;
                case st_blau:
                    farbenMap.put(symbol,blaue);
                    symbolMap.put(symbol,sterne);
                    break;
                case qu_blau:
                    farbenMap.put(symbol,blaue);
                    symbolMap.put(symbol,quadrate);
                    break;
                case kb_blau:
                    farbenMap.put(symbol,blaue);
                    symbolMap.put(symbol,kleeblaetter);
                    break;

                case ka_gelb:
                    farbenMap.put(symbol,gelbe);
                    symbolMap.put(symbol,karos);
                    break;
                case ks_gelb:
                    farbenMap.put(symbol,gelbe);
                    symbolMap.put(symbol,kreise);
                    break;
                case kr_gelb:
                    farbenMap.put(symbol,gelbe);
                    symbolMap.put(symbol,kreuze);
                    break;
                case st_gelb:
                    farbenMap.put(symbol,gelbe);
                    symbolMap.put(symbol,sterne);
                    break;
                case qu_gelb:
                    farbenMap.put(symbol,gelbe);
                    symbolMap.put(symbol,quadrate);
                    break;
                case kb_gelb:
                    farbenMap.put(symbol,gelbe);
                    symbolMap.put(symbol,kleeblaetter);
                    break;
                case ka_gruen:
                    farbenMap.put(symbol,gruene);
                    symbolMap.put(symbol,karos);
                    break;
                case ks_gruen:
                    farbenMap.put(symbol,gruene);
                    symbolMap.put(symbol,kreise);
                    break;
                case kr_gruen:
                    farbenMap.put(symbol,gruene);
                    symbolMap.put(symbol,kreuze);
                    break;
                case st_gruen:
                    farbenMap.put(symbol,gruene);
                    symbolMap.put(symbol,sterne);
                    break;
                case qu_gruen:
                    farbenMap.put(symbol,gruene);
                    symbolMap.put(symbol,quadrate);
                    break;
                case kb_gruen:
                    farbenMap.put(symbol,gruene);
                    symbolMap.put(symbol,kleeblaetter);
                    break;
                case ka_rot:
                    farbenMap.put(symbol,rote);
                    symbolMap.put(symbol,karos);
                    break;
                case ks_rot:
                    farbenMap.put(symbol,rote);
                    symbolMap.put(symbol,kreise);
                    break;
                case kr_rot:
                    farbenMap.put(symbol,rote);
                    symbolMap.put(symbol,kreuze);
                    break;
                case st_rot:
                    farbenMap.put(symbol,rote);
                    symbolMap.put(symbol,sterne);
                    break;
                case qu_rot:
                    farbenMap.put(symbol,rote);
                    symbolMap.put(symbol,quadrate);
                    break;
                case kb_rot:
                    farbenMap.put(symbol,rote);
                    symbolMap.put(symbol,kleeblaetter);
                    break;
                case ka_violett:
                    farbenMap.put(symbol,violette);
                    symbolMap.put(symbol,karos);
                    break;
                case ks_violett:
                    farbenMap.put(symbol,violette);
                    symbolMap.put(symbol,kreise);
                    break;
                case kr_violett:
                    farbenMap.put(symbol,violette);
                    symbolMap.put(symbol,kreuze);
                    break;
                case st_violett:
                    farbenMap.put(symbol,violette);
                    symbolMap.put(symbol,sterne);
                    break;
                case qu_violett:
                    farbenMap.put(symbol,violette);
                    symbolMap.put(symbol,quadrate);
                    break;
                case kb_violett:
                    farbenMap.put(symbol,violette);
                    symbolMap.put(symbol,kleeblaetter);
                    break;
                case ka_orange:
                    farbenMap.put(symbol,orangene);
                    symbolMap.put(symbol,karos);
                    break;
                case ks_orange:
                    farbenMap.put(symbol,orangene);
                    symbolMap.put(symbol,kreise);
                    break;
                case kr_orange:
                    farbenMap.put(symbol,orangene);
                    symbolMap.put(symbol,kreuze);
                    break;
                case st_orange:
                    farbenMap.put(symbol,orangene);
                    symbolMap.put(symbol,sterne);
                    break;
                case qu_orange:
                    farbenMap.put(symbol,orangene);
                    symbolMap.put(symbol,quadrate);
                    break;
                case kb_orange:
                    farbenMap.put(symbol,orangene);
                    symbolMap.put(symbol,kleeblaetter);
                    break;
            }

        }
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
