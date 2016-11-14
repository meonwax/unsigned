package de.meonwax.unsigned.gui;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Display extends JLabel {

    private static final long serialVersionUID = 1L;

    private final static int SCALE = 8;

    public void refresh(BufferedImage frame) {
        setIcon(new ImageIcon(frame.getScaledInstance(frame.getWidth() * SCALE, frame.getHeight() * SCALE, Image.SCALE_REPLICATE)));
    }
}
