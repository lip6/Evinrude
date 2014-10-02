package fr.lip6.move.evinrude.commons.model.cfg.interfaces;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Définition des comportements publics d'une <b>application</b>.
 * @author Jean-Baptiste Voron
 */
public interface IApplication {

	/**
	 * @return le nom de l'application
	 */
	String getName();

	/**
	 * @return dossier contenant les tous les fichiers générés pour cette application
	 */
	File getFolder();

	/**
	 * @return La liste des CFGs contenus dans l'application
	 */
	List<ICfg> getCfgs();

	/**
	 * Ajoute un CFG dans l'application<br>
	 * Le CFG est créé par l'application
	 * @param cfg Le CFG à ajouter à la liste des CFG
	 */
	void addCfg(ICfg cfg);

	/**
	 * Cherche une fonction (d'un CFG) dont le nom est passé en paramètre
	 * @param name Le nom de la fonction recherchée
	 * @return une liste de fonctions correspondantes, <code>null</code> sinon
	 */
	List<IFunction> getFunction(String name);

	/**
	 * Supprime les élements inutiles de l'application<br>
	 * <ul>
	 * 	<li>Fonctions GNU</li>
	 * </ul>
	 */
	void removeGnuFunctions();


	/**
	 * @param writer La classe chargée de l'écriture des logs
	 * @throws IOException en cas de problème d'écriture
	 * @return la description XML de l'application
	 */
	StringBuffer toXML(FileWriter writer) throws IOException;

	/**
	 * @return liste des exécutables de cette application
	 */
	List<IExecutable> getExecutables();

	/**
	 * Enregistre la valeur d'un timer
	 * @param timerType Le type du timer enregistré
	 * @param timerValue La valeur du timer enregistré
	 */
	void setTimer(int timerType, long timerValue);

	/**
	 * @return La liste de tous les timers enregistrés pour cette application
	 */
	Map<Integer, Long> getTimers();

	/**
	 * @param filter filtre à ajouter
	 */
	void addExecutableFilter(String filter);

	/**
	 * @param filters filtres à ajouter
	 */
	void addExecutablesFilter(List<String> filters);
}
