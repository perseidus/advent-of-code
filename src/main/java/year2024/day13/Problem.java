package year2024.day13;

/**
 * Class represents a machine claw problem (effects of button presses and position of prize)
 */
public class Problem {

  long aButtonX, aButtonY;  // move after pressing button 'A'
  long bButtonX, bButtonY;  // move after pressing button 'B'
  long prizeX, prizeY;  // position of prize

  public Problem(long aButtonX, long aButtonY, long bButtonX, long bButtonY, long prizeX,
      long prizeY) {
    this.aButtonX = aButtonX;
    this.aButtonY = aButtonY;
    this.bButtonX = bButtonX;
    this.bButtonY = bButtonY;
    this.prizeX = prizeX;
    this.prizeY = prizeY;
  }

  public boolean isSolution(long aPressed, long bPressed) {
    return ((aPressed * aButtonX) + (bPressed * bButtonX)) == prizeX
        && ((aPressed * aButtonY) + (bPressed * bButtonY)) == prizeY;
  }
}