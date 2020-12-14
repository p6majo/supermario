package src;

/**
 *
 * @author Marc Lob
 * @version 2019-05-26
 */
public class SuperMarioSocket
{
    private String ip;
    private int port;
    private int state;
    
    public static final int ZUSTAND_USER = 1;
    public static final int ZUSTAND_PASSIVE = 2;
    public static final int ZUSTAND_ACTIVE = 3;
   
    public SuperMarioSocket(String pIP, int pPort){
        ip    = pIP;
        port  = pPort;
        state = ZUSTAND_USER;
    }
    
    public String getIP(){
        return ip;
    }
    
    public int getPort(){
        return port;
    }
    
    public int getZustand()
    {
        return state;
    }

    public void setZustand(int pZustand){
        state = pZustand;
    }
    
    public String toString(){
        return ""+ip+":"+port;
    }

}
