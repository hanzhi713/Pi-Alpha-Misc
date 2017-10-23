package tests;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class ImShow {
    private JFrame frame;
    private final JLabel imageLabel = new JLabel();

    public ImShow(int w, int h) {
        this.frame = new JFrame();
        this.frame.setBounds(0, 0, w, h);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.getContentPane().setLayout(null);
        this.imageLabel.setBounds(0, 0, w, h);
        this.frame.getContentPane().add(imageLabel);
        frame.setVisible(true);
    }

    public void show(BufferedImage buffImg) {
        imageLabel.setIcon(new ImageIcon(buffImg));
    }
}
