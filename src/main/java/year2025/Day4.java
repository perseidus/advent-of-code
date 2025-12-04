package year2025;

import java.util.LinkedList;
import java.util.List;
import util.Conv;
import util.Solution;
import util.Util;

public class Day4 extends Solution {

  private int[] y = new int[]{-1, -1, -1, 0, 1, 1, 1, 0};
  private int[] x = new int[]{1, 0, -1, -1, -1, 0, 1, 1};


  @Override
  public void solvePart1() {
    char[][] grid = Conv.charMatrix(input);
    int result = getNumberOfRemoved(grid, false);
    System.out.println(result);
  }

  @Override
  public void solvePart2() {
    char[][] grid = Conv.charMatrix(input);

    int result = getNumberOfRemoved(grid, true);
    int current = -1;

    while (current != 0) {
      current = getNumberOfRemoved(grid, true);
      result += current;
    }

    System.out.println(result);
  }

  private int getNumberOfRemoved(char[][] grid, boolean remove) {
    int result = 0;
    List<Pos> list = new LinkedList<>();

    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        if (canBeRemoved(grid, i, j)) {
          result++;
          list.add(new Pos(i, j));
        }
      }
    }

    if (remove) {
      for (Pos pos : list) {
        grid[pos.y][pos.x] = '.';
      }
    }
    return result;
  }

  private boolean canBeRemoved(char[][] grid, int i, int j) {
    if (grid[i][j] != '@') {
      return false;
    }

    int count = 0;
    for (int dir = 0; dir < y.length; dir++) {
      Character c = Util.getOffsetChar(grid, i, j, y[dir], x[dir]);
      if (c != null && c == '@' && ++count > 3) {
        break;
      }
    }
    return count < 4;
  }

  record Pos(int y, int x){
  }

  public static void main(String[] args) {
    new Day4().solve();
  }
}
