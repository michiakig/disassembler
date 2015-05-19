package com.spacemanaki.disassembler.constantpool;

public class PlaceholderEntry extends Entry {
  public Type type;
  public byte[] raw;

  public PlaceholderEntry(Type type, byte[] raw) {
    this.type = type;
    this.raw = raw;
  }

  @Override
  public String toString() {
    return "PlaceholderEntry{" +
        "type=" + type +
        '}';
  }
}
