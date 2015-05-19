package com.spacemanaki.disassembler;

import com.spacemanaki.disassembler.constantpool.ConstantPool;

import java.io.DataInputStream;
import java.util.Arrays;
import java.util.EnumSet;
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
  public final ConstantPool constantPool;
  public final EnumSet<AccessFlag> accessFlags;
  public final Field[] fields;
  public final Method[] methods;

  public ClassFile(
        short minorVersion
      , short majorVersion
      , ConstantPool constantPool
      , EnumSet<AccessFlag> accessFlags
      , Field[] fields
      , Method[] methods
  ) {
    this.minorVersion = minorVersion;
    this.majorVersion = majorVersion;
    this.constantPool = constantPool;
    this.accessFlags = accessFlags;
    this.fields = fields;
    this.methods = methods;
  }

  public String prettyPrintedVersion() {
    return majorVersion + "." + minorVersion;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ClassFile classFile = (ClassFile) o;

    if (minorVersion != classFile.minorVersion) return false;
    if (majorVersion != classFile.majorVersion) return false;
    if (constantPool != null ? !constantPool.equals(classFile.constantPool) : classFile.constantPool != null)
      return false;
    if (accessFlags != null ? !accessFlags.equals(classFile.accessFlags) : classFile.accessFlags != null) return false;
    // Probably incorrect - comparing Object[] arrays with Arrays.equals
    if (!Arrays.equals(fields, classFile.fields)) return false;
    // Probably incorrect - comparing Object[] arrays with Arrays.equals
    return Arrays.equals(methods, classFile.methods);

  }

  @Override
  public int hashCode() {
    int result = (int) minorVersion;
    result = 31 * result + (int) majorVersion;
    result = 31 * result + (constantPool != null ? constantPool.hashCode() : 0);
    result = 31 * result + (accessFlags != null ? accessFlags.hashCode() : 0);
    result = 31 * result + (fields != null ? Arrays.hashCode(fields) : 0);
    result = 31 * result + (methods != null ? Arrays.hashCode(methods) : 0);
    return result;
  }
}
