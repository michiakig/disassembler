package com.spacemanaki.disassembler;

import java.io.DataInputStream;
import java.util.EnumSet;
import java.util.Set;

public class AccessFlags {
  public enum Flag implements HasMask {
      PUBLIC(0x0001)
    , FINAL(0x0010)
    , SUPER(0x0020)
    , INTERFACE(0x0200)
    , ABSTRACT(0x0400)
    , SYNTHETIC(0x1000)
    , ANNOTATION(0x2000)
    , ENUM(0x4000);

    private final int mask;
    Flag(int mask) {
      this.mask = mask;
    }

    @Override
    public int getMask() {
      return mask;
    }
  }

  public static EnumSet<Flag> readAccessFlags(DataInputStream in) throws Exception {
    Set<Flag> flags = Utility.readEnumSet(Flag.values(), in.readShort());
    return flags.isEmpty() ? EnumSet.noneOf(Flag.class) : EnumSet.copyOf(flags);
  }
}
