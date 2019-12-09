import java.util.*;
import java.io.*;

public class CS245A2
{
	public Graph graph;
	public String actor1, actor2;
	public String inputFileName;

	public CS245A2() {
		graph = new Graph();
	}

	public void readInput() {
		try {
			Scanner scan = new Scanner(new File(inputFileName));
			scan.nextLine();
			while (scan.hasNextLine()) {
			// for each line each movie
				ArrayList<String> actorsOfMovie = new ArrayList<String>();
				String line = scan.nextLine();
				String[] cast = line.split("}]");
				// cast[0] contains cast
				String[] castLines = cast[0].split("}, ");
				// castLines contains each set of cast
				for (int i=0; i<castLines.length; i++) {
					String[] t1 = (castLines[i]).split("name\"\": \"\"");
					if (t1.length >= 2) {
						String n = t1[1];
						String[] t2 = n.split("\"\"");
						if (t2.length >= 1) {
							String name = t2[0];
							actorsOfMovie.add(name.toLowerCase());
							// actorsOfMovie contains all actors in each movie
						}
					}
				}
				graph.addEdge(actorsOfMovie);
				// pass actors of each movie to graph
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("Input file not found.");
			return;
		}
		catch (Exception e) {
			System.out.println(e.toString());
			return;
		}
	}

	public void inputName(String name1, String name2) {
		actor1 = name1;
		actor2 = name2;
	}

	public boolean nameExists(String name) {
		return graph.isInGraph(name);
	}

	public ArrayList<String> getPath() {
		return graph.findShortestPath(actor1, actor2);
	}

	public static void main(String[] args) {
		CS245A2 assignment2 = new CS245A2();
		if (args.length == 0) {
			assignment2.inputFileName = "tmdb_5000_credits.csv";
			// open "tmdv_5000_credits.csv" by default if no command line argument
		}
		else if (args.length == 1) {
			assignment2.inputFileName = args[0];
			// take command line argument as file name
		}
		else {
			System.out.println("Invalid arguments. ");
			assignment2.inputFileName = "tmdb_5000_credits.csv";
			// default
		}
		assignment2.readInput();

		boolean again = true;
		while (again) {
			Scanner scan = new Scanner(System.in);
			System.out.print("Actor 1 name: ");
			String actor1Name = scan.nextLine();
			while (!assignment2.nameExists(actor1Name.toLowerCase())) {
			// search for the existence of actor1
				System.out.println("No such actor. ");
				System.out.print("Actor 1 name: ");
				actor1Name = scan.nextLine();
			}
			System.out.print("Actor 2 name: ");
			String actor2Name = scan.nextLine();
			while (!assignment2.nameExists(actor2Name.toLowerCase())) {
			// search for the existence of actor2
				System.out.println("No such actor. ");
				System.out.print("Actor 2 name: ");
				actor2Name = scan.nextLine();
			}
			assignment2.inputName(actor1Name.toLowerCase(), actor2Name.toLowerCase());
			// pass actor names to class

			ArrayList<String> path = assignment2.getPath();
			String n1 = actor1Name.toUpperCase().charAt(0) + actor1Name.toLowerCase().substring(1,actor1Name.length());
			String n2 = actor2Name.toUpperCase().charAt(0) + actor2Name.toLowerCase().substring(1,actor2Name.length());
			System.out.print("Path between "+n1+" and "+n2+": ");
			for (int i=path.size()-1; i>=0; i--) {
				String p = path.get(i).toUpperCase().charAt(0) + path.get(i).substring(1,path.get(i).length());
				System.out.print(p);
				if (i != 0) {
					System.out.print(" --> ");
				}
			}
			System.out.print("\n");
			// print pass

			Scanner scanContinue = new Scanner(System.in);
			System.out.println("Try again? (y/n)");
			String input = scanContinue.nextLine().toLowerCase();
			if (!input.equals("y")) {
				again = false;
			}
		}
	}
}