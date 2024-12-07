package year2024.day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day5 {

  // contains all values that may not come after a certain value
  static Map<Integer, Set<Integer>> beforeRules = new HashMap<Integer, Set<Integer>>();
  static int score = 0;
  static int correctedScore = 0;

  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    readInput("src/main/java/year2024/day5/input.txt", false);
    System.out.println("Sum of valid sequence middle points: " + score);
    long end1 = System.currentTimeMillis();

    readInput("src/main/java/year2024/day5/input.txt", true);
    System.out.println("Sum of corrected sequence middle points: " + correctedScore);
    long end2 = System.currentTimeMillis();

    System.out.println("Part 1: " + (end1 - start) / 1000.0 + "ms");
    System.out.println("Part 2: " + (end2 - end1) / 1000.0 + "ms");
  }

  /**
   * reads the input file, collects ordering rules and checks for ordered sequences
   *
   * @param file filepath
   */
  private static void readInput(String file, boolean part2) {
    score = 0;
    correctedScore = 0;
    try {
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);

      String line = br.readLine();
      while (line != null) {
        if (line.contains("|")) {
          addOrderingRule(line);
        } else if (line.contains(",")) {
          checkSequence(line);
        }
        line = br.readLine();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * adds an ordering rule
   *
   * @param line an ordering rule string
   */
  private static void addOrderingRule(String line) {
    int first = Integer.parseInt(line.substring(0, 2));
    int second = Integer.parseInt(line.substring(3, 5));
    if (!beforeRules.containsKey(second)) {
      beforeRules.put(second, new HashSet<Integer>());
    }
    beforeRules.get(second).add(first);
  }

  /**
   * verifies ordered sequences + sums up their center values (part 1), <br>
   * or orders them (part 2)
   *
   * @param line a sequence string
   */
  private static void checkSequence(String line) {
    int[] num = Arrays.stream(line.split(","))
        .mapToInt(Integer::parseInt)
        .toArray();

    Set<Integer> beforeSet;
    for (int i = 1; i < num.length; i++) {
      beforeSet = beforeRules.get(num[i]);
      for (int j = 0; j < i; j++) {
        if (beforeSet == null || !beforeSet.contains(num[j])) {
          naiveSort(num);
          return;
        }
      }
    }
    score += num[num.length / 2];
  }

  /**
   * sorts a sequence according to the ordering rules + sums up the center values (part 2)
   *
   * @param num the sequence array of integers
   */
  private static void naiveSort(int[] num) {
    boolean swapped = false;
    for (int i = 0; i < num.length; i++) {
      if (swapped){
        i = 0;
        swapped = false;
      }
      Set<Integer> beforeSet = beforeRules.get(num[i]);
      for (int j = i; j < num.length; j++) {
        if (beforeSet != null && beforeSet.contains(num[j])) {
          int tmp = num[j];
          num[j] = num[i];
          num[i] = tmp;
          swapped = true;
          break;
        }
      }
    }
    correctedScore += num[num.length / 2];
  }
}
