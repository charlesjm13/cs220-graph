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

public class Path implements Comparable {
	String name;
	int cost;
	
	public Path(String name, int cost) {
		this.name = name;
		this.cost = cost;
	}
	public int compareTo(Object other) {
		return this.cost - ((Path)other).cost;
	}
}
