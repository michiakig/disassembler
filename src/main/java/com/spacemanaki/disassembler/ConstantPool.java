package com.spacemanaki.disassembler;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ConstantPool {
  public enum InfoType {
    CLASS((byte)7),
    FIELD_REF((byte)9),
    METHOD_REF((byte)10),
    INTERFACE_METHOD_REF((byte)11),
    STRING((byte)8),
    INTEGER((byte)3),
    FLOAT((byte)4),
    LONG((byte)5),
    DOUBLE((byte)6),
    NAME_AND_TYPE((byte)12),
    UTF_8((byte)1),
    METHOD_HANDLE((byte)15),
    METHOD_TYPE((byte)16),
    INVOKE_DYNAMIC((byte)18);

    public final byte tag;
    InfoType(byte tag) {
      this.tag = tag;
    }

    private static final Map<Byte, InfoType> tags = new HashMap<>();
    static {
      for (InfoType infoType : InfoType.values()) {
        tags.put(infoType.tag, infoType);
      }
    }

    public static InfoType fromTag(byte tag) {
      return tags.get(tag);
    }
  }

  private static final Map<InfoType, Integer> sizes = new HashMap<>();
  static {
    sizes.put(InfoType.CLASS, 2);
    sizes.put(InfoType.FIELD_REF, 4);
    sizes.put(InfoType.METHOD_REF, 4);
    sizes.put(InfoType.INTERFACE_METHOD_REF, 4);
    sizes.put(InfoType.STRING, 2);
    sizes.put(InfoType.INTEGER, 4);
    sizes.put(InfoType.FLOAT, 4);
    sizes.put(InfoType.LONG, 8);
    sizes.put(InfoType.DOUBLE, 8);
    sizes.put(InfoType.NAME_AND_TYPE, 4);
    sizes.put(InfoType.METHOD_HANDLE, 3);
    sizes.put(InfoType.METHOD_TYPE, 2);
    sizes.put(InfoType.INVOKE_DYNAMIC, 4);
  }

  public static void skipConstantPool(InputStream in) throws IOException {
    DataInputStream data = new DataInputStream(in);
    short count = data.readShort();
    for (short i = 0; i < count - 1; i++) {
      skipEntry(in);
    }

  }

  public static void skipEntry(InputStream in) throws IOException {
    DataInputStream data = new DataInputStream(in);
    byte tag = data.readByte();
    InfoType infoType = InfoType.fromTag(tag);

    if (sizes.containsKey(infoType)) {
      data.skipBytes(sizes.get(infoType));
    } else if (infoType == InfoType.UTF_8) {
      short length = data.readShort();
      data.skipBytes(length);
    } else {
      throw new IllegalStateException("should never happen; static map data is bad: missing " + infoType);
    }
  }
}
