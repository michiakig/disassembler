package com.spacemanaki.disassembler;

import java.io.DataInputStream;
import java.io.IOException;

public class Attribute {
  public final short nameIndex;
  public final byte[] info;

  public Attribute(short nameIndex, byte[] info) {
    this.nameIndex = nameIndex;
    this.info = info;
  }

  public static Attribute read(DataInputStream in) throws IOException {
    short nameIndex = in.readShort();
    int length = in.readInt();
    byte[] info = new byte[length];
    for (int i = 0; i < length; i++) {
      info[i] = in.readByte();
    }
    return new Attribute(nameIndex, info);
  }
}
