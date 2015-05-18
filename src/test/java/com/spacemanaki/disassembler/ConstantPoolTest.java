package com.spacemanaki.disassembler;

import com.spacemanaki.disassembler.constantpool.ConstantPool;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.IOException;

import static com.spacemanaki.disassembler.Utility.*;
import static org.junit.Assert.assertEquals;

public class ConstantPoolTest {
  @Test public void testSkipEntryClassInfo() throws IOException {
    DataInputStream in = stream(bytes(ConstantPool.InfoType.CLASS.tag,0x00,0x00,0xff));
    ConstantPool.skipEntry(in);
    assertEquals(1, in.available());
    assertEquals(0xff, in.read());
  }

  @Test public void testSkipEntryUtf8Info() throws IOException {
    DataInputStream in = stream(bytes(ConstantPool.InfoType.UTF_8.tag,/* skip next 3 bytes */0x00,0x03,0x00,0x00,0x00,0xff));
    ConstantPool.skipEntry(in);

    assertEquals(1, in.available());
    assertEquals(0xff, in.read());
  }

  @Test public void testSkipConstantPool() throws IOException {
    byte classTag = ConstantPool.InfoType.CLASS.tag;
    DataInputStream in = stream(
        /* constant_pool_count = 4, so constant_pool[] has 3 entries */
        bytes(0x00,0x04,classTag,0x00,0x00,classTag,0x00,0x00,classTag,0x00,0x00,0xff)
    );
    ConstantPool.skipConstantPool(in);

    assertEquals(1, in.available());
    assertEquals(0xff, in.read());
  }
}
