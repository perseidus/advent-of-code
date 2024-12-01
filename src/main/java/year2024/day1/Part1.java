package year2024.day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

  static List<Integer> list1 = new ArrayList<>();
  static List<Integer> list2 = new ArrayList<>();

  public static void main(String[] args) {
    splitInput("src/main/java/year2024/day1/input.txt");
    list1 = mergeSort(list1);
    list2 = mergeSort(list2);
    System.out.println("Total distance = " + getTotalDistance(list1, list2));
  }

  private static void splitInput(String file) {
    FileReader fr = null;
    BufferedReader br = null;

    try {
      fr = new FileReader(file);
      br = new BufferedReader(fr);

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

  private static List<Integer> mergeSort(List<Integer> list) {
    if (list.size() <= 1) {
      return list;
    } else {
      List<Integer> left = new ArrayList<>(list.subList(0, list.size() / 2));
      List<Integer> right = new ArrayList<>(list.subList(list.size() / 2, list.size()));
      return merge(left, right);
    }
  }

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

  private static int getTotalDistance(List<Integer> list1, List<Integer> list2) {
    int totalDistance = 0;
    for (int i = 0; i < list1.size(); i++) {
      totalDistance += Math.abs(list1.get(i) - list2.get(i));
    }
    return totalDistance;
  }
}
