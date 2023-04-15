package com.example.lasertagproject;

import java.io.IOException;
import java.net.*;

import java.net.*;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
    private static DatagramSocket clientSocket;

    private static InetAddress address;



    public UDPClient() throws SocketException, UnknownHostException  {

        clientSocket = new DatagramSocket();
        address = InetAddress.getByName("localhost");

    }

    public static String sendToServer(String msg) throws IOException {


                byte[] buffer = msg.getBytes();
                DatagramPacket pack = new DatagramPacket(buffer, buffer.length, address, 7501);
                clientSocket.send(pack);

//                byte[] buffer = new byte[1024];
//                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                pack = new DatagramPacket(buffer, buffer.length);
                clientSocket.receive(pack);
                String received = new String(pack.getData(), 0, pack.getLength());
//                System.out.println("Received from server: " + received);

                return received;



    }
    public void close() {
        clientSocket.close();
    }
}

