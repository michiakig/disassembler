package com.spacemanaki.disassembler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;

import com.spacemanaki.disassembler.Method.AccessFlag;
import static com.spacemanaki.disassembler.Method.AccessFlag.*;

import static com.spacemanaki.disassembler.Utility.bytes;
import static com.spacemanaki.disassembler.Utility.stream;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class MethodAccessFlagTest {

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][]{
        {bytes(0x00,0x00), EnumSet.noneOf(AccessFlag.class)},
        {bytes(0x00,0x01), EnumSet.of(PUBLIC)},
        {bytes(0x00,0x09), EnumSet.of(PUBLIC,STATIC)},
        {bytes(0x00,0x29), EnumSet.of(PUBLIC,STATIC,SYNCHRONIZED)},
    });
  }

  private byte[] input;
  private EnumSet<AccessFlag> expected;

  public MethodAccessFlagTest(byte[] input, EnumSet<AccessFlag> expected) {
    this.input = input;
    this.expected = expected;
  }

  @Test
  public void test() throws IOException {
    assertEquals(expected, Method.readAccessFlags(stream(input)));
  }
}
