package de.meonwax.unsigned;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import de.meonwax.unsigned.gui.Display;
import de.meonwax.unsigned.util.Logger;

public class Main {

    private final static String FILENAME = "demo.bin";

    private final static int START_ADDRESS = 0xc000;

    public static void main(String[] args) {

        Logger.info("*** Unsigned 6502 emulator ***");

        Display display = new Display();
        Machine machine = new Machine(display);

        // Load file from resources folder
        File file = new File(Main.class.getClassLoader().getResource(FILENAME).getFile());
        if (!machine.loadFile(file, START_ADDRESS)) {
            Logger.error("Could not load file '" + FILENAME + "'");
            return;
        }
        Logger.info("'" + FILENAME + "' loaded to address " + START_ADDRESS);

        Logger.info("Running...");
        machine.setStart(START_ADDRESS);

        // Display loop
        System.setProperty("sun.java2d.opengl", "true");

        EventQueue.invokeLater(() -> {
            createDisplay(display);
        });

        // Main machine loop
        while (machine.next()) {
        }

        Logger.info("Machine halted.");
    }

    private static void createDisplay(Display display) {
        Frame window = new Frame();
        window.setTitle("Display");
        window.add(display);
        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                window.dispose();
                System.exit(0);
            }
        });
        new Thread(display).start();
    }
}
