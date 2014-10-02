package fr.lip6.move.evinrude.commons.model.cfg.interfaces;

/**
 * Définition des comportements publics d'une condition
 * Une condition est déinie par:
 * <ul>
 * 	<li>Un membre gauche</li>
 * 	<li>Un membre droit</li>
 * 	<li>Un comparateur</li>
 * </ul>
 * Les deux membres utilisées sont forcément des expressions simples (garantie du CFG)
 *
 * @author Jean-Baptiste Voron
 */
public interface ICondition extends IInstruction {
	/**
	 * @return l'opérande gauche de l'expression
	 */
	IExpressionSimple getLeftMember();

	/**
	 * @return l'opérande droite de l'expression
	 */
	IExpressionSimple getRightMember();

	/**
	 * @param leftMember the leftMember to set
	 */
	void setLeftMember(IExpressionSimple leftMember);

	/**
	 * @param rightMember the rightMember to set
	 */
	void setRightMember(IExpressionSimple rightMember);

	/**
	 * @return l'operateur de l'expression
	 */
	String getComparator();
}
