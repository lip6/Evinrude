package fr.lip6.move.evinrude.commons.model.cfg.interfaces;

import java.util.List;

/**
 * Définition des comportements publics d'une fonction<br>
 *
 * @author Jean-Baptiste Voron
 */
public interface IFunction {

	/**
	 * Création d'un bloc à l'intérieur de la fonction
	 * @param id l'identifiant du bloc à créer
	 * @return le bloc fraîchement créé
	 */
	IBlock createBlock(String id);

	/**
	 * Création d'un bloc à l'intérieur de la fonction
	 * @param id l'identifiant du bloc à créer
	 * @param startingLine ligne dans le fichier source où commence le block
	 * @return le bloc fraîchement créé
	 */
	IBlock createBlock(String id, int startingLine);

	/**
	 * Cherche et retourne le bloc identifié par <code>id</code><br>
	 * Si le bloc n'existe pas dans la liste des blocs de la fonction, il est automatiquement créé
	 * @param id l'identifiant du bloc cherché
	 * @return le bloc recherché
	 */
	IBlock getBlock(String id);

	/**
	 * @return l'identifiant de la fonction
	 */
	Integer getId();

	/**
	 * @return le nom de la fonction
	 */
	String getName();

	/**
	 * @return la liste de tous les blocs de la fonction
	 */
	List<IBlock> getBlocks();

	/**
	 * @return Le CFG associé à la fonction
	 */
	ICfg getCfg();

	/**
	 * @return liste des variables
	 */
	List<String> getVariables();

	/**
	 * @return Liste des parametres
	 */
	List<String> getParameters();

	/**
	 * Ajoute une variable à la liste des variables de la fonction
	 * @param name Le nom de la variable
	 */
	void addVariable(String name);

	/**
	 * Ajoute un paramètre à la liste des paramètres de la fonction
	 * @param parameterName Le nom du paramètre
	 */
	void addParameters(String parameterName);

	/**
	 * @return la description XML de la fonction
	 * @param level Niveau d'indentation
	 */
	StringBuffer toXML(int level);
}
