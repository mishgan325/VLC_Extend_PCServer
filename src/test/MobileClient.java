package test;

import java.io.*;
import java.net.*;

public class MobileClient {
    public static void main(String[] args) throws IOException {
        String serverAddress = "192.168.85.161"; // Замените на IP-адрес вашего ПК
        int serverPort = 12345; // Замените на порт, который вы указали на сервере

        Socket socket = new Socket(serverAddress, serverPort);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Отправляем сообщение на сервер
        out.println("Привет, сервер!");

        // Получаем ответ от сервера
        String response = in.readLine();
        System.out.println("Ответ от сервера: " + response);

        socket.close();
    }
}