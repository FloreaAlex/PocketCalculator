package Node;

import VisitorPattern.Visitable;
import VisitorPattern.Visitor;

/**
 * Class for an Unary node of the AST
 */

public class UnaryNode extends Node implements Visitable{

	private String operator;
	private Node child;
	
	/**
	 * Constructor that initializes the node
	 * 
	 * @param operator	operator of the node
	 * @param child		sub-tree of the node
	 */
	public UnaryNode (String operator, Node child) {
		this.setOperator(operator);
		this.setChild(child);
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Node getChild() {
		return child;
	}

	public void setChild(Node child) {
		this.child = child;
	}

	@Override
	public double accept(Visitor v) {
		return v.visit(this);
		
	}
	
}
