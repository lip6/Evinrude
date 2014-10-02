package fr.lip6.move.evinrude.optimizer.rules;

import fr.lip6.move.evinrude.commons.exceptions.EvinrudeException;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.optimizer.Reduction;
import fr.lip6.move.evinrude.optimizer.conditions.EmptyTargetCondition;
import fr.lip6.move.evinrude.optimizer.conditions.ICondition;
import fr.lip6.move.evinrude.optimizer.conditions.TypedPlaceCondition;

/**
 * Définition d'une réduction structurelle hiérarchique: <b>Empty Virtual Target</b><br>
 * Cette réduction concerne les places virtuelles donc la cible n'existe plus (suite à une réduction).
 *
 * @author Jean-Baptiste Voron
 */
public class PreReducStruct3 extends Reduction {

	/**
	 * Constructeur
	 */
	public PreReducStruct3() {
		super("Empty Virtual Target (aka. Phantom Zone)");
		setType(HIERARCHICAL);

		// Ajout des conditions
		addCondition(new TypedPlaceCondition(ICondition.SELF, IPlace.VIRTUAL));
		addCondition(new EmptyTargetCondition(ICondition.SELF));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void performReduction(IModel model, INode self) throws EvinrudeException {
		LOG.info(">\tReduction Rule: " + this.getName());

		// Suppression de la place entry
		LOG.fine("\tSuppression de la place " + self.getName());
		self.getSubModelContainer().removeNode(self);
	}
}
