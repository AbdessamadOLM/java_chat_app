package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class testMain {
    public static final int SERVERPORT = 5000;
    public static final int MAXIMUM_DATAGRAM_SIZE = 255;
    public static void main(String[] args) throws IOException {
        DatagramSocket serverSocket;
        byte[] recivePk;
        DatagramPacket packet;
        Thread loginserver;
        serverSocket = new DatagramSocket(SERVERPORT);

        while (true){
            recivePk = new byte[MAXIMUM_DATAGRAM_SIZE];
            packet = new DatagramPacket(recivePk,recivePk.length);
            serverSocket.receive(packet);
            loginserver = new LoginThread(serverSocket,packet);
            loginserver.start();
        }
    }

}
