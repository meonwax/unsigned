package de.meonwax.unsigned.memory;

public abstract class Overlay {

    protected byte[] content;

    public Overlay(int size) {
        content = new byte[size];
        reset();
    }

    public void reset() {
        // Initialize with zeros
        for (int i = 0; i < content.length; i++) {
            content[i] = 0;
        }
    }

    public byte getByte(int i) {
        return content[i];
    }
}
