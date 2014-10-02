package fr.lip6.move.evinrude.commons.model.cfg.interfaces;

/**
 * Définition des comportements publics d'une Expression complexe
 * Une expression complexe est déinie par:
 * <ul>
 * 	<li>Une opérande gauche</li>
 * 	<li>Une opérande droite</li>
 * 	<li>Un opérateur</li>
 * </ul>
 * Les deux opérandes utilisées sont forcément simple (garantie du CFG)
 *
 * @author Jean-Baptiste Voron
 */
public interface IExpressionComplex extends IExpression {

	/**
	 * @return l'opérande gauche de l'expression
	 */
	IExpressionSimple getLeftOperand();

	/**
	 * @return l'opérande droite de l'expression
	 */
	IExpressionSimple getRightOperand();

	/**
	 * @return l'operateur de l'expression
	 */
	String getOperator();

	/**
	 * @param leftOperand the leftOperand to set
	 */
	void setLeftOperand(IExpressionSimple leftOperand);

	/**
	 * @param rightOperand the rightOperand to set
	 */
	void setRightOperand(IExpressionSimple rightOperand);
}
