package year2025;

import java.util.List;
import util.Conv;
import util.Solution;

public class Day1 extends Solution {

  private List<String[]> moves;

  @Override
  public void setupPart1() {
    moves = Conv.split(input, 1);
  }

  @Override
  public void setupPart2() {
    moves = Conv.split(input, 1);
  }

  @Override
  public void solvePart1() {
    int pos = 50;
    int count = 0;

    for (String[] move : moves) {
      int dir = move[0].equals("L") ? -1 : 1;
      pos = (pos + Integer.parseInt(move[1]) * dir) % 100;

      if (pos == 0) {
        count++;
      }
    }

    System.out.println(count);
  }

  @Override
  public void solvePart2() {
    int pos = 50;
    int count = 0;

    for (String[] move : moves) {
      int dir = move[0].equals("L") ? -1 : 1;
      int oldPos = pos;
      pos = pos + Integer.parseInt(move[1]) * dir;

      if ((dir == 1 && oldPos < 0 && pos >= 0) || oldPos > 0 && pos <= 0) {
        count++;  // cross 0 at least once
      }
      count += (pos / 100) * dir; // cross multiple times

      pos = pos % 100;
      pos = (pos < 0) ? 100 + pos : pos;
    }

    System.out.println(count);
  }

  public static void main(String[] args) {
    new Day1().solve();
  }
}
