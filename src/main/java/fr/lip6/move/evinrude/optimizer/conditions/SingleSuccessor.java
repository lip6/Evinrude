package fr.lip6.move.evinrude.optimizer.conditions;

import fr.lip6.move.evinrude.commons.model.petri.interfaces.IArc;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IValuation;

/**
 * Condition pour la réduction d'un modèle :
 * <ul>
 * 	<li>Le noeud doit avoir <b>un seul</b> successeur</li>
 * </ul>
 *
 * @author Jean-Baptiste Voron
 */
public class SingleSuccessor extends Condition {
	/**
	 * Le noeud a-t-il <b>un</b> et <b>un seul</b> successeur ?
	 * @param reference Le noeud sur lequel s'applique la condition
	 */
	public SingleSuccessor(int reference) {
		super(reference);
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean isVerify(INode ref) {
		for (INode node : this.computeSubSet(ref)) {
			if (node.getNextNodes().size() != 1) { return false; }
			if (ref.getOutgoingArcs().get(0).getType() != IArc.NORMAL) { return false; }
			if (ref.getOutgoingArcs().get(0).getValuation().getType() != IValuation.DEFAULT_VALUATION) { return false; }
		}
		return true;
	}
}
