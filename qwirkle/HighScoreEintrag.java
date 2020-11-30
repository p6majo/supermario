package qwirkle;

import java.util.Date;

/**
 * The class HighScoreEintrag
 *
 * @author p6majo
 * @version 2019-07-04
 */
public class HighScoreEintrag {

    /*
     *********************************************
     ***           Attributes           **********
     *********************************************
     */

    private final String name;
    private final String vorname;
    private final int punkte;
    private final Date datum;

    /*
     **********************************************
     ****           Constructors         **********
     **********************************************
     */

    public HighScoreEintrag(String name, String vorname, int punkte, Date datum){
        this.name = name;
        this.vorname = vorname;
        this.punkte = punkte;
        this.datum = datum;
    }
    /*
     ***********************************************
     ***           Getters              ************
     ***********************************************
     */


    public String getName() {
        return name;
    }

    public String getVorname() {
        return vorname;
    }

    public int getPunkte() {
        return punkte;
    }

    public Date getDatum() {
        return datum;
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
        return "Eintrag vom "+datum.toString()+": "+name+", "+vorname+": "+punkte;
    }


}
