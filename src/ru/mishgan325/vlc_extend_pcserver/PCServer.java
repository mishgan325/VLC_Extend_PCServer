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

import com.google.gson.Gson;

public class PCServer {

    private static int xMouse;
    private static int yMouse;

    public static void main(String[] args) throws IOException, AWTException {
        Robot robot = new Robot();
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

        while ((message = in.readLine()) != null) {
            System.out.println("Получено от клиента: " + message);

            try {
                InputState inputState = gson.fromJson(message, InputState.class);
                processInputState(inputState, robot);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void processInputState(InputState inputState, Robot robot) {
        // Обработка нажатых клавиш
        for (String key : inputState.getPressedKeys()) {
            switch (key) {
                case "ArrowUp" -> {
                    robot.keyPress(KeyEvent.VK_UP);
                    robot.keyRelease(KeyEvent.VK_UP);
                }
                case "ArrowDown" -> {

                    robot.keyPress(KeyEvent.VK_DOWN);
                    robot.keyRelease(KeyEvent.VK_DOWN);
                }
                case "ArrowRight" -> {
                    robot.keyPress(KeyEvent.VK_RIGHT);
                    robot.keyRelease(KeyEvent.VK_RIGHT);
                }
                case "ArrowLeft" -> {
                    robot.keyPress(KeyEvent.VK_LEFT);
                    robot.keyRelease(KeyEvent.VK_LEFT);
                }

                case "VolumeUp" -> {
                    MediaButtonsControl.User32.INSTANCE.keybd_event((byte) MediaButtonsControl.User32.VK_VOLUME_UP, (byte) 0, 0, 0);
                    MediaButtonsControl.User32.INSTANCE.keybd_event((byte) MediaButtonsControl.User32.VK_VOLUME_UP, (byte) 0, 2, 0);
                }

                case "VolumeDown" -> {
                    MediaButtonsControl.User32.INSTANCE.keybd_event((byte) MediaButtonsControl.User32.VK_VOLUME_DOWN, (byte) 0, 0, 0);
                    MediaButtonsControl.User32.INSTANCE.keybd_event((byte) MediaButtonsControl.User32.VK_VOLUME_DOWN, (byte) 0, 2, 0);
                }

                case "PlayPause" -> {
                    MediaButtonsControl.User32.INSTANCE.keybd_event((byte) MediaButtonsControl.User32.VK_MEDIA_PLAY_PAUSE, (byte) 0, 0, 0);
                    MediaButtonsControl.User32.INSTANCE.keybd_event((byte) MediaButtonsControl.User32.VK_MEDIA_PLAY_PAUSE, (byte) 0, 2, 0);
                }
                case "MediaNext" -> {
                    MediaButtonsControl.User32.INSTANCE.keybd_event((byte) MediaButtonsControl.User32.VK_NEXT_TRACK, (byte) 0, 0, 0);
                    MediaButtonsControl.User32.INSTANCE.keybd_event((byte) MediaButtonsControl.User32.VK_NEXT_TRACK, (byte) 0, 2, 0);
                }
                case "MediaPrev" -> {
                    MediaButtonsControl.User32.INSTANCE.keybd_event((byte) MediaButtonsControl.User32.VK_PREVIOUS_TRACK, (byte) 0, 0, 0);
                    MediaButtonsControl.User32.INSTANCE.keybd_event((byte) MediaButtonsControl.User32.VK_PREVIOUS_TRACK, (byte) 0, 2, 0);
                }

                case "ButtonV" -> {
                    robot.keyPress(KeyEvent.VK_V);
                    robot.keyRelease(KeyEvent.VK_V);
                }
                case "ButtonB" -> {
                    robot.keyPress(KeyEvent.VK_B);
                    robot.keyRelease(KeyEvent.VK_B);
                }
                case "ButtonOpenBracket" -> {
                    robot.keyPress(KeyEvent.VK_OPEN_BRACKET);
                    robot.keyRelease(KeyEvent.VK_OPEN_BRACKET);
                }
                case "ButtonCloseBracket" -> {
                    robot.keyPress(KeyEvent.VK_CLOSE_BRACKET);
                    robot.keyRelease(KeyEvent.VK_CLOSE_BRACKET);
                }
                case "ButtonLMB" -> {
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                }
                case "ButtonRMB" -> {
                    robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                }

                default -> System.out.println("Don't know this button: " + key);
            }
        }
        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();

        robot.mouseMove((int)mouseLocation.getX() + (int)inputState.getDeltaX(), (int)mouseLocation.getY() + (int)inputState.getDeltaY());
    }

    // Класс для представления состояния ввода в JSON
    private static class InputState {
        private String[] pressedKeys;
        private float deltaX;
        private float deltaY;

        public String[] getPressedKeys() {
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
