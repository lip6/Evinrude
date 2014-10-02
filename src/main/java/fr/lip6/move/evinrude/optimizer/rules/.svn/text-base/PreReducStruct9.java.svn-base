package fr.lip6.move.evinrude.optimizer.rules;

import fr.lip6.move.evinrude.commons.exceptions.EvinrudeException;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.optimizer.Reduction;
import fr.lip6.move.evinrude.optimizer.conditions.FlatPlaceCondition;
import fr.lip6.move.evinrude.optimizer.conditions.ICondition;
import fr.lip6.move.evinrude.optimizer.conditions.NoMarkingValue;
import fr.lip6.move.evinrude.optimizer.conditions.NoPredecessor;
import fr.lip6.move.evinrude.optimizer.conditions.NoSuccessor;
import fr.lip6.move.evinrude.optimizer.conditions.TypedPlaceCondition;

/**
 * Définition d'une réduction structurelle de base : <b>Diamants</b><br>
 *
 * @author Jean-Baptiste Voron
 */

public class PreReducStruct9 extends Reduction {

	/**
	 * Constructeur
	 */
	public PreReducStruct9() {
		super("Lonely Places");
		setType(HIERARCHICAL);

		addCondition(new TypedPlaceCondition(ICondition.SELF, IPlace.NORMAL | IPlace.FUNCTIONENTRY | IPlace.FUNCTIONEXIT));
		addCondition(new FlatPlaceCondition(ICondition.SELF));
		addCondition(new NoMarkingValue(ICondition.SELF));
		addCondition(new NoSuccessor(ICondition.SELF));
		addCondition(new NoPredecessor(ICondition.SELF));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void performReduction(IModel model, INode self) throws EvinrudeException {
		LOG.info(">\tReduction Rule: " + this.getName() + " >> " + self.getName());

		// Suppression de la transition
		self.getSubModelContainer().removeNode(self);
		LOG.fine("\tSuppression de la place " + self.getName());
	}
}
