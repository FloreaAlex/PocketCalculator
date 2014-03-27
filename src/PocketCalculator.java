
import Node.BinaryNode;
import Node.Leaf;
import Node.UnaryNode;
import VisitorPattern.Visitor;

/**
 * Class that implements the Visitor interface and the logic of all its methods
 */

public class PocketCalculator implements Visitor {

	@Override
	public double visit(BinaryNode node) {
		if (node.getOperator().equals("+")) {
			return node.getLeftChild().accept(this) + node.getRightChild().accept(this);
		}
		else if (node.getOperator().equals("-")) {
			return node.getLeftChild().accept(this) - node.getRightChild().accept(this);
		}
		else if (node.getOperator().equals("*")) {
			return node.getLeftChild().accept(this) * node.getRightChild().accept(this);
		}
		else if (node.getOperator().equals("/")) {
			if (node.getRightChild().accept(this) == 0) {
				throw new EvaluatorException();
			}
			return node.getLeftChild().accept(this) / node.getRightChild().accept(this);
		}
		else if (node.getOperator().equals("^")) {
			return Math.pow(node.getLeftChild().accept(this), node.getRightChild().accept(this));
		}
		return 0f;

	}

	@Override
	public double visit(UnaryNode node) throws EvaluatorException {
		if (node.getOperator().equals("log")) {
			if (node.getChild().accept(this) <= 0) { 
				throw new EvaluatorException();
			}
			return Math.log10(node.getChild().accept(this));
		}
		else if (node.getOperator().equals("sqrt")) {
			if (node.getChild().accept(this) < 0) {
				throw new EvaluatorException();
			}
			return Math.sqrt(node.getChild().accept(this));
		}
		else if (node.getOperator().equals("sin")) {
			return Math.sin(node.getChild().accept(this));
		}
		else if (node.getOperator().equals("cos")) {
			return Math.cos(node.getChild().accept(this));
		}
		else if (node.getOperator().equals("unaryMinus")) {
			return -(node.getChild().accept(this));
		}
		return 0f;

	}

	@Override
	public double visit(Leaf node) {
		return node.getValue();

	}

}
