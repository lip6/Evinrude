package fr.lip6.move.evinrude.optimizer.conditions;

import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;

/**
 * Condition pour la réduction d'un modèle :
 * <ul>
 * 	<li>L'objet doit être une place</li>
 * 	<li>La place doit être du type requis</li>
 * </ul>
 * @see IPlace
 *
 * @author Jean-Baptiste Voron
 */
public class TypedPlaceCondition extends Condition {
	private int askedType;

	/**
	 * La place doit être du type requis
	 * @param reference Le noeud sur lequel s'applique la condition
	 * @param askedType Le type dont doit être la place référence
	 */
	public TypedPlaceCondition(int reference, int askedType) {
		super(reference);
		this.askedType = askedType;
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean isVerify(INode ref) {
		for (INode node : this.computeSubSet(ref)) {
			if (node.isTransition()) { return false; }
			IPlace place = (IPlace) node;
			if (place.isNotTyped(this.askedType)) { return false; }
		}
		return true;
	}
}
