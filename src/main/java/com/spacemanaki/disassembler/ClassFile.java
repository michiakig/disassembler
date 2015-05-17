package com.spacemanaki.disassembler;

import java.io.DataInputStream;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public class ClassFile {
  public enum AccessFlag implements HasMask {
      PUBLIC(0x0001)
    , FINAL(0x0010)
    , SUPER(0x0020)
    , INTERFACE(0x0200)
    , ABSTRACT(0x0400)
    , SYNTHETIC(0x1000)
    , ANNOTATION(0x2000)
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

  public static EnumSet<AccessFlag> readAccessFlags(DataInputStream in) throws Exception {
    Set<AccessFlag> accessFlags = Utility.readEnumSet(AccessFlag.values(), in.readShort());
    return accessFlags.isEmpty() ? EnumSet.noneOf(AccessFlag.class) : EnumSet.copyOf(accessFlags);
  }

  public final short minorVersion;
  public final short majorVersion;
  public final EnumSet<AccessFlag> accessFlags;

  public ClassFile(short minorVersion,short majorVersion, EnumSet<AccessFlag> accessFlags) {
    this.minorVersion = minorVersion;
    this.majorVersion = majorVersion;
    this.accessFlags = accessFlags;
  }

  public String prettyPrintedVersion() {
    return majorVersion + "." + minorVersion;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ClassFile classFile = (ClassFile) o;
    return Objects.equals(minorVersion, classFile.minorVersion) &&
        Objects.equals(majorVersion, classFile.majorVersion) &&
        Objects.equals(accessFlags, classFile.accessFlags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(minorVersion, majorVersion, accessFlags);
  }
}
