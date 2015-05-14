package com.spacemanaki.disassembler;

import org.junit.Test;

import static org.junit.Assert.*;
import static com.spacemanaki.disassembler.Utility.*;

public class UtilityTest {
  @Test public void testBytes() {
    assertArrayEquals(new byte[]{}, bytes());
    assertArrayEquals(new byte[]{0x01}, bytes(0x01));
    assertArrayEquals(new byte[]{0x01,0x02}, bytes(0x01, 0x02));

    assertArrayEquals(new byte[]{-1}, bytes(0xff));
    assertArrayEquals(new byte[]{-54}, bytes(0xca));
    assertArrayEquals(new byte[]{-2}, bytes(0xfe));
    assertArrayEquals(new byte[]{-54,-2,-70,-66}, bytes(0xca,0xfe,0xba,0xbe));
  }

  @Test public void testShorts() {
    assertArrayEquals(new short[]{}, shorts());
    assertArrayEquals(new short[]{1,2,3}, shorts(1,2,3));
    assertArrayEquals(new short[]{-1}, shorts(65535));
  }
}
