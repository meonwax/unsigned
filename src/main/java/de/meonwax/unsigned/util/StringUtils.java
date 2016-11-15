package de.meonwax.unsigned.util;

public class StringUtils {

    final protected static char[] HEX_CHARS = "0123456789abcdef".toCharArray();

    public static String byteToHex(byte b) {
        char[] hexChars = new char[2];
        int v = b & 0xff;
        hexChars[0] = HEX_CHARS[v >>> 4];
        hexChars[1] = HEX_CHARS[v & 0x0f];
        return new String(hexChars);
    }

    public static String integerToHex(int i) {
        String unpadded = Integer.toHexString(i);
        return "0000".substring(unpadded.length()) + unpadded;
    }
}
