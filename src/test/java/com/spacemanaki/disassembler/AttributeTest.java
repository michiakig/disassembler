package com.spacemanaki.disassembler;

import org.junit.Test;

import java.io.IOException;

import static com.spacemanaki.disassembler.Utility.bytes;
import static com.spacemanaki.disassembler.Utility.stream;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class AttributeTest {
  @Test
  public void read() throws IOException {
    Attribute a = Attribute.read(stream(bytes(
        0x00,0x03,
        0x00,0x00,0x00,0x04,
        0x01,0x03,0x03,0x07
    )));

    assertEquals(3, a.nameIndex);
    assertArrayEquals(bytes(0x01, 0x03, 0x03, 0x07), a.info);
  }
}
