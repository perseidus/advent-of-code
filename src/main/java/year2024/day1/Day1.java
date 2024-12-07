package year2024.day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day1 {

  static List<Integer> list1 = new ArrayList<>();
  static List<Integer> list2 = new ArrayList<>();

  public static void main(String[] args) {
    long start = System.currentTimeMillis();

    splitInput("src/main/java/year2024/day1/input.txt");
    list1 = mergeSort(list1);
    list2 = mergeSort(list2);

    System.out.println("Total distance = " + getTotalDistance(list1, list2));
    long end1 = System.currentTimeMillis();
    System.out.println("Similarity score = " + getSimilarity(list1, list2));
    long end2 = System.currentTimeMillis();

    System.out.println("Part 1: " + (end1 - start) / 1000.0 + "ms");
    System.out.println("Part 2: " + (end2 - end1) / 1000.0 + "ms");
  }

  /**
   * Splits input file into two lists of integers
   *
   * @param file filepath
   */
  private static void splitInput(String file) {
    try {
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);

      String line = br.readLine();
      while (line != null) {
        list1.add(Integer.parseInt(line.substring(0, 5)));
        list2.add(Integer.parseInt(line.substring(8)));
        line = br.readLine();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Sorts a list of integers using merge sort
   *
   * @param list list to sort
   * @return sorted list
   */
  private static List<Integer> mergeSort(List<Integer> list) {
    if (list.size() <= 1) {
      return list;
    } else {
      List<Integer> left = mergeSort(new ArrayList<>(list.subList(0, list.size() / 2)));
      List<Integer> right = mergeSort(new ArrayList<>(list.subList(list.size() / 2, list.size())));
      return merge(left, right);
    }
  }

  /**
   * Merges two lists following merge sort procedure
   *
   * @param left  list1
   * @param right list2
   * @return merged list
   */
  private static List<Integer> merge(List<Integer> left, List<Integer> right) {
    List<Integer> merged = new ArrayList<>();
    while (!left.isEmpty() && !right.isEmpty()) {
      if (left.get(0) <= right.get(0)) {
        merged.add(left.get(0));
        left.remove(0);
      } else {
        merged.add(right.get(0));
        right.remove(0);
      }
    }
    while (!left.isEmpty()) {
      merged.add(left.get(0));
      left.remove(0);
    }
    while (!right.isEmpty()) {
      merged.add(right.get(0));
      right.remove(0);
    }
    return merged;
  }

  /**
   * Calculates the total sum of the difference of two values at the same index
   *
   * @param list1 first list
   * @param list2 second list
   * @return total distance of two lists
   */
  private static int getTotalDistance(List<Integer> list1, List<Integer> list2) {
    int totalDistance = 0;
    for (int i = 0; i < list1.size(); i++) {
      totalDistance += Math.abs(list1.get(i) - list2.get(i));
    }
    return totalDistance;
  }

  /**
   * Calculates the sum for all values in list 1: value * occurrences in list2
   *
   * @param list1 first list
   * @param list2 second list
   * @return similarity score
   */
  private static int getSimilarity(List<Integer> list1, List<Integer> list2) {
    int similarity = 0;
    for (int i = 0; i < list1.size(); i++) {
      for (int j = 0; j < list2.size(); j++) {
        if (list1.get(i).equals(list2.get(j))) {
          int count = 1;
          while (list1.get(i).equals(list2.get(++j))) {
            count++;
          }
          similarity += list1.get(i) * count;
          break;
        }
      }
    }
    return similarity;
  }
}
