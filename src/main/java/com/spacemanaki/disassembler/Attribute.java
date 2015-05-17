package com.spacemanaki.disassembler;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Attribute attribute = (Attribute) o;

    if (nameIndex != attribute.nameIndex) return false;
    return Arrays.equals(info, attribute.info);

  }

  @Override
  public int hashCode() {
    int result = (int) nameIndex;
    result = 31 * result + (info != null ? Arrays.hashCode(info) : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Attribute{" +
        "nameIndex=" + nameIndex +
        ", info=" + Arrays.toString(info) +
        '}';
  }
}
