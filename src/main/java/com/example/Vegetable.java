// Creates a vegetable plant type that grows down from the top
package com.example;
//package com.gradescope.garden;
public class Vegetable extends Plant {
	private String variety;
	private int size;
	private String type;
	private char varChar;
	private int x;
	private int y;
	public Vegetable(int x, int y, String variety) {
		this.type="vegetable";
		this.variety=variety;
		this.size=1;
		this.varChar=variety.charAt(0);
		this.x=x;
		this.y=y;
		App.add(x,y,2,0,type);
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
		for (int i=1 ; i<size ; i++) {
			App.add(x,y,2,i,type);
		}
	}
	@Override
	public void harvest() {
		size=0;
		App.remove(x,y);
	}
}
