package com.spacemanaki.disassembler;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static com.spacemanaki.disassembler.Disassembler.readMagic;
import static com.spacemanaki.disassembler.Disassembler.readVersion;
import static com.spacemanaki.disassembler.Utility.bytes;
import static org.junit.Assert.*;

public class DisassemblerTest {
  @Test public void testMagicTooShort() {
    assertFalse(readMagic(new ByteArrayInputStream(bytes(0xca, 0xfe, 0xba))));
  }

  @Test public void testMagicOk() {
    assertTrue(readMagic(new ByteArrayInputStream(bytes(0xca, 0xfe, 0xba, 0xbe))));
  }

  @Test public void testMagicLong() {
    ByteArrayInputStream in = new ByteArrayInputStream(bytes(0xca, 0xfe, 0xba, 0xbe, /* extra bytes */ 0xff));
    assertTrue(readMagic(in));
    assertEquals(1, in.available());
    assertEquals(0xff, in.read());
  }

  @Test public void testVersion() {
    InputStream in = new ByteArrayInputStream(bytes(0x00,0x00,0x00,0x32));
    assertEquals(0, readVersion(in));
    assertEquals(50, readVersion(in));
  }
}
