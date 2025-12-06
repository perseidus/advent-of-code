package year2025;

import java.util.HashSet;
import java.util.Set;
import util.Conv;
import util.Solution;

public class Day2 extends Solution {

  private Set<Long> duplicates;
  private String[] ranges;


  @Override
  public void setupPart1() {
    ranges = Conv.split(input, ",").get(0);
  }

  @Override
  public void setupPart2() {
    setupPart1();
  }

  @Override
  public void solvePart1() {
    long result = 0;

    for (String range : ranges) {
      String[] rangeStr = range.split("-");
      long min = Long.parseLong(rangeStr[0]);
      long max = Long.parseLong(rangeStr[1]);
      String maxStr = rangeStr[1];

      duplicates = new HashSet<>();
      result += getAddedPatterns(min, max, maxStr, 2);
    }

    System.out.println(result);
  }

  @Override
  public void solvePart2() {
    long result = 0;

    for (String range : ranges) {
      String[] rangeStr = range.split("-");
      long min = Long.parseLong(rangeStr[0]);
      long max = Long.parseLong(rangeStr[1]);
      String maxStr = rangeStr[1];

      duplicates = new HashSet<>();
      for (int i = maxStr.length(); i > 1; i--)  {
        result += getAddedPatterns(min, max, maxStr, i);
      }
    }

    System.out.println(result);
  }

  /**
   * method that searches for patterns, within a range and with a specified number of times the
   * pattern repeats in the string
   *
   * @param min range start
   * @param max range end
   * @param maxStr range end as string
   * @param divide times the pattern repeats
   * @return sum of ids that follow a pattern that repeats *divide* times
   */
  private long getAddedPatterns(long min, long max, String maxStr, int divide) {
    if (maxStr.length() % divide != 0 && (maxStr.length() - 1) % divide != 0) {
      return 0; // pattern can't repeat in string of this length, as digits would be left over
    }

    long result = 0;

    StringBuilder currStr = new StringBuilder("9");
    int append = maxStr.length() / divide;
    currStr.append("9".repeat(append - 1));

    long curr = Long.parseLong(currStr.toString()); // the pattern
    long concat = max;
    while (concat > 0 && concat >= min) {
      concat = Long.parseLong(String.valueOf(curr).repeat(divide)); // repeat the pattern

      if (concat >= min && concat <= max) {
        if (!duplicates.contains(concat)) { // within bounds and no duplicate
          result += concat;
          duplicates.add(concat);
        }
      }
      curr--;
    }

    return result;
  }

  public static void main(String[] args) {
    new Day2().solve();
  }
}
