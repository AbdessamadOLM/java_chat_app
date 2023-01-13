package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientMain {
    public static final int MAXIMUM_DATAGRAM_SIZE = 255;

    public static void main(String[] args) throws IOException, InterruptedException {
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        int serverPort = 5000;
        DatagramPacket myDataPacket;
        WriteThread writeThread;
        ReadThread readThread;
        String info, readData;
        String[] userName;
        byte[] myData;
        InetAddress serverIP = InetAddress.getByName("127.0.0.1");
        DatagramSocket clientSocket = new DatagramSocket();


       do {
        // collecter les données
        System.out.println("Please enter action: [login,sign] username pass");
        info = userInput.readLine();
        //séparer le nom, mot de pass, nom du client cible et le stocker dans userName
        userName = info.split(" ");

        System.out.println("| Username set to <" + userName[1] + " and password " + userName[2] + ">. Sending to server... |");
        myData = info.getBytes();

        // Send packet with userName to server
        myDataPacket = new DatagramPacket(myData, myData.length, serverIP, serverPort);
        clientSocket.send(myDataPacket);
        // la réponse du serveur
        myData = new byte[MAXIMUM_DATAGRAM_SIZE];
        myDataPacket = new DatagramPacket(myData, myData.length);
        clientSocket.receive(myDataPacket);
        readData = new String(myDataPacket.getData(), 0, myDataPacket.getLength());
        System.out.println("choisissez un ami: " + readData);
    }while (readData.startsWith("username") || readData.startsWith("1nouveau") );
            System.out.println("[message] to [nom de ton amis] ou bien [deconnexion] pour se déconnecter");
            writeThread = new WriteThread(clientSocket,7000,userName[1]);
            readThread = new ReadThread(clientSocket,info);
            writeThread.start();
            readThread.start();
            writeThread.join();
            readThread.join();

    }
}
