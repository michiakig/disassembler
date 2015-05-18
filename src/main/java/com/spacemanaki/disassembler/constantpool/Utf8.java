package com.spacemanaki.disassembler.constantpool;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class Utf8 extends Entry {
  public final String data;

  public Utf8(DataInputStream in) throws IOException {
    short length = in.readShort();
    byte[] bytes = new byte[length];
    int read = in.read(bytes);
    if (read != length) {
      throw new IOException("Failed to read expected " + length + " bytes while reading UTF8 constant pool entry");
    }
    this.data = new String(bytes, Charset.forName("UTF-8"));
  }

  @Override
  public String toString() {
    return "Utf8{" +
        "data='" + data + '\'' +
        '}';
  }
}
