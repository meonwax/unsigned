package de.meonwax.unsigned;

import static org.junit.Assert.*;

import org.junit.Test;

import de.meonwax.unsigned.memory.Memory;

public class CpuTest {

    @Test
    public void unknownOpcode() {
        Memory memory = new Memory(64 * 1024);
        Cpu cpu = new Cpu(memory);

        int cycleCount = cpu.next();

        assertEquals(-1, cycleCount);
    }
}
