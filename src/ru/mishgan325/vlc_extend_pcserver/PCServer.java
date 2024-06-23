package ru.mishgan325.vlc_extend_pcserver;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;

public class PCServer {

    static ButtonMonitor buttonMonitor;

    public static void main(String[] args) throws IOException, AWTException {

        ServerSocket serverSocket = new ServerSocket(12345); // Создаем серверный соксет на порту 12345

        System.out.println("Сервер ожидает подключения...");


        InetAddress ip = InetAddress.getLocalHost();
        System.out.println("IP адрес сервера: " + ip.getHostAddress());

        Socket clientSocket = serverSocket.accept(); // Ожидаем подключение клиента
        System.out.println("Клиент подключен.");

        // Получение входного и выходного потоков для обмена данными с клиентом
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        String message;
        Gson gson = new Gson();

        buttonMonitor = new ButtonMonitor();

        while ((message = in.readLine()) != null) {
            System.out.println("Получено от клиента: " + message);

            try {
                InputState inputState = gson.fromJson(message, InputState.class);
                processInputState(inputState);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void processInputState(InputState inputState) {
        // Обработка нажатых клавиш

        buttonMonitor.processStrings(inputState.getPressedKeys());
        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();

        buttonMonitor.mouseMove((int)mouseLocation.getX() + (int)inputState.getDeltaX(), (int)mouseLocation.getY() + (int)inputState.getDeltaY());
    }

    // Класс для представления состояния ввода в JSON
    private static class InputState {
        private Set<String> pressedKeys;
        private float deltaX;
        private float deltaY;

        public Set<String> getPressedKeys() {
            return pressedKeys;
        }

        public float getDeltaX() {
            return deltaX;
        }

        public float getDeltaY() {
            return deltaY;
        }
    }
}
