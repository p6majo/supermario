package qwirkle;


import java.util.Random;

/**
 * The class Beutel
 *
 * @author p6majo
 * @version 2019-06-22
 */
public class Beutel {

    /*
     *********************************************
     ***           Attributes           **********
     *********************************************
     */

    private Ring<Stein> steine;
    private int anzahl;
    private Random random;



    /*
     **********************************************
     ****           Constructors         **********
     **********************************************
     */

    public Beutel(int n){

        random = new Random();

        steine = new Ring<>();
        for (int i = 0; i < n; i++)
            for (Symbol value : Symbol.values()) {
                steine.insert(new Stein(value,i));
                anzahl++;
            }
    }

    /*
     ***********************************************
     ***           Getters              ************
     ***********************************************
     */

    public int gibAnzahl(){
        return this.anzahl;
    }


    public Stein gibStein(){
        if (anzahl>0) {
            int zufall = random.nextInt(3 * anzahl);
            while (zufall > 0) {
                steine.next();
                zufall--;
            }
            anzahl--;
            Stein returnStein = steine.getContent();
            steine.remove();
            return returnStein;
        }
        return null;
    }

    /*
     ***********************************************
     ***           Setters              ************
     ***********************************************
     */


    public void addStein(Stein stein){
        this.steine.insert(stein);
        this.anzahl++;
    }


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
        return super.toString();
    }


}
