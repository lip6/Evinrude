package fr.lip6.move.evinrude.optimizer.conditions;

import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;

/**
 * Condition pour la réduction d'un modèle :
 * <ul>
 * 	<li>L'objet doit être une place</li>
 * 	<li>La place ne doit contenir aucun sous-modèle</li>
 * </ul>
 * @see IPlace
 *
 * @author Jean-Baptiste Voron
 */
public class FlatPlaceCondition extends Condition {
	/**
	 * Le noeud doit être une <b>place</b> et ne doit contenir <b>aucun sous-modèle</b>.
	 * @param reference Le noeud sur lequel s'applique la condition
	 */
	public FlatPlaceCondition(int reference) {
		super(reference);
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean isVerify(INode ref) {
		for (INode node : this.computeSubSet(ref)) {
			if (!node.isPlace()) { return false; }
			if (!(((IPlace) node).getSubModels().size() == 0)) { return false; }
		}
		return true;
	}
}
