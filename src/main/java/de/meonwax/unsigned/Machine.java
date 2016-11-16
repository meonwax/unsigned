package de.meonwax.unsigned;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import de.meonwax.unsigned.gui.Display;
import de.meonwax.unsigned.memory.Memory;
import de.meonwax.unsigned.util.Logger;

public class Machine {

    private Cpu cpu;

    private Memory memory;

    private int cycles = 0;

    public Machine(Display display) {
        memory = new Memory(64 * 1024);
        cpu = new Cpu(memory);

        // Add video adapter at address $0200 consisting of 1024 bytes (32x32 pixels)
        memory.addOverlay(display.getVideo(), 0x200);

        // Initialize
        reset();

        Logger.info("Machine created.");
    }

    public boolean next() {
        int cycles = cpu.next();
        if (cycles >= 0) {
            this.cycles += cycles;
        } else {
            Logger.error("CPU error");
            return false;
        }
//        Logger.info("Cycles: " + this.cycles);
        return true;
    }

    public void reset() {
        cpu.reset();
        memory.reset();
    }

    public boolean loadFile(String filename, int address) {
        return loadFile(new File(filename), address);
    }

    public boolean loadFile(File file, int address) {
        try (InputStream is = new FileInputStream(file)) {
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
            Logger.error("Loading error: " + e.getMessage());
            return false;
        }
    }

    public void setStart(int address) {
        cpu.setStart(address);
    }
}
