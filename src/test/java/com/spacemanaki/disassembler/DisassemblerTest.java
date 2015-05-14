package com.spacemanaki.disassembler;

import org.junit.Test;

import java.io.ByteArrayInputStream;

import static com.spacemanaki.disassembler.Utility.bytes;
import static org.junit.Assert.*;

public class DisassemblerTest {
  @Test public void testMagicTooShort() {
    assertFalse(Disassembler.readMagic(new ByteArrayInputStream(bytes(0xca,0xfe,0xba))));
  }

  @Test public void testMagicOk() {
    assertTrue(Disassembler.readMagic(new ByteArrayInputStream(bytes(0xca,0xfe,0xba,0xbe))));
  }

  @Test public void testMagicLong() {
    ByteArrayInputStream in = new ByteArrayInputStream(bytes(0xca, 0xfe, 0xba, 0xbe, /* extra bytes */ 0xff));
    assertTrue(Disassembler.readMagic(in));
    assertEquals(1, in.available());
    assertEquals(0xff, in.read());
  }
}
