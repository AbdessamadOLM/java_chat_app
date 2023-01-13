package client;

import server.LoginThread;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ReadThread extends Thread{
    DatagramSocket readSocket;
    String clientCible;


    public ReadThread(DatagramSocket s, String cltCible){
        readSocket = s;
        clientCible = cltCible;
    }

    public void run(){
        byte [] readData ;
        DatagramPacket readPacket ;
        String readMessage;
        while (true){
            try {
                readData = new byte[LoginThread.MAXIMUM_DATAGRAM_SIZE];
                readPacket = new DatagramPacket(readData,readData.length, InetAddress.getLocalHost(),7000);
                readSocket.receive(readPacket);
                readMessage = new String(readPacket.getData(), 0, readPacket.getLength());
                String[] mess = readMessage.split(" ",2);
                if(readMessage.equals(WriteThread.DECONNECION))
                    break;
                System.out.println( "read thread<" + mess[0] + "> : " +mess[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("vous êtes déconnecté");

    }
}
