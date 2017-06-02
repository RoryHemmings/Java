
/*
Algorithm works like so:
 *add all nodes to queue
 *sort queue
 *find distance to all of the first index on the queue's neighbors
 *remove first index from the queue
 *add old first index to the finished list
 *sort list
 *repeat process until end node is hit, then retrace steps from end node back the the start node
 *return those steps (which will be the shortest path)
*/

package dev.algorithm;

import java.util.ArrayList;
import java.util.Collections;

import dev.Main;

public class Dijkstras {

	private ArrayList<Node> queue;
	private ArrayList<Node> done;
	private ArrayList<Node> shortest_path;
	private Node end = null;

	public ArrayList<Node> findShortestPath(Node[][] grid, int edge_distance, Main main) {
		queue = new ArrayList<Node>();
		done = new ArrayList<Node>();
		shortest_path = new ArrayList<Node>();

		Node closest = null;
		end = null;
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				if (!(grid[x][y].getClassification() == Node.wall))
					queue.add(grid[x][y]);
			}
		}

		while (end == null) {
			Collections.sort(queue);
			closest = queue.get(0);
			if(closest.getClassification() == Node.end) {end = closest;}

			evaluate(closest, edge_distance);

			if (queue.size() > 1)
				queue.remove(closest);
			done.add(closest);
		}

		Node temp = end;
		shortest_path.add(temp);

		while (true) {
			shortest_path.add(temp.getPrevious_node());
			temp = temp.getPrevious_node();
			if (temp.getPrevious_node() == null)
				break;
		}
		
		System.out.println("Finished");
		return shortest_path;
	}

	public void evaluate(Node node, int edge_distance) {
		for (Node n : node.getNeighbors()) {
			if (n.getClassification() != Node.wall) {
				int d = 0;
				d = node.getDistance() + edge_distance;
				if (d < n.getDistance() || n.getDistance() == Integer.MAX_VALUE) {
					n.setDistance(d);
					n.setPrevious_node(node);
				}
			}
		}

	}

}
