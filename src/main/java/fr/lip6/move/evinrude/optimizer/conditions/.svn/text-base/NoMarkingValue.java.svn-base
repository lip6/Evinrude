package fr.lip6.move.evinrude.optimizer.conditions;

import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;

/**
 * Condition pour la réduction d'un modèle :
 * <ul>
 * 	<li>La place <b>ne doit pas être</b> marquée (initialement)</li>
 * </ul>
 *
 * @author Jean-Baptiste Voron
 */
public class NoMarkingValue extends Condition {

	/**
	 * La place <b>ne doit pas</b> être marquée.
	 * @param reference Le choix du noeud sur lequel s'applique la condition
	 */
	public NoMarkingValue(int reference) {
		super(reference);
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean isVerify(INode ref) {
		for (INode node : this.computeSubSet(ref)) {
			if (node.isTransition()) { return false; }
			if (!((IPlace) node).getInitialMarking().equals("")) { return false; }
		}
		return true;
	}
}
