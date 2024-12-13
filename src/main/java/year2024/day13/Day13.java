package year2024.day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Day13 {

  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    List<Problem> problems = readInput("src/main/java/year2024/day13/input.txt");
    long tokenCost = solveAllProblems(problems);  // part 1
    long end1 = System.currentTimeMillis();
    System.out.println("Solution to part 1: " + tokenCost);

    for (Problem problem : problems) {
      problem.prizeX = problem.prizeX + 10000000000000L;
      problem.prizeY = problem.prizeY + 10000000000000L;
    }
    tokenCost = solveAllProblems(problems); // part 2
    long end2 = System.currentTimeMillis();
    System.out.println("Solution to part 2: " + tokenCost);

    System.out.println("Part 1: " + (end1 - start) / 1000.0 + "s");
    System.out.println("Part 2: " + (end2 - end1) / 1000.0 + "s");
  }

  /**
   * reads the input file
   *
   * @param file filepath
   * @return list of all claw machine problems
   */
  private static List<Problem> readInput(String file) {
    List<Problem> problems = new LinkedList<>();
    String input;
    long aButtonX = 0, aButtonY = 0;
    long bButtonX = 0, bButtonY = 0;
    long prizeX, prizeY;

    try {
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);

      input = br.readLine();
      while (input != null) {
        if (input.contains("A")) {  // button 'A'
          aButtonX = Long.parseLong(input.substring(input.indexOf('X') + 2, input.indexOf(',')));
          aButtonY = Long.parseLong(input.substring(input.indexOf('Y') + 2));
        } else if (input.contains("B")) { // button 'B'
          bButtonX = Long.parseLong(input.substring(input.indexOf('X') + 2, input.indexOf(',')));
          bButtonY = Long.parseLong(input.substring(input.indexOf('Y') + 2));
        } else if (input.contains("Prize")) { // position of prize
          prizeX = Long.parseLong(input.substring(input.indexOf('X') + 2, input.indexOf(',')));
          prizeY = Long.parseLong(input.substring(input.indexOf('Y') + 2));
          problems.add(new Problem(aButtonX, aButtonY, bButtonX, bButtonY, prizeX, prizeY));
        }
        input = br.readLine();
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return problems;
  }

  /**
   * Solves all given claw machine problems (if they are solvable) and returns the total number of
   * tokens that are required.
   *
   * @param problems list of problems
   * @return total number of required tokens for all solvable problems
   */
  private static long solveAllProblems(List<Problem> problems) {
    long tokens = 0;
    long solution;
    for (Problem problem : problems) {  // for all problems
      solution = solveProblem(problem);
      if (solution > 0) {
        tokens += solution;
      }
    }
    return tokens;
  }

  /**
   * Solves a claw machine problem, returns the number of required tokens. Starting equations: <br>
   * X = A * weightX_A + B * weightX_B <br>
   * Y = A * weightY_A + B * weightY_B <br>
   * are used for this method, with weightX_A = e, weightX_B = f, weightY_A = d and weightY_B = c
   *
   * @param problem a claw machine problem
   * @return number of required tokens if problem is solvable, -1 else
   */
  private static long solveProblem(Problem problem) {
    long x = problem.prizeX, y = problem.prizeY;
    long c = problem.bButtonY;
    long d = problem.aButtonY;
    long e = problem.aButtonX;
    long f = problem.bButtonX;

    long b = ((e * y) - (d * x)) / ((e * c) - (d * f)); // number of 'B' button presses
    long a = (y - (b * c)) / d; // number of 'A' button presses

    if (problem.isSolution(a, b)) { // only true if there are whole-number solutions to a and b
      return a * 3 + b;
    }
    return -1;
  }
}