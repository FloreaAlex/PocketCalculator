package VisitorPattern;

public interface Visitable {
	
	/**
	 * Method that accepts the visitor
	 * 
	 * @param v	the visitor that will visit the AST
	 * @return	the result of the expression
	 */
	double accept(Visitor v);

}
