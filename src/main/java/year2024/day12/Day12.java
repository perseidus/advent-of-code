package year2024.day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import year2024.day8.Position;

public class Day12 {

  static int maxRow;
  static int maxCol;

  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    int[][] plants = readInput("src/main/java/year2024/day12/input.txt");
    int[] fencingPrice = totalFencingCost(plants);
    System.out.println("Fencing costs for each fence: " + fencingPrice[0]);
    System.out.println("Fencing costs for each side: " + fencingPrice[1]);
    long end = System.currentTimeMillis();

    System.out.println("Part 1 + 2: " + (end - start) / 1000.0 + "s");
  }

  /**
   * reads the input file
   *
   * @param file filepath
   * @return 2-dimensional int array of topographic map
   */
  private static int[][] readInput(String file) {
    List<int[]> plantsRows = new ArrayList<>();
    String input;
    try {
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);

      input = br.readLine();
      while (input != null) {
        plantsRows.add(Arrays.stream(input.split(""))
            .mapToInt(a -> (int) a.charAt(0))
            .toArray());
        input = br.readLine();
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    maxRow = plantsRows.size();
    maxCol = plantsRows.get(0).length;
    int[][] plants = new int[maxRow][maxCol];
    for (int i = 0; i < maxRow; i++) {
      plants[i] = plantsRows.get(i);
    }
    return plants;
  }

  /**
   * Calculates the fencing costs per fence (part 1) and the fencing costs per sides (part 2) for
   * all plants and returns them.
   *
   * @param plants grid of plants
   * @return costs for part 1 and 2
   */
  private static int[] totalFencingCost(int[][] plants) {
    int totalCost = 0;
    int toalCost2 = 0;
    for (int i = 0; i < maxRow; i++) {
      for (int j = 0; j < maxCol; j++) {
        if (plants[i][j] >= 0) {
          int[] result = eachFenceCosts(plants, i, j);
          totalCost += result[0];
          toalCost2 += result[1];
        }
      }
    }
    return new int[]{totalCost, toalCost2};
  }

  /**
   * Calculates the fencing costs per fence (part 1) and the fencing costs per sides (part 2) for a
   * given plant, by finding all neighboring plants of the same type.
   *
   * @param plants grid of plants
   * @param row    row index of the starting plant
   * @param col    column index of the starting plant
   * @return array of length 2 containing result for part 1 and 2
   */
  private static int[] eachFenceCosts(int[][] plants, int row, int col) {
    int plant = plants[row][col];
    int fences = 0; // number of fences (perimeter) for part 1

    List<Integer> tmpList;  // maps for storing all fences (for part 2)
    Map<Integer, List<Integer>> leftSides = new HashMap<>();
    Map<Integer, List<Integer>> rightSides = new HashMap<>();
    Map<Integer, List<Integer>> downSides = new HashMap<>();
    Map<Integer, List<Integer>> upSides = new HashMap<>();

    Position current = new Position(row, col), newPos;
    Set<Position> visited = new HashSet<>();  // visited plants (eliminating duplicates)
    List<Position> todo = new LinkedList<>(); // fifo queue
    todo.add(current);
    visited.add(current);
    // searches all neighboring plants of the same type (BFS)
    while (!todo.isEmpty()) {   // for all 4 directions
      current = todo.remove(0);

      newPos = new Position(current.x - 1, current.y);  // UP neighbor
      if (current.x > 0 && plants[current.x - 1][current.y] == plant) { // plant of same type
        if (!visited.contains(newPos)) {  // not already encountered (duplicate)
          todo.add(newPos);
          visited.add(newPos);
        }
      } else {  // found edge (fence) of field
        fences++;
        if (upSides.containsKey(newPos.x)) {  // add fence to map of UP direction (part 2)
          upSides.get(newPos.x).add(newPos.y);
        } else {
          tmpList = new ArrayList<>();
          tmpList.add(newPos.y);
          upSides.put(newPos.x, tmpList);
        }
      }

      newPos = new Position(current.x + 1, current.y);  // DOWN neighbor
      if (current.x < maxRow - 1 && plants[current.x + 1][current.y] == plant) {
        if (!visited.contains(newPos)) {
          todo.add(newPos);
          visited.add(newPos);
        }
      } else {
        fences++;
        if (downSides.containsKey(newPos.x)) {
          downSides.get(newPos.x).add(newPos.y);
        } else {
          tmpList = new ArrayList<>();
          tmpList.add(newPos.y);
          downSides.put(newPos.x, tmpList);
        }
      }

      newPos = new Position(current.x, current.y - 1);  // LEFT neighbor
      if (current.y > 0 && plants[current.x][current.y - 1] == plant) {
        if (!visited.contains(newPos)) {
          todo.add(newPos);
          visited.add(newPos);
        }
      } else {
        fences++;
        if (leftSides.containsKey(newPos.y)) {
          leftSides.get(newPos.y).add(newPos.x);
        } else {
          tmpList = new ArrayList<>();
          tmpList.add(newPos.x);
          leftSides.put(newPos.y, tmpList);
        }
      }

      newPos = new Position(current.x, current.y + 1);  // RIGHT neighbor
      if (current.y < maxCol - 1 && plants[current.x][current.y + 1] == plant) {
        if (!visited.contains(newPos)) {
          todo.add(newPos);
          visited.add(newPos);
        }
      } else {
        fences++;
        if (rightSides.containsKey(newPos.y)) {
          rightSides.get(newPos.y).add(newPos.x);
        } else {
          tmpList = new ArrayList<>();
          tmpList.add(newPos.x);
          rightSides.put(newPos.y, tmpList);
        }
      }
    }

    for (Position p : visited) {
      plants[p.x][p.y] = -1;  // area of field of plants
    }
    int sides = 0;  // number of sides (part 2)
    sides += calculateSides(upSides);
    sides += calculateSides(downSides);
    sides += calculateSides(rightSides);
    sides += calculateSides(leftSides);

    int[] result = new int[]{fences * visited.size(), sides * visited.size()};
    return result;
  }

  /**
   * Receives all edges for one direction (e.g. upper edges) and calculates the sides (all edges in
   * a straight line form a side) - part 2
   *
   * @param edges map of all edges for one direction (e.g. up)
   * @return number of sides
   */
  private static int calculateSides(Map<Integer, List<Integer>> edges) {
    int sides = 0;
    for (List<Integer> list : edges.values()) { // for all rows/columns
      int[] arr = list.stream().mapToInt(a -> a).toArray();
      Arrays.sort(arr); // sort list
      sides++;
      for (int i = 1; i < arr.length; i++) {
        if (Math.abs(arr[i - 1] - arr[i]) != 1) { // if not neighboring
          sides++;  // new side
        } else {
        }
      }
    }
    return sides;
  }
}