// Creates a flower plant type that blossoms out
package com.example;
//package com.gradescope.garden;
public class Flower extends Plant {
	private String variety;
	private int size;
	private String type;
	private char varChar;
	private int x;
	private int y;
	
	public Flower(int x, int y, String variety) {
		this.type="flower";
		this.variety=variety;
		this.size=1;
		this.varChar=variety.charAt(0);
		this.x=x;
		this.y=y;
		App.add(x,y,2,2,type);
	}
	public String getVariety() {
        return variety;
	}
	public String getType() {
        return type;
	}
	public int getSize() {
		return size;
	}
	public void grow(int incr) {
		boolean maxGrowth=false;
		if (size==5) {
			String coords=String.format("(%d, %d)", x, y);
			maxGrowth=true;
			App.printError("There is no room for growth! "+coords+"\n\n");
		}
		if ((size+incr)<=5) {
			size=size+incr;
		} else size=5;
		int[][] growth2Coords = { {1, 2}, {3, 2}, {2, 1}, {2, 3} };
		int[][] growth3Coords = { {1, 1}, {1, 3}, {3, 1}, {3, 3}, 
		{0, 2}, {2, 0}, {4, 2}, {2, 4} };
		int[][] growth4Coords = { {1, 0}, {1, 4}, {3, 0}, {3, 4}, 
		{0, 1}, {0, 3}, {4, 1}, {4, 3} };
		int[][] growth5Coords = { {0, 0}, {0, 4}, {4, 0}, {4, 4} };
		if (size >= 2 && !maxGrowth) {
			applyGrowth(growth2Coords);
	    }
	    if (size >= 3 && !maxGrowth) {
			applyGrowth(growth3Coords);
	    }
        if (size>=4 && !maxGrowth) {
			applyGrowth(growth4Coords);
        }
        if (size==5 && !maxGrowth) {
			applyGrowth(growth5Coords);
        }
    }

	private void applyGrowth(int[][] coords) {
		for (int[] coord : coords) {
			int row = coord[0];
			int col = coord[1];
			App.add(x,y,col,row,type);
		}
	}
	@Override
	public void pick() {
		size=0;
		App.remove(x,y);
	}
}
