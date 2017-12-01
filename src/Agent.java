// KING SAUD UNIVERSITY
// CCIS
// CSC 361

// NAME:  Abdulmajeed Fahad Altaweel
// ID: 435105646

import java.io.*;
import java.util.*;

public class Agent {

	public static void main(String[] args) throws IOException {

		int n_args = args.length;
		if (n_args!=4) {
			System.out.println("ERROR: ILLEGAL NUMBER OF ARGUMENTS.");
			System.out.println("Number of arguments must be 4");
			return;
		}
		
		String mapFile = args[0];
		String commandsFile = args[1];
		String finalMapFile = args[2];
		String logFile = args[3];
		
		State test = new State(mapFile);	// Declare a state and add the mapFile to read it
		
		test.writeLogs(commandsFile, logFile);	// Here we write the logs into logFile
		
		test.writeFinalMap(finalMapFile);	// Here we write the final map into the finalMapFile
	}
}

