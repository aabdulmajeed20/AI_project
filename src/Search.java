// KING SAUD UNIVERSITY
// CCIS
// CSC 361

// NAME:  ...................
// ID: ...................

import java.io.*;
import java.util.*;

public class Search {


	// CONSTANTS:
	private static final int CLOSED_MAX_SIZE = 100000;
	private static final int FRINGE_MAX_SIZE = 100000;
	
	// ATTRIBUTES:
	private Node root;			// the root node
	private Queue<Node> fringe;		// the BFS frings: change it 							// for the other searches.
	private Node goal;			// the goal node
	private int numNodesExpanded;		// number of nodes expanded
	
	
	// ALTHOUGH YOU ARE NOT REQUIRED TO, BUT IT IS
	// HIGHLY RECOMMENDED THAT YOU IMPLEMENT THE 
	// CLOSED LIST MECHANISM (THE GRAPH SEARCH).
	// THE NEED FOR IT IS DUE TO THE VERY LARGE NUMBER OF 
	// REDUNDANT STATES THAT YOU WILL GENERATE BEFORE 
	// REACHING THE GOAL.
	// THE FOLLOWING IS A SIMPLE IMPLEMENTATION OF THE CLOSED
	// LIST, REPRESENTED AS A SIMPLE ARRAY OF NODES:

	private Node closed[];			// the closed list containing 							// visited nodes
	private int n_closed;			// size of closed list
	
	
	// CONSTRUCTOR 1:
	// THIS CONSTRUCTOR WILL CREATE A SEARCH OBJECT.
	Search( State init_state ) {
		root = new Node(init_state, null, -1, 0, 0); // make the root node
		fringe = new LinkedList<>();	// initialize Queue
		closed = null;
		n_closed = 0;
		goal = null;
		numNodesExpanded = 0;
		
		// ...
		
	}
	

	// THIS METHOD INITIALIZES THE CLOSED LIST
	private void initialize_closed() {
		if (closed==null)
			closed = new Node[CLOSED_MAX_SIZE];
		n_closed=0;
	}
	
	// THIS METHOD TESTS WHETHER THE NODE WAS
	// VISITED OR NOT USING A SIMPLE FOR LOOP.
	// YOU CAN CHANGE IT.
	private boolean visited(Node n) {
		for (int i=0; i<n_closed; i++) {
			// ...
			if (closed[i].hasSameState(n))  // change this
				return true;
		}
		return false;
	}
	

	// THIS METHOD ADDS A NODE TO THE CLOSED LIST.
	// IT SIMPLY ADDS IT TO THE ND OF THE LIST. YOU
	// CAN CHANGE IT TO A MORE SOPHISTICATED METHOD.
	private void mark_as_visited(Node n) {
		// if the list is  full do a left shift:
		if (n_closed==CLOSED_MAX_SIZE) {
			for(int i=0; i<CLOSED_MAX_SIZE-1; i++)
				closed[i] = closed[i+1];
		}
		else
			n_closed++;
		closed[n_closed-1] = n;
	}
	
	
	// THIS METHOD WILL DO THE SEARCH AND CAN
	// RETURN THE GOAL NODE. YOU CAN EXTRACT
	// THE SOLUTION BY FOLLOWING THE PARENT NODES	
	public Node doSearch(int na) {

		numNodesExpanded = 0;
		Node nodesList[];
		Node current = root;
		initialize_closed();

		if (na == 1 || na == 2) {
			if(na == 2) {
				Comparator<Node> comparator = new MDComprator();
				fringe = new PriorityQueue<>(comparator);
			}
			fringe.add(current);

			while (!fringe.isEmpty()) {
				current = fringe.remove();

				if (current.isGoal()) {
					return current;
				}
				mark_as_visited(current);
				nodesList = current.expand();
				numNodesExpanded++;
				System.out.println("Battery: " + current.state.Battery + ", MapX&Y: [" + current.state.map[current.state.x][current.state.y]+"] ---> " + current.state.reCharged);
				for (int i = 0; i < 5; i++) { // we have 5 actions
					if (nodesList[i] != null) {
						System.out.print(nodesList[i].getAction());
						if(nodesList[4]!=null)
							System.out.print(" {"+nodesList[4].state.Battery+"} ");
						if (!visited(nodesList[i])) {
							fringe.add(current = nodesList[i]);
							System.out.print(", Action: " + current.getAction() + "| ");
						}
					}
					// ...
				}
			}
		} else if(na == 3) {
			Node checker = current;
			do {
				checker = current;
				nodesList = current.expand();
				for(int i = 0; i < 5; i++) {
					if(nodesList[i] != null) {
						System.out.print(nodesList[i].getAction());
						if(nodesList[i].h_md() < current.h_md()) {
							System.out.print(", Action: " + nodesList[i].getAction());
							current = nodesList[i];
						}
					}
				}
				System.out.println();
			}while(current != checker);
		}
		return null; // goal not found

	}

	
	
	// GIVEN THE GOAL NODE, THIS METHOD WILL EXTRACT
	// THE SOLUTION, WHICH IS A SEQUENCE OF ACTIONS.	
	public String[] extractSolution( Node goalNode ) {
	
		// first find solution length;
		int len=0;	
		Node n = goalNode;
		while (n!=null) {
			n = n.getParent();
			len++;
		}
		
		//declare an array of strings
		String sol[] = new String[len-1];

		n = goalNode;
		for (int i=len-2; i>=0; i--) {
			switch (n.getAction()) {
				case 0: sol[i] = new String("move-N");
					break;
				case 1: sol[i] = new String("move-E");
					break;
				case 2: sol[i] = new String("move-S");
					break;
				case 3: sol[i] = new String("move-W");
					break;
				case 4: sol[i] = new String("recharge");
			}
			
			n = n.getParent();
		}
		return sol;		
	}
	
	// THIS METHOD WILL DISPLAY THE SOLUTION
	public void displaySolution(Node goalNode, State s, String actionsFile) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(actionsFile));
		
		if(goalNode == null){
			System.out.println("NOOP");
			writer.append("NOOP");
		} else{
		String sol[] = extractSolution(goalNode);
		for (int i=0; i<sol.length; i++){
			writer.append(sol[i]);
			writer.newLine();
			s.doCommandAndLog(sol[i]);
			System.out.println(sol[i] + " [" + (i+1) + "]");
		}
		}
		writer.close();
	}
}