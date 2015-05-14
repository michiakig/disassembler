package com.spacemanaki.disassembler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;

import static com.spacemanaki.disassembler.Utility.*;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class AccessFlagsTest {
  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][]{
        {bytes(0x00,0x00),EnumSet.noneOf(AccessFlags.Flag.class)},
        {bytes(0x00,0x01),EnumSet.of(AccessFlags.Flag.PUBLIC)},
        {bytes(0x00,0x11),EnumSet.of(AccessFlags.Flag.PUBLIC,AccessFlags.Flag.FINAL)},
        {bytes(0x40,0x00),EnumSet.of(AccessFlags.Flag.ENUM)},
        {bytes(0x02,0x00),EnumSet.of(AccessFlags.Flag.INTERFACE)},
        {bytes(0x04,0x11),EnumSet.of(AccessFlags.Flag.PUBLIC,AccessFlags.Flag.FINAL,AccessFlags.Flag.ABSTRACT)}
    });
  }

  private byte[] input;
  private EnumSet<AccessFlags.Flag> expected;

  public AccessFlagsTest(byte[] input, EnumSet<AccessFlags.Flag> expected) {
    this.input = input;
    this.expected = expected;
  }

  @Test
  public void test() throws Exception {
    assertEquals(expected, AccessFlags.readAccessFlags(stream(input)));
  }
}
