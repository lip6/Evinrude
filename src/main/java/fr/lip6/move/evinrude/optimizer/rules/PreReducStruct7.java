package fr.lip6.move.evinrude.optimizer.rules;

import fr.lip6.move.evinrude.commons.exceptions.EvinrudeException;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ITransition;
import fr.lip6.move.evinrude.optimizer.Reduction;
import fr.lip6.move.evinrude.optimizer.conditions.FlatPlaceCondition;
import fr.lip6.move.evinrude.optimizer.conditions.ICondition;
import fr.lip6.move.evinrude.optimizer.conditions.Loop;
import fr.lip6.move.evinrude.optimizer.conditions.SinglePredecessor;
import fr.lip6.move.evinrude.optimizer.conditions.SingleSuccessor;
import fr.lip6.move.evinrude.optimizer.conditions.TypedPlaceCondition;
import fr.lip6.move.evinrude.optimizer.conditions.TypedTransitionCondition;

/**
 * Définition d'une réduction structurelle hiérarchique : <b>Transitions implicites</b><br>
 * @see ReducStruct3
 *
 * @author Jean-Baptiste Voron
 */

public class PreReducStruct7 extends Reduction {

	/**
	 * Constructeur
	 */
	public PreReducStruct7() {
		super("Hierarchical Implicit Transitions");
		setType(HIERARCHICAL);

		addCondition(new TypedTransitionCondition(ICondition.SELF, ITransition.STRUCTURAL));
		addCondition(new SingleSuccessor(ICondition.SELF));
		addCondition(new SinglePredecessor(ICondition.SELF));
		addCondition(new Loop(ICondition.SELF));

		addCondition(new FlatPlaceCondition(ICondition.SUCC));
		addCondition(new TypedPlaceCondition(ICondition.SUCC, IPlace.NORMAL));
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
