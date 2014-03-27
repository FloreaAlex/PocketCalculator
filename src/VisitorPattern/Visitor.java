package VisitorPattern;

import Node.BinaryNode;
import Node.Leaf;
import Node.UnaryNode;

public interface Visitor {
	
	/**
	 * Method for visiting a BinaryNode
	 * @param node	the node that is going to be visited
	 * @return	the result of the expression 
	 */
	double visit (BinaryNode node);
	
	/**
	 * Method for visiting a UnaryNode
	 * @param node	the node that is going to be visited
	 * @return	the result of the expression 
	 */
	double visit (UnaryNode node);
	
	/**
	 * Method for visiting a LeafNode
	 * @param node	the node that is going to be visited
	 * @return	the result of the expression 
	 */
	double visit (Leaf node);
}
