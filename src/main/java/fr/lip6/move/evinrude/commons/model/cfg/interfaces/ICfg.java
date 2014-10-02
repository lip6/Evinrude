package fr.lip6.move.evinrude.commons.model.cfg.interfaces;

import java.util.List;

/**
 * Définition des comportements publics d'un CFG<br>
 * @author Jean-Baptiste Voron
 */
public interface ICfg {

	/**
	 * @return le nom du fichier duquel est extrait le CFG
	 */
	String getFileName();

	/**
	 * @return le nom du fichier (mais sans l'extension)
	 */
	String getName();

	/**
	 * @return l'identifiant numérique du CFG
	 */
	Integer getId();

	/**
	 * @return l'application qui contient le CFG
	 */
	IApplication getApplication();

	/**
	 * Création d'une fonction à l'intérieur de ce CFG
	 * @param id l'identifiant de la fonction à créer
	 * @param name le nom de la fonction à créer
	 * @return La fonction fraîchement créée
	 */
	IFunction createFunction(Integer id, String name);

	/**
	 * @return la liste des fonctions du CFG
	 */
	List<IFunction> getFunctions();

	/**
	 * Recherche une fonction nommée <code>name</code> dans le CFG
	 * @param name Le nom de la fonction recherchée
	 * @return la fonction si elle existe ou <code>null</code>
	 */
	IFunction getFunction(String name);

	/**
	 * Supprime la fonction désignée du CFG
	 * @param name Le nom de la fonction à supprimer
	 */
	void removeFunction(String name);

	/**
	 * @return la description XML du CFG
	 * @param level Niveau d'indentation
	 */
	StringBuffer toXML(int level);

	/**
	 * @return le nombre de ligne du fichier cfg
	 */
	int getLineNb();

	/**
	 * @param lineNb le nombre de ligne du fichier cfg parsé
	 */
	void setLineNb(int lineNb);

	/**
	 * @return le nombre de ligne couverte par le parser
	 */
	int getCoveredLineNb();

	/**
	 * @param coveredLineNb le nombre de ligne couverte par le parser
	 */
	void setCoveredLineNb(int coveredLineNb);
}
