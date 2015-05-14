package com.spacemanaki.disassembler;

import java.util.Objects;

public class ClassFile {
  public final short minorVersion;
  public final short majorVersion;
  public ClassFile(short minorVersion,short majorVersion) {
    this.minorVersion = minorVersion;
    this.majorVersion = majorVersion;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ClassFile classFile = (ClassFile) o;
    return Objects.equals(minorVersion, classFile.minorVersion) &&
        Objects.equals(majorVersion, classFile.majorVersion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(minorVersion, majorVersion);
  }

  public String prettyPrintedVersion() {
    return majorVersion + "." + minorVersion;
  }
}
