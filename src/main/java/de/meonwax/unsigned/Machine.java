package de.meonwax.unsigned;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.Timer;

import de.meonwax.unsigned.gui.Display;
import de.meonwax.unsigned.memory.Memory;
import de.meonwax.unsigned.memory.Video;

public class Machine {

    private CPU cpu;

    private Memory memory;

    private Video video;

    public Machine(Display display) {
        memory = new Memory(64 * 1024);
        cpu = new CPU(memory);

        // Add video adapter at address $0200 consisting of 1024 bytes (32x32
        // pixels)
        video = new Video(1024, display);
        memory.addOverlay(video, 0x200);

        // Refresh video with 30 Hz
        new Timer(1000 / 30, video).start();

        // Initialize
        reset();
    }

    public void reset() {
        cpu.reset();
        memory.reset();
        video.reset();
    }

    public boolean loadFile(String filename, int address) {
        try (InputStream is = new FileInputStream(new File(filename))) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[16384];
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            memory.write(address, buffer.toByteArray());
            return true;
        } catch (IOException e) {
            System.err.println("Loading error: " + e.getMessage());
            return false;
        }
    }
    
    public void setStart(int address) {
        cpu.setStart(address);
    }

    public void run() {

        System.out.println("Running...");

        // Run program on CPU
        while (cpu.step() >= 0) {
        }

        System.out.println("Stopped.");
    }
    
    public void step() {
        cpu.step();
    }
}
