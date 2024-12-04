package year2024.day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {

  static int result = 0;
  static Pattern pattern;   // pattern for mul( , ) operations
  static boolean doDontFlag = true;
  // true for filtering with do/don't operations (part 2)
  // false for default (part 1)

  public static void main(String[] args) {
    pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
    readInput("src/main/java/year2024/day3/input.txt");
    System.out.println("Result: " + result);
  }

  /**
   * reads the input file and forwards the resulting string to the findMatches methods
   *
   * @param file filepath
   */
  private static void readInput(String file) {
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

    if (doDontFlag) {
      findMatchesLimited(sb.toString());
    } else {
      findMatches(sb.toString());
    }
  }

  /**
   * receives a string and increases the result for all mul(x,y) operations contained in it<br>
   * <pre> result = result + (x * y) for all operations
   *
   * @param input input string
   */
  private static void findMatches(String input) {
    Matcher matcher = pattern.matcher(input);
    while (matcher.find()) {
      result += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
    }
  }

  /**
   * receives a string and increases the result for all mul(x,y) operations contained in it,<br> but
   * every operation with a leading don't() and no do() between it and the operation gets filtered
   * <pre> result = result + (x * y) for all enabled operations
   *
   * @param input
   */
  private static void findMatchesLimited(String input) {
    int split;
    while (true) {
      if (input.contains("don't()")) {
        split = input.indexOf("don't()");
        findMatches(input.substring(0, split));
        input = input.substring(split + 1);
      } else {
        findMatches(input);
        break;
      }
      if (input.contains("do()")) {
        split = input.indexOf("do()");
        input = input.substring(split + 1);
      } else {
        break;
      }
    }
  }
}
