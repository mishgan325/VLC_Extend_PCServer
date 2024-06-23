package ru.mishgan325.vlc_extend_pcserver;

public class LowPassFilter {
    private final float alpha;
    private final int[] lastValues;

    public LowPassFilter(int size, float alpha) {
        this.alpha = alpha;
        this.lastValues = new int[size];
    }

    public int[] filter(int[] values) {
        for (int i = 0; i < values.length; i++) {
            lastValues[i] = lastValues[i] + (int)(alpha * (values[i] - lastValues[i]));
        }
        return lastValues;
    }
}