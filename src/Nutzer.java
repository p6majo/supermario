package src;

/**
 * The class Nutzer
 *
 * @author p6majo
 * @version 2020-10-28
 */
public class Nutzer {

    /*
     *********************************************
     ***           Attributes           **********
     *********************************************
     */

    private int nutzerId=-1;
    private String name;
    private String vorname;
    private String nickname;


    /*
     **********************************************
     ****           Constructors         **********
     **********************************************
     */

    public Nutzer(int nutzerId, String name, String vorname, String nickname) {
        this.nutzerId = nutzerId;
        this.name = name;
        this.vorname = vorname;
        this.nickname =nickname;
    }


    /*
     ***********************************************
     ***           Getters              ************
     ***********************************************
     */

    public int getNutzerId() {
        return nutzerId;
    }

    public String getName() {
        return name;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNickname() { return nickname; }

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
    public String toString() { return "Nutzer "+getNutzerId()+" mit Namen: "+getVorname()+" "+getName()+" mit Spitznamen "+getNickname(); }



}
