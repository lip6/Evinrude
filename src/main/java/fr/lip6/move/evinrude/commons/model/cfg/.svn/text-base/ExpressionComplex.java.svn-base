package fr.lip6.move.evinrude.commons.model.cfg;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IExpressionComplex;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IExpressionSimple;

/**
 * Définition d'une expression complexe.<br>
 *
 * @author Jean-Baptiste Voron
 */
public class ExpressionComplex implements IExpressionComplex {

	/** L'opérande de gauche */
	private IExpressionSimple leftOperand;

	/** L'opérande de droite */
	private IExpressionSimple rightOperand;

	/** L'opérateur */
	private String operator;

	/**
	 * Constructeur d'une expression complexe
	 * @param operator L'opérateur utilisé
	 */
	public ExpressionComplex(String operator) {
		this.operator = operator;
	}

	/**
	 * {@inheritDoc}
	 */
	public final IExpressionSimple getLeftOperand() {
		return this.leftOperand;
	}

	/**
	 * {@inheritDoc}
	 */
	public final String getOperator() {
		return this.operator;
	}

	/**
	 * {@inheritDoc}
	 */
	public final IExpressionSimple getRightOperand() {
		return this.rightOperand;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void setLeftOperand(IExpressionSimple leftOperand) {
		this.leftOperand = leftOperand;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void setRightOperand(IExpressionSimple rightOperand) {
		this.rightOperand = rightOperand;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		StringBuffer pretty = new StringBuffer();
		pretty.append("Expression complexe: [").append(leftOperand.toString()).append("]");
		pretty.append("[").append(operator).append("]");
		pretty.append("[").append(rightOperand).append("]");
		return pretty.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	public final StringBuffer toXML(int level) {
		int i = 0;
		StringBuffer toReturn = new StringBuffer();
		StringBuffer indentation = new StringBuffer();

		// Indentation
		for (i = 0; i < level; i++) { indentation.append("\t"); }

		toReturn.append(indentation).append("<complex operator=\"").append(this.operator).append("\">\n");
		toReturn.append(indentation).append("\t<left>\n");
		toReturn.append(this.leftOperand.toXML(level + 2));
		toReturn.append(indentation).append("\t</left>\n");

		toReturn.append(indentation).append("\t<right>\n");
		toReturn.append(this.rightOperand.toXML(level + 2));
		toReturn.append(indentation).append("\t</right>\n");
		toReturn.append(indentation).append("</complex>\n");

		return toReturn;
	}
}
