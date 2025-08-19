/* RunGarden.java works with a variety of .java files to create a Garden
 * with an inheritance tree that allows for the planting, growth, and
 * cultivation of numerous plant varieties under a collection of plant
 * types (Vegetables, Trees, Flowers, and Vines). The file takes in commands
 * for these functions and can print a 2D string of the entire garden.
 */
package com.example;

import java.util.ArrayList;
public class RunGarden {

	public static int[] getCoords(String coordStr) {
        coordStr = coordStr.replace("(", "").replace(")", ""); 
        String[] values = coordStr.split(","); 
        int x = Integer.parseInt(values[0]);
        int y = Integer.valueOf(values[1]);
        int[] coords = {x,y};
        return coords;
	}
	// decides what cultivation technique to use based on plant type
	public static void removePlant(Garden mainGarden, ArrayList<String> line) {
		String removal = line.get(0).toLowerCase();
		int x; int y; String id;
		if (line.size()==2) {
			if (line.get(1).contains("(")) {
				int[] coordList=getCoords(line.get(1));
				x = coordList[0]; y = coordList[1]; id = "null";
			} else {
				x = -1; y = -1; id = line.get(1).toLowerCase();
			}
		} else {
			x= -1; y= -1; id= "null";
		}
		if (x<0 || mainGarden.getPlots()[x][y]!=null) {
			if (removal.equals("harvest")) mainGarden.harvest(x, y, id);
			else if (removal.equals("pick")) mainGarden.pick(x, y, id);
			else if (removal.equals("snap")) mainGarden.snap(x,y,id);
			else mainGarden.cut(x, y, id);
		}
	}
	// turns command line into string to be printed
	public static String getLineStr(ArrayList<String> line) {
		String lineStr="> " +line.get(0).toUpperCase()+ " ";
		for (int i=1; i<line.size(); i++) {
			lineStr+=line.get(i)+" ";
		}
		lineStr=lineStr.trim();
		lineStr+="\n";
		return lineStr;
	}
	// takes in list of commands and carries them out through Garden
	public static String initiateCommands(Garden mainGarden, 
			ArrayList<String> line) {
		String cmd = line.get(0).toLowerCase();
		if (cmd.equals("plant")) {
			int[] coordList=getCoords(line.get(1));
			int x = coordList[0];
			int y = coordList[1];
			String plant=line.get(2).toLowerCase();
			mainGarden.plant(x, y, plant);
		} else if (cmd.equals("print")) {
			System.out.print(getLineStr(line));
		}
		else if (cmd.equals("grow")) {
			int incr=Integer.valueOf(line.get(1));
			System.out.print(getLineStr(line)+"\n");
			if (line.size()==3) {
				if (line.get(2).contains("(")) {
					int[] coordList=getCoords(line.get(2));
					int x = coordList[0];
					int y = coordList[1];
					mainGarden.grow(incr, x, y, null);
				} else mainGarden.grow(incr, null, null, line.get(2));
			} else mainGarden.grow(incr, null, null, null);
		} else {
			System.out.print(getLineStr(line)+"\n");
			removePlant(mainGarden, line);
		}
		return getLineStr(line);
	}

}
