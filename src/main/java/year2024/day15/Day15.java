package year2024.day15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Day15 {

  static StringBuffer moves;  // sequence of moves for robot
  static int startingRow, startingCol;  // starting position of robot
  static Stack<DoubleBox> doubleBoxStack; // storing boxes that could potentially be moved (part 2)

  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    char[][] warehouse = readInput("src/main/java/year2024/day15/input.txt", false);
    int coordinates = simulateMoves(warehouse, startingRow, startingCol);
    long end1 = System.currentTimeMillis();
    System.out.println("Sum of box coordinate scores: " + coordinates);

    warehouse = readInput("src/main/java/year2024/day15/input.txt", true);
    coordinates = simulateMoves(warehouse, startingRow, startingCol);
    long end2 = System.currentTimeMillis();
    System.out.println("Sum of double-box coordinate scores: " + coordinates);

    System.out.println("Part 1: " + (end1 - start) / 1000.0 + "s");
    System.out.println("Part 2: " + (end2 - end1) / 1000.0 + "s");
  }

  /**
   * reads the input file
   *
   * @param file       filepath
   * @param doubleSize if the warehouse and its objects should be doubled in size (part 2)
   * @return grid of warehouse
   */
  private static char[][] readInput(String file, boolean doubleSize) {
    List<char[]> gridRow = new LinkedList<>();
    String input;
    moves = new StringBuffer();

    try {
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);

      int i = 0;
      input = br.readLine();
      while (input != null) {
        if (input.contains("#")) {  // input is warehouse grid
          if (doubleSize) {   // everything gets doubled in size (part 2)
            input = input.replaceAll("#", "##");
            input = input.replaceAll("O", "[]");
            input = input.replaceAll("\\.", "..");
            input = input.replaceAll("@", "@.");
          }

          if (input.contains("@")) {  // starting position found
            startingRow = i;
            startingCol = input.indexOf('@');
          }
          i++;
          gridRow.add(input.toCharArray());
        } else if (input.contains("v")) { // input is sequence of moves
          moves.append(input);
        }

        input = br.readLine();
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    char[][] output = new char[gridRow.size()][gridRow.get(0).length];
    for (int i = 0; i < gridRow.size(); i++) {
      output[i] = gridRow.get(i);
    }
    return output;
  }

  /**
   * Simulates all moves for the given grid (warehouse) and returns the sum of all coordinate scores
   * (100 * xPos + yPos) of the boxes.
   *
   * @param warehouse grid of warehouse (borders, boxes and robot)
   * @param row       row index of robot
   * @param col       column index of robot
   * @return sum of coordinate score of all boxes
   */
  private static int simulateMoves(char[][] warehouse, int row, int col) {
    boolean moved;
    int xOff = 0, yOff = 0;
    for (int i = 0; i < moves.length(); i++) {
      if (moves.charAt(i) == '<') { // move left
        xOff = 0;
        yOff = -1;
      } else if (moves.charAt(i) == '>') {  // move right
        xOff = 0;
        yOff = 1;
      } else if (moves.charAt(i) == '^') {  // move up
        xOff = -1;
        yOff = 0;
      } else if (moves.charAt(i) == 'v') {  // move down
        xOff = 1;
        yOff = 0;
      } else {
        continue;
      }
      moved = moveRobot(warehouse, row, col, xOff, yOff);
      if (moved) {  // move was possible -> update robot position
        row += xOff;
        col += yOff;
      }
    }

    int coordinateSum = 0;
    for (int i = 0; i < warehouse.length; i++) {
      for (int j = 0; j < warehouse[i].length; j++) {
        if (warehouse[i][j] == 'O' || warehouse[i][j] == '[') { // if box
          coordinateSum += (100 * i) + j; // coordinate score = 100 * row + col
        }
      }
    }
    return coordinateSum; // sum of all coordinate scores of boxes
  }

  /**
   * Given a grid, a starting position and a direction, the robot tries to move in a direction,
   * pushing boxes if necessary and possible, and returns whether the move was successful.
   *
   * @param warehouse grid of warehouse (borders, boxes and robot)
   * @param row       row index of robot
   * @param col       column index of robot
   * @param xOff      direction of robot vertically (either 1, -1 or 0, if xOff!=0)
   * @param yOff      direction of robot horizontally (either 1, -1 or 0, if yOff!=0)
   * @return true if the robot could move one square in the given direction, false else
   */
  private static boolean moveRobot(char[][] warehouse, int row, int col, int xOff, int yOff) {
    char moveTo = warehouse[row + xOff][col + yOff];
    if (moveTo == '#') {  // border -> don't move
      return false;
    }
    if (moveTo == '.') { // free space -> move
      warehouse[row][col] = '.';
      warehouse[row + xOff][col + yOff] = '@';
      return true;
    }
    if (moveTo == 'O') {  // box -> pushable?
      int boxX = row + xOff;
      int boxY = col + yOff;
      boolean boxesMoveable = false;
      while (warehouse[boxX][boxY] != '#') {  // while box(es) between border and robot
        if (warehouse[boxX][boxY] == '.') { // find free space
          boxesMoveable = true;
          break;
        }
        boxX += xOff;
        boxY += yOff;
      }

      if (boxesMoveable) {  // free space -> boxes pushable -> push
        warehouse[row][col] = '.';  // move robot from
        warehouse[row + xOff][col + yOff] = '@';  // move robot to
        warehouse[boxX][boxY] = 'O';  // push box to
        return true;
      }
      return false; // boxes unmovable -> don't move
    }

    // boxes are two spaces wide, [] instead of O (part 2)
    doubleBoxStack = new Stack<>();
    return moveBoxes(warehouse, row, col, xOff, yOff, true);  // move (+ move boxes) if possible
  }

  /**
   * The robot has to push double boxes to move in a certain direction, this method handles their
   * behavior for part 2, returns whether the move was successful.
   *
   * @param warehouse grid of warehouse (borders, boxes and robot)
   * @param row       row index of robot
   * @param col       column index of robot
   * @param xOff      direction of robot vertically (either 1, -1 or 0, if xOff!=0)
   * @param yOff      direction of robot horizontally (either 1, -1 or 0, if yOff!=0)
   * @param firstCall true, if first method call (not recursive), false if called recursively
   * @return true if the robot could move one square in the given direction, false else
   */
  private static boolean moveBoxes(char[][] warehouse, int row, int col, int xOff, int yOff,
      boolean firstCall) {
    // HORIZONTAL
    if (xOff == 0 && warehouse[row][col + yOff] == '#') { // border -> don't push box
      return false;
    } else if (xOff == 0 && warehouse[row][col + yOff] == '.') {  // free space -> push box
      warehouse[row][col + yOff] = warehouse[row][col];
      warehouse[row][col] = '.';
      return true;
    } else if (xOff == 0) { // horizontal recursive free space?
      if (moveBoxes(warehouse, row, col + yOff, xOff, yOff, false)) {
        warehouse[row][col + yOff] = warehouse[row][col];
        warehouse[row][col] = '.';
        return true;
      } else {
        return false;
      }
    }

    // VERTICAL
    boolean box1Moveable = false;
    boolean box2Moveable = false;
    if (yOff == 0 && warehouse[row + xOff][col] == '#') { // border -> don't push box
      return false;
    } else if (yOff == 0 && warehouse[row + xOff][col] == '.') {  // free space -> push box
      return true;
    } else if (yOff == 0 && warehouse[row + xOff][col] == '[') {  // new box -> recursive call
      DoubleBox box = new DoubleBox(row + xOff, col, row + xOff, col + 1);
      doubleBoxStack.push(box);
      warehouse[box.leftX][box.leftY] = '.';  // set to free space temporarily
      warehouse[box.rightX][box.rightY] = '.';
      box1Moveable = moveBoxes(warehouse, row + xOff, col, xOff, yOff, false);
      box2Moveable = moveBoxes(warehouse, row + xOff, col + 1, xOff, yOff, false);
    } else if (yOff == 0 && warehouse[row + xOff][col] == ']') {  // new box -> recursive call
      DoubleBox box = new DoubleBox(row + xOff, col - 1, row + xOff, col);
      doubleBoxStack.push(box);
      warehouse[box.leftX][box.leftY] = '.';  // set to free space temporarily
      warehouse[box.rightX][box.rightY] = '.';
      box1Moveable = moveBoxes(warehouse, row + xOff, col - 1, xOff, yOff, false);
      box2Moveable = moveBoxes(warehouse, row + xOff, col, xOff, yOff, false);
    }
    if (!box1Moveable || !box2Moveable) { // one of the boxes cannot be pushed -> don't move
      while (firstCall && !doubleBoxStack.isEmpty()) {
        DoubleBox box = doubleBoxStack.pop();
        warehouse[box.leftX][box.leftY] = '[';  // reset all boxes
        warehouse[box.rightX][box.rightY] = ']';  // reset all boxes
      }
      return false;
    } else if (!firstCall) {  // all boxes can be pushed -> back propagate
      return true;
    } else { // back propagated & all boxes pushable -> push all boxes + move robot
      while (!doubleBoxStack.isEmpty()) {
        DoubleBox box = doubleBoxStack.pop();
        warehouse[box.leftX + xOff][box.leftY] = '['; // move boxes
        warehouse[box.rightX + xOff][box.rightY] = ']'; // move boxes
      }
      warehouse[row + xOff][col] = '@'; // move robot
      warehouse[row][col] = '.';
      return true;
    }
  }
}