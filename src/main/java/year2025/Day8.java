package year2025;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import util.Conv;
import util.Solution;
import util.Util;

public class Day8 extends Solution {

  int clusterLimit = 1000;

  private List<int[]> elements;
  private double[][] dist;

  private Map<Integer, Integer> elementCluster;
  private Map<Integer, Set<Integer>> clusters;
  private int[] lastXCoords;

  @Override
  public void setupPart1() {
    elements = Conv.toIntArray(Conv.split(input, ","));
    elementCluster = new HashMap<>();
    clusters = new HashMap<>();
  }

  @Override
  public void setupPart2() {
    setupPart1();
  }

  @Override
  public void solvePart1() {
    getDistMatrix();

    cluster(clusterLimit);

    List<Integer> top3 = clusters.values()
        .stream()
        .sorted((e1, e2) -> Integer.compare(e2.size(), e1.size()))
        .limit(3)
        .map(Set::size)
        .toList();

    System.out.println(top3.get(0) * top3.get(1) * top3.get(2));  // three biggest clusters
  }

  @Override
  public void solvePart2() {
    getDistMatrix();

    cluster(-1);  // cluster until finished
    System.out.println(lastXCoords[0] * lastXCoords[1]);
  }

  private void getDistMatrix() {
    dist = new double[elements.size()][elements.size()];
    for (int i = 0; i < elements.size(); i++) {
      for (int j = i + 1; j < elements.size(); j++) {
        dist[i][j] = Util.euclideanDistance(elements.get(i), elements.get(j));
      }
    }
  }

  private void cluster(int n) {
    int count = 0;

    while (count++ < n || n == -1) {
      int[] shortest = Util.findShortestDistance(dist);
      int i = shortest[0];
      int j = shortest[1];

      if (dist[i][j] == Double.MAX_VALUE) {
        break;
      }
      dist[i][j] = Double.MAX_VALUE;

      merge(i, j, count);
      lastXCoords = new int[]{elements.get(i)[0], elements.get(j)[0]};

      if (finished()) {
        return;
      }
    }
  }

  private void merge(int i, int j, int clusterId) {
    Integer clusterI = elementCluster.get(i);
    Integer clusterJ = elementCluster.get(j);
    boolean firstInCluster = clusterI != null;
    boolean secondInCluster = clusterJ != null;

    if (firstInCluster && secondInCluster) {
      if (clusterI.equals(clusterJ)) { // already in same cluster
        return;
      }

      for (int ele : clusters.get(clusterJ)) {  // in different clusters, change index for one
        elementCluster.put(ele, clusterI);
      }
      clusters.get(clusterI).addAll(clusters.get(clusterJ));  // put all of one cluster in the other
      clusters.remove(clusterJ);
      return;
    }

    if (!firstInCluster && !secondInCluster) {  // both not clustered
      elementCluster.put(i, clusterId);
      elementCluster.put(j, clusterId);
      Set<Integer> tmp = new HashSet<>();
      tmp.add(i);
      tmp.add(j);
      clusters.put(clusterId, tmp);
      return;
    }

    if (firstInCluster) { // one element is clustered
      elementCluster.put(j, clusterI);
      clusters.get(clusterI).add(j);
    } else {
      elementCluster.put(i, clusterJ);
      clusters.get(clusterJ).add(i);
    }
  }

  private boolean finished() {
    Integer clusterId = elementCluster.get(0);
    if (clusterId == null) {
      return false;
    }
    Set<Integer> c = clusters.get(clusterId);  // check any cluster

    if (c != null && c.size() == input.size()) {
      return true;  // all elements in one cluster
    }
    return false;
  }

  public static void main(String[] args) {
    new Day8().solve(50);
  }
}
