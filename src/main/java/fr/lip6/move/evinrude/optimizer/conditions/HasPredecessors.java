package fr.lip6.move.evinrude.optimizer.conditions;

import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;

/**
 * Condition pour la réduction d'un modèle :
 * <ul>
 * 	<li>Le noeud doit avoir <b>un></b> ou <b>plusieurs</b> predecesseurs</li>
 * </ul>
 *
 * @author Jean-Baptiste Voron
 */
public class HasPredecessors extends Condition {
	/**
	 * Le noeud a-t-il <b>un</b> ou <b>plusieurs</b> prédécesseurs ?
	 * @param reference Le noeud sur lequel s'applique la condition
	 */
	public HasPredecessors(int reference) {
		super(reference);
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean isVerify(INode ref) {
		for (INode node : this.computeSubSet(ref)) {
			if (node.getPreviousNodes().size() <= 0) { return false; }
		}
		return true;
	}
}
