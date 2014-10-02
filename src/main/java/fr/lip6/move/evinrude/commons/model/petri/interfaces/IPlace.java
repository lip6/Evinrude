package fr.lip6.move.evinrude.commons.model.petri.interfaces;

import fr.lip6.move.evinrude.commons.exceptions.EvinrudeException;

import java.util.Collection;
import java.util.List;

/**
 */
public interface IPlace extends INode {
	// TODO : Uniformiser les noms des types avec ITransition
	/** Type : Place normale */
	int NORMAL = 0;
	/** Type : Place qui fait référence à une autre place */
	int VIRTUAL = 1 << 1;
	/** Type : Place d'entrée de sous-modèle */
	int INPUT = 1 << 2;
	/** Type : Place de sortie de sous-modèle */
	int OUTPUT = 1 << 3;
	/** Type : Place remarquable contenant des ressources globales */
	int RESOURCE = 1 << 4;
	/** Type : Place d'entrée de fonction */
	int FUNCTIONENTRY = 1 << 5;
	/** Type : Place  de sortie de fonction */
	int FUNCTIONEXIT = 1 << 6;
	/** Type : Place de contrôle de retour */
	int PATH = 1 << 7;
	/** Type : Place de contrôle (intialisation à la configuration) */
	int SPECIAL = 1 << 8;

	/**
	 * @return la liste des noeuds atteignables depuis ce noeud
	 */
	List<ITransition> getNext();

	/**
	 * @return la liste des noeuds qui mènent à ce noeud
	 */
	List<ITransition> getPrevious();

	/**
	 * Indique le marquage initial de la place
	 * @param initialMarking Le marquage initial de la place
	 */
	void setInitialMarking(String initialMarking);

	/**
	 * @return le marquage initial de la place
	 */
	String getInitialMarking();

	/**
	 * Créer un sous-modèle attaché à cette place.
	 * @param instructionNumber instruction liée à ce sous-modèle.
	 * @return ISubModel créé
	 * @throws EvinrudeException Si un sous-modèle est déjà attaché à cette instruction
	 */
	ISubModel createSubModel(int instructionNumber) throws EvinrudeException;

	/**
	 * @param instructionNumber numero d'instruction
	 * @return liste des sous-modèles liés à cette instruction
	 */
	ISubModel getSubModel(int instructionNumber);

	/**
	 * @return liste des sous-modèles ordonné par instruction
	 */
	Collection<ISubModel> getSubModels();

	/**
	 * @return le nom d'une place cible ou <code>null</code>
	 */
	String getTargetName();

	/**
	 * Supprime tous les sous-modèles
	 */
	void removeSubModels();

	/**
	 * Supprime le sous-modèle désigné par con numéro d'instruction
	 * @param instructionNumber Lenuméro d'instruction du sous-modèle à supprimer
	 */
	void removeSubModel(int instructionNumber);
}
