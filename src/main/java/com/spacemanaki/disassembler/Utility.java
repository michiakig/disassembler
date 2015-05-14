package com.spacemanaki.disassembler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class Utility {
  public static byte[] bytes(int... ints) {
    byte[] bytes = new byte[ints.length];
    for (int i = 0; i < ints.length; i++) {
      bytes[i] = (byte) ints[i];
    }
    return bytes;
  }
  public static DataInputStream stream(byte[] bytes) {
    return new DataInputStream(new ByteArrayInputStream(bytes));
  }
}
