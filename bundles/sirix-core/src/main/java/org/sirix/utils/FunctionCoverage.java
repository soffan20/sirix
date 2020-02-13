package org.sirix.utils;

import java.util.HashMap;

public class FunctionCoverage {
  private double totalLines;
  private HashMap<Integer, Integer> branches = new HashMap<>();

  protected FunctionCoverage(int totalLines) {
    this.totalLines = totalLines;
  }

  public void inBranch(int branch, int lineCount) {
    branches.put(branch, lineCount);
  }

  public Integer coveredLines() {
    return branches.values().stream().reduce(0, Integer::sum);
  }

  public String stats(String name) {
    StringBuilder sb = new StringBuilder();
    Integer covered = coveredLines();
    double percent = 100 * covered / totalLines;
    sb.append(name)
        .append(": ")
        .append(covered)
        .append('/')
        .append(totalLines)
        .append(String.format(" %.2f%%\n", percent));
    for (var branch : branches.entrySet()) {
      sb.append("\tBranch: ")
          .append(branch.getKey())
          .append(" +")
          .append(branch.getValue())
          .append('\n');
    }
    return sb.toString();
  }
}
