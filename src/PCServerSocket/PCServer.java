package PCServerSocket;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.net.InetAddress;

public class PCServer {

    private static int xMove = 0;
    private static int yMove = 0;

    private static String parts[];

    public static void main(String[] args) throws IOException, AWTException {
        Robot robot = new Robot();
        ServerSocket serverSocket = new ServerSocket(12345); // Создаем серверный соксет на порту 12345

        InetAddress ip = InetAddress.getLocalHost();
        System.out.println("IP адрес сервера: " + ip.getHostAddress());
        System.out.println("Сервер ожидает подключения...");

        QRCodeGenerator qrCodeGenerator = new QRCodeGenerator();
        qrCodeGenerator.generateQRCode(ip.getHostAddress());

        Point mouseLocation;

        Socket clientSocket = serverSocket.accept(); // Ожидаем подключение клиента

        System.out.println("Клиент подключен.");
        qrCodeGenerator.closeQRCodeWindow();

        // Получение входного и выходного потоков для обмена данными с клиентом
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        String message;
        while ((message = in.readLine()) != null) {
//            System.out.println("Получено от клиента: " + message);
            xMove = yMove = 0;
            mouseLocation = MouseInfo.getPointerInfo().getLocation();

            switch (message) {
                case "WINDOWS" -> {
                    robot.keyPress(KeyEvent.VK_WINDOWS);
                    robot.keyRelease(KeyEvent.VK_WINDOWS);
                }
                case "VOLUME+" -> {
                    MediaButtonsControl.User32.INSTANCE.keybd_event((byte) MediaButtonsControl.User32.VK_VOLUME_UP, (byte) 0, 0, 0);
                    MediaButtonsControl.User32.INSTANCE.keybd_event((byte) MediaButtonsControl.User32.VK_VOLUME_UP, (byte) 0, 2, 0);
                }
                case "VOLUME-" -> {
                    MediaButtonsControl.User32.INSTANCE.keybd_event((byte) MediaButtonsControl.User32.VK_VOLUME_DOWN, (byte) 0, 0, 0);
                    MediaButtonsControl.User32.INSTANCE.keybd_event((byte) MediaButtonsControl.User32.VK_VOLUME_DOWN, (byte) 0, 2, 0);
                }
                case "MUTE" -> {
                    MediaButtonsControl.User32.INSTANCE.keybd_event((byte) MediaButtonsControl.User32.VK_VOLUME_MUTE, (byte) 0, 0, 0);
                    MediaButtonsControl.User32.INSTANCE.keybd_event((byte) MediaButtonsControl.User32.VK_VOLUME_MUTE, (byte) 0, 2, 0);
                }
                case "PAUSE" -> {
                    MediaButtonsControl.User32.INSTANCE.keybd_event((byte) MediaButtonsControl.User32.VK_MEDIA_PLAY_PAUSE, (byte) 0, 0, 0);
                    MediaButtonsControl.User32.INSTANCE.keybd_event((byte) MediaButtonsControl.User32.VK_MEDIA_PLAY_PAUSE, (byte) 0, 2, 0);
                }
                case "NEXT" -> {
                    MediaButtonsControl.User32.INSTANCE.keybd_event((byte) MediaButtonsControl.User32.VK_NEXT_TRACK, (byte) 0, 0, 0);
                    MediaButtonsControl.User32.INSTANCE.keybd_event((byte) MediaButtonsControl.User32.VK_NEXT_TRACK, (byte) 0, 2, 0);
                }
                case "PREV" -> {
                    MediaButtonsControl.User32.INSTANCE.keybd_event((byte) MediaButtonsControl.User32.VK_PREVIOUS_TRACK, (byte) 0, 0, 0);
                    MediaButtonsControl.User32.INSTANCE.keybd_event((byte) MediaButtonsControl.User32.VK_PREVIOUS_TRACK, (byte) 0, 2, 0);
                }
                case "RIGHT" -> {
                    robot.keyPress(KeyEvent.VK_RIGHT);
                    robot.keyRelease(KeyEvent.VK_RIGHT);
                }
                case "LEFT" -> {
                    robot.keyPress(KeyEvent.VK_LEFT);
                    robot.keyRelease(KeyEvent.VK_LEFT);
                }
                case "UP" -> {
                    robot.keyPress(KeyEvent.VK_UP);
                    robot.keyRelease(KeyEvent.VK_UP);
                }
                case "DOWN" -> {
                    robot.keyPress(KeyEvent.VK_DOWN);
                    robot.keyRelease(KeyEvent.VK_DOWN);
                }
                case "V" -> {
                    robot.keyPress(KeyEvent.VK_V);
                    robot.keyRelease(KeyEvent.VK_V);
                }
                case "B" -> {
                    robot.keyPress(KeyEvent.VK_B);
                    robot.keyRelease(KeyEvent.VK_B);
                }
                case "[" -> {
                    robot.keyPress(KeyEvent.VK_OPEN_BRACKET);
                    robot.keyRelease(KeyEvent.VK_OPEN_BRACKET);
                }
                case "]" -> {
                    robot.keyPress(KeyEvent.VK_CLOSE_BRACKET);
                    robot.keyRelease(KeyEvent.VK_CLOSE_BRACKET);
                }
                case "LMB", "LMB_CLICK" -> {
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                }
                case "RMB", "RMB_CLICK" -> {
                    robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                }

                case "LMB_HOLD" -> {
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                }
                case "LMB_RELEASE" -> {
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                }
                default -> {
                    if(message.contains("MouseWheel")) {
                        parts = message.split("\\|");
                        robot.mouseWheel(Integer.parseInt(parts[1])/10);
                    }
                    else {
                        parts = message.split("\\|");
                        robot.mouseMove((int)mouseLocation.getX() + Integer.parseInt(parts[0]), (int)mouseLocation.getY() + + Integer.parseInt(parts[1]));
                    }

                }


            }


        }

        //clientSocket.close();
        //serverSocket.close();
    }
}