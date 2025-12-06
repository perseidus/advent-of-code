package year2025;

import java.util.ArrayList;
import java.util.List;
import util.Conv;
import util.Solution;

public class Day6 extends Solution {

  private List<String[]> operands;
  private String[] operators;

  @Override
  public void setupPart1() {
    List<String> input2 = new ArrayList<>(input.size());
    for (int i = 0; i < input.size(); i++) {
      input2.add(i, input.get(i).stripLeading());
    }

    List<String[]> lines = Conv.split(input2, "\s+");
    operands = lines.subList(0, lines.size() - 1);
    operators = lines.get(lines.size() - 1);
  }

  @Override
  public void setupPart2() {
    List<String[]> lines = Conv.split(input, "\s+");
    operands = lines.subList(0, lines.size() - 1);
    operators = lines.get(lines.size() - 1);
  }

  @Override
  public void solvePart1() {
    long result = 0;

    for (int i = 0; i < operators.length; i++) {
      long tmp = Long.parseLong(operands.get(0)[i]);

      for (int j = 1; j < operands.size(); j++) {
        if (operators[i].equals("+")) {
          tmp += Long.parseLong(operands.get(j)[i]);
        } else {
          tmp *= Long.parseLong(operands.get(j)[i]);
        }
      }
      result += tmp;
    }

    System.out.println(result);
  }

  @Override
  public void solvePart2() {
    int length = 0;
    for (String line : input) {
      length = Math.max(length, line.length());
    }

    int[] split = new int[operators.length + 1];
    split[split.length - 1] = length;

    // get indices of every new operator
    String ops = input.get(input.size() - 1);
    for (int i = 0, c = 0; i < ops.length(); i++) {
      if (ops.charAt(i) == '+' || ops.charAt(i) == '*') {
        split[c++] = i;
      }
    }

    long result = 0;

    // for every operator
    for (int i = 0; i < split.length - 1; i++) {
      String operand;
      int currentIndex = split[i];
      int maxIndex = split[i + 1];
      long tmpResult = Integer.parseInt(getVerticalDigitsAt(currentIndex++));

      while (currentIndex < maxIndex) { // while new operands available
        operand = getVerticalDigitsAt(currentIndex);
        if (operand.isEmpty()) {  // check if still digits left
          break;
        }

        if (operators[i].equals("+")) {
          tmpResult += Integer.parseInt(operand);
        } else {
          tmpResult *= Integer.parseInt(operand);
        }
        currentIndex++;
      }

      result += tmpResult;
    }
    System.out.println(result);
  }

  // get digits top to bottom for given index
  private String getVerticalDigitsAt(int index) {
    StringBuilder digits = new StringBuilder();
    for (int i = 0; i < input.size() - 1; i++) { // append digit for operand
      digits.append(getDigitAt(input.get(i), index));
    }
    return digits.toString();
  }

  // returns digit or empty string
  private String getDigitAt(String line, int index) {
    if (index >= line.length()) {
      return "";
    }

    char c = line.charAt(index);
    if (c >= '0' && c <= '9') {
      return c + "";
    }
    return "";
  }

  public static void main(String[] args) {
    new Day6().solve(50);
  }
}
