package fr.lip6.move.evinrude.commons.model.petri.interfaces;

import java.util.List;



/**
 * Définition des comportements publics d'un noeud.<br>
 * Rappel : Un noeud peut-être de différent types :
 * <ul>
 * 	<li>Une place</li>
 * 	<li>Une transition</li>
 * </ul>
 */
public interface INode {

	/**
	 * @param flag check this flag
	 * @return <code>true</code> if this place is typed with this flag
	 */
	boolean isTyped(int flag);

	/**
	 * Change le type du noeud.<br>
	 * <b>A utiliser avec beaucoup de précaution !</b>
	 * Attention à l'interface utilisée pour définir le type...
	 * @param newType nouveau type de l'objet
	 * @see IPlace
	 * @see ITransition
	 */
	void setType(int newType);

	/**
	 * Retourne le type du noeud.<br>
	 * <b>A utiliser avec beaucoup de précaution !</b>
	 * Préférez l'utilisation de {@link #isTyped(int)}.
	 * @return type du nœud sous forme d'une combinaison de flag
	 */
	int getType();

	/**
	 * @return Le sous-modèle contenant ce nœud
	 */
	ISubModel getSubModelContainer();

	/**
	 * @return le nom du noeud
	 */
	String getName();

	/**
	 * @return la liste des arcs entrants du noeud courant
	 */
	List<IArc> getIncomingArcs();

	/**
	 * @return la liste des arcs sortants du noeud courant
	 */
	List<IArc> getOutgoingArcs();

	/**
	 * @return Nœuds précédent ce nœud
	 */
	List<INode> getPreviousNodes();

	/**
	 * @return Nœuds suivant ce nœud
	 */
	List<INode> getNextNodes();

	/**
	 * @return <code>true</code> si ce nœud est une place.
	 */
	boolean isPlace();

	/**
	 * @return <code>true</code> si ce nœud est une transition.
	 */
	boolean isTransition();

	/**
	 * @param flag combinaison de type (utilisez l'opérateur |)
	 * @return <code>true</code> si le type de ce nœud n'est d'aucun type contenu dans le flag, <code>false</code> sinon
	 */
	boolean isNotTyped(int flag);

}
