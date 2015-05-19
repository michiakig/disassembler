package com.spacemanaki.disassembler;

import com.spacemanaki.disassembler.constantpool.ConstantPool;
import com.spacemanaki.disassembler.constantpool.Entry;
import com.spacemanaki.disassembler.constantpool.PlaceholderEntry;
import com.spacemanaki.disassembler.constantpool.Utf8;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.IOException;

import static com.spacemanaki.disassembler.Utility.*;
import static org.junit.Assert.assertEquals;

public class ConstantPoolTest {
  @Test public void testSkipEntryClassInfo() throws IOException {
    DataInputStream in = stream(bytes(Entry.Type.CLASS.tag,0x00,0x00,0xff));
    Entry.read(in);
    assertEquals(1, in.available());
    assertEquals(0xff, in.read());
  }

  @Test public void testSkipEntryUtf8Info() throws IOException {
    DataInputStream in = stream(bytes(Entry.Type.UTF_8.tag,/* skip next 3 bytes */0x00,0x03,0x00,0x00,0x00,0xff));
    Entry.read(in);

    assertEquals(1, in.available());
    assertEquals(0xff, in.read());
  }

  @Test public void testSkipConstantPool() throws IOException {
    byte classTag = Entry.Type.CLASS.tag;
    DataInputStream in = stream(
        /* constant_pool_count = 4, so constant_pool[] has 3 entries */
        bytes(0x00,0x04,classTag,0x00,0x00,classTag,0x00,0x00,classTag,0x00,0x00,0xff)
    );
    ConstantPool.read(in);

    assertEquals(1, in.available());
    assertEquals(0xff, in.read());
  }

  @Test public void lookupByUtf8() throws IOException {
    ConstantPool pool = new ConstantPool();
    Utf8 utf8 = new Utf8("foo");
    pool.add(new PlaceholderEntry(Entry.Type.INTEGER, bytes(0x10,0x01,0x10,0x01)));
    pool.add(utf8);

    assertEquals(utf8, pool.lookupUtf8(2));
  }
}
