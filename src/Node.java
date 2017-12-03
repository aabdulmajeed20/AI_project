// KING SAUD UNIVERSITY
// CCIS
// CSC 361

// NAME:  ...................
// ID: ...................

import java.io.*;
import java.util.*;

class Node{
	private State state;	// the state
	private Node parent;	// the parent node
	private int action;	// the number of the action 
				// that lead to this state
	private int path_cost;	// the cost spent so far to reach this node
	private int depth;	// the depth of the node in the tree
	
	// CONSTRUCTOR :
	// THIS CONSTRUCTOR WILL CREATE A NODE GIVEN A STATE
	Node( State st, Node pa, int a, int c, int d){
		state = st;	
		parent = pa;
		action = a;
		path_cost = c;
		depth = d;
	}
	
	
	// THIS METHOD RETURNS TRUE IS THE 
	// NODE'S STATE IS THE SAME AS THE OTHER NODE'S STATE
	public boolean hasSameState(Node n) {
		return (state.equal(n.state));
	}
	
 	// THIS METHOD WILL RETURN THE NEIGHBORING NODES 
	// OF COURSE, YOU CAN & SHOULD CHANGE IT
	public Node[] expand() {
		Node next_nodes[] = new Node[5];	// there are 5 actions
		State next_states[] = state.successors();
		for (int i=0; i<5; i++) {		// create nodes
			if (next_states[i]!=null){
				next_nodes[i] = new Node(next_states[i], this, next_states[i].actionLeads, path_cost++, this.depth + 1);
			}
			
				// ...
				
		}		
		return next_nodes;
	}
	
	// GOAL TEST: THIS WILL TELL 
	// WHETHER THE NODE'S STATE IS A GOAL.	
	public boolean isGoal() {
		return state.foundTreasure();
	}
	
	// MANHATTAN DISTANCE HEURISTIC
	public int h_md() {
		
		return Math.abs(state.getInitX() - state.getInitGoalX()) + Math.abs(state.getInitY() - state.getInitGoalY());
	}
	
	// DISPLAY THE NODE'S INFO
	public void display() {

		// ...
		
	}
	
	public Node getParent() {
		return parent;
	}
	
	public int getAction() {
		return action;
	}
}

