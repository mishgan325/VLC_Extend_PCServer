package ru.mishgan325.vlc_extend_pcserver;

import java.awt.*;

public class MouseController {
    static Robot robot;

    public MouseController() throws AWTException {
        robot = new Robot();
    }
    public static void main(String[] args) throws AWTException {
        robot = new Robot();
        System.out.println(MouseInfo.getPointerInfo().getLocation());
//        moveTo(MouseInfo.getPointerInfo().getLocation(), 100, 100);
        System.out.println(MouseInfo.getPointerInfo().getLocation());
    }

    public void moveTo(final int deltaX, final int deltaY) throws InterruptedException {
        // Начальная позиция мыши
        int startX = (int) MouseInfo.getPointerInfo().getLocation().getX();
        int startY = (int) MouseInfo.getPointerInfo().getLocation().getY();

        // Вычисляем конечную позицию мыши
        int endX = startX + deltaX;
        int endY = startY + deltaY;

        // Плавно перемещаем мышь
        int steps = 25; // Количество шагов
        for (int i = 0; i < steps; i++) {
            double progress = (double) i / steps;
            int currentX = (int) (startX + progress * (endX - startX));
            int currentY = (int) (startY + progress * (endY - startY));

            // Перемещаем мышь
            robot.mouseMove(currentX, currentY);

            // Задержка между шагами (можно настроить)
//            Thread.sleep(10); // 10 миллисекунд
        }
    }
}

