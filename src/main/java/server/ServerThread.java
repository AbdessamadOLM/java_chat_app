package server;


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

public class ServerThread extends Thread{
    public static final int MAXIMUM_DATAGRAM_SIZE = 255;
    public DatagramSocket serverSocket;
    public DatagramPacket readPacket;
    public DbConnection db = new DbConnection();
    public String str_to_send;


    public ServerThread(DatagramSocket s,DatagramPacket p){
        serverSocket = s;
        readPacket = p;
    }
    public void run(){
        try {

            String data_recu = new String(readPacket.getData(), 0, readPacket.getLength());
            System.out.println("mess recu "+data_recu);
            String[] tab_aux = data_recu.split(",",3);
            byte[] data_to_send = new byte[MAXIMUM_DATAGRAM_SIZE];
            System.out.println("la fonction recoit ("+tab_aux[2]+","+tab_aux[1]);
            System.out.println("nom du client cibl"+tab_aux[1]+" mess "+tab_aux[0]+" nom de l'emetteur "+tab_aux[2]);
            // verifier si le client cible :(tab_aux[1]) est un ami de l'emetteur (tab_aux[2])
            //tester si le client cible est connecter
            int user_port = getUser(tab_aux[1].trim());
            if(user_port == 0){
                str_to_send =tab_aux[1]+ " " + "est hors ligne";
                data_to_send = str_to_send.getBytes();
                readPacket = new DatagramPacket(data_to_send, data_to_send.length, InetAddress.getLocalHost(), readPacket.getPort());
                serverSocket.send(readPacket);
                // verifier si le client cible :(tab_aux[1]) est un ami de l'emetteur (tab_aux[2])
            }else if (!(db.chercher_ami(tab_aux[2].trim(),tab_aux[1].trim()))){
                str_to_send= tab_aux[2]+" ce n'est pas votre ami";
                data_to_send = str_to_send.getBytes();
                readPacket = new DatagramPacket(data_to_send, data_to_send.length, InetAddress.getLocalHost(), readPacket.getPort());
                serverSocket.send(readPacket);
            }else {
                str_to_send= tab_aux[2]+ " " + tab_aux[0];
                data_to_send = str_to_send.getBytes();
                readPacket = new DatagramPacket(data_to_send, data_to_send.length, InetAddress.getLocalHost(), user_port);
                serverSocket.send(readPacket);
                System.out.println("donné envoyé sont "+ new String(readPacket.getData()));
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public int  getUser(String user_name){
        List<User> session = LoginThread.session;
        int u = 0;
        for (int i = 0; i<session.size();i++){
            if((session.get(i).user_name).equals(user_name))
                u = session.get(i).port;
        }
        return u;
    }
}
