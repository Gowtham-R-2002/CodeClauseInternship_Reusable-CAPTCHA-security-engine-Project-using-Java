package com.gowthamr.reusablecaptchasecurityengine;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class ReusableCaptchaSecurityEngine extends JFrame {
    private static final int CAPTCHA_WIDTH = 200;
    private static final int CAPTCHA_HEIGHT = 80;

    private BufferedImage captchaImage;
    private String captchaText;

    public ReusableCaptchaSecurityEngine() {
        setTitle("CAPTCHA Generator");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        generateCaptcha();

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel captchaLabel = new JLabel(new ImageIcon(captchaImage));
        panel.add(captchaLabel, BorderLayout.CENTER);

        JTextField userInputField = new JTextField(10);
        panel.add(userInputField, BorderLayout.SOUTH);

        JButton validateButton = new JButton("Validate");
        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = userInputField.getText();
                if (userInput.equals(captchaText)) {
                    JOptionPane.showMessageDialog(null, "CAPTCHA validation successful!");
                    generateCaptcha();
                    captchaLabel.setIcon(new ImageIcon(captchaImage));
                    userInputField.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "CAPTCHA validation failed. Try again.");
                }
            }
        });
        panel.add(validateButton, BorderLayout.EAST);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void generateCaptcha() {
        captchaText = generateRandomText();
        System.out.println(captchaText);
        captchaImage = new BufferedImage(CAPTCHA_WIDTH, CAPTCHA_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = captchaImage.createGraphics();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, CAPTCHA_WIDTH, CAPTCHA_HEIGHT);

    
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        g2d.drawString(captchaText, 40, 50);

        Random random = new Random();
        for (int i = 0; i < 150; i++) {
            int x = random.nextInt(CAPTCHA_WIDTH);
            int y = random.nextInt(CAPTCHA_HEIGHT);
            g2d.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256),random.nextInt(256)));
            g2d.drawLine(x, y, x + random.nextInt(100), y + random.nextInt(100));
        }

        g2d.dispose();
    }

    private String generateRandomText() {
        
        String caps="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lows="abcdefghijklmnopqrstuvwxyz";
        String nums="1234567890";

        String all=caps + lows + nums;
        Random random=new Random();

        String ans="";
        for(int i=0;i<5;i++){
            ans+=all.charAt(random.nextInt(all.length()));
        }
        return ans;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReusableCaptchaSecurityEngine());
    }
}
