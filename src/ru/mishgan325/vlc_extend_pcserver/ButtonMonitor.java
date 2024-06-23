package ru.mishgan325.vlc_extend_pcserver;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class ButtonMonitor {
    private Set<String> pressedButtons;
    private Robot robot;

    ButtonMonitor() throws AWTException {
        this.pressedButtons = new HashSet<>();
        robot = new Robot();

    }

    public void processStrings(Set<String> updatedButtons) {

        for (String key : updatedButtons) {
            if (!pressedButtons.contains(key)) {
                keyPress(key);
            }
        }

        for (String key : pressedButtons) {
            if (!updatedButtons.contains(key)) {
                keyRelease(key);
            }
        }

        pressedButtons = new HashSet<>(updatedButtons);
    }


    private void keyPress(String key) {
        switch (key) {
            case "ArrowUp" -> robot.keyPress(KeyEvent.VK_UP);

            case "ArrowDown" -> robot.keyPress(KeyEvent.VK_DOWN);

            case "ArrowRight" -> robot.keyPress(KeyEvent.VK_RIGHT);

            case "ArrowLeft" -> robot.keyPress(KeyEvent.VK_LEFT);

            case "VolumeUp" -> MediaButtonsControl.keyPress(MediaButtonsControl.User32.VK_VOLUME_UP);

            case "VolumeDown" -> MediaButtonsControl.keyPress(MediaButtonsControl.User32.VK_VOLUME_DOWN);

            case "PlayPause" -> MediaButtonsControl.keyPress(MediaButtonsControl.User32.VK_MEDIA_PLAY_PAUSE);

            case "MediaNext" -> MediaButtonsControl.keyPress(MediaButtonsControl.User32.VK_NEXT_TRACK);

            case "MediaPrev" -> MediaButtonsControl.keyPress(MediaButtonsControl.User32.VK_PREVIOUS_TRACK);

            case "ButtonV" -> robot.keyPress(KeyEvent.VK_V);

            case "ButtonB" -> robot.keyPress(KeyEvent.VK_B);

            case "ButtonOpenBracket" -> robot.keyPress(KeyEvent.VK_OPEN_BRACKET);

            case "ButtonCloseBracket" -> robot.keyPress(KeyEvent.VK_CLOSE_BRACKET);

            case "ButtonLMB" -> robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

            case "ButtonRMB" -> robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);

            default -> System.out.println("Don't know this button: " + key);
        }
    }

    private void keyRelease(String key) {
        switch (key) {
            case "ArrowUp" -> robot.keyRelease(KeyEvent.VK_UP);

            case "ArrowDown" -> robot.keyRelease(KeyEvent.VK_DOWN);

            case "ArrowRight" -> robot.keyRelease(KeyEvent.VK_RIGHT);

            case "ArrowLeft" -> robot.keyRelease(KeyEvent.VK_LEFT);

            case "VolumeUp" -> MediaButtonsControl.keyRelease(MediaButtonsControl.User32.VK_VOLUME_UP);

            case "VolumeDown" -> MediaButtonsControl.keyRelease(MediaButtonsControl.User32.VK_VOLUME_DOWN);

            case "PlayPause" -> MediaButtonsControl.keyRelease(MediaButtonsControl.User32.VK_MEDIA_PLAY_PAUSE);

            case "MediaNext" -> MediaButtonsControl.keyRelease(MediaButtonsControl.User32.VK_NEXT_TRACK);

            case "MediaPrev" -> MediaButtonsControl.keyRelease(MediaButtonsControl.User32.VK_PREVIOUS_TRACK);

            case "ButtonV" -> robot.keyRelease(KeyEvent.VK_V);

            case "ButtonB" -> robot.keyRelease(KeyEvent.VK_B);

            case "ButtonOpenBracket" -> robot.keyRelease(KeyEvent.VK_OPEN_BRACKET);

            case "ButtonCloseBracket" -> robot.keyRelease(KeyEvent.VK_CLOSE_BRACKET);

            case "ButtonLMB" -> robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

            case "ButtonRMB" -> robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);

            default -> System.out.println("Don't know this button: " + key);
        }

    }

    public void mouseMove(int x, int y) {
        robot.mouseMove(x, y);
    }
}
