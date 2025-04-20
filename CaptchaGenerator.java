package com.captcha;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class CaptchaGenerator {

    public static String generateCaptchaText() {
        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
        StringBuilder captchaStr = new StringBuilder();
        Random rand = new Random();

        for (int i = 0; i < 6; i++) {
            captchaStr.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return captchaStr.toString();
    }

    public static BufferedImage generateCaptchaImage(String captchaText) {
        int width = 320;  // Increased width for 6 chars
        int height = 60;  // Slightly taller for padding
    
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();
    
        // Anti-aliasing for smoother text
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
        // Background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
    
        // Font
        Font font = new Font("Arial", Font.BOLD, 40);
        g.setFont(font);
        g.setColor(Color.BLACK);
    
        // Calculate the spacing
        FontMetrics fontMetrics = g.getFontMetrics();
        int charWidth = fontMetrics.charWidth('W');
        int startX = 20;
        int startY = (height - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent();
    
        // Draw characters
        for (int i = 0; i < captchaText.length(); i++) {
            g.drawString(String.valueOf(captchaText.charAt(i)), startX + i * charWidth, startY);
        }
    
        // === Add distortion lines (like "Cut Captcha") ===
        Random rand = new Random();
        g.setStroke(new BasicStroke(2));
        for (int i = 0; i < 4; i++) {  // Draw 4 random lines
            int x1 = rand.nextInt(width);
            int y1 = rand.nextInt(height);
            int x2 = rand.nextInt(width);
            int y2 = rand.nextInt(height);
            g.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
            g.drawLine(x1, y1, x2, y2);
        }
    
        g.dispose();
        return bufferedImage;
    }
    
}
