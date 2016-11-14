package de.meonwax.unsigned.memory;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import de.meonwax.unsigned.gui.Display;

/**
 * The values $00 to $0f represent 16 different colors ($00 is black and $01 is
 * white). Storing the value $01 at startAddress draws a white pixel at the top
 * left corner.
 *
 * $0: Black $1: White $2: Red $3: Cyan $4: Purple $5: Green $6: Blue $7: Yellow
 * $8: Orange $9: Brown $a: Light red $b: Dark grey $c: Grey $d: Light green $e:
 * Light blue $f: Light grey
 */
public class Video extends Overlay implements ActionListener {

    private final static int WIDTH = 32;
    private final static int HEIGHT = 32;

    private Display display;

    public Video(int size, Display display) {
        super(size);
        this.display = display;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BufferedImage frame = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                frame.setRGB(x, y, getColor(content[x + y]));
            }
        }
        display.refresh(frame);
    }

    private int getColor(byte value) {
        // Random r = new Random();
        // switch (r.nextInt() & 0x0f) {

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
}
