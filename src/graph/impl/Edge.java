package graph.impl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import graph.IGraph;
import graph.INode;
import graph.NodeVisitor;
import java.util.Comparator; 
import java.util.PriorityQueue;

public class Edge {
	Node origin;
	Node result;
	int weight;
	
	public Edge(Node origin, Node result, int weight) {
		this.origin = origin;
		this.result = result;
		this.weight = weight;
	}
	
	public int compareTo(Edge other) {
		return Integer.compare(this.weight, other.weight);
		
	}
	
}
