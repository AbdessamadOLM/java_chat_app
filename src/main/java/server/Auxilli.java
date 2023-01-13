package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;

import static server.testMain.MAXIMUM_DATAGRAM_SIZE;

public class Auxilli extends Thread{
    public static final String DECONNECION = "deconnexion";
    DatagramSocket serverSocket = new DatagramSocket(7000);
    DatagramPacket PacketIn;
    List<User> session;
    int readPort;
    String username;
    User u;

    public Auxilli(int readPort,String username,User user) throws SocketException {
       this.session = LoginThread.session;
       this.readPort = readPort;
       this.username = username;
       u = user;
    }

    public void run(){
          while (true){
                    try {
                        byte[] write = new byte[MAXIMUM_DATAGRAM_SIZE];
                        PacketIn = new DatagramPacket(write, write.length, InetAddress.getLocalHost(), readPort);
                        serverSocket.receive(PacketIn);
                        String data = new String(PacketIn.getData(),0,PacketIn.getLength());
                        System.out.println("premier contact"+data);
                        if(data.equals(DECONNECION)){
                            for (int i = 0 ;i<session.size();i++) {
                                if(session.get(i).port == PacketIn.getPort()){
                                    session.remove(i);
                                }
                            }
                            String mes = DECONNECION;
                            write = new byte[MAXIMUM_DATAGRAM_SIZE];
                            write = mes.getBytes();
                            PacketIn = new DatagramPacket(write, write.length, PacketIn.getAddress(), PacketIn.getPort());
                            serverSocket.send(PacketIn);
                         }else {
                            Thread clientThread1 = new ServerThread(serverSocket, PacketIn);
                            clientThread1.start();
                            clientThread1.join();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
        }
    }
}
