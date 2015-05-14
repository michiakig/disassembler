package com.spacemanaki.disassembler;

import java.io.FileInputStream;

public class Disassembler {
  static char[] chars = "0123456789abcdef".toCharArray();

  public static void main(String[] args) throws Exception {
    if (args.length < 1) {
      System.out.println("must supply a filename");
      System.exit(1);
    }

    String filename = args[0];
    FileInputStream in = new FileInputStream(filename);
    byte[] bytes = new byte[4];
    int read = in.read(bytes);
    for (byte b : bytes) {
      System.out.print(chars[(b & 0xf0) >> 4]);
      System.out.print(chars[b & 0x0f]);
    }
    System.out.println();

    in.close();
  }
}
