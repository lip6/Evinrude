package fr.lip6.move.evinrude.commons.model.cfg.interfaces;

import java.util.List;

/**
 * Description des comportements publics d'un appel de fonction.<br>
 * Les informations accessibles sont:
 * <ul>
 * 	<li>Le nom de l'appel (le nom de la fonction appellée)</li>
 * 	<li>Les paramètres de l'appel</li>
 * </ul>
 *
 * Pour aider la construction du modèle, on associe un type à chaque callsite.
 * <ul>
 * 	<li>Type inconnu: On essaye alors de déterminer qu'elle est la fonction appelée</li>
 * 	<li>Type application: La fonction appelée fait partie de l'application</li>
 * 	<li>Type library: La fonction appelée fait partie d'une bibliothèque</li>
 * </ul>
 *
 * @author Jean-Baptiste Voron
 */
public interface ICall extends IInstruction, IOperand {
	/** Type associé à un registre */
	int TYPE_UNKNOWN = 0;

	/** Type associé à une variable */
	int TYPE_APPLICATION = 1;

	/** Type associé à une variable */
	int TYPE_LIBRARY = 2;

	/**
	 * @return le nom de l'appel
	 */
	String getFunctionName();

	/**
	 * @return la liste des paramètres
	 */
	List<IExpressionSimple> getParameters();
}
