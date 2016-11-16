package de.meonwax.unsigned;

import de.meonwax.unsigned.memory.Memory;

public class Cpu {

    /****************
     * Registers
     ****************/

    // Accumulator
    private byte ac;

    // X register
    private byte xr;

    // Y register
    private byte yr;

    // Stack pointer
    private byte sp;

    // Program counter
    private int pc;

    // Processor flags
    private byte flags;

    /****************/

    // 64k of memory address space
    private Memory memory;

    public Cpu(Memory memory) {
        this.memory = memory;
    }

    public void reset() {
        pc = ac = xr = yr = 0;
        sp = (byte) 0xff;
        flags = 0x20;
    }

    public int next() {

        int cycleCount;

        // Fetch
        byte opcode = memory.read(pc);
        switch (opcode & 0xff) {

            // LDA immediate
            case 0xa9:
                ac = memory.read(++pc);
                cycleCount = 2;
//                Logger.debug("LDA #$" + StringUtils.byteToHex(ac));
                break;

            // STA absolute
            case 0x8d:
                int addressHigh = memory.read(++pc);
                int addressLow = memory.read(++pc);
                int address = addressLow << 8 | addressHigh & 0xff;
                memory.write(address, ac);
                cycleCount = 4;
//                Logger.debug("STA $" + StringUtils.integerToHex(address));
                break;

            default:
//                Logger.warn("Unknown opcode: $" + StringUtils.byteToHex(opcode));
                return -1;
        }
        pc++;
        return cycleCount;
    }

    public void setStart(int address) {
        pc = address;
    }
}
