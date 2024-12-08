package year2024.day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day6 {

  static int startRow;
  static int startCol;
  static int maxRow;
  static int maxCol;
  static int visited = 0;
  static Set<State> visitedPositions;
  static List<char[]> startingRows = new ArrayList<char[]>();

  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    readInput("src/main/java/year2024/day6/input.txt");

    simulatePath(startingRows, -1, -1);
    long end1 = System.currentTimeMillis();
    System.out.println("Visited squares: " + visited);

    int validObstacles = checkObstacles();
    System.out.println("Valid obstacles to generate a loop: " + validObstacles);
    long end2 = System.currentTimeMillis();

    System.out.println("Part 1: " + (end1 - start) / 1000.0 + "s");
    System.out.println("Part 2: " + (end2 - end1) / 1000.0 + "s");
  }

  /**
   * reads the input file, finds row and column index for '^'
   *
   * @param file filepath
   */
  private static void readInput(String file) {
    try {
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);

      String line = br.readLine();
      int row = 0;
      while (line != null) {
        startingRows.add(line.toCharArray());
        if (line.contains("^")) {
          startRow = row;
          startCol = line.indexOf('^');
        }
        line = br.readLine();
        row++;
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    maxRow = startingRows.size();
    maxCol = startingRows.get(0).length;
  }

  /**
   * Simulates a path for an agent that turns right at every obstacle '#', counts the visited
   * squares (part 1). <br>Places an extra obstacle at [i, j] if parameter i > 0 and checks if the
   * resulting path leads to a loop (part 2).
   *
   * @param copyThat starting grid, gets deep copied for this method
   * @param i        row index for inserted obstacle
   * @param j        column index for inserted obstacle
   * @return true if the path is a loop, false if the path exits the grid
   */
  private static boolean simulatePath(List<char[]> copyThat, int i, int j) {
    int currentRow = startRow, currentCol = startCol;
    List<char[]> rows = new ArrayList<>();
    for (char[] row : copyThat) {
      rows.add(Arrays.copyOf(row, row.length));
    }
    if (i >= 0) {
      rows.get(i)[j] = '#';
    }
    visitedPositions = new HashSet<>();
    int vertical = -1;
    int horizontal = 0;
    int moved;

    while (true) {
      if (!(rows.get(currentRow)[currentCol] == 'X')) { // new visited square (count up for part 1)
        rows.get(currentRow)[currentCol] = 'X';
        visited++;
      }

      moved = moveSquare(rows, currentRow, currentCol, vertical, horizontal);
      if (moved == 0) { // loop (part 2)
        return true;
      } else if (moved == -2) { // path leaves grid
        return false;
      } else if (moved == 1) {  // next square forward is clear -> step forward
        currentRow += vertical;
        currentCol += horizontal;
      } else {  // obstacle -> change direction 90 degrees to the right
        int tmp = horizontal;
        horizontal = -vertical;
        vertical = tmp;
      }
    }
  }

  /**
   * Moves the agent for a given position and directions if there is no obstacle.
   *
   * @param rows    the input grid
   * @param row     current row index
   * @param col     current column index
   * @param vertOff move offset vertically
   * @param horOff  move offset horizontally
   * @return 0 if a loop is encountered, -1 if an obstacle is encountered, -2 if the path leaves the
   * grid, 1 else (no obstacle)
   */
  private static int moveSquare(List<char[]> rows, int row, int col, int vertOff, int horOff) {
    try {
      if (rows.get(row + vertOff)[col + horOff] == '#') {
        State newTurn = new State(vertOff, horOff, row, col);
        if (visitedPositions.contains(newTurn)) {
          return 0; // duplicate -> path is a loop
        } else {
          visitedPositions.add(newTurn);
          return -1;  // obstacle but no loop
        }
      }
    } catch (IndexOutOfBoundsException e) { // path leaves grid
      return -2;
    }
    return 1; // no obstacle for next move forward
  }

  /**
   * Counts all obstacles that can be added to the grid and leave the agent stuck in a loop.
   *
   * @return count of possible obstacles to generate loops
   */
  private static int checkObstacles() {
    int validObstacles = 0;
    for (int i = 0; i < startingRows.size(); i++) {
      for (int j = 0; j < startingRows.get(0).length; j++) {
        if (simulatePath(startingRows, i, j)) {
          validObstacles++;
        }
      }
    }
    return validObstacles;
  }
}