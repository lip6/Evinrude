package fr.lip6.move.evinrude.optimizer.conditions;

import fr.lip6.move.evinrude.commons.model.petri.interfaces.IArc;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ITransition;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IValuation;

/**
 * Condition pour la réduction d'un modèle :
 * <ul>
 * 	<li>Le noeud doit avoir au plus un arc entrant</li>
 * 	<li>Le noeud doit avoir au plus un arc sortant</li>
 * 	<li>Le noeud d'entrée et le noeud de sortie doivent être différentes</li>
 * 	<li>Le noeud doit être une transition</li>
 * 	<li>Le frêre du noeud doit être du même type (type (formalism), type et garde)
 * </ul>
 *
 * @author Jean-Baptiste Voron
 */
public class SiblingCondition extends Condition {
	/**
	 * Constructeur
	 * @param reference Le choix du noeud sur lequel s'applique la condition
	 */
	public SiblingCondition(int reference) {
		super(reference);
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean isVerify(INode ref) {
		// Un successeur / Un prédécesseur
		if (ref.getPreviousNodes().size() != 1) { return false; }
		if (ref.getNextNodes().size() != 1) { return false; }

		// Les arcs doivent être normaux
		if (ref.getIncomingArcs().get(0).getType() != IArc.NORMAL) { return false; }
		if (ref.getOutgoingArcs().get(0).getType() != IArc.NORMAL) { return false; }

		// Les arcs ne doivent pas être valués
		if (ref.getIncomingArcs().get(0).getValuation().getType() != IValuation.DEFAULT_VALUATION) { return false; }
		if (ref.getOutgoingArcs().get(0).getValuation().getType() != IValuation.DEFAULT_VALUATION) { return false; }

		// Recherche du frêre
		INode parent = ref.getPreviousNodes().get(0);
		if (parent.getNextNodes().size() <= 1) { return false; }

		for (INode brother : parent.getNextNodes()) {
			if (brother == ref) { continue; }
			if (brother.getPreviousNodes().size() != 1) { continue; }
			if (brother.getNextNodes().size() != 1) { continue; }

			// Le fils du brother et de la ref doivent être différents.
			// Sinon TwinCondition
			if (brother.getNextNodes().get(0).equals(ref.getNextNodes().get(0))) { continue; }

			// Les 2 objets doivent être du même type (type (place|transition), type(struct|event), garde)
			if (ref.isTransition() != brother.isTransition()) { continue; }
			if (ref.getType() != brother.getType()) { continue; }

			if ((ref.isTransition()) && (brother.isTransition())) {
				ITransition tref = (ITransition) ref;
				ITransition tbrother = (ITransition) brother;
				// Si les guardes evenementielles sont nulles toutes les 2... Ce sont des transitions structurelles
				if ((tref.getEventGuard() == null) && (tbrother.getEventGuard() == null)) { return true; }
				// Pour traiter le cas de 2 transitions événementielles
				if ((tref.getEventGuard() != null) && (tbrother.getEventGuard() != null) && (tref.getEventGuard().getEvent().equals(tbrother.getEventGuard().getEvent()))) { return true; }
			}
		}
		return false;
	}
}
