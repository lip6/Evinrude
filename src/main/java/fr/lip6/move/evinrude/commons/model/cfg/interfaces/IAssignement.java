package fr.lip6.move.evinrude.commons.model.cfg.interfaces;

/**
 * Description des comportements publics d'une <b>affectation</b>.<br>
 * Une affectation se compose obligatoirement :
 * <ul>
 * <li>d'un membre gauche qui est une variable;</li>
 * <li>d'un membre droit qui est une operande.</li>
 * </ul>
 * @author Jean-Baptiste Voron
 */
public interface IAssignement extends IInstruction {

	/**
	 * @return le membre de gauche (variable)
	 */
	IVariable getLeft();

	/**
	 * @param left membre gauche
	 */
	void setLeft(IVariable left);

	/**
	 * @return le membre de droite (opérande)
	 */
	IOperand getRight();

	/**
	 * @param right membre droit
	 */
	void setRight(IOperand right);
}
