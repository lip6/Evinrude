package fr.lip6.move.evinrude.optimizer.conditions;

import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;

/**
 * Condition pour la réduction d'un modèle :
 * <ul>
 * 	<li>Le noeud doit avoir <b>un></b> ou <b>plusieurs</b> successeurs</li>
 * </ul>
 *
 * @author Jean-Baptiste Voron
 */
public class HasSuccessors extends Condition {

	/**
	 * Le noeud doit avoir <b>un</b> ou <b>plusieurs</b> successeurs.
	 * @param reference Le choix du noeud sur lequel s'applique la condition
	 */
	public HasSuccessors(int reference) {
		super(reference);
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean isVerify(INode ref) {
		for (INode node : this.computeSubSet(ref)) {
			if (node.getNextNodes().size() <= 0) { return false; }
		}
		return true;
	}
}
