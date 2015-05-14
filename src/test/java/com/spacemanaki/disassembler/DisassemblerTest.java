package com.spacemanaki.disassembler;

import org.junit.Test;

import java.io.DataInputStream;
import java.io.IOException;

import static com.spacemanaki.disassembler.Disassembler.*;
import static com.spacemanaki.disassembler.Utility.*;
import static org.junit.Assert.*;

public class DisassemblerTest {
  @Test public void testMagicTooShort() {
    assertFalse(readMagic(stream(bytes(0xca, 0xfe, 0xba))));
  }

  @Test public void testMagicOk() {
    assertTrue(readMagic(stream(bytes(0xca, 0xfe, 0xba, 0xbe))));
  }

  @Test public void testMagicLong() throws IOException {
    DataInputStream in = stream(bytes(0xca, 0xfe, 0xba, 0xbe, /* extra bytes */ 0xff));
    assertTrue(readMagic(in));
    assertEquals(1, in.available());
    assertEquals(0xff, in.read());
  }

  @Test public void testVersion() {
    DataInputStream in = stream(bytes(0x00,0x00,0x00,0x32));
    assertEquals(0, readVersion(in));
    assertEquals(50, readVersion(in));
  }
}
