package com.example.androidmatrikelnummer;
import java.io.*;
import java.net.*;

public class ClientSocket {
    public String run() {
        String line = null;
        try {
            int serverPort = 53212;
            InetAddress host = InetAddress.getByName("se2-isys.aau.at");
            System.out.println("Connecting to server on port " + serverPort);

            int matrikelNummer = 11717299;

            Socket socket = new Socket(host, serverPort);
            System.out.println("Just connected to " + socket.getRemoteSocketAddress());
            PrintWriter toServer =
                    new PrintWriter(socket.getOutputStream(), true);
            BufferedReader fromServer =
                    new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
            toServer.println(matrikelNummer);
            line = fromServer.readLine();
            System.out.println("Client received: " + line + " from Server");
            toServer.close();
            fromServer.close();
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }


        return line;
    }

    public static void main(String[] args) {
        ClientSocket client = new ClientSocket();
        client.run();

    }
}
