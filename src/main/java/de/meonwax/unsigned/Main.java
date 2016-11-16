package de.meonwax.unsigned;

import java.awt.EventQueue;
import java.io.File;

import de.meonwax.unsigned.gui.Display;
import de.meonwax.unsigned.util.Logger;
import de.meonwax.unsigned.util.StringUtils;

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
        Logger.info("'" + FILENAME + "' loaded to address " + StringUtils.integerToHex(START_ADDRESS));

        Logger.info("Running...");
        machine.setStart(START_ADDRESS);

        // Display loop
        System.setProperty("sun.java2d.opengl", "true");
        EventQueue.invokeLater(() -> {
            display.start();
        });

        // Main machine loop
        while (machine.next()) {
        }

        Logger.info("Machine halted.");
    }
}
