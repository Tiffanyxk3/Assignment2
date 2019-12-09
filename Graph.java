import java.util.*;

public class Graph
{
	public HashMap<String, ArrayList<String>> graph;
	
	public Graph() {
		graph = new HashMap<String, ArrayList<String>>();
	}

	public void addEdge(ArrayList<String> actors) {
		for (int a=0; a<actors.size(); a++) {
			String tempActor = actors.get(a);
			ArrayList<String> tempActors = new ArrayList<String>(actors);
			tempActors.remove(tempActor);
			
			if (graph.containsKey(tempActor)) {
			// if no key, add a new one
				for (int i=0; i<tempActors.size(); i++) {
					if (!(graph.get(tempActor)).contains(tempActors.get(i))) {
						(graph.get(tempActor)).add(tempActors.get(i));
					}
				}
			}
			else {
			// key exists, put tempActor directly
				graph.put(tempActor, tempActors);
			}
		}
	}

	public ArrayList<String> findShortestPath(String actor1, String actor2) {
		ArrayList<String> shortestPath = new ArrayList<String>();
		LinkedList<String> queue = new LinkedList<String>();
		HashMap<String, String> pathTable = new HashMap<String, String>();
		if (actor1.equals(actor2)) {
			shortestPath.add(actor2);
			return shortestPath;
			// if actor1 equals actor2, then path is just actor2
		}
		queue.add(actor1);
		pathTable.put(actor1, actor1);
		String qd = queue.poll();
		ArrayList<String> al = graph.get(qd);
		queue.addAll(al);
		for (int i=0; i<al.size(); i++) {
			pathTable.put(al.get(i), qd);
		}
		
		while (true) {
			String dequeued = queue.poll();
			if (dequeued.equals(actor2)) {
				break;
			}
			ArrayList<String> addList = graph.get(dequeued);
			for (String s:addList) {
				if (!pathTable.containsKey(s)) {
					queue.add(s);
				}
			}
			for (int j=0; j<addList.size(); j++) {
				if (!pathTable.containsKey(addList.get(j))) {
				// put new set if not in pathTable
					pathTable.put(addList.get(j), dequeued);
				}
			}
		}
		shortestPath.add(actor2);
		String key = actor2;
		String value = "";
		while (!key.equals(actor1)) {
		// find through pathTable
			value = pathTable.get(key);
			shortestPath.add(value);
			key = value;
		}
		return shortestPath;
	}

	public boolean isInGraph(String name) {
	// check if the actor exists
		return graph.containsKey(name);
	}
}