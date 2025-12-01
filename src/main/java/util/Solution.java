package util;

import java.util.List;

public abstract class Solution {

  protected List<String> input;

  protected long start;

  public Solution() {
    input = IO.getInput();
  }

  public abstract void solvePart1();

  public abstract void solvePart2();

  protected void solve() {
    start = System.currentTimeMillis();

    solvePart1();
    double seconds = lap();
    System.out.println(" - pt. 1 finished in: " + seconds + "s");

    solvePart2();
    seconds = lap();
    System.out.println(" - pt. 2 finished in: " + seconds + "s");
  }

  protected void solveSample(int sampleSize) {
    start = System.currentTimeMillis();

    double seconds = 0.0;
    for (int i = 0; i < sampleSize; i++) {
      solvePart1();
      seconds += lap();
    }
    System.out.println(" - pt. 1 avg solve time: " + seconds / sampleSize + "s, with sample size: " + sampleSize);

    seconds = 0.0;
    for (int i = 0; i < sampleSize; i++) {
      solvePart2();
      seconds += lap();
    }
    System.out.println(" - pt. 1 avg solve time: " + seconds / sampleSize + "s, with sample size: " + sampleSize);
  }

  protected double lap() {
    double seconds = (System.currentTimeMillis() - start) / 1000.0;
    start = System.currentTimeMillis();
    return seconds;
  }
}
