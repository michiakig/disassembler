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
        {bytes(0x00,0x00),EnumSet.noneOf(ClassFile.AccessFlag.class)},
        {bytes(0x00,0x01),EnumSet.of(ClassFile.AccessFlag.PUBLIC)},
        {bytes(0x00,0x11),EnumSet.of(ClassFile.AccessFlag.PUBLIC, ClassFile.AccessFlag.FINAL)},
        {bytes(0x40,0x00),EnumSet.of(ClassFile.AccessFlag.ENUM)},
        {bytes(0x02,0x00),EnumSet.of(ClassFile.AccessFlag.INTERFACE)},
        {bytes(0x04,0x11),EnumSet.of(ClassFile.AccessFlag.PUBLIC, ClassFile.AccessFlag.FINAL, ClassFile.AccessFlag.ABSTRACT)}
    });
  }

  private byte[] input;
  private EnumSet<ClassFile.AccessFlag> expected;

  public AccessFlagsTest(byte[] input, EnumSet<ClassFile.AccessFlag> expected) {
    this.input = input;
    this.expected = expected;
  }

  @Test
  public void test() throws Exception {
    assertEquals(expected, ClassFile.readAccessFlags(stream(input)));
  }
}
