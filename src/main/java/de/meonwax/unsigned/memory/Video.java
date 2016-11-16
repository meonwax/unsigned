package de.meonwax.unsigned.memory;

import de.meonwax.unsigned.util.Logger;
import de.meonwax.unsigned.util.StringUtils;

public class Video extends Overlay {

    public Video(int size) {
        super(size);
    }

    @Override
    public void write(int address, byte byteValue) {
        Logger.debug("Video write at address: " + StringUtils.integerToHex(address) + " value: " + StringUtils.byteToHex(byteValue));
        super.write(address, byteValue);
    }
}
