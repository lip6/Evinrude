package fr.lip6.move.evinrude.optimizer.conditions;

import fr.lip6.move.evinrude.commons.model.petri.interfaces.IArc;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IValuation;

/**
 * Condition pour la réduction d'un modèle :
 * <ul>
 * 	<li>Le noeud doit avoir exactement un arc entrant</li>
 * 	<li>Le noeud doit avoir exactement un arc sortant</li>
 * 	<li>Le noeud d'entrée et le noeud de sortie doivent être les mêmes</li>
 * </ul>
 *
 * @author Jean-Baptiste Voron
 */
public class Loop extends Condition {
	/**
	 * Le noeud d'entrée et le noeud de sortie <b>doivent être les mêmes</b>
	 * @param reference Le noeud sur lequel s'applique la condition
	 */
	public Loop(int reference) {
		super(reference);
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean isVerify(INode ref) {
		if (ref.getPreviousNodes().size() != 1) { return false; }
		if (ref.getNextNodes().size() != 1) { return false; }

		// Les arcs doivent être normaux
		if (ref.getIncomingArcs().get(0).getType() != IArc.NORMAL) { return false; }
		if (ref.getOutgoingArcs().get(0).getType() != IArc.NORMAL) { return false; }

		// Les arcs ne doivent pas avoir de valuation
		if (ref.getIncomingArcs().get(0).getValuation().getType() != IValuation.DEFAULT_VALUATION) { return false; }
		if (ref.getOutgoingArcs().get(0).getValuation().getType() != IValuation.DEFAULT_VALUATION) { return false; }

		// Le noeud d'entrée et le noeud de sortie doivent être le même
		if (!ref.getPreviousNodes().get(0).equals(ref.getNextNodes().get(0))) { return false; }

		return true;
	}
}
