package org.sirix.service.json.serializer;

import org.junit.Test;
import org.sirix.service.json.serialize.StringValue;

import static org.junit.Assert.assertEquals;

public final class StringValueTest {

  @Test
  public void escapeControlCharacters() {
    assertEquals("Form feed character '\\f' should be escaped", "\\f", StringValue.escape("\f"));
    assertEquals("Tab character '\\t' should be escaped", "\\t", StringValue.escape("\t"));
    assertEquals("Backspace character '\\b' should be escaped", "\\b", StringValue.escape("\b"));
    assertEquals("Newline character '\\n' should be escaped", "\\n", StringValue.escape("\n"));
  }

  @Test
  public void escapeUnicode() {
    assertEquals("Bomb emoji should not be escaped", "💣", StringValue.escape("💣"));
    assertEquals("Heavy Check Mark emoji should not be escaped", "✔", StringValue.escape("✔"));
    assertEquals("Special unicode should be escaped", "\\u20AB", StringValue.escape("₫"));
  }
}
