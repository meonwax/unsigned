package de.meonwax.unsigned.memory;

/**
 * An overlay can be added to the main memory to cover a given address range.
 * This emulates memory mapped registers of external controllers
 */
public abstract class Overlay {

    protected byte[] content;

    public Overlay(int size) {
        content = new byte[size];
        reset();
    }

    public int getLength() {
        return content.length;
    }

    public void write(int address, byte byteValue) {
        content[address] = byteValue;
    }

    public byte read(int address) {
        return content[address];
    }

    public void reset() {
        // Initialize with zeros
        for (int i = 0; i < content.length; i++) {
            content[i] = 0;
        }
    }
}
