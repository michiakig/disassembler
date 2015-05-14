package com.spacemanaki.disassembler;

public class Utility {
  public static byte[] bytes(int... ints) {
    byte[] bytes = new byte[ints.length];
    for (int i = 0; i < ints.length; i++) {
      bytes[i] = (byte) ints[i];
    }
    return bytes;
  }
}
