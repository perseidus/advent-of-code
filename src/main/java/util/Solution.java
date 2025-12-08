package util;

import java.util.List;

public abstract class Solution {

  protected List<String> input;

  protected long start;

  public Solution() {
    input = IO.getInput();
  }

  public abstract void setupPart1();

  public abstract void setupPart2();

  public abstract void solvePart1();

  public abstract void solvePart2();

  protected void solve() {
    start = System.currentTimeMillis();

    start(1);
    solvePart1();
    double seconds = end();
    System.out.println(" - pt. 1 finished in: " + seconds + "s");

    start(2);
    solvePart2();
    seconds = end();
    System.out.println(" - pt. 2 finished in: " + seconds + "s");
  }

  protected void solve(int samples) {
    start = System.currentTimeMillis();

    double seconds = 0.0;
    for (int i = 0; i < samples; i++) {
      start(1);
      solvePart1();
      seconds += end();
    }
    System.out.println(" - pt. 1 avg solve time: " + seconds / samples + "s, with sample size: " + samples);

    seconds = 0.0;
    for (int i = 0; i < samples; i++) {
      start(2);
      solvePart2();
      seconds += end();
    }
    System.out.println(" - pt. 2 avg solve time: " + seconds / samples + "s, with sample size: " + samples);
  }

  protected void start(int part) {
    if (part == 1) {
      setupPart1();
    } else {
      setupPart2();
    }
    start = System.currentTimeMillis();
  }

  protected double end() {
    return (System.currentTimeMillis() - start) / 1000.0;
  }
}
