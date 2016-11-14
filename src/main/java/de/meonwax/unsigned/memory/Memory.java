package de.meonwax.unsigned.memory;

import java.util.ArrayList;
import java.util.List;

import de.meonwax.unsigned.util.Utils;

public class Memory {

    private byte[] ram;

    private List<Overlay> overlays = new ArrayList<>();

    public Memory(int size) {
        ram = new byte[size];
    }

    public void reset() {
        // Initialize with zeros
        for (int address = 0; address < ram.length; address++) {
            ram[address] = 0;
        }
    }

    /**
     * Read a single byte from memory address
     */
    public byte read(int address) {
        return ram[address];
        // TODO: respect overlays
    }

    /**
     * Write a single byte to memory address
     */
    public void write(int address, byte byteValue) {
        System.out.println("Memory write. Address: " + Utils.integerToHex(address) + " Value: " + Utils.byteToHex(byteValue));
        ram[address] = byteValue;
        // TODO: respect overlays
    }

    /**
     * Write an array of bytes to memory starting at specific address
     */
    public void write(int startAddress, byte[] data) {
        for (int i = 0; i < data.length; i++) {
            ram[startAddress + i] = data[i];
        }
        // TODO: respect overlays
    }

    public void addOverlay(Overlay overlay, int startAddress) {
        // TODO:
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int address = 0; address < ram.length; address++) {
            if (address % 8 == 0) {
                sb.append("\n");
                sb.append(Utils.integerToHex(address)).append(":");
            }
            sb.append(" ").append(Utils.byteToHex(ram[address]));
        }
        return sb.toString();
    }
}