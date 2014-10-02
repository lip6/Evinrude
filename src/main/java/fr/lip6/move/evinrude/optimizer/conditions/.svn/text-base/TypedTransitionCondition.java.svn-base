package fr.lip6.move.evinrude.optimizer.conditions;

import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ITransition;

/**
 * Condition pour la réduction d'un modèle :
 * <ul>
 * 	<li>Le noeud doit être une transition</li>
 * 	<li>La transition doit être du type requis</li>
 * </ul>
 *
 * @author Jean-Baptiste Voron
 */
public class TypedTransitionCondition extends Condition {
	private int askedType;
	/**
	 * La transition est-elle une transition du type requis ?
	 * @param reference Le noeud sur lequel s'applique la condition
	 * @param askedType Le type que doit satisfaire la transition référence
	 */
	public TypedTransitionCondition(int reference, int askedType) {
		super(reference);
		this.askedType = askedType;
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean isVerify(INode ref) {
		for (INode node : this.computeSubSet(ref)) {
			// Transition
			if (node.isPlace()) { return false; }
			ITransition transition = (ITransition) node;
			// Structurelle
			if (transition.isNotTyped(this.askedType)) { return false; }
		}
		return true;
	}
}
