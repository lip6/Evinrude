package fr.lip6.move.evinrude.commons.model.cfg.interfaces;

/**
 * Définition des comportements publics d'une variable.<br>
 * Une variable peut -être de deux types:
 * <ul>
 * 	<li>Un registre temporaire pour stockage d'un resultat intermédiaire</li>
 * 	<li>Une variable originale</li>
 * </ul>
 *
 * @author Jean-Baptiste Voron
 */
public interface IVariable extends IExpressionSimple {

	/**
	 * @return Le registre utilisé.
	 */
	String getRegister();

	/**
	 * @return la description XMl de la variable
	 * @param level Niveau d'indentation
	 */
	StringBuffer toXML(int level);
}
