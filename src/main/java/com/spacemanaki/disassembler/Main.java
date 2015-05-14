package com.spacemanaki.disassembler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {
  public static void bail(String message) {
    System.err.println(message);
    System.exit(1);
  }

  public static void main(String[] args) throws Exception {
    if (args.length < 1) {
      bail("must supply a filename");
    }

    String filename = args[0];
    FileInputStream in = null;
    try {
      in = new FileInputStream(filename);
      Disassembler.disassemble(in);
    } catch (FileNotFoundException e) {
      bail("no such file " + filename);
    }
  }
}
