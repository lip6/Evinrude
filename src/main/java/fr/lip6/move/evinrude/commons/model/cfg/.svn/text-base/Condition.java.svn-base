package fr.lip6.move.evinrude.commons.model.cfg;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IBlock;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICondition;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IExpressionSimple;

/**
 * Description d'une condition.<br>
 * La condition est la partie qui détermine le résultat d'une instruction du type IF.
 * @author Jean-Baptiste Voron
 */
public class Condition extends Instruction implements ICondition {

	/** Le comparateur utilise dans le test */
	private String comparator;
	/** Le membre de gauche */
	private IExpressionSimple leftMember;
	/** Le membre de droite */
	private IExpressionSimple rightMember;

	/**
	 * Constructor
	 * @param cfgLine Le numero de ligne dans le cfg
	 * @param lineNumber Le numero de ligne dans le CFG
	 * @param comparator Le comparateur
	 * @param block Block contenant cette instruction
	 */
	public Condition(int cfgLine, int lineNumber, String comparator, IBlock block) {
		super(cfgLine, lineNumber, block);
		this.comparator = comparator;
	}

	/**
	 * {@inheritDoc}
	 */
	public final String getComparator() {
		return this.comparator;
	}

	/**
	 * {@inheritDoc}
	 */
	public final IExpressionSimple getLeftMember() {
		return this.leftMember;
	}

	/**
	 * {@inheritDoc}
	 */
	public final IExpressionSimple getRightMember() {
		return this.rightMember;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void setLeftMember(IExpressionSimple leftMember) {
		this.leftMember = leftMember;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void setRightMember(IExpressionSimple rightMember) {
		this.rightMember = rightMember;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		StringBuffer pretty = new StringBuffer();
		pretty.append("Condition: [").append(this.leftMember.toString()).append("]");
		pretty.append("[").append(this.comparator).append("]");
		pretty.append("[").append(this.rightMember.toString()).append("]");
		return pretty.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	public final StringBuffer toXML(int level) {
		int i = 0;
		StringBuffer toReturn = new StringBuffer();

		// Indentation
		for (i = 0; i < level; i++) {
			toReturn.append("\t");
		}

		toReturn.append("<conditionnal line=\"").append(this.getCfgLine()).append("\" operator=\">").append(this.comparator).append("\">");
		toReturn.append("<left>").append(this.leftMember.toXML(level + 1)).append("</left>");
		toReturn.append("<right>").append(this.rightMember.toXML(level + 1)).append("</right>");
		toReturn.append("</conditionnal>");
		return toReturn;
	}
}
