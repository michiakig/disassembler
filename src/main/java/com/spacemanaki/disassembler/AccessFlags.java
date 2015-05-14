package com.spacemanaki.disassembler;

import java.io.DataInputStream;
import java.util.EnumSet;

public class AccessFlags {
  public enum Flag {
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
  }

  public static EnumSet<Flag> readAccessFlags(DataInputStream in) throws Exception {
    short value = in.readShort();

    EnumSet<Flag> flags = EnumSet.noneOf(Flag.class);
    for (Flag flag : Flag.values())
      if ((flag.mask & value) == flag.mask)
        flags.add(flag);

    return flags;
  }
}
