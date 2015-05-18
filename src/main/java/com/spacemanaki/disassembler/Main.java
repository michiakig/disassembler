package com.spacemanaki.disassembler;

import com.spacemanaki.disassembler.constantpool.Entry;
import com.spacemanaki.disassembler.constantpool.Utf8;

import java.io.DataInputStream;
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
      ClassFile classFile = Disassembler.disassemble(new DataInputStream(in));
      if (classFile != null) {
        System.out.println("class file version: " + classFile.prettyPrintedVersion());
        System.out.println("class access flags: " + classFile.accessFlags);
        System.out.println("found " + classFile.fields.length + " fields");
        System.out.println("found " + classFile.methods.length + " methods");

        for (Method method : classFile.methods) {
          Entry entry = classFile.constantPool.lookup(method.nameIndex);
          if (entry instanceof Utf8) {
            Utf8 utf8 = (Utf8)entry;
            System.out.println("  " + utf8.data+"(...)");
          } else {
            System.err.println("Method's name index points to an entry in the constant pool that is not a UTF-8 string");
          }
        }
      } else {
        bail("failed to read class file");
      }
    } catch (FileNotFoundException e) {
      bail("no such file " + filename);
    }
  }
}
