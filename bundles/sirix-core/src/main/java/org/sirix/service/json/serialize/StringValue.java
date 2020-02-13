package org.sirix.service.json.serialize;

import org.sirix.utils.Coverage;

public final class StringValue {
  public static Coverage cov = new Coverage();
  public static String escape(final String value) {
    var fun = cov.inFunction("StringValue.escape", 63);
    fun.inBranch(0, 8);
    final StringBuilder sb = new StringBuilder();
    final int len = value.length();

    for (int i = 0; i < len; i++) {
      fun.inBranch(1, 2);
      char ch = value.charAt(i);
      switch (ch) {
        case '"':
          fun.inBranch(2, 3);
          sb.append("\\\"");
          break;
        case '\\':
          fun.inBranch(3, 3);
          sb.append("\\\\");
          break;
        case '\b':
          fun.inBranch(4, 3);
          sb.append("\\b");
          break;
        case '\f':
          fun.inBranch(5, 3);
          sb.append("\\f");
          break;
        case '\n':
          fun.inBranch(6, 3);
          sb.append("\\n");
          break;
        case '\r':
          fun.inBranch(7, 3);
          sb.append("\\r");
          break;
        case '\t':
          fun.inBranch(8, 3);
          sb.append("\\t");
          break;
        case '/':
          fun.inBranch(9, 3);
          sb.append("\\/");
          break;
        default:
          fun.inBranch(10, 2);
          //Reference: http://www.unicode.org/versions/Unicode5.1.0/
          if ((ch >= '\u0000' && ch <= '\u001F') || (ch >= '\u007F' && ch <= '\u009F') || (ch >= '\u2000'
              && ch <= '\u20FF')) {
            fun.inBranch(11, 5);
            String ss = Integer.toHexString(ch);
            sb.append("\\u");
            for (int k = 0; k < 4 - ss.length(); k++) {
              fun.inBranch(12, 2);
              sb.append('0');
            }
            sb.append(ss.toUpperCase());
          } else {
            fun.inBranch(13, 2);
            sb.append(ch);
          }
      }
    }

    return sb.toString();
  }
}
