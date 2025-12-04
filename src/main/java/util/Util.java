package util;

public class Util {

  public static Character getOffsetChar(char[][] grid, int y, int x, int yOff, int xOff) {
    if (outOfBounds(grid, y + yOff, x + xOff)) {
      return null;
    }
    return grid[y + yOff][x + xOff];
  }

  public static boolean outOfBounds(char[][] grid, int y, int x) {
    return y < 0 || y > grid.length - 1 || x < 0 || x > grid[y].length - 1;
  }

}
