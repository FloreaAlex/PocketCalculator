package Node;

import VisitorPattern.Visitable;
import VisitorPattern.Visitor;

/**
 * Class for a leaf in the AST
 */

public class Leaf extends Node implements Visitable {

	private double value;
	
	/**
	 * Constructor that sets the value of the node

	 * @param value	the value of the node 
	 */
	public Leaf (double value) {
		this.setValue(value);
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public double accept(Visitor v) {
		return v.visit(this);
		
	}
	
}
