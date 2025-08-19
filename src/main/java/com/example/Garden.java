/* Sets up the basic functions for the garden as a whole (planting, cultivation,
 * and growth). Defines the plant varieties, and prints out a view of the garden 
 * from an 2d array of Plant objects.
 */
package com.example;
//package com.gradescope.garden;
import java.util.Arrays;
public class Garden {
	private int rows;
	private int columns;
	private Plant[][] plots;
	private String[] vegetables = {"garlic", "zucchini", "tomato", 
			"yam", "lettuce"};
	private String[] flowers = {"iris", "lily", "rose", 
			"daisy", "tulip", "sunflower"};
	private String[] trees = {"oak", "willow", "banana", "coconut", "pine"};
	private String[] vines = {"ivy", "honeysuckle", "clematis", "wisteria"};
	
	public Garden(int rows, int columns) {
		this.rows=rows;
		this.columns=columns;
		this.plots = new Plant[rows][columns];
	}
	// adds plant to plots 2d list and creates the correct type
	public void plant(int x, int y, String plant) {
		Plant newPlant;
		if (plots[x][y]==null) {
			if (Arrays.asList(vegetables).contains(plant)) {
				newPlant = new Vegetable(x,y,plant);
			} else if (Arrays.asList(flowers).contains(plant)) {
				newPlant = new Flower(x,y,plant);
			} else if (Arrays.asList(trees).contains(plant)) {
				newPlant = new Tree(x,y,plant);
			} else if (Arrays.asList(vines).contains(plant)) {
				newPlant = new Vine(x,y,plant);
			} else {
				newPlant=null;
				App.printError("Invalid plant! ");
			}
			plots[x][y]=newPlant;
		}
	}
	// grows plant based on command input
	public void grow(int incr, Integer x, Integer y, String id) {
		if (x==null) {
			for (Plant[] row: plots) {
				for (Plant plant: row) {
					if (plant!= null) {
						if (id!=null) {
							if (plant.getVariety().equals(id) || 
									plant.getType().equals(id)) plant.grow(incr);
						}
						else plant.grow(incr);
					}
				}
			}
		} else {
			if (x<plots.length && y<plots[x].length) {
				if (plots[x][y]!=null) plots[x][y].grow(incr);
				else App.printError("Can't grow there.\n\n");
			} else App.printError("Can't grow there.\n\n");
		}
	}
	
	public void harvest(Integer x, Integer y, String id) {
		if (x!=-1) {
			if (x<(rows) && y<(columns) && plots[x][y].getType()=="vegetable") {
				plots[x][y].harvest();
			} else App.printError("Can't harvest there.\n\n");
		} else {
			for (Plant[] row: plots) {
				for (Plant plant: row) {
					if (plant!= null) {
						if (id!="null") {
							if (plant.getVariety()==id) 
								plant.harvest();
						} else if (plant.getType()=="vegetable") plant.harvest();
					}
				}
			}
		}
	}
	
	public void pick(Integer x, Integer y, String id) {
		if (x!=-1) {
			if (x<(rows) && y<(columns) && plots[x][y].getType()=="flower") {
				plots[x][y].pick();
			} else App.printError("Can't pick there.\n\n");
		} else {
			for (Plant[] row: plots) {
				for (Plant plant: row) {
					if (plant!= null) {
						if (id!="null") {
							if (plant.getVariety()==id) plant.pick();
						} else if (plant.getType()=="flower") plant.pick();
					}
				}
			}
		}
	}
	
	public void cut(Integer x, Integer y, String id) {
		if (x!=-1) {
			if (x<(rows) && y<(columns) && plots[x][y].getType()=="tree") {
				plots[x][y].cut();
			} else App.printError("Can't cut there.\n\n");
		} else {
			for (Plant[] row: plots) {
				for (Plant plant: row) {
					if (plant!= null) {
						if (id!="null") {
							if (plant!= null && plant.getVariety()==id) plant.cut();
						} else if (plant.getType()=="tree") plant.cut();
					}
				}
			}
		}
	}
	public void snap(Integer x, Integer y, String id) {
		if (x!=-1) {
			if (x<(rows) && y<(columns) && plots[x][y].getType()=="vine") {
				plots[x][y].cut();
			} else App.printError("Can't snap there.\n\n");
		} else {
			for (Plant[] row: plots) {
				for (Plant plant: row) {
					if (plant!= null) {
						if (id!="null") {
							if (plant!= null && plant.getVariety()==id) plant.snap();
						} else if (plant.getType()=="vine") plant.snap();
					}
				}
			}
		}
	}
	
	public Plant[][] getPlots() {
		return plots;
	}
	
	public String getDimensions() {
		return "the garden has: "+rows+" rows and "+columns+" columns.";
	}
	
}

