package com.spacemanaki.disassembler;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.EnumSet;

import static com.spacemanaki.disassembler.Utility.bytes;

public class Disassembler {
  static char[] chars = "0123456789abcdef".toCharArray();

  public static byte[] readBytes(DataInputStream in, int length) {
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
  public static boolean readMagic(DataInputStream in) {
    byte[] bytes = readBytes(in, 4);
    return bytes != null && Arrays.equals(magic, bytes);
  }

  public static short readVersion(DataInputStream in) {
    try {
      return in.readShort();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static ClassFile disassemble(DataInputStream in) throws Exception {
    if(!readMagic(in)) {
      return null;
    }

    short minor = readVersion(in);
    short major =  readVersion(in);
    ConstantPool.skipConstantPool(in);
    EnumSet<ClassFile.AccessFlag> accessFlags = ClassFile.readAccessFlags(in);

    in.skipBytes(2); // this_class
    in.skipBytes(2); // super_class

    Interfaces.read(in); // skip interfaces

    short fieldsCount = in.readShort();
    Field[] fields = new Field[fieldsCount];
    for (int i = 0; i < fieldsCount; i++) {
      fields[i] = Field.read(in);
    }

    short methodsCount = in.readShort();
    Method[] methods = new Method[methodsCount];
    for (int i = 0; i < methodsCount; i++) {
      methods[i] = Method.read(in);
    }

    return new ClassFile(minor, major, accessFlags, fields, methods);
  }
}
