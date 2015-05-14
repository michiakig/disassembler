package com.spacemanaki.disassembler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.HashSet;
import java.util.Set;

public class Utility {
  public static byte[] bytes(int... ints) {
    byte[] bytes = new byte[ints.length];
    for (int i = 0; i < ints.length; i++) {
      bytes[i] = (byte) ints[i];
    }
    return bytes;
  }
  public static short[] shorts(int... ints) {
    short[] shorts = new short[ints.length];
    for (int i = 0; i < ints.length; i++) {
      shorts[i] = (short) ints[i];
    }
    return shorts;
  }
  public static DataInputStream stream(byte[] bytes) {
    return new DataInputStream(new ByteArrayInputStream(bytes));
  }

  public static <T extends HasMask> Set<T> readEnumSet(T[] values, short value) {
    Set<T> flags = new HashSet<>();
    for (T flag : values) {
      if ((flag.getMask() & value) == flag.getMask()) {
          flags.add(flag);
      }
    }
    return flags;
  }
}
