package com.godpalace.fastlibrary.net;

import java.io.BufferedOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class NetworkSender {
    public static boolean sendTo(InetAddress address, int port, byte[] data, DatapacketType type) {
        try {
            if (type == DatapacketType.TCP) {
                Socket socket = new Socket(address, port);
                BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());

                bos.write(data);
                bos.flush();

                bos.close();
                socket.close();
            } else {
                DatagramSocket socket = new DatagramSocket();

                socket.send(new DatagramPacket(data, data.length, address, port));

                socket.close();
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
