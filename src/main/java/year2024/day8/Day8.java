package year2024.day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day8 {

  static int maxRow;
  static int maxCol;
  static Map<Character, List<Position>> antennae = new HashMap<Character, List<Position>>();

  public static void main(String[] args) {
    long start = System.currentTimeMillis();

    readInput("src/main/java/year2024/day8/input.txt");
    int result = calculateAllAntinodes(false);
    long end1 = System.currentTimeMillis();
    System.out.println("Number of antinodes: " + result);

    result = calculateAllAntinodes(true);
    long end2 = System.currentTimeMillis();
    System.out.println("Number of antinodes with resonant harmonics: " + result);

    System.out.println("Part 1: " + (end1 - start) / 1000.0 + "s");
    System.out.println("Part 2: " + (end2 - end1) / 1000.0 + "s");
  }

  /**
   * reads the input file, stores identical character's positions in a set
   *
   * @param file filepath
   */
  private static void readInput(String file) {
    try {
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);

      int i = 0;
      String line = br.readLine();
      maxCol = line.length() - 1;
      while (line != null) {
        int j = 0;
        for (Character c : line.toCharArray()) {
          if (!c.equals('.')) {
            if (!antennae.containsKey(c)) { // new frequency
              antennae.put(c, new ArrayList<Position>());
            }
            antennae.get(c).add(new Position(i, j)); // add position of antenna for frequency c
          }
          j++;
        }
        line = br.readLine();
        i++;
      }
      maxRow = i - 1;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Counts the antinodes for all antennae: positions on the grid, that are in a line with 2
   * antennae with the same frequency (same character) and one of the antennae is twice as far away
   * as the other (part1), or in a line at any position (part 2)
   *
   * @param part2 antinodes also occur in a line at any grid position (part 2)
   * @return number of antinodes
   */
  private static int calculateAllAntinodes(boolean part2) {
    Set<Position> antinodes = new HashSet<Position>();  // all antinodes of all antennae

    for (List<Position> positions : antennae.values()) {  // for every frequency (character)
      for (Position a1 : positions) { // for all antennae of the same frequency
        for (Position a2 : positions) {
          List<Position> newAN = calculateAntinodes(a1, a2, part2); // calculate antinodes
          antinodes.addAll(newAN);
        }
      }
    }
    return antinodes.size();  // number of antinodes
  }

  /**
   * Counts the antinodes on the grid for two given antennae (2 antinodes for part 1), in a line of
   * the two antennae at any grid position (part 2)
   *
   * @param a1    position of antenna 1
   * @param a2    position of antenna 2
   * @param part2 antinodes also occur in a line at any grid position (part 2)
   * @return list of all antinode positions created by 2 antennae
   */
  private static List<Position> calculateAntinodes(Position a1, Position a2, boolean part2) {
    List<Position> antinodes = new ArrayList<>();
    if (a1.equals(a2)) {  // same antenna
      return antinodes;
    } else if (part2) {   // for part 2: antenna positions are also antinodes
      antinodes.add(a1);
      antinodes.add(a2);
    }

    int newRow = a1.x, newCol = a1.y;
    int xOff = a1.x - a2.x; // row offset
    int yOff = a1.y - a2.y; // column offset
    newRow += xOff; // row index of antinode
    newCol += yOff; // column index of antinode
    boolean resonant = true;

    while ((resonant || part2)    // once for part 1, to the end of grid for part 2
        && newRow >= 0 && newCol >= 0 && newRow <= maxRow && newCol <= maxCol) {
      antinodes.add(new Position(newRow, newCol));  // antinodes in one direction
      newRow += xOff;
      newCol += yOff;
      resonant = false;
    }

    newRow = a2.x - xOff;
    newCol = a2.y - yOff;
    resonant = true;

    while ((resonant || part2)    // once for part 1, to the end of grid for part 2
        && newRow >= 0 && newCol >= 0 && newRow <= maxRow && newCol <= maxCol) {
      antinodes.add(new Position(newRow, newCol));  // antinodes in the other direction
      newRow -= xOff;
      newCol -= yOff;
      resonant = false;
    }

    return antinodes; // all antinodes created by a1 and a2
  }
}
