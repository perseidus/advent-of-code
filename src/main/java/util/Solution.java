package util;

import java.util.List;

public abstract class Solution {

  protected List<String> input;

  protected long start;

  public Solution() {
    input = IO.getInput();
    start = System.currentTimeMillis();
  }

  public abstract void solvePart1();

  public abstract void solvePart2();

  protected void lap() {
    double seconds = (System.currentTimeMillis() - start) / 1000.0;
    System.out.println(" - finished in: " + seconds + "s");
    start = System.currentTimeMillis();
  }
}
