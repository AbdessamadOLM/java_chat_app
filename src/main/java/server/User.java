package server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class User {
    public DatagramSocket socket;
    public DatagramPacket packet;
    public String user_name;
    public int port;

    public User(DatagramSocket userSocket,DatagramPacket datagramPacket,String name,int port){
        socket = userSocket;
        packet = datagramPacket;
        user_name = name;
        this.port = port;
    }

}
