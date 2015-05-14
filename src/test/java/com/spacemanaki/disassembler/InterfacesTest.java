package com.spacemanaki.disassembler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static com.spacemanaki.disassembler.Utility.*;
import static org.junit.Assert.assertArrayEquals;

@RunWith(Parameterized.class)
public class InterfacesTest {
  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][]{
        {bytes(0x00,0x01,0x00,0xff), shorts(255)},
        {bytes(0x00,0x02,0x00,0xff,0xff,0x00), shorts(255,65280)},
        {bytes(0x00,0x03,0x00,0xff,0xff,0x00,0xff,0xff), shorts(255,65280,65535)}
    });
  }

  private DataInputStream input;
  private short[] expected;

  public InterfacesTest(byte[] input, short[] expected) {
    this.input = stream(input);
    this.expected = expected;
  }

  @Test public void test() throws IOException {
    assertArrayEquals(expected, Interfaces.read(input));
  }
}
