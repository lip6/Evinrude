package fr.lip6.move.evinrude.optimizer.rules;

import fr.lip6.move.evinrude.commons.exceptions.EvinrudeException;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ITransition;
import fr.lip6.move.evinrude.optimizer.Reduction;
import fr.lip6.move.evinrude.optimizer.conditions.ICondition;
import fr.lip6.move.evinrude.optimizer.conditions.SinglePredecessor;
import fr.lip6.move.evinrude.optimizer.conditions.SingleSuccessor;
import fr.lip6.move.evinrude.optimizer.conditions.TwinCondition;
import fr.lip6.move.evinrude.optimizer.conditions.TypedTransitionCondition;

/**
 * Définition d'une réduction structurelle de base : <b>Diamants</b><br>
 *
 * @author Jean-Baptiste Voron
 */

public class PreReducStruct8 extends Reduction {

	/**
	 * Constructeur
	 */
	public PreReducStruct8() {
		super("Hierarchical Diamonds");
		setType(HIERARCHICAL);

		addCondition(new TypedTransitionCondition(ICondition.SELF, ITransition.STRUCTURAL));
		addCondition(new SingleSuccessor(ICondition.SELF));
		addCondition(new SinglePredecessor(ICondition.SELF));
		addCondition(new TwinCondition(ICondition.SELF));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void performReduction(IModel model, INode self) throws EvinrudeException {
		LOG.info(">\tReduction Rule: " + this.getName());

		// Suppression de la transition
		self.getSubModelContainer().removeNode(self);
		LOG.fine("\tSuppression de la transition " + self.getName());
	}
}
