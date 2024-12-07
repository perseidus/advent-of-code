package year2024.day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day7 {

  static List<long[]> equations = new ArrayList<>();
  static long result = 0;

  public static void main(String[] args) {
    readInput("src/main/java/year2024/day7/input.txt");
    for (long[] equation : equations) {
      breadthFirstSearch(equation, false);  // part 1
    }
    System.out.println("Sum of all valid equations (* and +): " + result);
    result = 0;
    for (long[] equation : equations) {
      breadthFirstSearch(equation, true);   // part 2
    }
    System.out.println("Sum of all valid equations (*, + and ||): " + result);
  }

  /**
   * reads the input file, a row parses to an array of integers
   *
   * @param file filepath
   */
  private static void readInput(String file) {
    try {
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);

      String line = br.readLine();
      while (line != null) {
        equations.add(Arrays.stream(line.split(" |: "))
            .mapToLong(Long::parseLong)
            .toArray());
        line = br.readLine();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Searches for an assignment of operators, so that the equation is true.<br> Default operators
   * are * and +, || (concatenation) can be enabled with enableConcat (part 2). <br> Increases the
   * result if the equation can be true.
   *
   * @param equation     equation to check, first value is desired result
   * @param enableConcat if true, concatenation will be a possible operator (part 2)
   */
  private static void breadthFirstSearch(long[] equation, boolean enableConcat) {
    Set<State> visited = new HashSet<>();   // set of visited assignments for eliminating duplicates
    List<State> todo = new ArrayList<>();   // FIFO list for bfs
    todo.add(new State(new int[equation.length - 2]));
    visited.add(todo.get(0));

    while (!todo.isEmpty()) { // no solution found
      int[] operators = todo.get(0).getOperators();
      todo.remove(0);
      if (isSolution(equation, operators)) {
        result += equation[0];  // solution found
        return;
      } else {  // add all new child nodes
        List<State> children = expand(operators, visited, enableConcat);
        todo.addAll(children);
      }
    }
    // no solution
  }

  /**
   * Checks if an equation is true with some currently assigned operators.
   *
   * @param equation     the equation to check, first value is desired result
   * @param operators    an array of operators (0 equals +, 1 equals *, 2 equals ||)
   * @return true if the equation is solved with the current operators
   */
  private static boolean isSolution(long[] equation, int[] operators) {
    long goal = equation[0], result = equation[1];
    for (int i = 2; i < equation.length; i++) {
      if (operators[i - 2] == 0) {
        result += equation[i];
      } else if (operators[i - 2] == 1) {
        result *= equation[i];
      } else {
        result = Long.parseLong(result + "" + equation[i]);
      }
    }
    return result == goal;  // true if solution found
  }

  /**
   * Expands the current operation assignment and returns all new children (one changed operator
   * each), eliminates duplicates.
   *
   * @param operators    an array of operators (0 equals +, 1 equals *, 2 equals ||)
   * @param visited      visited assignments of operators
   * @param enableConcat if true concatenation is enabled
   * @return a list of expanded child nodes
   */
  public static List<State> expand(int[] operators, Set<State> visited, boolean enableConcat) {
    List<State> children = new ArrayList<>();
    int[] tmp1, tmp2;
    State state1, state2;

    for (int i = 0; i < operators.length; i++) {
      tmp1 = Arrays.copyOf(operators, operators.length);
      tmp2 = Arrays.copyOf(operators, operators.length);
      tmp1[i] = 1;  // change one operator to *
      tmp2[i] = 2;  // change one operator to ||
      state1 = new State(tmp1);
      state2 = new State(tmp2);
      if (!visited.contains(state1)) { // does not add duplicates
        children.add(state1);
        visited.add(state1);
      }
      if (enableConcat && !visited.contains(state2)) {  // does not add duplicates
        children.add(state2);
        visited.add(state2);
      }
    }
    return children;
  }
}