package year2025;

import java.util.ArrayList;
import java.util.List;
import util.Conv;
import util.Solution;

public class Day5 extends Solution {

  private List<long[]> ranges;
  private List<Long> ids;

  @Override
  public void setupPart1() {
    int empty = 0;
    for (String line : input) {
      if (line.isEmpty()) {
        break;
      }
      empty++;
    }
    ranges = Conv.toLongArray(Conv.split(input.subList(0, empty), "-"));
    ids = Conv.toLong(input.subList(empty + 1, input.size()));
  }

  @Override
  public void setupPart2() {
    // no need
  }

  @Override
  public void solvePart1() {
    long result = 0;

    for (long id : ids) {
      for (long[] range : ranges) {
        if (id >= range[0] && id <= range[1]) {
          result++;
          break;
        }
      }
    }

    System.out.println(result);
  }

  @Override
  public void solvePart2() {
    List<Range> expRanges = new ArrayList<>();

    ranges.forEach(r -> expRanges.add(new Range(r[0], r[1])));

    // iterate while ranges can be merged
    boolean merged = true;
    while (merged) {
      merged = false;
      int index1 = -1;
      int index2 = -1;

      for (int i = 0; i < expRanges.size(); i++) {
        for (int j = i + 1; j < expRanges.size(); j++) {
          if (expRanges.get(i).overlaps(expRanges.get(j))) {
            index1 = i;
            index2 = j;
            merged = true;
            break;
          }
        }
        if (merged) {
          break;
        }
      }

      if (merged) {
        expRanges.set(index1, expRanges.get(index1).merge(expRanges.get(index2)));
        expRanges.remove(index2);
      }
    }

    long result = 0;
    for (Range range : expRanges) {
      result += range.end - range.start + 1;
    }
    System.out.println(result);
  }

  private static class Range {

    long start;
    long end;

    public Range(long start, long end) {
      this.start = start;
      this.end = end;
    }

    public boolean overlaps(Range other) {
      return (start <= other.start && end >= other.start)
          || (start <= other.end && end >= other.end)
          || (other.start <= start && other.end >= start)
          || (other.start <= end && other.end >= end);
    }

    public Range merge(Range other) {
      long newStart = Math.min(start, other.start);
      long newEnd = Math.max(end, other.end);
      return new Range(newStart, newEnd);
    }
  }

  public static void main(String[] args) {
    new Day5().solve();
  }
}
