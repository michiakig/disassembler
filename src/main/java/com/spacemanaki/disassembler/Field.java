package com.spacemanaki.disassembler;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;

public class Field {
  enum AccessFlag implements HasMask {
      PUBLIC(0x0001)
    , PRIVATE(0x0002)
    , PROTECTED(0x0004)
    , STATIC(0x0008)
    , FINAL(0x0010)
    , VOLATILE(0x0040)
    , TRANSIENT(0x0080)
    , SYNTHETIC(0x1000)
    , ENUM(0x4000);

    private final int mask;
    AccessFlag(int mask) {
      this.mask = mask;
    }

    @Override
    public int getMask() {
      return mask;
    }
  }

  public final EnumSet<AccessFlag> accessFlags;
  public final short nameIndex;
  public final short descriptorIndex;
  public final Attribute[] attributes;

  public Field(
        EnumSet<AccessFlag> accessFlags
      , short nameIndex
      , short descriptorIndex
      , Attribute[] attributes
  ) {
    this.accessFlags = accessFlags;
    this.nameIndex = nameIndex;
    this.descriptorIndex = descriptorIndex;
    this.attributes = attributes;
  }

  public static EnumSet<AccessFlag> readAccessFlag(DataInputStream in) throws IOException {
    short value = in.readShort();
    Set<AccessFlag> flags = Utility.readEnumSet(AccessFlag.values(), value);
    return flags.isEmpty() ? EnumSet.noneOf(AccessFlag.class) : EnumSet.copyOf(flags);
  }

  public static Field read(DataInputStream in) throws IOException {
    EnumSet<AccessFlag> flags = readAccessFlag(in);
    short nameIndex = in.readShort();
    short descriptorIndex = in.readShort();
    Attribute[] attributes = Attribute.readArray(in);
    return new Field(flags, nameIndex, descriptorIndex, attributes);
  }
}
