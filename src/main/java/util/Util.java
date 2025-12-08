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

  public static double euclideanDistance(int[] a, int[] b) {
    double distance = 0;
    for (int i = 0; i < a.length; i++) {
      distance += Math.pow(a[i] - b[i], 2);
    }
    return Math.sqrt(distance);
  }

  public static int[] findShortestDistance(double[][] dist) {
    double shortest = Double.MAX_VALUE;
    int index1 = -1, index2 = -1;

    for (int i = 0; i < dist.length; i++) {
      for (int j = i + 1; j < dist[i].length; j++) {
        if (dist[i][j] < shortest) {
          shortest = dist[i][j];
          index1 = i;
          index2 = j;
        }
      }
    }
    return new int[]{index1, index2};
  }
}
