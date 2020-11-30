package qwirkle;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Die Klasse Spieler fasst die f&uuml;r das {@link QwirkleSpiel} relevanten Informationen
 * eines Spielers zusammen, etwa
 * <ul>
 *     <li>seine Steine</li>
 *     <li>seinen Punktestand</li>
 *      <li></li>
 * </ul>
 *
 * @author p6majo
 * @version 2019-07-02
 */
public class Spieler {

    /*
     *********************************************
     ***           Attributes           **********
     *********************************************
     */
    public final static String SEP = "-";

    private int punkte;
    private List<Stein> steine;
    private boolean aktiv;
    private final int index;

    /*
     **********************************************
     ****           Constructors         **********
     **********************************************
     */

    public Spieler(int pIndex){
        this.punkte = 0;
        this.steine = new ArrayList<>();
        this.aktiv = false;
        this.index = pIndex;
    }

    /*
     ***********************************************
     ***           Getters              ************
     ***********************************************
     */

    public int gibPunkteStand(){
        return punkte;
    }

    public boolean istAktiv() {
        return aktiv;
    }
    public int gibIndex() {
        return index;
    }

    public String gibSteineString(){
        String out = "";
        for (Stein stein : steine) {
            out+=stein.toString()+SEP;
        }

        if (out.length()>0) out =  out.substring(0,out.length()-1);
        return out;
    }

    public int gibAnzahlSteine(){
        return this.steine.size();
    }

    /*
     ***********************************************
     ***           Setters              ************
     ***********************************************
     */

    public void addStein(Stein pStein){
        this.steine.add(pStein);
    }

    public void setzeAktiv(boolean aktiv) {
        this.aktiv = aktiv;
    }

    /*
     ***********************************************
     ***           Public methods       ************
     ***********************************************
     */

    public void legeStein(Stein stein,int punkte){
        this.punkte=this.punkte+punkte;
        loescheStein(stein);
    }

    public void loescheStein(Stein pStein){
        List<Stein> steineUpdate = new ArrayList<>();
        for (Stein stein : steine) {
            if (stein.compareTo(pStein)!=0)
                steineUpdate.add(stein);
        }
        this.steine = steineUpdate;
    }

    public void loescheAlleSteine(){
        this.steine.clear();
    }

    /**
     * Gibt die maximale Anzahl an gleichartigen Steinen zur&uuml;ck, z.B. eine vier,
     * wenn ein Spieler 4 Kreise unterschiedlicher Farbe besitzt.
     * @return
     */
    public int gibStartWert(){

        List<Stein> verschiedene = new ArrayList<>();
        TreeSet<String> symbolStrings = new TreeSet<>();

        for (Stein stein : steine) {
            try {
                String symbolString = stein.gibSymbol().toString();
                if (!symbolStrings.contains(symbolString)) {
                    symbolStrings.add(symbolString);
                    verschiedene.add(stein);
                }
            }
            catch(Exception ex){
                System.out.println(ex.toString() + stein.toString());
            }
        }

        int rot=0;
        int gruen=0;
        int blau=0;
        int gelb=0;
        int orange=0;
        int violett=0;

        int kreis=0;
        int quadrat=0;
        int karo =0;
        int kleeblatt = 0;
        int stern = 0;
        int kreuz =0;

        for (Stein stein : verschiedene) {
            Symbol symbol = stein.gibSymbol();

            switch(symbol){
                case ka_blau:
                    blau++;
                    karo++;
                    break;
                case ka_gelb:
                    karo++;
                    gelb++;
                    break;
                case ka_gruen:
                    karo++;
                    gruen++;
                    break;
                case ka_orange:
                    karo++;
                    orange++;
                    break;
                case ka_rot:
                    karo++;
                    rot++;
                    break;
                case ka_violett:
                    karo++;
                    violett++;
                    break;
                case kb_blau:
                    kleeblatt++;
                    blau++;
                    break;
                case kb_gelb:
                    kleeblatt++;
                    gelb++;
                    break;
                case kb_gruen:
                    kleeblatt++;
                    gruen++;
                    break;
                case kb_orange:
                    kleeblatt++;
                    orange++;
                    break;
                case kb_rot:
                    kleeblatt++;
                    rot++;
                    break;
                case kb_violett:
                    kleeblatt++;
                    violett++;
                    break;
                case kr_blau:
                    kreuz++;
                    blau++;
                    break;
                case kr_gelb:
                    kreuz++;
                    gelb++;
                    break;
                case kr_gruen:
                    kreuz++;
                    gruen++;
                    break;
                case kr_orange:
                    kreuz++;
                    orange++;
                    break;
                case kr_rot:
                    kreuz++;
                    rot++;
                    break;
                case kr_violett:
                    kreuz++;
                    violett++;
                    break;
                case ks_blau:
                    kreis++;
                    blau++;
                    break;
                case ks_gelb:
                    kreis++;
                    gelb++;
                    break;
                case ks_gruen:
                    kreis++;
                    gruen++;
                    break;
                case ks_orange:
                    kreis++;
                    orange++;
                    break;
                case ks_rot:
                    kreis++;
                    rot++;
                    break;
                case ks_violett:
                    kreis++;
                    violett++;
                    break;
                case qu_blau:
                    quadrat++;
                    blau++;
                    break;
                case qu_gelb:
                    quadrat++;
                    gelb++;
                    break;
                case qu_gruen:
                    quadrat++;
                    gruen++;
                    break;
                case qu_orange:
                    quadrat++;
                    orange++;
                    break;
                case qu_rot:
                    quadrat++;
                    rot++;
                    break;
                case qu_violett:
                    quadrat++;
                    violett++;
                    break;
                case st_blau:
                    stern++;
                    blau++;
                    break;
                case st_gelb:
                    stern++;
                    gelb++;
                    break;
                case st_gruen:
                    stern++;
                    gruen++;
                    break;
                case st_orange:
                    stern++;
                    orange++;
                    break;
                case st_rot:
                    stern++;
                    rot++;
                    break;
                case st_violett:
                    stern++;
                    violett++;
                    break;
            }
        }

        int rg = Math.max(rot,gruen);
        int gb = Math.max(blau,gelb);
        int vo = Math.max(violett,orange);

        int kq = Math.max(karo,quadrat);
        int kk = Math.max(kreis,kreuz);
        int sk = Math.max(stern,kleeblatt);

        int rggb = Math.max(rg,gb);
        int vokq = Math.max(vo,kq);
        int kksk = Math.max(kk,sk);

        int rggbvokq = Math.max(rggb,vokq);
        return Math.max(kksk,rggbvokq);
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
        String out = "Spieler "+index;
        if (aktiv) out+=" aktiv";
        else out+=" nicht aktiv";

        out+=" Steine: "+gibSteineString();

        return out;
    }

}
