package year2024.day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {

  static Pattern pattern;   // pattern for mul( , ) operations
  // true for filtering with do/don't operations (part 2)
  // false for default (part 1)

  public static void main(String[] args) {
    pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
    StringBuffer sb = readInput("src/main/java/year2024/day3/input.txt");

    long start = System.currentTimeMillis();
    int result = findMatches(sb.toString());
    long end1 = System.currentTimeMillis();
    System.out.println("Result: " + result);
    result = findMatchesLimited(sb.toString());
    long end2 = System.currentTimeMillis();
    System.out.println("Result with do/don't: " + result);

    System.out.println("Part 1: " + (end1 - start) / 1000.0 + "ms");
    System.out.println("Part 2: " + (end2 - end1) / 1000.0 + "ms");
  }

  /**
   * reads the input file and forwards the resulting string to the findMatches methods
   *
   * @param file filepath
   * @return the input as stringbuffer
   */
  private static StringBuffer readInput(String file) {
    StringBuffer sb;
    try {
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);

      String line = br.readLine();
      sb = new StringBuffer(line);
      while (line != null) {
        line = br.readLine();
        sb.append(line);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return sb;
  }

  /**
   * receives a string and increases the result for all mul(x,y) operations contained in it<br>
   * <pre> result = result + (x * y) for all operations
   *
   * @param input input string
   * @return result
   */
  private static int findMatches(String input) {
    int result = 0;
    Matcher matcher = pattern.matcher(input);
    while (matcher.find()) {
      result += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
    }
    return result;
  }

  /**
   * receives a string and increases the result for all mul(x,y) operations contained in it,<br> but
   * every operation with a leading don't() and no do() between it and the operation gets filtered
   * <pre> result = result + (x * y) for all enabled operations
   *
   * @param input input string
   * @return result
   */
  private static int findMatchesLimited(String input) {
    int split;
    int result = 0;
    while (true) {
      if (input.contains("don't()")) {
        split = input.indexOf("don't()");
        result += findMatches(input.substring(0, split));
        input = input.substring(split + 1);
      } else {
        result += findMatches(input);
        return result;
      }
      if (input.contains("do()")) {
        split = input.indexOf("do()");
        input = input.substring(split + 1);
      } else {
        return result;
      }
    }
  }
}
