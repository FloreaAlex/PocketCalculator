package Node;

import VisitorPattern.Visitor;

/**
 * Class for a binaryNode in the AST
 */

public class BinaryNode extends Node{

	private String operator;
	private Node leftChild;
	private Node rightChild;
	
	/**
	 * Constructor that sets the variables of the class
	 * 
	 * @param operator 		the operator from the node
	 * @param leftChild		the left sub-tree
	 * @param rightChild	the right sub-tree
	 */
	public BinaryNode (String operator, Node leftChild, Node rightChild) {
		this.setOperator(operator);
		this.setLeftChild(leftChild);
		this.setRightChild(rightChild);
	}
	
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Node getLeftChild() {
		return leftChild;
	}
	public void setLeftChild(Node leftChild) {
		this.leftChild = leftChild;
	}
	public Node getRightChild() {
		return rightChild;
	}
	public void setRightChild(Node rightChild) {
		this.rightChild = rightChild;
	}

	@Override
	public double accept(Visitor v) {
		return v.visit(this);
		
	}
	
	
}
