package qwirkle;


public class QwirkleServerTest {

    private QwirkleServer server;




    public QwirkleServerTest(){
        server = new QwirkleServer(10000);
        System.out.println("Geoeffnet: "+server.isOpen());

        System.out.println("Melde Spieler an: ");
        QwirkleClient spieler = new QwirkleClient("localhost",10000,"Johannes","Martin","24.08.1976");

        System.out.println("Melde Spieler an: ");
        spieler = new QwirkleClient("localhost",10000,"Jan","Pickshaus");


        System.out.println("Melde Spieler an: ");
        spieler = new QwirkleClient("localhost",10000,"Elena","Karan");

        System.out.println("Melde Spieler an: ");
        spieler = new QwirkleClient("localhost",10000,"Marc","Lob");


    }

    public static void main(String[] args) {
        new QwirkleServerTest();
    }

}