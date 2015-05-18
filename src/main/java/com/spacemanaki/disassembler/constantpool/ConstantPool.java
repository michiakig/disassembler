package com.spacemanaki.disassembler.constantpool;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ConstantPool implements Iterable<Entry> {
  public static ConstantPool read(DataInputStream in) throws IOException {
    short count = in.readShort();
    ConstantPool pool = new ConstantPool();
    // constant pool table is one entry shorter than specified length for some reason
    for (short i = 0; i < count - 1; i++) {
      pool.add(Entry.read(in));
    }
    return pool;
  }

  private List<Entry> entries = new ArrayList<>();
  public ConstantPool() {}

  public Entry lookup(int i) {
    if (i < 1 || i > entries.size()) {
      throw new IllegalArgumentException("Constant pool index out of bounds: " + i);
    }
    return entries.get(i - 1);
  }

  public void add(Entry entry) {
    entries.add(entry);
  }

  @Override
  public Iterator<Entry> iterator() {
    return entries.iterator();
  }
}
