package org.sirix.utils;

import java.util.HashMap;

public class Coverage {
  private HashMap<String, FunctionCoverage> cov = new HashMap<>();

  public FunctionCoverage inFunction(String name, int lineCount) {
    return cov.computeIfAbsent(name, (n) -> new FunctionCoverage(lineCount));
  }

  public String stats() {
    StringBuilder sb = new StringBuilder("Total coverage:\n");
    for (var entry : cov.entrySet()) {
      var name = entry.getKey();
      var function = entry.getValue();
      sb.append(function.stats(name)).append('\n');
    }
    return sb.toString();
  }
}

