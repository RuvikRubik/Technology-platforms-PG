package org.example;

import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (
                Socket socket = new Socket(SERVER_IP, SERVER_PORT);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))
        ) {
            String start = (String) inputStream.readObject();
            System.out.println("Serwer: " + start);
            System.out.print("Podaj liczbę: ");
            int n = Integer.parseInt(reader.readLine());
            outputStream.writeObject(n);
            start = (String) inputStream.readObject();
            System.out.println("Serwer: " + start);
            for (int i = 0; i < n; i++) {
                System.out.print("Podaj treść wiadomości " + (i + 1) + ": ");
                String content = reader.readLine();
                Message message = new Message(i + 1, content);
                outputStream.writeObject(message);
            }
            outputStream.writeObject("Finish Client");
            String confirmation = (String) inputStream.readObject();
            System.out.println("Serwer: " + confirmation);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}