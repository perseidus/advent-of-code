package util;

public class Util {

  public static Character getOffsetChar(char[][] grid, int y, int x, int yOff, int xOff) {
    if (outOfBounds(grid, y + yOff, x + xOff)) {
      return null;
    }
    return grid[y + yOff][x + xOff];
  }

  public static boolean outOfBounds(char[][] grid, int row, int col) {
    return row < 0 || row > grid.length - 1 || col < 0 || col > grid[row].length - 1;
  }

}
