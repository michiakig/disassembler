package com.spacemanaki.disassembler;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.EnumSet;

import static com.spacemanaki.disassembler.Utility.bytes;

public class Disassembler {
  static char[] chars = "0123456789abcdef".toCharArray();

  public static byte[] readBytes(InputStream in, int length) {
    byte[] bytes = new byte[length];
    try {
      if (in.read(bytes) < bytes.length) {
        return null;
      }
    } catch (IOException e) {
      return null;
    }
    return bytes;
  }

  private static byte[] magic = bytes(0xca,0xfe,0xba,0xbe);
  public static boolean readMagic(InputStream in) {
    byte[] bytes = readBytes(in, 4);
    return bytes != null && Arrays.equals(magic, bytes);
  }

  public static short readVersion(InputStream in) {
    DataInputStream data = new DataInputStream(in);
    try {
      return data.readShort();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static ClassFile disassemble(InputStream in) throws Exception {
    if(!readMagic(in)) {
      return null;
    }

    short minor = readVersion(in);
    short major =  readVersion(in);
    ConstantPool.skipConstantPool(in);
    EnumSet<AccessFlags.Flag> flags = AccessFlags.readAccessFlags(new DataInputStream(in));

    return new ClassFile(minor, major, flags);
  }
}
