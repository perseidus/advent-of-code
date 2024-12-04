package year2024.day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day4 {

  static List<String> rows = new ArrayList<>();
  static int xmasCount = 0; // part 1
  static int xMASCount = 0; // part 2
  static int maxRows;
  static int maxCols;

  public static void main(String[] args) {
    readInput("src/main/java/year2024/day4/input.txt");
    iterateInput();
    System.out.println("Instances of 'XMAS': " + xmasCount);
    System.out.println("Instances of X-'MAS': " + xMASCount);
  }

  /**
   * reads the input file and saves the rows in a list of strings
   *
   * @param file filepath
   */
  private static void readInput(String file) {
    try {
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);

      String line = br.readLine();
      rows.add(line);
      while (line != null) {
        line = br.readLine();
        rows.add(line);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * iterates through all characters of the input and handles X's (part 1) and A's (part 2)
   */
  private static void iterateInput() {
    String row;
    maxRows = rows.size() - 1;
    maxCols = rows.get(0).length();
    for (int i = 0; i < maxRows; i++) {
      row = rows.get(i);
      for (int j = 0; j < maxCols; j++) {
        if (row.charAt(j) == 'X') {
          searchXMAS(row, i, j);  // part 1
        } else if (row.charAt(j) == 'A') {
          searchMAS(i, j);      // part 2
        }
      }
    }
  }

  /**
   * checks for all 8 directions if the word 'XMAS' can be found, with [i,j] as the first character
   * 'X' (part 1)
   *
   * @param row text of current row
   * @param i   index of current row of 'X'
   * @param j   index of current column of 'X'
   */
  private static void searchXMAS(String row, int i, int j) {
    String rowP1 = null, rowP2 = null, rowP3 = null, rowM1 = null, rowM2 = null, rowM3 = null;
    try {
      rowP1 = rows.get(i + 1);
      rowP2 = rows.get(i + 2);
      rowP3 = rows.get(i + 3);
    } catch (IndexOutOfBoundsException e) {
      // do nothing, rows won't be needed
    }
    try {
      rowM1 = rows.get(i - 1);
      rowM2 = rows.get(i - 2);
      rowM3 = rows.get(i - 3);
    } catch (IndexOutOfBoundsException e) {
      // do nothing, rows won't be needed
    }

    if (i > 2 && rowM1.charAt(j) == 'M' && rowM2.charAt(j) == 'A' && rowM3.charAt(j) == 'S') { // up
      xmasCount++;
    }
    if (i > 2 && j < (maxCols - 3) && rowM1.charAt(j + 1) == 'M' && rowM2.charAt(j + 2) == 'A'
        && rowM3.charAt(j + 3) == 'S') {  // up right
      xmasCount++;
    }
    if (j < (maxCols - 3) && row.charAt(j + 1) == 'M' && row.charAt(j + 2) == 'A'
        && row.charAt(j + 3) == 'S') {  // right
      xmasCount++;
    }
    if (i < (maxRows - 3) && j < (maxCols - 3) && rowP1.charAt(j + 1) == 'M'
        && rowP2.charAt(j + 2) == 'A' && rowP3.charAt(j + 3) == 'S') {  // down right
      xmasCount++;
    }
    if (i < (maxRows - 3) && rowP1.charAt(j) == 'M' && rowP2.charAt(j) == 'A'
        && rowP3.charAt(j) == 'S') {  // down
      xmasCount++;
    }
    if (i < (maxRows - 3) && j > 2 && rowP1.charAt(j - 1) == 'M' && rowP2.charAt(j - 2) == 'A'
        && rowP3.charAt(j - 3) == 'S') {  // down left
      xmasCount++;
    }
    if (j > 2 && row.charAt(j - 1) == 'M' && row.charAt(j - 2) == 'A'
        && row.charAt(j - 3) == 'S') { // left
      xmasCount++;
    }
    if (i > 2 && j > 2 && rowM1.charAt(j - 1) == 'M' && rowM2.charAt(j - 2) == 'A'
        && rowM3.charAt(j - 3) == 'S') {  // up left
      xmasCount++;
    }
  }

  /**
   * checks if the word 'MAS' can be found in the shape of an X, with [i,j]='A' as center point
   * (part 2)
   *
   * @param i index of current row of 'A'
   * @param j index of current column of 'A'
   */
  private static void searchMAS(int i, int j) {
    if ((i - 1 < 0) || (i + 1 > maxRows - 1) || (j - 1 < 0) || (j + 1 > maxCols - 1)) {
      return;   // index out of bounds
    }
    String rowP1 = rows.get(i + 1), rowM1 = rows.get(i - 1);
    int mas = 0;

    if ((rowM1.charAt(j - 1) == 'M' && rowP1.charAt(j + 1) == 'S')
        || (rowM1.charAt(j - 1) == 'S' && rowP1.charAt(j + 1) == 'M')) {
      mas++;
    }
    if ((rowM1.charAt(j + 1) == 'M' && rowP1.charAt(j - 1) == 'S')
        || (rowM1.charAt(j + 1) == 'S' && rowP1.charAt(j - 1) == 'M')) {
      mas++;
    }
    if (mas > 1) {  // two 'MAS' in the shape of an X
      xMASCount++;
    }
  }
}
