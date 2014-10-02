package fr.lip6.move.evinrude.optimizer.rules;

import fr.lip6.move.evinrude.commons.exceptions.EvinrudeException;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ITransition;
import fr.lip6.move.evinrude.optimizer.Reduction;
import fr.lip6.move.evinrude.optimizer.conditions.ICondition;
import fr.lip6.move.evinrude.optimizer.conditions.NoMarkingValue;
import fr.lip6.move.evinrude.optimizer.conditions.SinglePredecessor;
import fr.lip6.move.evinrude.optimizer.conditions.SingleSuccessor;
import fr.lip6.move.evinrude.optimizer.conditions.TypedPlaceCondition;
import fr.lip6.move.evinrude.optimizer.conditions.TypedTransitionCondition;

/**
 * Définition d'une réduction structurelle hiérarchique : <b>Dummy Functions</b><br>
 * Cette réduction est appliquée sur une fonction lorsque celle ci ne contient qu'une transition structurelle.
 *
 * @author Jean-Baptiste Voron
 */
public class PreReducStruct5 extends Reduction {

	/**
	 * Constructeur
	 */
	public PreReducStruct5() {
		super("Dummy Functions");
		setType(HIERARCHICAL);

		addCondition(new TypedTransitionCondition(ICondition.SELF, ITransition.STRUCTURAL));
		addCondition(new SingleSuccessor(ICondition.SELF));
		addCondition(new SinglePredecessor(ICondition.SELF));

		addCondition(new TypedPlaceCondition(ICondition.PRED, IPlace.FUNCTIONENTRY));
		addCondition(new NoMarkingValue(ICondition.PRED));
		addCondition(new SingleSuccessor(ICondition.PRED));

		addCondition(new TypedPlaceCondition(ICondition.SUCC, IPlace.FUNCTIONEXIT));
		addCondition(new SinglePredecessor(ICondition.SUCC));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void performReduction(IModel model, INode self) throws EvinrudeException {
		LOG.info(">\tReduction Rule: " + this.getName());

		// Suppression de la place entry
		LOG.fine("\tSuppression de la place " + self.getPreviousNodes().get(0).getName());
		self.getSubModelContainer().removeNode(self.getPreviousNodes().get(0));

		// Suppression de la place exit
		LOG.fine("\tSuppression de la place " + self.getNextNodes().get(0).getName());
		self.getSubModelContainer().removeNode(self.getNextNodes().get(0));

		// Suppression de la transition
		LOG.fine("\tSuppression de la transition " + self.getName());
		self.getSubModelContainer().removeNode(self);
	}
}
