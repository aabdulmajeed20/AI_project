// KING SAUD UNIVERSITY
// CCIS
// CSC 361

// NAME:  Abdulmajeed Fahad Altaweel
// ID: 435105646

import java.awt.Point;
import java.io.*;
import java.util.*;

public class State {

	// THE FOLLOWING IS AN EXAMPLE OF THE ATTRIBUTES 
	// THAT YOU NEED TO PUT IN A STATE.
	// YOU SHOULD CHANGE IT:
	
	public int x;		// THIS IS X (MAYBE NOT NEEDED)
	public int y;		// THIS IS Y (MAYBE NOT NEEDED)
	private int z; 		// THIS IS Z (MAYBE NOT NEEDED)
//	private Map map;	// THE MAP
	public char[][] map;	// define the map
	public int mapX;	// map's rows
	public int mapY;	// map's columns
	private int initX;	// initial X position of Robot
	private int initY;	// initial Y position of Robot
	private int initGoalX;
	private int initGoalY;
	private Point[] Goals;
	public int Battery;	// The Battery for Robot
	public int actionLeads;	// The number of action leads to this state
	public Node GoalNode;
	public int reCharged;
	// -----------------------------

	//THE FOLLOWING ARE THE CONSTRUCTORS
	// YOU CAN CHANGE OR REPALCE THEM.
	
	// CONSTRUCTOR 1:
	// THIS CONSTRUCTOR WILL CREATE A STATE FROM FILE.
	State(String fileName) throws IOException {
		BufferedReader read = new BufferedReader(new FileReader(fileName)); // Here we take the fileName from parameter
																			// and read it by bufferdReader
		
		mapY = Integer.parseInt(read.readLine()); 	// Here we read the number of rows
		mapX = Integer.parseInt(read.readLine());	// Here we read the number of columns
		Battery = mapY + mapX;	// The capacity of the battery is sum of rows and columns
		Goals = new Point[10];
		int g = 0;
		
		map = new char[mapX][mapY];		// Here we initialize the map which is 2D array
		actionLeads = -1;
		
		// Here we add data to the map
		for(int i = 0; i < mapY; i++) {
			String s = read.readLine();
			for(int j = 0; j < mapX; j++) {
				if(s.charAt(j) == 'R') {		// search about the robot cell by cell then store its position
					initX = x = j;
					initY = y = i;
				}
				if(s.charAt(j) == 'T') {		// search about the robot cell by cell then store its position
					Goals[g++] = new Point(j,i);
					initGoalX = j;
					initGoalY = i;
				}
				map[j][i] = s.charAt(j);
			}
		}
		
		
	}
	
	// CONSTRUCTOR 2:
	// THIS CONSTRUCTOR WILL CREATE A RANDOM STATE.
	State(int n, int m, int rseed) {
		// ...
	}
	
	
	// CONSTRUCTOR 3:
	// COPY CONSTRUCTOR.
	State( State s) {
		x = s.x;
		y = s.y;
		z = s.z;
		map = s.map;
		mapX = s.mapX;
		mapY = s.mapY;
		initX = s.initX;
		initY = s.initY;
		initGoalX = s.initGoalX;
		initGoalY = s.initGoalY;
		Goals = s.Goals;
		Battery = s.Battery;
		reCharged = s.reCharged;
	}
	
	// -----------------------------
	
	// STATE GETTERS AND SETTERS
	// YOU CAN & SHOULD CHANGE THEM.
	// IF YOU HAVE QUESTIONS ASK THE INSTRUCTOR.
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int getZ() {
		return z;
	}
	
//	public Map getMap() { 
//		return map;
//	}
	
	
	// METHOD THAT TELLS WHETHER THIS STATE IS EQUAL
	// TO ANOTHER STATE.
	public boolean equal(State s) {
		return ((x==s.x)&&(y==s.y)&&(z==s.z)&&(map.equals(s.map)&&(reCharged==s.reCharged)));
	}
	
	// -----------------------------
	
	// THE ACTIONS:
	// YOU CAN CHANGE THE ACTIONS CONTENTS,
	// WHAT THE ACTIONS RETURN, ETC.
	
	// ACTION move North, it's moves the robot to the North
	// RETURNS BOOLEAN: 
	//     TRUE MEANS ACTION WAS APPLIED, 
	//     FALSE MEANS ACTOIN COULD NOT AND WAS NOT APPLIED.
	public boolean move_N() {
		if(Battery <= 0)
			return false;
		if(y == 0)						// checking if Robot is on the top of the map and return false if that correct
			return false;
		if(map[x][y-1] == 'B')		// checking if the above cell is block cell
			return false;
		if(map[x][y] == 'H')		// checking if Robot fallen in Hole
			return false;
		y--;							// if the above conditions not pass, moving Robot up and return true
		Battery--;
		return true;
		
	}

	// ACTION move East, it's moves the robot to the East
	public boolean move_E() {	
		if(Battery <= 0)
			return false;
		if(x == mapX-1)
			return false;
		if(map[x+1][y] == 'B')					// Same of move_N
			return false;
		if(map[x][y] == 'H')
			return false;
		x++;
		Battery--;
		return true;
		
	}
	
	// ACTION move South, it's moves the robot to the South
		public boolean move_S() {
			if(Battery <= 0)
				return false;
			if(y == mapY-1)
				return false;
			if(map[x][y+1] == 'B')				// Same of move_N
				return false;
			if(map[x][y] == 'H')
				return false;
			y++;
			Battery--;
			return true;
			
		}
		
		// ACTION move West, it's moves the robot to the West
		public boolean move_W() {
			if(Battery <= 0)
				return false;
			if(x == 0)
				return false;
			if(map[x-1][y] == 'B')				// Same of move_N
				return false;
			if(map[x][y] == 'H')
				return false;
			x--;
			Battery--;
			return true;
			
		}
		
		//Action charge which will increase battery of Robot
		public boolean reCharge() {
			if(map[x][y] == 'C') {				// if Robot on the station then charge will done
				Battery = mapX + mapY;
				reCharged = 1;
				return true;
			}
			return false;	
		}
	


	// -----------------------------

	// GOAL TEST: THIS WILL 
	// TELL WHETHER THE TREASURE WAS FOUND.
	public boolean foundTreasure() {
		if(map[x][y] == 'T') {		//checking if Robot in the same cell of Treasure
			return true;
		}
		return false;
	}

	// -----------------------------


	// DISPLAY THE STATE
	public void display() {
		
	}
	
	
	// THIS METHOD WILL DO the GIVEN COMMAND
	// AND WILL RETURN THE LOG MESSAGE
	public String doCommandAndLog(String cmd) { // Here we will move on every command and do its action, then we check
												// if the action returns true then log DONE
												// else if the action returns false then log FAIL
		String log = "ERROR";
		if (Battery < 0)
			log = "NO BATTERY";
		else {
			switch (cmd) {
			case "move-N":
				if (move_N())
					log = "DONE";
				else
					log = "FAIL";

				break;

			case "move-E":
				if (move_E())
					log = "DONE";
				else
					log = "FAIL";
				break;

			case "move-S":
				if (move_S())
					log = "DONE";
				else
					log = "FAIL";
				break;

			case "move-W":
				if (move_W())
					log = "DONE";
				else
					log = "FAIL";
				break;

			case "recharge":
				if (reCharge())
					log = "DONE";
				else
					log = "FAIL";
				break;
			}
		}
		
		return log;
	}
        

 	// This method will write logs of commands into the logFile
	public void writeLogs(String commandsFile, String logFile) throws IOException {
		BufferedReader read = new BufferedReader(new FileReader(commandsFile)); // Buffer to read from commandsFile
		BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));	// Buffer to write into logFile
		
		String cmd;
		String check;
		while((cmd = read.readLine()) != null) {		// Here we call doCommandAndLog() and write its value on logFile
			check = doCommandAndLog(cmd);
			writer.append(check);
			if(check == "DONE")
				if(map[x][y] == 'H') {				// checking if Robot fall in Hole, add log HOLE
					writer.newLine();
					writer.append("HOLE");
				}
				else if(foundTreasure()) {			// checking if Robot's position same Treasure position, add log GOAL
					writer.newLine();
					writer.append("GOAL");
				}
				else if(map[x][y] == 'Y') {			// checking if checking if Robot fall in Hole contains Treasure, add log GOAL then HOLE
					writer.newLine();
					writer.append("GOAL");
					writer.newLine();
					writer.append("HOLE");
				}
				
			writer.newLine();
		}
		writer.append("BATTERY: " + Battery);			// show battery remaining
		writer.close();
		read.close();
	}
	
	// This method will write the final map into finalMapFile
	public void writeFinalMap(String finalMapFile) throws IOException {
		if(map[x][y] != map[initX][initY])
		map[initX][initY] = ' ';		// Make the initial Robot position empty because it moves
		if(foundTreasure())			// checking if Robot at the same cell of Treasure, then change the value
			map[x][y] = 'U';
		else if(map[x][y] == 'H')		// checking if Robot at the same cell of Hole, then change the value
			map[x][y] = 'X';
		else if(map[x][y] == 'Y')
			map[x][y] = 'Z';
//		else if(map[x][y] == 'C')
//			map[x][y] = 'D';
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(finalMapFile)); //buffer to write into finalMapFile
		for(int i = 0; i<mapY; i++) {
			for(int j = 0; j<mapX; j++) {
				writer.append(map[j][i]);
			}
			writer.newLine();
		}
		writer.append("Robot Position -> (" + x + ", " + y + ")");
		writer.close();
	}

	
	// -----------------------------


 	// THIS METHOD WILL RETURN THE SUCCESSOR STATES 
	// OF COURSE, YOU CAN & SHOULD CHANGE IT
        public State[] successors() {
        	State children[] = new State[5]; // we have 5 actions
		
		// action 1: move to North		

		children[0] = new State(this);
		if (!children[0].move_N())
			children[0] = null;
		else{
			children[0].actionLeads = 0;
		}

		// action 2: move to East

		children[1] = new State(this);
		if (!children[1].move_E())
			children[1] = null;
		else{
			children[1].actionLeads = 1;
		}
		
		// action 3: move to South
		
		children[2] = new State(this);
		if (!children[2].move_S())
			children[2] = null;
		else{
			children[2].actionLeads = 2;
		}
		
		// action 4: move to West
		
		children[3] = new State(this);
		if (!children[3].move_W())
			children[3] = null;
		else{
			children[3].actionLeads = 3;
		}
		
		// action 5: reCharge
		
		children[4] = new State(this);
		if (!children[4].reCharge())
			children[4] = null;
		else{
			children[4].actionLeads = 4;
		}
		
        	return children;
        }
        
       	// -----------------------------

	public int getInitX() {
		return initX;
	}
	public int getInitY() {
		return initY;
	}
	public int getInitGoalX() {
		return initGoalX;
	}
	public int getInitGoalY() {
		return initGoalY;
	}
	public Point getNearestGoal() {
		int i = 1;
		Point current = new Point(x,y);
		Point minPoint = Goals[0];
		while(Goals[i] != null) {
			if(current.distance(Goals[i]) < current.distance(minPoint)){
				minPoint = Goals[i];
			}
			i++;
		}
		return minPoint;
	}
		
}
