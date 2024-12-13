package year2024.day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import year2024.day8.Position;

public class Day10 {

  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    int[][] map = readInput("src/main/java/year2024/day10/input.txt");
    calculateAllScores(map);
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
    List<int[]> rows = new ArrayList<int[]>();

    String input;
    try {
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);

      input = br.readLine();
      while (input != null) {
        rows.add(Arrays.stream(input.split(""))
            .mapToInt(Integer::parseInt)
            .toArray());
        input = br.readLine();
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    int[][] output = new int[rows.size()][rows.get(0).length];
    for (int i = 0; i < rows.size(); i++) {
      output[i] = rows.get(i);
    }
    return output;
  }

  /**
   * Calculates the sum of all trailhead <i>scores</i> and prints it (part 1). A trailhead is a
   * starting point 0, with at least one hiking trail to a peak (path to a 9). A <i>score</i> is the
   * number of distinct peaks that can be reached from a trailhead. <br> Calculates the sum of all
   * trailhead <i>ratings</i> and prints it (part 2). A <i>rating</i> is the number of distinct
   * trails starting from a trailhead.
   *
   * @param map 2-dimensional int array of topographic map
   */
  private static void calculateAllScores(int[][] map) {
    int totalTrailScore = 0;
    int totalTrailRating = 0;
    Evaluation evaluation;
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[i].length; j++) {
        if (map[i][j] == 0) { // if possible trailhead
          evaluation = searchTrail(map, i, j, new Evaluation());
          totalTrailScore += evaluation.peaks.size(); // number of peaks (score, part 1)
          totalTrailRating += evaluation.trails;  // number of trails (rating, part 2)
        }
      }
    }
    System.out.println("Sum of all trailhead scores: " + totalTrailScore);
    System.out.println("Sum of all trailhead ratings: " + totalTrailRating);
  }

  /**
   * Gets called recursively to find paths from a starting point '0' to a peak '9' (in steps of
   * +1).
   * <br>Remembers and returns a set of all peaks that could be reached and the number of distinct
   * hiking trails.
   *
   * @param map  2-dimensional int array of topographic map
   * @param i    current row index
   * @param j    current column index
   * @param eval current evaluation
   * @return evaluation of a trailhead (set of peaks, number of distinct trails)
   */
  private static Evaluation searchTrail(int[][] map, int i, int j, Evaluation eval) {
    int current = map[i][j];
    Set<Position> peaks = eval.peaks;

    if (current == 9) { // reached a peak
      peaks.add(new Position(i, j));  // store peak
      eval.trails++;  // increase number of trails
      return eval;
    } else {
      if ((i > 0) && (map[i - 1][j] == current + 1)) {  // up
        searchTrail(map, i - 1, j, eval);
      }
      if ((i < map.length - 1) && (map[i + 1][j] == current + 1)) { // down
        searchTrail(map, i + 1, j, eval);
      }
      if ((j > 0) && (map[i][j - 1] == current + 1)) {  // left
        searchTrail(map, i, j - 1, eval);
      }
      if ((j < map[i].length - 1) && (map[i][j + 1] == current + 1)) { // right
        searchTrail(map, i, j + 1, eval);
      }
    }
    return eval;
  }

  /**
   * Class for storing a set of all peaks + number of distinct trails starting from a trailhead
   */
  private static class Evaluation {

    Set<Position> peaks;
    int trails;

    public Evaluation() {
      this.peaks = new HashSet<>();
      this.trails = 0;
    }
  }
}