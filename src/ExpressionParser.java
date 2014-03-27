import java.util.Stack;

import Node.BinaryNode;
import Node.Leaf;
import Node.Node;
import Node.UnaryNode;

/** 
 * Class for creating an AST from an expression and evaluating 
 * the expression by visiting the tree with visitors 
 * 
 */
public class ExpressionParser {

	private Stack<Node> resultStack;
	private Stack<String> operatorsStack;
	
	/**
	 * Constructor that initializes the two stacks that will be used   
	 */ 
	public ExpressionParser () {
		resultStack = new Stack<Node>();
		operatorsStack = new Stack<String>();
	} 
	
	/**
	 * Creates the AST
	 * 
	 * @params	expression	the string containing the expression
	 * @return	the result of the expression after the AST was visited
	 */
	public float eval(String expression) throws SyntacticException, EvaluatorException{
		int i = 0;
		String[] e = expression.split(" +");
		
		if (!hasGoodParanthesis(e)) {
			throw new SyntacticException();
		}
		
		if (e[0].equals("-")) { // the unary minus is replaced with a string in order to avoid confusion 
			operatorsStack.push("unaryMinus");
			i++;
		}
		for (; i < e.length; i ++) {
			
			if (!isNumeric(e[i]) && isWrongOperator(e[i])) { // check if is a valid operator or number
				throw new SyntacticException();
			}
			
			if (isOperator(e[i])) { 
				if (operatorsStack.empty()) {
					operatorsStack.push(e[i]);
				}
				else if (precedence(e[i]) <= precedence(operatorsStack.peek())) {
					while (!operatorsStack.empty() && precedence(e[i]) <= precedence(operatorsStack.peek()) ) {
						String operator = operatorsStack.pop();
						if (isUnaryFunction(operator)) {
							Node operand1 = resultStack.pop();
							resultStack.push(new UnaryNode(operator, operand1));
						}
						else { // is binary operator
							Node operand1 = resultStack.pop();
							Node operand2 = resultStack.pop();
							resultStack.push(new BinaryNode(operator, operand2, operand1));
						}
					}	
					operatorsStack.push(e[i]);
				}
				else {
					operatorsStack.push(e[i]);
				}
			}
			else if (isLeftParanthesis(e[i])) { 
				if (e[i + 1].equals("-")) { // special case for unary minus
					operatorsStack.push(e[i]);
					operatorsStack.push("unaryMinus");
					i ++;
				}
				else {
					operatorsStack.push(e[i]);
				}
			}
			else if (isRightParanthesis(e[i])) {
				if (operatorsStack.peek().equals("(")) { // if there is only one number between the brackets 
					operatorsStack.pop();
					if (isUnaryFunction(operatorsStack.peek())) {
						Node operand1 = resultStack.pop();
						resultStack.push(new UnaryNode(operatorsStack.pop(), operand1));
					}
					else {
						Node operand1 = resultStack.pop();
						Node operand2 = resultStack.pop();
						resultStack.push(new BinaryNode(operatorsStack.pop(), operand2, operand1));
					}
				}
				else {
					while (!operatorsStack.empty() && !operatorsStack.peek().equals("(")) {
						String operator = operatorsStack.pop();
						if (isUnaryFunction(operator)) {
							Node operand1 = resultStack.pop();
							resultStack.push(new UnaryNode(operator, operand1));
						}
						else {
							Node operand1 = resultStack.pop();
							Node operand2 = resultStack.pop();
							resultStack.push(new BinaryNode(operator, operand2, operand1));
						}
					}
					operatorsStack.pop();
					if (!operatorsStack.empty() && isUnaryFunction(operatorsStack.peek())) { // if the next element in the stack
																							 // is a unary function
						Node operand1 = resultStack.pop();
						resultStack.push(new UnaryNode(operatorsStack.pop(), operand1));
					}
				}
			}
			else {
				resultStack.push(new Leaf(Double.parseDouble(e[i])));
			}
		}
		
		// if there are any operators left in the stack, all of them are poped
		while (!operatorsStack.empty()) {
			String operator = operatorsStack.pop();
			if (isUnaryFunction(operator)) {
				Node operand1 = resultStack.pop();
				resultStack.push(new UnaryNode(operator, operand1));
			}
			else {
				Node operand1 = resultStack.pop();
				Node operand2 = resultStack.pop();
				resultStack.push(new BinaryNode(operator, operand2, operand1));
			}
		}
		return getResult();
		
	}
	
	/**
	 * Method that checks if is an operator
	 * 
	 * @param	operator	a string containing the operator
	 * @return				true if it is an operator, false otherwise
	 */ 
	private boolean isOperator (String operator) {
		String operators = "+-*/^";
		
		if (operator.length() == 0) {
			return false;
		}
		
		for (int i = 0; i < operators.length(); i ++) {
			if (operator.charAt(0) == operators.charAt(i)) {
				if (operator.length() == 2) {
					return false;
				}
				return true;
			}
		}
		
		if (operator.equals("log")) {
			return true;
		}
		else if (operator.equals("sqrt")) {
			return true;
		}
		else if (operator.equals("sin")) {
			return true;
		}
		else if (operator.equals("cos")) {
			return true;
		}
		return false; 
	}
	
	/**
	 * Method that returns the precedence of an operator
	 * 
	 * @param operator	a string containing the operator
	 * @return	the precedence of the operator
	 */
	private int precedence(String operator) {
		if (operator.charAt(0) == '+' || operator.charAt(0) == '-') {
			return 0;
		}
		else if (operator.charAt(0) == '*' || operator.charAt(0) == '/') {
			return 1;
		}
		else if (operator.charAt(0) == '^') {
			return 2;
		}
		else  if (operator.equals("unaryMinus")) {
			return 3;
		}
		else if (operator.equals("log") || operator.equals("sqrt") || 
				 operator.equals("sin") || operator.equals("cos")) {
			return 4;
		}
		else if (operator.equals("(")) {
			return -1;
		}
		return -2;
	}
	
	/**
	 * Method that checks if an operator is an unary function
	 * 
	 * @param operator	the string containing the operator
	 * @return	true if is an unary function, false otherwise
	 */
	private boolean isUnaryFunction (String operator) {
		if (operator.equals("log")) {
			return true;
		}
		else if (operator.equals("sqrt")) {
			return true;
		}
		else if (operator.equals("sin")) {
			return true;
		}
		else if (operator.equals("cos")) {
			return true;
		}
		else if (operator.equals("unaryMinus")) {
			return true;
		}
		return false; 
	}
	
	/**
	 * Method that checks if a string is a right bracket
	 * 
	 * @param operator	the string containing the bracket
	 * @return	true if it is, false otherwise
	 */
	private boolean isRightParanthesis (String operator) {
		if (operator.equals(")")) {
			return true;
		}
		return false;
	}
	
	/**
	 * Method that checks if a string is a left bracket
	 * 
	 * @param operator	the string containing the bracket
	 * @return	true if it is, false otherwise
	 */
	private boolean isLeftParanthesis (String operator) {
		if (operator.equals("(")) {
			return true;
		}
		return false;
	}
	
	/**
	 * Method that checks if a string is an operator
	 * 
	 * @param operator the string that can contain the operator
	 * @return	true if it doesn't contain an operator, false otherwise
	 */
	private boolean isWrongOperator (String operator) {
		
		if (operator.equals("+") || operator.equals("-")) {
			return false;
		}
		else if (operator.equals("*") || operator.equals("/")) {
			return false;
		}
		else if (operator.equals("^")) {
			return false;
		}
		else  if (operator.equals("unaryMinus")) {
			return false;
		}
		else if (operator.equals("(") || operator.equals(")")) {
			return false;
		}
		else if (operator.equals("log") || operator.equals("sqrt") || 
				 operator.equals("sin") || operator.equals("cos")) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Method that checks if a string matches a number
	 * 
	 * @param str the string that can contain the number
	 * @return	true if it matches, false otherwise
	 */
	private boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");
	}
	
	/**
	 * Method that checks if an expression has good parenthesis
	 * 
	 * @param expression a string containing the expression
	 * @return	true if has good parenthesis, false otherwise
	 */
	private boolean hasGoodParanthesis (String[] expression) {
		int c1 = 0, c2 = 0;
		for (int i = 0; i < expression.length; i ++) {
			if (expression[i].equals("(")) {
				c1 ++;
			}
			else if (expression[i].equals(")")) {
				c2 ++;
			}
		}
		if (c1 == c2) {
			return true;
		}
		return false;
	}
	
	/**
	 * Method that gets the final result of the expression
	 * 
	 * @return	the result
	 */
	private float getResult () {
		PocketCalculator p = new PocketCalculator();
		return (float) resultStack.pop().accept(p);
		
	}
	
}
