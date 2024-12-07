package year2024.day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day2 {

  static int safeCount = 0;
  static int safeCountImproved = 0;

  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    readInput("src/main/java/year2024/day2/input.txt", false);
    long end1 = System.currentTimeMillis();
    readInput("src/main/java/year2024/day2/input.txt", true);
    long end2 = System.currentTimeMillis();

    System.out.println("Number of safe reports: " + safeCount);
    System.out.println("Number of improved safe reports: " + safeCountImproved);
    System.out.println("Part 1: " + (end1 - start) / 1000.0 + "ms");
    System.out.println("Part 1: " + (end2 - end1) / 1000.0 + "ms");
  }

  /**
   * Reads the input file, converts it to lists of integers and counts the safe reports
   *
   * @param file filepath
   */
  private static void readInput(String file, boolean part2) {
    try {
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);

      String line = br.readLine();
      List<Integer> report;
      String[] levels;
      while (line != null) {
        report = new ArrayList<>();
        levels = line.split(" ");
        for (String level : levels) {
          report.add(Integer.parseInt(level));  // line to list of integers
        }
        if (!part2 && checkSafety(report)) {  // safe without removing any level
          safeCount++;
        } else if (part2) {
          List<Integer> tmp;
          for (int i = 0; i < report.size(); i++) {
            tmp = new ArrayList<>(report);
            tmp.remove(i);
            if (checkSafety(tmp)) { // safe by removing one level ("Problem Dampener")
              safeCountImproved++;
              break;
            }
          }
        }
        line = br.readLine();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * checks whether a given list of integers is safe<br> a report is safe when it is strictly
   * increasing or strictly decreasing, but never by more than 3
   *
   * @param report list of integer values (a report)
   * @return true if the report is "safe"
   */
  private static boolean checkSafety(List<Integer> report) {
    boolean inc = false, dec = false;
    for (int i = 1; i < report.size(); i++) {
      int diff = report.get(i - 1) - report.get(i);
      if (diff > 3 || diff < -3 || diff == 0) {
        return false;
      }
      if (diff > 0) {
        dec = true;
      } else {
        inc = true;
      }
    }
    if (inc && dec) { // not strictly monotonous
      return false;
    }
    return true;
  }
}
