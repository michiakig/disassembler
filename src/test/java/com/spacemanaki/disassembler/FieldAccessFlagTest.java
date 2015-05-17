package com.spacemanaki.disassembler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;

import static com.spacemanaki.disassembler.Utility.bytes;
import static com.spacemanaki.disassembler.Utility.stream;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class FieldAccessFlagTest {
  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][]{
        {bytes(0x00,0x00),EnumSet.noneOf(Field.AccessFlag.class)},
        {bytes(0x00,0x01),EnumSet.of(Field.AccessFlag.PUBLIC)},
        {bytes(0x00,0x02),EnumSet.of(Field.AccessFlag.PRIVATE)},
        {bytes(0x00,0x19),EnumSet.of(Field.AccessFlag.PUBLIC,Field.AccessFlag.STATIC,Field.AccessFlag.FINAL)},
    });
  }

  private byte[] input;
  private EnumSet<AccessFlags.Flag> expected;

  public FieldAccessFlagTest(byte[] input, EnumSet<AccessFlags.Flag> expected) {
    this.input = input;
    this.expected = expected;
  }

  @Test
  public void test() throws IOException {
    assertEquals(expected, Field.readAccessFlag(stream(input)));
  }
}
