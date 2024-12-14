package year2024.day14;

/**
 * Class represents a robot (X and Y position, move in X and Y direction after 1 second, new
 * position after moves)
 */
public class Robot {

  int x, y;
  int moveX, moveY;
  int newX, newY;

  public Robot(int x, int y, int moveX, int moveY) {
    this.x = x;
    this.y = y;
    this.moveX = moveX;
    this.moveY = moveY;
  }
}