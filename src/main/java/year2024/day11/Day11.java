package year2024.day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day11 {

  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    List<Long> stones = readInput("src/main/java/year2024/day11/input.txt");
    System.out.println("Stones after blinking 25 times: " + blink(stones, 25));
    long end1 = System.currentTimeMillis();
    System.out.println("Stones after blinking 75 times: " + blink(stones, 75));
    long end2 = System.currentTimeMillis();

    System.out.println("Part 1: " + (end1 - start) / 1000.0 + "s");
    System.out.println("Part 2: " + (end2 - end1) / 1000.0 + "s");
  }

  /**
   * reads the input file
   *
   * @param file filepath
   * @return 2-dimensional int array of topographic map
   */
  private static List<Long> readInput(String file) {
    List<Long> stones;
    String input;
    try {
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);

      input = br.readLine();
      stones = Arrays.stream(input.split(" "))
          .map(Long::valueOf)
          .collect(Collectors.toList());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return stones;
  }

  /**
   * Receives an input of values (stones) and a number of blinks, and calculates the number of
   * stones after the blinks (according to the rules)
   *
   * @param stones input stones
   * @param blinks number of blinks
   * @return number of stones
   */
  private static long blink(List<Long> stones, int blinks) {
    Map<Long, Long> todo = new HashMap<>();
    for (long stone : stones) { // store initial stones
      todo.put(stone, 1L);
    }

    for (int i = 0; i < blinks; i++) {  // for every blink
      Map<Long, Long> newTodo = new HashMap<>();
      for (long stone : todo.keySet()) {  // for every current stone
        for (long newStone : generateStones(stone)) { // generate all new stones (rules)
          if (!newTodo.containsKey(newStone)) {
            newTodo.put(newStone, todo.get(stone)); // store new stones
          } else {
            newTodo.put(newStone, newTodo.get(newStone) + todo.get(stone)); // or update seen stones
          }
        }
      }
      todo = newTodo; // update the current stones
    }

    long result = 0;
    for (long count : todo.values()) {  // count all stones
      result += count;
    }
    return result;
  }

  /**
   * Receives one stone and changes it according to the rules (one blink)<br> 0 -> 1, <br>even
   * number of digits -> split, <br> uneven number of digits -> times 2024
   *
   * @param stone value of the stone
   * @return one or two values (stones) according to the rules
   */
  private static List<Long> generateStones(long stone) {
    List<Long> stones = new ArrayList<>();
    String stoneAsString;
    if (stone == 0) { // 0 -> 1
      stones.add(1L);
    } else if (((int) Math.log10(stone) + 1) % 2 == 0) { // split digits
      stoneAsString = Long.toString(stone);
      stones.add(Long.valueOf(stoneAsString.substring(0, stoneAsString.length() / 2)));
      stones.add(Long.valueOf(stoneAsString.substring(stoneAsString.length() / 2)));
    } else {
      stones.add(stone * 2024); // * 2024
    }
    return stones;
  }
}