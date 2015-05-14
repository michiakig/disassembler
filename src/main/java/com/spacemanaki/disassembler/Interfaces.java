package com.spacemanaki.disassembler;

import java.io.DataInputStream;
import java.io.IOException;

public class Interfaces {
  public static short[] read(DataInputStream in) throws IOException {
    short count = in.readShort();
    short[] indices = new short[count];
    for (int i = 0; i < count; i++) {
      indices[i] = in.readShort();
    }
    return indices;
  }
}
