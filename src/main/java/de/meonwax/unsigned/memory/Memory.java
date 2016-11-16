package de.meonwax.unsigned.memory;

import java.util.ArrayList;
import java.util.List;

import de.meonwax.unsigned.util.Logger;
import de.meonwax.unsigned.util.StringUtils;

public class Memory {

    // Plain memory content
    private byte[] ram;

    private List<OverlayMapping> overlayMappings = new ArrayList<>();

    public Memory(int size) {
        ram = new byte[size];
    }

    public void reset() {
        // Initialize with zeros
        for (int address = 0; address < ram.length; address++) {
            ram[address] = 0;
        }
        for (OverlayMapping mapping : overlayMappings) {
            mapping.overlay.reset();
        }
    }

    /**
     * Read a single byte from memory address
     */
    public byte read(int address) {
        OverlayMapping mapping = getOverlayMapping(address);
        if (mapping != null) {
            return mapping.overlay.read(address - mapping.startAddress);
        }
        return ram[address];
    }

    /**
     * Write an array of bytes to memory starting at specific address
     */
    public void write(int startAddress, byte[] data) {
        for (int i = 0; i < data.length; i++) {
            write(startAddress + i, data[i]);
        }
    }

    /**
     * Write a single byte to memory address
     */
    public void write(int address, byte byteValue) {
        OverlayMapping mapping = getOverlayMapping(address);
        if (mapping != null) {
            mapping.overlay.write(address - mapping.startAddress, byteValue);
        } else {
            ram[address] = byteValue;
        }
    }

    /**
     * Return an overlay for a specific memory address
     */
    private OverlayMapping getOverlayMapping(int address) {
        for (OverlayMapping mapping : overlayMappings) {
            if (address >= mapping.startAddress && address <= mapping.endAddress) {
                return mapping;
            }
        }
        return null;
    }

    /**
     * Add a new memory overlay, e.g. a video interface
     */
    public void addOverlay(Overlay overlay, int startAddress) {
        int endAddress = startAddress + overlay.getLength() - 1;
        Logger.info("Registering memory overlay from " + StringUtils.integerToHex(startAddress) + " to " + StringUtils.integerToHex(endAddress));
        OverlayMapping mapping = new OverlayMapping();
        mapping.overlay = overlay;
        mapping.startAddress = startAddress;
        mapping.endAddress = endAddress;
        overlayMappings.add(mapping);
    }

    /**
     * Dump the memory content with overlays
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int address = 0; address < ram.length; address++) {
            if (address % 8 == 0) {
                sb.append("\n");
                sb.append(StringUtils.integerToHex(address)).append(":");
            }
            sb.append(" ").append(StringUtils.byteToHex(read(address)));
        }
        return sb.toString();
    }

    private class OverlayMapping {
        int startAddress;
        int endAddress;
        Overlay overlay;
    }
}