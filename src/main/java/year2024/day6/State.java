package year2024.day6;

public class State {

  boolean up;
  boolean right;
  boolean down;
  boolean left;

  int row;
  int col;

  public State(int vertOff, int horOff, int row, int col) {
    if (vertOff > 0) {
      this.up = false;
      this.down = true;
    } else if (vertOff < 0) {
      this.up = true;
      this.down = false;
    }
    if (horOff > 0) {
      this.right = true;
      this.left = false;
    } else if (horOff < 0) {
      this.right = false;
      this.left = true;
    }
    this.row = row;
    this.col = col;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof State state)) {
      return false;
    }

    return up == state.up && right == state.right && down == state.down && left == state.left
        && row == state.row && col == state.col;
  }

  @Override
  public int hashCode() {
    int result = Boolean.hashCode(up);
    result = 31 * result + Boolean.hashCode(right);
    result = 31 * result + Boolean.hashCode(down);
    result = 31 * result + Boolean.hashCode(left);
    result = 31 * result + row;
    result = 31 * result + col;
    return result;
  }
}
