package com.spacemanaki.disassembler.constantpool;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class Entry {
  public enum Type {
    CLASS((byte) 7),
    FIELD_REF((byte) 9),
    METHOD_REF((byte) 10),
    INTERFACE_METHOD_REF((byte) 11),
    STRING((byte) 8),
    INTEGER((byte) 3),
    FLOAT((byte) 4),
    LONG((byte) 5),
    DOUBLE((byte) 6),
    NAME_AND_TYPE((byte) 12),
    UTF_8((byte) 1),
    METHOD_HANDLE((byte) 15),
    METHOD_TYPE((byte) 16),
    INVOKE_DYNAMIC((byte) 18);

    public final byte tag;

    Type(byte tag) {
      this.tag = tag;
    }

    private static final Map<Byte, Type> tags = new HashMap<>();

    static {
      for (Type infoType : Type.values()) {
        tags.put(infoType.tag, infoType);
      }
    }

    public static Type fromTag(byte tag) {
      return tags.get(tag);
    }
  }

  // (temporary) metadata about how long (in bytes) each type of constant pool entry is, used to skip over entries
  private static final Map<Type, Integer> sizes = new HashMap<>();
  static {
    sizes.put(Type.CLASS, 2);
    sizes.put(Type.FIELD_REF, 4);
    sizes.put(Type.METHOD_REF, 4);
    sizes.put(Type.INTERFACE_METHOD_REF, 4);
    sizes.put(Type.STRING, 2);
    sizes.put(Type.INTEGER, 4);
    sizes.put(Type.FLOAT, 4);
    sizes.put(Type.LONG, 8);
    sizes.put(Type.DOUBLE, 8);
    sizes.put(Type.NAME_AND_TYPE, 4);
    sizes.put(Type.METHOD_HANDLE, 3);
    sizes.put(Type.METHOD_TYPE, 2);
    sizes.put(Type.INVOKE_DYNAMIC, 4);
  }

  public static Entry read(DataInputStream in) throws IOException {
    Type type = Type.fromTag(in.readByte());

    if (sizes.containsKey(type)) {
      int length = sizes.get(type);
      byte[] raw = new byte[length];
      int read = in.read(raw);
      if (read != length) {
        throw new IOException("Failed to read " + length + " bytes while reading constant pool entry of type " + type);
      }
      return new PlaceholderEntry(type, raw);
    } else if (type == Type.UTF_8) {
      return new Utf8(in);
    } else {
      throw new IllegalStateException("should never happen; static map data is bad: missing " + type);
    }
  }
}