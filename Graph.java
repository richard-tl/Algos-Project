import java.util.*;

class Graph {
  private int vertices;
  private List<List<Integer>> list;
  private List<List<Integer>> paths;
  private boolean pathsFound = false;

  public Graph(int v) {
    vertices = v;
    makeGraph();
  }

  public void makeGraph() {
    list = new ArrayList<>();
    for(int i = 0; i < vertices+1; i++) {
      list.add(new ArrayList<>());
    }

  }

  public void addEdge(Integer a, Integer b) {
    list.get(a).add(b);
  }

  public List<List<Integer>> getGraph() {
    return list;
  }

  public List<Integer> getEdges(int index) {
    return list.get(index);
  }

       
  public List<List<Integer>> returnPaths(List<List<Integer>> nodePairs, int skip) { 
    paths = new ArrayList<>();
    boolean[] visited = new boolean[vertices+1];
    doNotVisit(visited, nodePairs, skip);
    dfs(new ArrayList<>(), nodePairs.get(skip).get(0), nodePairs.get(skip).get(1), visited);
    return paths;
  }
    
  private void dfs(ArrayList<Integer> local, int current, int dest, boolean[] visited) {
    if(current == dest)
    {
        local.add(current);
        paths.add(new ArrayList<>(local));
        local.remove(local.size()-1);
        return;
    }
    local.add(current);
    visited[current] = true;
    for(int child : list.get(current)) {
       
      if(!visited[child]) {  
        dfs(local, child, dest, visited);
      }
    }
    visited[current] = false;
    local.remove(local.size()-1);
  }

  private void doNotVisit(boolean[] visited, List<List<Integer>> nodePairs, int skip) {
    for(int i = 0; i < nodePairs.size(); i++) {
      if(i != skip) {
        visited[nodePairs.get(i).get(0)] = true;
        visited[nodePairs.get(i).get(1)] = true;
      }
    }
  }

  public List<List<Integer>> allConnectingPaths(List<List<Integer>> nodePairs) {
    boolean[] deletedNodes = new boolean[vertices+1];
    List<List<Integer>> allPaths = new ArrayList<>();
    if(!hasSolution(nodePairs)) {
      return allPaths;
    }
    recurse(nodePairs, deletedNodes, nodePairs.size(), 0, allPaths);
    return allPaths;
  }

  private void recurse(List<List<Integer>> nodePairs, boolean[] deleted, int totalPairs, int pairNum, List<List<Integer>> allPaths) {
    if(pathsFound || allPaths.size() == nodePairs.size()) {
      pathsFound = true;
      return;
    }
    List<List<Integer>> connectingPaths = returnPaths(nodePairs, pairNum);
    for(List<Integer> tmp : connectingPaths) {
      if(hasOverlap(deleted, tmp)) {
        continue;
      }
      allPaths.add(tmp);
      checkNodes(deleted, tmp);
      System.out.println("Adding to path from : " + nodePairs.get(pairNum).get(0) + " to " + nodePairs.get(pairNum).get(1));
      for(int i : tmp) {
        System.out.print(i + " ");
      }
      System.out.println();
      recurse(nodePairs, deleted, totalPairs, pairNum+1, allPaths);
      if(pathsFound) {
        return;
      } else {
        List<Integer> uncheck = allPaths.remove(allPaths.size() - 1);
        uncheckNodes(deleted, uncheck);
        System.out.println("Deleting: ");
        for(int i : tmp) {
          System.out.print(i + " ");
        }
        System.out.println();
      }
    }
  }

  private boolean hasOverlap(boolean[] deleted, List<Integer> nodes) {
    for(int i = 0; i < nodes.size(); i++) {
      if(deleted[nodes.get(i)]) {
        return true;
      }
    }
    return false;
  }
  private void checkNodes(boolean[] deleted, List<Integer> nodes) {
    for(int i = 0; i < nodes.size(); i++) {
      deleted[nodes.get(i)] = true;
    }
  }

  private void uncheckNodes(boolean[] deleted, List<Integer> nodes) {
    for(int i = 0; i < nodes.size(); i++) {
      deleted[nodes.get(i)] = false;
    }
  }

  private boolean hasSolution(List<List<Integer>> nodePairs) { {
    for(List<Integer> p: nodePairs)  {
      if(list.get(p.get(0)).isEmpty()) {
        return false;
      }
    }
    return true;
  }
  }
  
}