// Creates a tree plant type that grows up from the bottom
package com.example;
//package com.gradescope.garden;
public class Tree extends Plant {
	private String variety;
	private int size;
	private String type;
	private char varChar;
	private int x;
	private int y;
	
	public Tree(int x, int y, String variety) {
		this.type = "tree";
		this.variety=variety;
		this.size=1;
		this.varChar=variety.charAt(0);
		this.x=x;
		this.y=y;
		App.add(x,y,2,4,type);
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
		if (size==5) {
			String coords=String.format("(%d, %d)", x, y);
			App.printError("There is no room for growth! "+coords+"\n\n");
		}
		if ((size+incr)<=5) {
			size=size+incr;
		} else size=5;
		for (int i=3; i>(4-size); i--) {
			App.add(x,y,2,i,type);
		}
	}
	@Override
	public void cut() {
		size=0;
		App.remove(x,y);
	}
}
