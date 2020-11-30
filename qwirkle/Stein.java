package qwirkle;

import java.awt.*;



/**
 * The class Stein
 *
 * @author p6majo
 * @version 2019-06-20
 */
public class Stein implements Comparable<Stein> {

    /*
     *********************************************
     ***           Attributes           **********
     *********************************************
     */

    public static final String SEP = "&";
    private Integer zeile= null;
    private Integer spalte= null;
    private final  Symbol symbol;
    private final int family;

    private final Color blau=new Color(3,174,254);
    private final  Color rot=new Color(254,49,46);
    private final Color gruen = new Color(7,182,41);
    private final Color violett = new Color(138,161,255);
    private final Color gelb = new Color(255,255,17);
    private final Color orange= new Color(255,114,6);


    /*
     **********************************************
     ****           Constructors         **********
     **********************************************
     */

    public Stein(Symbol pSymbol,int family, Integer pZeile, Integer pSpalte){
        this.zeile = pZeile;
        this.spalte = pSpalte;
        this.symbol = pSymbol;
        this.family = family;

    }

    public Stein(Symbol pSymbol,int family){
        this(pSymbol,family,null,null);
    }

    public Stein(String steinString){
        String[] parts = steinString.split(SEP);
            this.symbol = Symbol.valueOf(parts[0]);
        this.family = Integer.parseInt(parts[1]);
        if (parts[2].equals("n")){
            this.zeile = null;
            this.spalte = null;
        }
        else {
            this.zeile = Integer.parseInt(parts[2]);
            this.spalte = Integer.parseInt(parts[3]);
        }
    }


    /*
     ***********************************************
     ***           Getters              ************
     ***********************************************
     */

    public Integer gibZeile() {
        return zeile;
    }

    public Integer gibSpalte() {
        return spalte;
    }

    public Symbol gibSymbol() {
        return symbol;
    }

    public String gibFarbString(){ return symbol.toString().substring(3);}

    public String gibSymbolString(){ return symbol.toString().substring(0,2);}


    /*
     ***********************************************
     ***           Setters              ************
     ***********************************************
     */

    public void setzeZeile(int pZ){
        this.zeile = pZ;
    }


    public void setzeSpalte(int pS){
        this.spalte = pS;
    }



    /*
     ***********************************************
     ***           Public methods       ************
     ***********************************************
     */

    public void erzeugeGraphik(Graphics g, int groesse, int x, int y, int rand){
        //black background
        g.setColor(Color.BLACK);
        g.fillRect(x+rand,y+rand,groesse,groesse);

        if (symbol!=null)
            switch(symbol){
                case ka_blau:
                    g.setColor(blau);
                    karo(g,groesse,x,y,rand);
                    break;
                case ka_gelb:
                    g.setColor(gelb);
                    karo(g,groesse,x,y,rand);
                    break;
                case ka_gruen:
                    g.setColor(gruen);
                    karo(g,groesse,x,y,rand);
                    break;
                case ka_orange:
                    g.setColor(orange);
                    karo(g,groesse,x,y,rand);
                    break;
                case ka_rot:
                    g.setColor(rot);
                    karo(g,groesse,x,y,rand);
                    break;
                case ka_violett:
                    g.setColor(violett);
                    karo(g,groesse,x,y,rand);
                    break;
                case kr_blau:
                    g.setColor(blau);
                    kreuz(g,groesse,x,y,rand);
                    break;
                case kr_gelb:
                    g.setColor(gelb);
                    kreuz(g,groesse,x,y,rand);
                    break;
                case kr_gruen:
                    g.setColor(gruen);
                    kreuz(g,groesse,x,y,rand);
                    break;
                case kr_orange:
                    g.setColor(orange);
                    kreuz(g,groesse,x,y,rand);
                    break;
                case kr_rot:
                    g.setColor(rot);
                    kreuz(g,groesse,x,y,rand);
                    break;
                case kr_violett:
                    g.setColor(violett);
                    kreuz(g,groesse,x,y,rand);
                    break;
                case ks_blau:
                    g.setColor(blau);
                    kreis(g,groesse,x,y,rand);
                    break;
                case ks_gelb:
                    g.setColor(gelb);
                    kreis(g,groesse,x,y,rand);
                    break;
                case ks_gruen:
                    g.setColor(gruen);
                    kreis(g,groesse,x,y,rand);
                    break;
                case ks_orange:
                    g.setColor(orange);
                    kreis(g,groesse,x,y,rand);
                    break;
                case ks_rot:
                    g.setColor(rot);
                    kreis(g,groesse,x,y,rand);
                    break;
                case ks_violett:
                    g.setColor(violett);
                    kreis(g,groesse,x,y,rand);
                    break;
                case kb_blau:
                    g.setColor(blau);
                    kleeblatt(g,groesse,x,y,rand);
                    break;
                case kb_gelb:
                    g.setColor(gelb);
                    kleeblatt(g,groesse,x,y,rand);
                    break;
                case kb_gruen:
                    g.setColor(gruen);
                    kleeblatt(g,groesse,x,y,rand);
                    break;
                case kb_orange:
                    g.setColor(orange);
                    kleeblatt(g,groesse,x,y,rand);
                    break;
                case kb_rot:
                    g.setColor(rot);
                    kleeblatt(g,groesse,x,y,rand);
                    break;
                case kb_violett:
                    g.setColor(violett);
                    kleeblatt(g,groesse,x,y,rand);
                    break;
                case qu_blau:
                    g.setColor(blau);
                    quadrat(g,groesse,x,y,rand);
                    break;
                case qu_gelb:
                    g.setColor(gelb);
                    quadrat(g,groesse,x,y,rand);
                    break;
                case qu_gruen:
                    g.setColor(gruen);
                    quadrat(g,groesse,x,y,rand);
                    break;
                case qu_orange:
                    g.setColor(orange);
                    quadrat(g,groesse,x,y,rand);
                    break;
                case qu_rot:
                    g.setColor(rot);
                    quadrat(g,groesse,x,y,rand);
                    break;
                case qu_violett:
                    g.setColor(violett);
                    quadrat(g,groesse,x,y,rand);
                    break;
                case st_blau:
                    g.setColor(blau);
                    stern(g,groesse,x,y,rand);
                    break;
                case st_gelb:
                    g.setColor(gelb);
                    stern(g,groesse,x,y,rand);
                    break;
                case st_gruen:
                    g.setColor(gruen);
                    stern(g,groesse,x,y,rand);
                    break;
                case st_orange:
                    g.setColor(orange);
                    stern(g,groesse,x,y,rand);
                    break;
                case st_rot:
                    g.setColor(rot);
                    stern(g,groesse,x,y,rand);
                    break;
                case st_violett:
                    g.setColor(violett);
                    stern(g,groesse,x,y,rand);
                    break;
            }
    }

     /*
     ***********************************************
     ***           Private methods      ************
     ***********************************************
     */
     private void kreis(Graphics g, int groesse, int x, int y, int rand){
         double kreisScaling = 0.8;
         int objGroesse = (int) (groesse*kreisScaling);
         g.fillOval(x+rand+(int) (0.5*(1-kreisScaling)*groesse),y+rand+(int) (0.5*(1-kreisScaling)*groesse),objGroesse,objGroesse);
     }

    private void karo(Graphics g, int groesse, int x, int y, int rand){
        double karoScaling = 0.4;
        Polygon polygon = new Polygon();
        int centerX = (int) (x+groesse*0.5+rand);
        int centerY = (int) (y+0.5*groesse+rand);
        int  objGroesse = (int) (karoScaling*groesse);
        polygon.addPoint(centerX+objGroesse,centerY);
        polygon.addPoint(centerX,centerY+objGroesse);
        polygon.addPoint(centerX-objGroesse,centerY);
        polygon.addPoint(centerX,centerY-objGroesse);
        polygon.addPoint(centerX+objGroesse,centerY);
        g.fillPolygon(polygon);
    }

    private void stern(Graphics g, int groesse, int x, int y, int rand){
        int centerX = (int) (x+0.5*groesse+rand);
        int centerY = (int) (y+0.5*groesse+rand);
        double rOut = groesse*0.4;
        double rIn = groesse*0.2;
        int px,py;
        Polygon polygon = new Polygon();
        for (int i = 0; i < 8; i++) {
            px =(int) (rOut*Math.cos(Math.PI/4*i));
            py =(int) (rOut*Math.sin(Math.PI/4*i));
            polygon.addPoint(centerX+px,centerY+py);
            double phi = Math.PI/8;
            px =(int) (rIn*Math.cos(phi+Math.PI/4*i));
            py =(int) (rIn*Math.sin(phi+Math.PI/4*i));
            polygon.addPoint(centerX+px,centerY+py);
        }
        g.fillPolygon(polygon);
    }

    private void kreuz(Graphics g, int groesse, int x, int y, int rand){
        int centerX = (int) (x+0.5*groesse+rand);
        int centerY = (int) (y+0.5*groesse+rand);
        double rOut = groesse*0.45;
        double rIn = groesse*0.15;
        int px,py;
        double phi = Math.PI/4;
        Polygon polygon = new Polygon();
        for (int i = 0; i < 4; i++) {
            px =(int) (rOut*Math.cos(-phi+Math.PI/2*i));
            py =(int) (rOut*Math.sin(-phi+Math.PI/2*i));
            polygon.addPoint(centerX+px,centerY+py);
            px =(int) (rIn*Math.cos(Math.PI/2*i));
            py =(int) (rIn*Math.sin(Math.PI/2*i));
            polygon.addPoint(centerX+px,centerY+py);
        }
        g.fillPolygon(polygon);
    }


    private void quadrat(Graphics g, int groesse, int x, int y, int rand){
        double squareScaling = 0.6;
        int objGroesse = (int) (groesse*squareScaling);
        g.fillRect(x+rand+(int) (0.5*(1-squareScaling)*groesse),y+rand+(int) (0.5*(1-squareScaling)*groesse),objGroesse,objGroesse);
    }

    /**
     * Erzeugt das Kleeblattsymbol
     * @param g  Grafik, in der das Symbol gezeichnet werden soll (hier meist JLabel oder BufferedImage)
     * @param groesse  Gr&ouml;&szlig;e der Grafik
     * @param x  x-Position in der Grafik
     * @param y  y-Position in der Grafik
     * @param rand  m&ouml;gliche Randgr&ouml;&szlig;e
     */
    private void kleeblatt(Graphics g, int groesse, int x, int y, int rand){
        double kreuzScaling = 0.25;
        int  centerX = (int) (x+0.5*groesse+rand);
        int centerY = (int) (y+0.5*groesse+rand);
        int objGroesse = (int) (kreuzScaling*groesse);
        int objGroesse2 =(int) ( objGroesse*1.4);
        g.fillOval(centerX-objGroesse2/2,centerY-objGroesse2/2,objGroesse2,objGroesse2);
        g.fillOval(centerX-objGroesse2/2,centerY-objGroesse2/2-objGroesse,objGroesse2,objGroesse2) ;
        g.fillOval(centerX-objGroesse2/2,centerY-objGroesse2/2+objGroesse,objGroesse2,objGroesse2);
        g.fillOval(centerX-objGroesse2/2-objGroesse,centerY-objGroesse2/2,objGroesse2,objGroesse2);
        g.fillOval(centerX-objGroesse2/2+objGroesse,centerY-objGroesse2/2,objGroesse2,objGroesse2);
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
        String out = this.symbol.toString()+SEP+this.family+SEP;
        if (this.zeile==null)
           out+="n"+SEP+"n";
        else
            out+=this.zeile+SEP+this.spalte;
        return out;
    }


    /**
     * Zwei Steine sind genau dann gleich, wenn sie in Symbol und Familie &uuml;bereinstimmen.
     * Die Position der Steine auf dem Spielfeld ist unerheblich.
     * @param o
     * @return
     */
    @Override
    public int compareTo(Stein o) {
        int symboldiff = this.gibSymbol().toString().compareTo(o.gibSymbol().toString());
        int familydiff = this.family-o.family;
        if (symboldiff==0 && familydiff ==0) return 0;
        if (symboldiff==0) return familydiff;
        return symboldiff;
    }
}
