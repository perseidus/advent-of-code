package util;

import java.util.LinkedList;
import java.util.List;

public class Conv {

  public static List<String[]> split(List<String> lines, String sep) {
    List<String[]> result = new LinkedList<>();

    for (String line : lines) {
      result.add(line.split(sep));
    }
    return result;
  }

  public static List<String[]> split(List<String> lines, int... sep) {
    List<String[]> result = new LinkedList<>();

    for (String line : lines) {
      String[] tmp = new String[sep.length + 1];
      int curr = 0;

      for (int i = 0; i < sep.length; i++) {
        tmp[i] = line.substring(curr, sep[i]);
        curr = sep[i];
      }
      tmp[tmp.length - 1] = line.substring(curr);

      result.add(tmp);
    }
    return result;
  }

  public static List<Integer> toInt(List<String> lines) {
    List<Integer> result = new LinkedList<>();
    for (String line : lines) {
      result.add(Integer.parseInt(line));
    }
    return result;
  }

  public static List<int[]> toIntArray(List<String[]> lines) {
    List<int[]> result = new LinkedList<>();

    for (String[] line : lines) {
      int[] tmp = new int[line.length];
      for (int i = 0; i < line.length; i++) {
        tmp[i] = Integer.parseInt(line[i]);
      }
      result.add(tmp);
    }
    return result;
  }

  public static List<Double> toDouble(List<String> lines) {
    List<Double> result = new LinkedList<>();
    for (String line : lines) {
      result.add(Double.parseDouble(line));
    }
    return result;
  }

  public static List<double[]> toDoubleArray(List<String[]> lines) {
    List<double[]> result = new LinkedList<>();

    for (String[] line : lines) {
      double[] tmp = new double[line.length];
      for (int i = 0; i < line.length; i++) {
        tmp[i] = Double.parseDouble(line[i]);
      }
      result.add(tmp);
    }
    return result;
  }

  public static List<Long> toLong(List<String> lines) {
    List<Long> result = new LinkedList<>();
    for (String line : lines) {
      result.add(Long.parseLong(line));
    }
    return result;
  }

  public static List<long[]> toLongArray(List<String[]> lines) {
    List<long[]> result = new LinkedList<>();

    for (String[] line : lines) {
      long[] tmp = new long[line.length];
      for (int i = 0; i < line.length; i++) {
        tmp[i] = Long.parseLong(line[i]);
      }
      result.add(tmp);
    }
    return result;
  }

  public static List<Character> toChar(List<String> lines) {
    List<Character> result = new LinkedList<>();
    for (String line : lines) {
      result.add(line.charAt(0));
    }
    return result;
  }

  public static List<char[]> toCharArray(List<String[]> lines) {
    List<char[]> result = new LinkedList<>();

    for (String[] line : lines) {
      char[] tmp = new char[line.length];
      for (int i = 0; i < line.length; i++) {
        tmp[i] = line[i].charAt(0);
      }
      result.add(tmp);
    }
    return result;
  }

  public static int[][] intMatrix(List<String> lines) {
    int[][] result = new int[lines.size()][lines.get(0).length()];

    for (int i = 0; i < lines.size(); i++) {
      for (int j = 0; j < lines.get(i).length(); j++) {
        result[i][j] = Integer.parseInt(lines.get(i).charAt(j) + "");
      }
    }
    return result;
  }

  public static char[][] charMatrix(List<String> lines) {
    char[][] result = new char[lines.size()][lines.get(0).length()];

    for (int i = 0; i < lines.size(); i++) {
      for (int j = 0; j < lines.get(i).length(); j++) {
        result[i][j] = lines.get(i).charAt(j);
      }
    }
    return result;
  }

}
