package year2024.day9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day9 {

  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    String input = readInput("src/main/java/year2024/day9/input.txt");
    StringBuffer blocks = dataAsBlocks(input);
    rearrangeBlocksNaive(blocks);
    System.out.println("Primitive disk sorting, result: " + calculateChecksum(blocks));
    long end1 = System.currentTimeMillis();

    blocks = dataAsBlocks(input);
    List<File> files = gatherFiles(blocks);
    rearrangeBlocksImproved(blocks, files);
    System.out.println("Advanced disk sorting, result: " + calculateChecksum(blocks));
    long end2 = System.currentTimeMillis();

    System.out.println("Part 1: " + (end1 - start) / 1000.0 + "s");
    System.out.println("Part 2: " + (end2 - end1) / 1000.0 + "s");
  }

  /**
   * reads the input file into one string
   *
   * @param file filepath
   * @return input as a string
   */
  private static String readInput(String file) {
    String input;
    try {
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);
      input = br.readLine();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return input;
  }

  /**
   * generates the block representation with '丠' (unicode 20000) as empty space
   *
   * @param input input string (e.g. '12345', 1 data, 2 empty, 3 data...)
   * @return StringBuffer of block representation (e.g. '0丠丠111丠丠丠丠22222')
   */
  private static StringBuffer dataAsBlocks(String input) {
    int spaces;
    int unicodeChar = 0;
    StringBuffer sb = new StringBuffer();

    for (int i = 0; i < input.length(); i++) {
      spaces = input.charAt(i) - '0'; // how many slots
      if (i % 2 == 0) {  // data slots
        for (int j = 0; j < spaces; j++) {
          sb.appendCodePoint(unicodeChar);
        }
        unicodeChar++;
      } else { // empty slots
        for (int j = 0; j < spaces; j++) {
          sb.append('丠');
        }
      }
    }
    return sb;
  }

  /**
   * Receives the input in block representation and fills empty spaces with data from the other end
   * of the string (part 1).
   *
   * @param blocks input in block representation
   */
  private static void rearrangeBlocksNaive(StringBuffer blocks) {
    String input = blocks.toString();
    int j = input.length();
    char insert;

    for (int i = 0; i < input.length() && i < j; i++) {
      if (input.charAt(i) == '丠') { // for every empty slot
        j--;
        insert = input.charAt(j);
        while (insert == '丠') { // find data starting from the right
          insert = input.charAt(--j);
        }
        if (i >= j) {
          break;
        }
        blocks.setCharAt(i, insert);  // move data from right to the left (to empty slot)
        blocks.setCharAt(j, '丠');
      }
    }
  }

  /**
   * Receives the input in block representation and fills empty spaces with data from the other end
   * of the string, but checks every block only once starting from behind and does not separate data
   * in a block (part 2).
   *
   * @param blocks input in block representation
   * @param files  all blocks of data (file) in a list
   */
  private static void rearrangeBlocksImproved(StringBuffer blocks, List<File> files) {
    int emptyIndex, emptyLength;

    for (File file : files) { // for every block of data
      emptyIndex = 0;
      emptyLength = 0;
      for (int i = 0; i < blocks.length(); i++) { // iterate over disk
        if (file.start < emptyIndex) {  // data cannot be moved to the left -> break
          break;
        } else if (file.size <= emptyLength) {  // data can be moved to the left
          for (int j = 0; j < file.size; j++) {
            blocks.setCharAt(emptyIndex + j, file.id);  // move data
            blocks.setCharAt(file.start + j, '丠');
          }
          break;
        } else if (blocks.charAt(i) == '丠' && emptyLength == 0) { // found new empty slot
          emptyIndex = i;
          emptyLength = 1;
        } else if (blocks.charAt(i) == '丠' && emptyLength > 0) {
          emptyLength++;
        } else { // not empty anymore + file did not fit
          emptyLength = 0;
        }
      }
    }
  }

  /**
   * Receives the data in block representation and returns a list of all files (blocks), for part
   * 2.
   *
   * @param blocks input in block representation
   * @return list of files (each contains: end, start, size, id)
   */
  private static List<File> gatherFiles(StringBuffer blocks) {
    List<File> files = new ArrayList<File>();
    char c = '丠';
    int i = blocks.length() - 1;
    int start, end;

    while (blocks.charAt(i) == c) { // search for the first block of data from behind
      i--;
    }
    for (boolean newChar = true; i >= 0; i--) { // search blocks of data starting from behind
      if (newChar) {  // iterated over a block of data
        c = blocks.charAt(i);
        end = i;
        while (i >= 0 && blocks.charAt(i) == c) {
          i--;
        }
        start = ++i;
        files.add(new File(start, end, end - start + 1, c));  // store block as file
        newChar = false;
        continue; // search for new blocks of data
      }

      while (i >= 0 && blocks.charAt(i) == '丠') { // empty slot -> iterated over block of data
        i--;
      }
      c = blocks.charAt(++i);
      newChar = true;
    }
    return files;
  }

  /**
   * Calculates the sum for all: index of id * id (if not empty)
   *
   * @param blocks data in block representation
   * @return checksum as long
   */
  private static long calculateChecksum(StringBuffer blocks) {
    long result = 0;
    for (int i = 0; i < blocks.length(); i++) {
      if (blocks.charAt(i) != '丠') {  // data not empty
        result += blocks.charAt(i) * i;
      }
    }
    return result;
  }
}