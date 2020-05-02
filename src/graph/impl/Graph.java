package graph.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import graph.IGraph;
import graph.INode;
import graph.NodeVisitor;
import java.util.Comparator; 
import java.util.PriorityQueue; 


/**
 * A basic representation of a graph that can perform BFS, DFS, Dijkstra,
 * and Prim-Jarnik's algorithm for a minimum spanning tree.
 * 
 * @author jspacco
 *
 */

public class Graph implements IGraph
{
	HashMap<String, Node> nodes;

	public Graph() {
		this.nodes = new HashMap<String,Node>();
	}
	/**
	 * Return the {@link Node} with the given name.
	 * 
	 * If no {@link Node} with the given name exists, create
	 * a new node with the given name and return it. Subsequent
	 * calls to this method with the same name should
	 * then return the node just created.
	 * 
	 * @param name
	 * @return
	 */
	public INode getOrCreateNode(String name) {
		if(this.nodes.containsKey(name)) {
			return this.nodes.get(name);
		}
		else {
			INode temp = new Node(name);
			this.nodes.put(name, (Node)temp);
			return this.nodes.get(name);
		}
	}

	/**
	 * Return true if the graph contains a node with the given name,
	 * and false otherwise.
	 * 
	 * @param name
	 * @return
	 */
	public boolean containsNode(String name) {
		return this.nodes.containsKey(name);
	}

	/**
	 * Return a collection of all of the nodes in the graph.
	 * 
	 * @return
	 */
	public Collection<INode> getAllNodes() {
		List<INode> temp = new ArrayList<INode>(this.nodes.values());
		return temp;
	}

	/**
	 * Perform a breadth-first search on the graph, starting at the node
	 * with the given name. The visit method of the {@link NodeVisitor} should
	 * be called on each node the first time we visit the node.
	 * 
	 * 
	 * @param startNodeName
	 * @param v
	 */
	public void breadthFirstSearch(String startNodeName, NodeVisitor v)
	{
		Set<Node> visited = new HashSet<Node>();
		Queue<Node> q = new LinkedList<Node>();
		q.add((Node)this.getOrCreateNode(startNodeName));
		v.visit((Node)this.getOrCreateNode(startNodeName));
		visited.add((Node)this.getOrCreateNode(startNodeName));
		while(!q.isEmpty()) {
			Node curr = q.remove();

			for(INode neighbor: curr.getNeighbors()) {
				if(! visited.contains(neighbor)) {
					q.add((Node)neighbor);
					v.visit(neighbor);
					visited.add((Node)neighbor);
				}
			}

		}
	}

	/**
	 * Perform a depth-first search on the graph, starting at the node
	 * with the given name. The visit method of the {@link NodeVisitor} should
	 * be called on each node the first time we visit the node.
	 * 
	 * 
	 * @param startNodeName
	 * @param v
	 */
	public void depthFirstSearch(String startNodeName, NodeVisitor v)
	{
		Set<Node> visited = new HashSet<Node>();
		Stack<Node> s = new Stack<Node>();
		s.push((Node)this.getOrCreateNode(startNodeName));
		while(! s.isEmpty()) {
			Node curr = s.pop();
			if(!visited.contains(curr)) {
				v.visit(curr);
				visited.add(curr);
				for(INode neighbors: curr.getNeighbors()) {
					s.push((Node)neighbors);
				}		
			}
		}

	}
	/**
	 * Perform Dijkstra's algorithm for computing the cost of the shortest path
	 * to every node in the graph starting at the node with the given name.
	 * Return a mapping from every node in the graph to the total minimum cost of reaching
	 * that node from the given start node.
	 * 
	 * <b>Hint:</b> Creating a helper class called Path, which stores a destination
	 * (String) and a cost (Integer), and making it implement Comparable, can be
	 * helpful. Well, either than or repeated linear scans.
	 * 
	 * @param startName
	 * @return
	 */
	public Map<INode,Integer> dijkstra(String startName) {
	
		PriorityQueue<Path> q = new PriorityQueue<Path>();
		HashMap<INode, Integer> distanceFS = new HashMap<INode, Integer>();
		
		//distanceFS.put((Node)this.getOrCreateNode(startName), 0);
			
		q.add(new Path(nodes.get(startName).getName(), 0));
			
		
		while(!q.isEmpty()) {
			Path next =q.poll();
			//Node curr = null;
			int m = next.cost;
			if(distanceFS.containsKey(nodes.get(next.name))) {
				continue;
			}
			distanceFS.put(nodes.get(next.name), m);
			
			for(INode neighbor: nodes.get(next.name).getNeighbors()) {
				if(distanceFS.containsKey(neighbor)) {
					continue;
				}
				int alt = m + nodes.get(next.name).getWeight(neighbor);
				q.add(new Path(neighbor.getName(), alt));
				
			}
				
		}
		
		return distanceFS;
	}
	

	/**
	 * Perform Prim-Jarnik's algorithm to compute a Minimum Spanning Tree (MST).
	 * 
	 * The MST is itself a graph containing the same nodes and a subset of the edges 
	 * from the original graph.
	 * 
	 * @return
	 */
	
	public IGraph primJarnik() {
		PriorityQueue<Edge> q = new PriorityQueue<Edge>();
		IGraph newGraph = new Graph();
		INode first = nodes.values().iterator().next();
		List<Edge> graphedges = new ArrayList<Edge>();
		
		for(INode neighbor: first.getNeighbors()) {
			q.add(new Edge(first, neighbor, first.getWeight(neighbor)));
		}
		while(!q.isEmpty()) {	
			Edge next = q.poll();
			if(newGraph.containsNode(next.result.getName())){
				continue;
			}
			INode org = newGraph.getOrCreateNode(next.origin.getName());
			INode res = newGraph.getOrCreateNode(next.result.getName());
			org.addUndirectedEdgeToNode(res, next.weight);
			//System.out.println("Edge from " + next.origin.getName() + "to " + next.result.getName() + "weight " + next.weight);
			
			for(INode neighbor: nodes.get(next.result.getName()).getNeighbors()) {
				if(newGraph.containsNode(neighbor.getName())){
					continue;
				}
				q.add(new Edge(next.result, neighbor, next.result.getWeight(neighbor)));
			}
		
		}		
		return newGraph;
	}
}