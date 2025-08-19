/* abstract class acting as super class to plant types (declares 
 * expected functions and variables).
 */
package com.example;
//package com.gradescope.garden;
abstract class Plant {
	public abstract String getType();
	public abstract String getVariety();
	public abstract void grow(int incr);
	// public to prevent compulsory inclusion of all three in each subclass
	public void harvest() {
		
	}
	public void pick() {
		
	}
	public void cut() {
		
	}
	public void snap() {
		
	}
}
