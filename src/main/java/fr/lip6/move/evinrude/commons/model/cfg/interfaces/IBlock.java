package fr.lip6.move.evinrude.commons.model.cfg.interfaces;

import java.util.Collection;
import java.util.Map;

/**
 * Définition des comportements publics d'un <b>bloc</b>.
 * @author Jean-Baptiste Voron
 */
public interface IBlock {

	/**
	 * @return l'identifiant du bloc
	 */
	String getId();

	/**
	 * @return la ligne associée au début du bloc
	 */
	int getStartingLine();

	/**
	 * <b>WARNING !</b> Cette méthode ne doit pas être utilisée !<br>
	 * Modifie la ligne où débute le bloc.
	 * @param startingLine la ligne où commence le bloc
	 */
	void setStartingLine(int startingLine);

	/**
	 * @return la fonction parente du bloc
	 */
	IFunction getFunction();

	/**
	 * Ajoute un successeur au bloc courant
	 * @param id l'identifiant du bloc à ajouter
	 */
	void addSuccessor(String id);

	/**
	 * Ajoute un prédécesseur au bloc courant
	 * @param id l'identifiant du bloc à ajouter
	 */
	void addPredecessor(String id);

	/**
	 * Ajoute un mot-clé au bloc. Le mot clé désigne une fonction.
	 * @param call Appel de méthode à ajouter
	 */
	void addCall(ICall call);

	/**
	 * Ajoute une affectation à la liste déjà existante
	 * @param assign Affectation à ajouter
	 */
	void addAssignement(IAssignement assign);

	/**
	 * Ajoute une condition de passage aux blocs suivants
	 * @param condition La condition {@link ICondition}
	 */
	void addConditionnal(ICondition condition);

	/**
	 * @return une liste de toutes les fonctions locales à l'application appelées depuis ce bloc
	 */
	Map<Integer, IFunction> getLocalFunctionCalls();

	/**
	 * @return une liste des tous les appels à des fonctions non définies dans l'application
	 */
	Map<Integer, ICall> getLibraryCalls();

	/**
	 * @return La liste des successeurs du bloc courant
	 */
	Collection<IBlock> getSuccessors();

	/**
	 * @return la liste des prédécesseurs du bloc courant
	 */
	Collection<IBlock> getPredecessors();

	/**
	 * @return la condition de passage aux blocs suivants si elle existe
	 */
	ICondition getCondition();

	/**
	 * @return la liste des instructions du bloc courant
	 */
	Map<Integer, IInstruction> getInstructions();

	/**
	 * @return la description XML du Block
	 * @param level Niveau d'indentation
	 */
	StringBuffer toXML(int level);
}
