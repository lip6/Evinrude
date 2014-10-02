package fr.lip6.move.evinrude.commons.model.petri.interfaces;

import fr.lip6.move.evinrude.commons.exceptions.EvinrudeException;

import java.util.Collection;
import java.util.List;


/**
 * Définition d'un sous-modèle (d'un réseau de Petri)
 */
public interface ISubModel {

	/**
	 * @return Le modèle de plus haut niveau
	 */
	IModel getTopModel();

	/**
	 * @return Place à laquelle est rattachée ce sous modèle ou <code>null</code> si on est à la racine.
	 */
	IPlace getParentPlace();

	/**
	 * @return Numero d'instruction identifiant ce sous modèle.
	 */
	int getInstructionNumber();


	/**
	 * @param source source
	 * @param target cible
	 * @param type type de l'arc, voir {@link IArc}
	 * @return arc créé
	 * @throws EvinrudeException Si la source et la target sont du même type
	 */
	IArc createArc(INode source, INode target, int type) throws EvinrudeException;

	/**
	 * @param source source
	 * @param target cible
	 * @return arc créé
	 * @throws EvinrudeException Si la source et la target sont du même type
	 */
	IArc createArc(INode source, INode target) throws EvinrudeException;

	/**
	 * @return Liste des arcs
	 */
	Collection<IArc> getArcs();


	/**
	 * Créé une place
	 * @param name Nom de la place
	 * @param type type de la place, voir {@link IPlace}
	 * @return la place créée
	 * @throws EvinrudeException Si cette place existe déjà
	 */
	IPlace createPlace(String name, int type) throws EvinrudeException;

	/**
	 * Créé une place virtuelle
	 * @param name Nom de la place virtuelle
	 * @param targetName nom de la place cible
	 * @return la place créée
	 * @throws EvinrudeException Si cette place existe déjà
	 */
	IPlace createPlace(String name, String targetName) throws EvinrudeException;

	/**
	 * Créé une place à partir d'une autre
	 * @param place Place à copier
	 * @return la place créée
	 * @throws EvinrudeException Si cette place existe déjà
	 */
	IPlace createPlace(IPlace place) throws EvinrudeException;

	/**
	 * Recherche une place.
	 * @param name Nom de la place
	 * @return La place recherchée ou <code>null</code>
	 */
	IPlace getPlace(String name);

	/**
	 * @return Liste de toutes les places
	 */
	Collection<IPlace> getPlaces();


	/**
	 * Créé une transition
	 * @param name Nom de la transition
	 * @param type Type de la transition, voir {@link ITransition}
	 * @return Transition créée
	 * @throws EvinrudeException Cette transition existe déjà
	 */
	ITransition createTransition(String name, int type) throws EvinrudeException;

	/**
	 * Créé une transition à partir d'une transition
	 * @param transition Transition à copier
	 * @return Transition créée
	 * @throws EvinrudeException Cette transition existe déjà
	 */
	ITransition createTransition(ITransition transition) throws EvinrudeException;

	/**
	 * Recherche une transition
	 * @param name Nom de la transition
	 * @return La transition ou <code>null</code>
	 */
	ITransition getTransition(String name);

	/**
	 * @return Liste des transitions
	 */
	Collection<ITransition> getTransitions();

	/**
	 * @return Liste des places entrantes
	 */
	IPlace getInputPlace();

	/**
	 * @return Liste des places sortantes
	 */
	List<IPlace> getOutputPlaces();

	/**
	 * Recherche un noeud dans <b>le sous-modèle courant</b> et dans <b>ses sous-modèles</b>.<br>
	 * Pour une recherche globale, il faut appeler cette méthode depuis le modèle de haut niveau.
	 * @see ISubModel#getTopModel()
	 * @param name nom du nœud recherché
	 * @return Le nœud correspondant ou <code>null</code> si il n'existe pas
	 */
	INode getNode(String name);

	/**
	 * @return Liste de nœuds dans ce niveau de hiérarchie
	 */
	Collection<INode> getNodes();

	/**
	 * Intègre tous les éléments d'un sous-modèle (fils) dans le modèle de haut-niveau (père)
	 * @param submodel Le sous-modèle qui doit être intégré
	 * @throws EvinrudeException Si une construction d'arc échoue
	 */
	void integrateSubModel(ISubModel submodel) throws EvinrudeException;

	/**
	 * @param arc arc à supprimer
	 * @return arc supprimé ou <code>null</code>
	 */
	IArc removeArc(IArc arc);

	/**
	 * Supprime un nœud, si le nœud est une place contenant des sous-modèles on ne peut pas faire la suppression
	 * @param node nœud à supprimer
	 * @return nœud supprimé ou <code>null</code>
	 */
	INode removeNode(INode node);

	/**
	 * Recherche des arcs entre deux nœuds
	 * @param source source de l'arc
	 * @param target cible de l'arc
	 * @return arcs recherché ou une liste vide
	 */
	Collection<IArc> getArcs(INode source, INode target);

	/**
	 * Déplace le nœud dans ce sous-modèle.
	 * Attention, tous les arcs de ce nœud sont supprimé
	 * @param node nœud à déplacer
	 * @return instance du nœud dans ce sous-modèle
	 */
	INode moveNode(INode node);

	/**
	 * @param source nœud source
	 * @param target nœud cible
	 * @return un arc entre la source et la cible ou <code>null</code> si il n'y en a aucun.
	 * Attention si il y a plusieurs arcs, il n'y a aucune garantie sur celui qui va être retourné.
	 */
	IArc getFirstArc(INode source, INode target);
}
