package de.meonwax.unsigned.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import de.meonwax.unsigned.memory.Video;

public class Display extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;

    private final static int WIDTH = 32;
    private final static int HEIGHT = 32;
    private final static int SCALE = 20;
    private final static int FPS = 30; // Refresh display with 30 Hz

    private long lastDraw = System.currentTimeMillis();
    private int frameCount = 0;
    private float fps = 0f;

    private Video video = new Video(WIDTH * HEIGHT);
    private BufferedImage frame = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) frame.getRaster().getDataBuffer()).getData();

    public Display() {
        setBackground(Color.BLACK);
        setSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
    }

    public Video getVideo() {
        return video;
    }

    public void update() {
        updateFps();
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = i + frameCount;
            // pixels[i] = getColor(video.get(i));
        }
        frameCount++;
        lastDraw = System.currentTimeMillis();
    }

    public void draw(Graphics2D g) {

        // Render frame
        g.drawImage(frame, 0, 0, getWidth(), getHeight(), null);

        // Draw FPS indicator
        g.setColor(Color.white);
        g.drawString("FPS: " + Math.round(fps), 10, 20);

    }

    private void updateFps() {
        if (frameCount % 30 == 0) {
            fps = 1000f / (System.currentTimeMillis() - lastDraw);
        }
    }

    /**
     * The values $00 to $0f represent 16 different colors ($00 is black and $01 is white).
     * Storing the value $01 at startAddress draws a white pixel at the top left corner.
     * $0: Black
     * $1: White
     * $2: Red
     * $3: Cyan
     * $4: Purple
     * $5: Green
     * $6: Blue
     * $7: Yellow
     * $8: Orange
     * $9: Brown
     * $a: Light red
     * $b: Dark grey
     * $c: Grey
     * $d: Light green
     * $e: Light blue
     * $f: Light grey
     */
    private int getColor(byte value) {
        switch (value & 0x0f) {
            case 0x0:
                return Color.black.getRGB();
            case 0x1:
                return Color.white.getRGB();
            case 0x2:
                return Color.red.getRGB();
            case 0x3:
                return Color.cyan.getRGB();
            case 0x4:
                return Color.pink.darker().getRGB();
            case 0x5:
                return Color.green.getRGB();
            case 0x6:
                return Color.blue.getRGB();
            case 0x7:
                return Color.yellow.getRGB();
            case 0x8:
                return Color.orange.getRGB();
            case 0x9:
                return Color.orange.darker().getRGB();
            case 0xa:
                return Color.red.brighter().getRGB();
            case 0xb:
                return Color.darkGray.getRGB();
            case 0xc:
                return Color.gray.getRGB();
            case 0xd:
                return Color.green.brighter().getRGB();
            case 0xe:
                return Color.blue.brighter().getRGB();
            case 0xf:
                return Color.lightGray.getRGB();
            default:
                return Color.black.getRGB();
        }
    }

    @Override
    public void run() {
        while (true) {

            update();

            BufferStrategy bs = getBufferStrategy();
            if (bs == null) {
                createBufferStrategy(2);
                continue;
            }

            Graphics2D g = (Graphics2D) bs.getDrawGraphics();
            draw(g);
            g.dispose();
            bs.show();

            try {
                Thread.sleep(1000 / FPS);
            } catch (InterruptedException e) {
            }
        }
    }
}
