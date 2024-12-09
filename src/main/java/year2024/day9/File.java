package year2024.day9;

/**
 * Class represents one block of data (one file)
 */
public class File {

  int start;
  int end;
  int size;
  char id;

  public File(int start, int end, int size, char id) {
    this.start = start;
    this.end = end;
    this.size = size;
    this.id = id;
  }
}
