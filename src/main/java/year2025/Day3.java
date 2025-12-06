package year2025;

import util.Conv;
import util.Solution;

public class Day3 extends Solution {

  private int[][] rows;

  @Override
  public void setupPart1() {
    rows = Conv.intMatrix(input);
  }

  @Override
  public void setupPart2() {
    setupPart1();
  }

  @Override
  public void solvePart1() {
    long result = 0;

    for (int[] row : rows) {
      int i1 = getHighest(row, 0, row.length - 1);
      int i2 = getHighest(row, i1 + 1, row.length);

      result += Long.parseLong(row[i1] + "" + row[i2]);
    }

    System.out.println(result);
  }

  @Override
  public void solvePart2() {
    long result = 0;

    for (int[] row : rows) {
      StringBuilder bat = new StringBuilder();
      int index = -1;

      for (int i = 11; i >= 0; i--) {
        index = getHighest(row, index + 1, row.length - i);
        bat.append(row[index]);
      }

      result += Long.parseLong(bat.toString());
    }

    System.out.println(result);
  }

  private int getHighest(int[] arr, int start, int end) {
    int current = -1;
    int index = -1;

    for (int i = start; i < end; i++) {
      if (arr[i] > current) {
        current = arr[i];
        index = i;

        if (current == 9) {
          break;
        }
      }
    }

    return index;
  }

  public static void main(String[] args) {
    new Day3().solve();
  }
}
