package fr.lip6.move.evinrude.optimizer.rules;

import fr.lip6.move.evinrude.commons.exceptions.EvinrudeException;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IArc;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ITransition;
import fr.lip6.move.evinrude.optimizer.IReduction;
import fr.lip6.move.evinrude.optimizer.Reduction;
import fr.lip6.move.evinrude.optimizer.conditions.HasSuccessors;
import fr.lip6.move.evinrude.optimizer.conditions.ICondition;
import fr.lip6.move.evinrude.optimizer.conditions.NoMarkingValue;
import fr.lip6.move.evinrude.optimizer.conditions.SinglePredecessor;
import fr.lip6.move.evinrude.optimizer.conditions.SingleSuccessor;
import fr.lip6.move.evinrude.optimizer.conditions.TypedPlaceCondition;
import fr.lip6.move.evinrude.optimizer.conditions.TypedTransitionCondition;

/**
 * Définition d'une réduction structurelle de base : <b>Post-Agglomération</b><br>
 *
 * @author Jean-Baptiste Voron
 */
public class ReducStruct1 extends Reduction {

	/**
	 * Constructeur
	 */
	public ReducStruct1() {
		super("Post-Agglomeration of Transitions");
		setType(IReduction.FLAT);

		// Ajout des conditions
		addCondition(new TypedTransitionCondition(ICondition.SELF, ITransition.STRUCTURAL));
		addCondition(new SingleSuccessor(ICondition.SELF));
		addCondition(new SinglePredecessor(ICondition.SELF));

		addCondition(new TypedPlaceCondition(ICondition.SUCC, IPlace.NORMAL));
		addCondition(new NoMarkingValue(ICondition.SUCC));
		addCondition(new SinglePredecessor(ICondition.SUCC));
		addCondition(new HasSuccessors(ICondition.SUCC));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void performReduction(IModel model, INode self) throws EvinrudeException {
		LOG.info(">\tReduction Rule: " + this.getName());

		INode succ = self.getNextNodes().get(0);

		// Création des liens entre :
		// - les placeq d'entrée (PRED) de la transition SELF
		// - toutes les transitions suivants de SUCC
		for (INode post : succ.getNextNodes()) {
			IArc newArc = model.createArc(self.getPreviousNodes().get(0), post);
			// Recopie de la valuation
			newArc.setValuation(model.getFirstArc(succ, post).getValuation());
		}

		// Suppression de la place en sortie (SUCC) de la transition
		model.removeNode(succ);
		LOG.fine("\tSuppression de la place " + succ.getName());

		// Suppression de la transition SELF
		model.removeNode(self);
		LOG.fine("\tSuppression de la transition " + self.getName());
	}
}
