package fr.lip6.move.evinrude.optimizer.rules;

import fr.lip6.move.evinrude.commons.exceptions.EvinrudeException;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IArc;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ITransition;
import fr.lip6.move.evinrude.optimizer.Reduction;
import fr.lip6.move.evinrude.optimizer.conditions.ICondition;
import fr.lip6.move.evinrude.optimizer.conditions.SinglePredecessor;
import fr.lip6.move.evinrude.optimizer.conditions.SingleSuccessor;
import fr.lip6.move.evinrude.optimizer.conditions.TypedPlaceCondition;

/**
 * Définition d'une réduction structurelle hiérarchique : <b>Useless Path Place</b><br>
 * Cette réduction est appliquée sur les place de contrôme de retour de fonction dont l'appel a été supprimé
 *
 * @author Jean-Baptiste Voron
 */
public class PreReducStruct4 extends Reduction {

	/**
	 * Constructeur
	 */
	public PreReducStruct4() {
		super("Useless Path Place");
		setType(HIERARCHICAL);

		addCondition(new TypedPlaceCondition(ICondition.SELF, IPlace.PATH));
		addCondition(new SingleSuccessor(ICondition.PRED));
		addCondition(new SinglePredecessor(ICondition.SUCC));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void performReduction(IModel model, INode self) throws EvinrudeException {
		LOG.info(">\tReduction Rule: " + this.getName());

		ITransition callTransition = (ITransition) self.getPreviousNodes().get(0);
		ITransition returnTransition = (ITransition) self.getNextNodes().get(0);

		ITransition newStruct = self.getSubModelContainer().createTransition("tempo_path_" + self.getName(), ITransition.STRUCTURAL);

		for (IArc arc : callTransition.getIncomingArcs()) { self.getSubModelContainer().createArc(arc.getSource(), newStruct); }
		for (IArc arc : returnTransition.getOutgoingArcs()) { self.getSubModelContainer().createArc(newStruct, arc.getTarget()); }

		// Suppression de la transition call
		LOG.fine("\tSuppression de la transition " + callTransition.getName());
		self.getSubModelContainer().removeNode(callTransition);

		// Suppression de la transition return
		LOG.fine("\tSuppression de la transition " + returnTransition.getName());
		self.getSubModelContainer().removeNode(returnTransition);

		// Suppression de la place path
		LOG.fine("\tSuppression de la place " + self.getName());
		self.getSubModelContainer().removeNode(self);
	}
}
