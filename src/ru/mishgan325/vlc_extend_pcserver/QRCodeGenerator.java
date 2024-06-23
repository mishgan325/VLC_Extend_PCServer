package ru.mishgan325.vlc_extend_pcserver;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.client.j2se.MatrixToImageWriter;


public class QRCodeGenerator {

    private static JFrame frame = new JFrame("QR Code Generator");

    public static void main(String[] args) {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JTextField inputField = new JTextField();
        JButton generateButton = new JButton("Generate QR Code");
        JLabel qrCodeLabel = new JLabel();

        generateButton.addActionListener(e -> {
            String textToEncode = inputField.getText();
            int width = 300;
            int height = 300;

            Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

            try {
                BitMatrix bitMatrix = new MultiFormatWriter().encode(textToEncode, BarcodeFormat.QR_CODE, width, height, hintMap);
                BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

                qrCodeLabel.setIcon(new ImageIcon(qrCodeImage));
                frame.pack();
            } catch (WriterException ex) {
                ex.printStackTrace();
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.add(inputField);
        inputPanel.add(generateButton);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(qrCodeLabel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    public void generateQRCode(String IP) {
        JFrame frame = new JFrame("QR Code Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel qrCodeLabel = new JLabel();

        int width = 300;
        int height = 300;

        Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(IP, BarcodeFormat.QR_CODE, width, height, hintMap);
            BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            qrCodeLabel.setIcon(new ImageIcon(qrCodeImage));
            frame.pack();
        } catch (WriterException ex) {
            ex.printStackTrace();
        }


        JPanel inputPanel = new JPanel();

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(qrCodeLabel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    public void closeQRCodeWindow()
    {
        frame.setVisible(false);
    }
}