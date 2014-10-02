package fr.lip6.move.evinrude.optimizer.conditions;

import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;

/**
 * Condition pour la réduction d'un modèle :
 * <ul>
 * 	<li>Le noeud ne doit pas avoir de successeur</li>
 * </ul>
 *
 * @author Jean-Baptiste Voron
 */
public class NoSuccessor extends Condition {
	/**
	 * Le noeud n'a-t-il <b>aucun</b> successeur ?
	 * @param reference Le noeud sur lequel s'applique la condition
	 */
	public NoSuccessor(int reference) {
		super(reference);
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean isVerify(INode ref) {
		for (INode node : this.computeSubSet(ref)) {
			if (node.getNextNodes().size() > 0) { return false; }
		}
		return true;
	}
}
