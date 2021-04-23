import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Main {
  public static void main(String[] args) throws IOException {
    int numnode = 100;
    int numpairs = 10;
    /*these are the values we will use consistently*/
    List<List<Integer>> pairs = new ArrayList<>();
    for(int i = 0; i < numpairs; i++) {
      pairs.add(new ArrayList<>());
    }
    Graph g = new Graph(numnode);
    /* input and output files */
    String inputFile = args[0];
    String outputFile = args[1];
    //
    readFile(inputFile, g, pairs, numpairs);
    File f = createFile(outputFile);
    List<List<Integer>> res = g.allConnectingPaths(pairs);
    writeToFile(res, f);
  }

  public static void readFile(String name, Graph g, List<List<Integer>> pairs, int numpairs) throws FileNotFoundException {
    File file = new File(name);
    Scanner input = new Scanner(file);
    Pattern p = Pattern.compile("\\d+");
    input.nextLine();
    // the while loop creates the graph and enters all the edges
    while (input.hasNextLine()) {
        ArrayList<Integer> n = new ArrayList<>();
        String data = input.nextLine();
        if(data.equals("PAIRS")) {
          break;
        }
        Matcher m = p.matcher(data);
        while(m.find()) {
          n.add(Integer.parseInt(m.group()));  
        }
        for(int i = 1; i < n.size(); i++) {
          g.addEdge(n.get(0), n.get(i));
        }
    }
    //the next while loop will get all the pairs
    int count = 0;
    while(input.hasNextLine() && count < numpairs) {
      String pair = input.nextLine();
      Matcher m = p.matcher(pair);
      while(m.find()) {
        pairs.get(count).add(Integer.parseInt(m.group()));
      }
      count++;
    }
    input.close();
  }

  public static File createFile(String name) throws IOException {
    File output = new File(name);
    try {
      if (output.createNewFile()) {
          System.out.println("File created: " + output.getName());
      } else {
          System.out.println("File already exists.");
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    return output;

  }

  public static void writeToFile(List<List<Integer>> result, File output) throws IOException {
    try (FileWriter myWriter = new FileWriter(output.getName())){
      for(int i = 0; i < result.size(); i++) {
        for(int j = 0; j < result.get(i).size(); j++) {
          myWriter.write(String.valueOf(result.get(i).get(j)) + " ");
        }
        myWriter.write("\n");
        }
        System.out.println("Successfully wrote to the file.");
      } catch (IOException e) {
        e.printStackTrace();
      }
  }

}