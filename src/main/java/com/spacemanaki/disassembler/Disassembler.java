package com.spacemanaki.disassembler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

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

  public static void disassemble(InputStream in) throws Exception {
    if(readMagic(in)) {
      System.out.println("ok");
    } else {
      System.out.println("failed to read magic number");
    }
  }
}
