package year2024.day14;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class Day14 {

  static int width = 101;
  static int height = 103;

  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    List<Robot> robots = readInput("src/main/java/year2024/day14/input.txt");
    int safetyFactor = simulateMovement(robots, 100);
    long end = System.currentTimeMillis();
    System.out.println("Safety factor: " + safetyFactor);

    List<Integer> seconds = new ArrayList<>();
    seconds.add(8149);  // found trough manual labor
    generatesPNGForAll(robots, seconds);

    System.out.println("Part 1: " + (end - start) / 1000.0 + "s");
    System.out.println("Part 2: found by manually looking through pictures for all generated PNG"
        + " files, approximately 10min");
  }

  /**
   * reads the input file
   *
   * @param file filepath
   * @return list of all robots
   */
  private static List<Robot> readInput(String file) {
    List<Robot> robots = new ArrayList<>();
    String input;
    String a[];
    int x, y, moveX, moveY;

    try {
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);

      input = br.readLine();
      while (input != null) {
        a = input.split(" ");
        x = Integer.parseInt(a[0].substring(2, a[0].indexOf(","))); // starting X pos
        y = Integer.parseInt(a[0].substring(a[0].indexOf(",") + 1));  // starting Y pos
        moveX = Integer.parseInt(a[1].substring(2, a[1].indexOf(","))); // moves in X dir
        moveY = Integer.parseInt(a[1].substring(a[1].indexOf(",") + 1));  // moves in Y dir
        robots.add(new Robot(x, y, moveX, moveY));

        input = br.readLine();
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return robots;
  }

  /**
   * Simulates position of all robots after a given number of seconds/moves and returns the safety
   * factor (part 1). If a robot leaves one side of the grid, it teleports to the opposite site.
   *
   * @param robots  list of all robots
   * @param seconds number of seconds/moves to simulate
   * @return safety factor (number of robots in each quadrant multiplied with each other)
   */
  private static int simulateMovement(List<Robot> robots, int seconds) {
    int q1 = 0, q2 = 0, q3 = 0, q4 = 0;
    int halfWidth = width / 2;
    int halfHeight = height / 2;
    int newX, newY;

    for (Robot robot : robots) {  // for each robot
      newX = (robot.x + (robot.moveX * seconds)) % width; // new X pos with residual class width
      newY = (robot.y + (robot.moveY * seconds)) % height;  // new Y pos with residual class height
      if (newX < 0) { // leaves left border -> teleports to right border
        newX = width + newX;
      }
      if (newY < 0) { // leaves upper border -> teleports to lower border
        newY = height + newY;
      }

      if (newX > halfWidth && newY < halfHeight) {  // robot in quadrant 1
        q1++;
      } else if (newX < halfWidth && newY < halfHeight) { // robot in quadrant 2
        q2++;
      } else if (newX < halfWidth && newY > halfHeight) { // robot in quadrant 3
        q3++;
      } else if (newX > halfWidth && newY > halfHeight) { // robot in quadrant 4
        q4++;
      }
      robot.newX = newX;
      robot.newY = newY;
    }
    return q1 * q2 * q3 * q4; // safety factor
  }

  /**
   * Receives a list of integers (seconds / number of moves) and generates PNG representations for
   * all of them. By manually looking through all of them I found the right one (number 8149), that
   * shows the robots in a Christmas tree formation (part 2 of day 14). <br> The main method calls
   * this method for only this value.
   *
   * @param robots  list of all robots
   * @param seconds list of seconds/values to generate PNG files to
   */
  private static void generatesPNGForAll(List<Robot> robots, List<Integer> seconds) {
    BufferedImage img;

    for (int i : seconds) { // for all values
      simulateMovement(robots, i);  // simulate robot position after i moves
      char[][] grid = new char[width][height];
      for (Robot robot : robots) {
        grid[robot.newX][robot.newY] = '|';
      }

      img = new BufferedImage(grid.length, grid[0].length, BufferedImage.TYPE_BYTE_GRAY);
      int color;
      for (int j = 0; j < grid.length; j++) {
        for (int k = 0; k < grid[j].length; k++) {    // white pixel for every robot
          color = (grid[j][k] == '|') ? 0xFF : 0x00;  // black pixel else
          img.getRaster().setSample(j, k, 0, color);
        }
      }

      try {
        File file = new File("src/main/java/year2024/day14/output" + i + ".png");
        ImageIO.write(img, "png", file);  // write PNG file
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}