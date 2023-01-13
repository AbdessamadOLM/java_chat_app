package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginThread extends Thread{

    public static final int MAXIMUM_DATAGRAM_SIZE = 255;

    DbConnection db = new DbConnection();
    String connecUsers = ""; //contient les utilisateur connecté
    byte[] write ;
    String username;
    String[] tabData = new String[3];
    DatagramSocket serverSocket;
    DatagramPacket PacketIn;
    String infoRecu;
    int readPort;
    public static List<User> session = new ArrayList<User>() ;

    // constructeur pour initialiser le port
    public LoginThread(DatagramSocket s, DatagramPacket p) throws IOException {
        serverSocket = s;
        PacketIn = p;
    }

    public void run() {

            try {
                readPort = PacketIn.getPort();
                infoRecu = new String(PacketIn.getData(), 0, PacketIn.getLength());
                tabData = infoRecu.split(" ");
                // boolean b = db.login(tabData[1],tabData[2]);
                if (tabData[0].equals("login")) {
                    if (db.login(tabData[1], tabData[2])) {
                        //ajouter l'utilisateur courant à la session du serveur
                        //envoyer la list des user actif
                        //lancer le thread de chater
                        System.out.println("login de :" + tabData[1] + " port " + PacketIn.getPort());
                        username = tabData[1];
                        User user = new User(serverSocket, PacketIn, username,readPort);
                        session.add(user);
                        System.out.println("session size "+session.size());
                        //chercher les client actif
                        connecUsers = userActif(tabData[1]);

                        if (connecUsers.equals(""))
                            connecUsers = "personne n'est encore connecter";

                        PacketIn.setData(connecUsers.getBytes());
                        serverSocket.send(PacketIn);
                        //si la session est pleine on envoie on doit demander le nom du client cible
                        if (session.size() > 1) {
                            Thread aux = new Auxilli(readPort,username,user);
                            aux.start();
                            aux.join();
                        }
                    } else {
                        // envoyer un messager à l'utilisateur pour réssayer ou bien s'inscrire
                        String mes = "username or password is wrong";
                        write = new byte[MAXIMUM_DATAGRAM_SIZE];
                        write = mes.getBytes();
                        PacketIn = new DatagramPacket(write, write.length, PacketIn.getAddress(), readPort);
                        serverSocket.send(PacketIn);
                    }
                } else if (tabData[0].equals("sign")) {
                    String mes = db.ajouter_utilisateur(tabData[1], tabData[2]);
                    write = new byte[MAXIMUM_DATAGRAM_SIZE];
                    write = mes.getBytes();
                    PacketIn = new DatagramPacket(write, write.length, PacketIn.getAddress(), readPort);
                    serverSocket.send(PacketIn);

                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


    }


    public static String userActif( String user){
        String connecUsers = "";
        for (User u:session) {
            if((u.user_name).equals(user))
                continue;
            connecUsers +=  u.user_name+" ";
        }
        return connecUsers;
    }
}
