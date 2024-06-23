package PCServerSocket;

import java.awt.AWTException;
import java.awt.Robot;
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

    private static int xMouse = 0;
    private static int yMouse = 0;

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
//                processInputState(inputState, robot);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void processInputState(InputState inputState, Robot robot) {
        // Обработка нажатых клавиш
        for (String key : inputState.getPressedKeys()) {
            switch (key) {
                case "ArrowUp":
                    robot.keyPress(KeyEvent.VK_UP);
                    robot.keyRelease(KeyEvent.VK_UP);
                    break;
                case "ArrowDown":
                    robot.keyPress(KeyEvent.VK_DOWN);
                    robot.keyRelease(KeyEvent.VK_DOWN);
                    break;
                // Добавьте другие обработчики для других клавиш по аналогии
                default:
                    break;
            }
        }

        // Обработка изменения координаты мыши
        xMouse += inputState.getDeltaX();
        yMouse += inputState.getDeltaY();
        robot.mouseMove(xMouse, yMouse);
    }

    // Класс для представления состояния ввода в JSON
    private static class InputState {
        private String[] pressedKeys;
        private int deltaX;
        private int deltaY;

        public String[] getPressedKeys() {
            return pressedKeys;
        }

        public void setPressedKeys(String[] pressedKeys) {
            this.pressedKeys = pressedKeys;
        }

        public int getDeltaX() {
            return deltaX;
        }

        public void setDeltaX(int deltaX) {
            this.deltaX = deltaX;
        }

        public int getDeltaY() {
            return deltaY;
        }

        public void setDeltaY(int deltaY) {
            this.deltaY = deltaY;
        }
    }
}
