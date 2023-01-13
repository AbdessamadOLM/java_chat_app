package client;

import server.LoginThread;
import server.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class WriteThread extends Thread{
    public static final int MAXIMUM_DATAGRAM_SIZE = 255;
    public static final String DECONNECION = "deconnexion";
    DatagramSocket writeSocket;  //socket to write in
    DatagramPacket writePacket;
    int serverPort;
    InetAddress serverAddress;
    String userName;

    public WriteThread(DatagramSocket s, int serverPort, String user_name) throws UnknownHostException {
        writeSocket = s;
        this.serverPort = serverPort;
        serverAddress = InetAddress.getLocalHost();
        this.userName = user_name;

    }

    public void run(){
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        byte [] writeByte;
        while (true){
            writeByte = new byte[MAXIMUM_DATAGRAM_SIZE];
           try {
               String writeData = input.readLine();
               if(writeData.equals(DECONNECION)){
                   writeByte = writeData.getBytes();
                   writePacket = new DatagramPacket(writeByte,writeByte.length,serverAddress,serverPort);
                   writeSocket.send(writePacket);
                   break;
               }

               String[] data = writeData.split("to",2);
               writeData = data[0]+","+data[1]+","+userName;
               writeByte = writeData.getBytes();
               writePacket = new DatagramPacket(writeByte,writeByte.length,serverAddress,serverPort);
               writeSocket.send(writePacket);
               System.out.println("write thread<"+userName+"> : "+data[0]+" > "+data[1]);
           } catch (IOException e) {
               e.printStackTrace();
           }catch (ArrayIndexOutOfBoundsException e) {
               continue;
           }
       }

       // dans ce cas vous êtes déconnecté

            System.out.println("write thread<"+userName+"> :  a été déconnecté");



    }
}
