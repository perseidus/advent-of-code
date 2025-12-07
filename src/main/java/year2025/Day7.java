package year2025;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import util.Conv;
import util.Solution;
import util.Util;

public class Day7 extends Solution {

  private char[][] grid;
  private int start;

  @Override
  public void setupPart1() {
    grid = Conv.charMatrix(input);
  }

  @Override
  public void setupPart2() {
    setupPart1();
  }

  @Override
  public void solvePart1() {
    for (int i = 0; i < grid[0].length; i++) {
      if (grid[0][i] == 'S') {
        start = i;
      }
    }

    Set<Pos> splitters = new HashSet<>();
    int result = countTachyonSplitters(splitters, 0, start);
    System.out.println(result);
  }

  @Override
  public void solvePart2() {
    Map<Pos, Long> splitters = new HashMap<>();
    long result = countTachyonPaths(splitters, 0, start);
    System.out.println(result);
  }

  /**
   * Simulates the tachyon beam given the current position. Returns if the beam is out of bounds.
   * Recursive DFS that eliminates duplicate splitter positions.
   *
   * @param splitters already visited splitters (duplicates)
   * @param row current row
   * @param col current column
   * @return number of times the beams get split
   */
  private int countTachyonSplitters(Set<Pos> splitters, int row, int col) {
    row = getNextPos(row, col);

    // end of input
    if (Util.outOfBounds(grid, row, col) || splitters.contains(new Pos(row, col))) {
      return 0;
    }

    splitters.add(new Pos(row, col));
    return 1
        + countTachyonSplitters(splitters, row, col - 1)
        + countTachyonSplitters(splitters, row, col + 1);
  }

  /**
   * Simulates the tachyon beam given the current position. Returns the number of all possible beam
   * paths. Recursive DFS with memoization.
   *
   * @param splitters cache for visited splitters (and fully calculated number of paths)
   * @param row current row
   * @param col current column
   * @return number of distinct paths
   */
  private long countTachyonPaths(Map<Pos, Long> splitters, int row, int col) {
    row = getNextPos(row, col);

    // end of input
    if (Util.outOfBounds(grid, row, col)) {
      return 1;
    }

    Pos pos = new Pos(row, col);
    if (splitters.containsKey(pos)) {
      return splitters.get(pos);
    }

    long left = countTachyonPaths(splitters, row, col - 1);
    long right = countTachyonPaths(splitters, row, col + 1);

    splitters.put(pos, left + right);
    return left + right;
  }

  private int getNextPos(int row, int col) {
    row++;  // find next splitting position (or end of grid)
    while (!Util.outOfBounds(grid, row, col) && grid[row][col] == '.') {
      row++;
    }
    return row;
  }

  public static class Pos {

    int row, col;

    public Pos(int row, int col) {
      this.row = row;
      this.col = col;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Pos pos)) {
        return false;
      }

      return row == pos.row && col == pos.col;
    }

    @Override
    public int hashCode() {
      int result = row;
      result = 31 * result + col;
      return result;
    }
  }

  public static void main(String[] args) {
    new Day7().solve(50);
  }
}
