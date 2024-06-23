package ru.mishgan325.vlc_extend_pcserver;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class MediaButtonsControl {

    public interface User32 extends Library {
        User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);

        int VK_MEDIA_PLAY_PAUSE = 0xB3; // Код медиаклавиши Play/Pause
        int VK_VOLUME_UP = 0xAF; // Код медиаклавиши Volume Up
        int VK_VOLUME_DOWN = 0xAE;
        int VK_VOLUME_MUTE = 0xAD;
        int VK_PREVIOUS_TRACK = 0xB1;
        int VK_NEXT_TRACK = 0xB0;

        boolean setCursorPos(int x, int y);
        void keybd_event(byte bVk, byte bScan, int dwFlags, int dwExtraInfo);
    }

    public static void keyPress(int keyCode) {
        User32.INSTANCE.keybd_event((byte) keyCode, (byte) 0, 0, 0);
    }

    public static void keyRelease(int keyCode) {
        User32.INSTANCE.keybd_event((byte) keyCode, (byte) 0, 2, 0);
    }
}