package year2024.day8;

/**
 * Position of an antenna, mainly for eliminating duplicates
 */
public class Position {

  int x;
  int y;

  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Position position)) {
      return false;
    }

    return x == position.x && y == position.y;
  }

  @Override
  public int hashCode() {
    int result = x;
    result = 31 * result + y;
    return result;
  }
}
