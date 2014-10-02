package fr.lip6.move.evinrude.commons.model.cfg.interfaces;

/**
 * Description des comportements publics d'une expression.<br>
 * Une opérande peut être de 2 types:
 * <ul>
 * 	<li>Une expression simple</li>
 * 	<li>Un appel de fonction</li>
 * </ul>
 *
 * @author Jean-Baptiste Voron
 */
public interface IOperand {
	/**
	 * @return la description XMl de l'expression simple
	 * @param level Niveau d'indentation
	 */
	StringBuffer toXML(int level);
}
