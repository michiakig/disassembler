package com.spacemanaki.disassembler;

import org.junit.Test;

import java.io.IOException;
import java.util.EnumSet;

import static com.spacemanaki.disassembler.Utility.bytes;
import static com.spacemanaki.disassembler.Utility.stream;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class FieldTest {
  @Test
  public void test() throws IOException {
    Field f = Field.read(stream(bytes(
        0x00,0x01,
        0x00,0x03,
        0x00,0x05,
        0x00,0x02, /* two attributes */
        0x00,0x07, /* first attribute */
        0x00,0x00,0x00,0x02,
        0x01,0x03,
        0x00,0x09, /* second attribute */
        0x00,0x00,0x00,0x01,
        0x09
    )));

    assertEquals(EnumSet.of(Field.AccessFlag.PUBLIC), f.accessFlags);
    assertEquals(3, f.nameIndex);
    assertEquals(5, f.descriptorIndex);

    assertArrayEquals(new Attribute[]{
          new Attribute((short)7, bytes(0x01,0x03))
        , new Attribute((short)9, bytes(0x09))
    }, f.attributes);
  }
}
