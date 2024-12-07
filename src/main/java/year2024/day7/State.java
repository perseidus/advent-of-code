package year2024.day7;

import java.util.Arrays;

/**
 * State class, mainly for elimination of duplicates
 */
public class State {

  private int[] operators;

  public State(int[] operators) {
    this.operators = operators;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof State state)) {
      return false;
    }

    return Arrays.equals(operators, state.operators);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(operators);
  }

  public int[] getOperators() {
    return operators;
  }
}
