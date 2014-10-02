package fr.lip6.move.evinrude.optimizer.rules;

import fr.lip6.move.evinrude.commons.exceptions.EvinrudeException;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IArc;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ITransition;
import fr.lip6.move.evinrude.optimizer.IReduction;
import fr.lip6.move.evinrude.optimizer.Reduction;
import fr.lip6.move.evinrude.optimizer.conditions.HasPredecessors;
import fr.lip6.move.evinrude.optimizer.conditions.ICondition;
import fr.lip6.move.evinrude.optimizer.conditions.NoMarkingValue;
import fr.lip6.move.evinrude.optimizer.conditions.SinglePredecessor;
import fr.lip6.move.evinrude.optimizer.conditions.SingleSuccessor;
import fr.lip6.move.evinrude.optimizer.conditions.TypedPlaceCondition;
import fr.lip6.move.evinrude.optimizer.conditions.TypedTransitionCondition;

/**
 * Définition d'une réduction structurelle de base : <b>Pré-Agglomération</b><br>
 *
 * @author Jean-Baptiste Voron
 */
public class ReducStruct2 extends Reduction {

	/**
	 * Constructeur
	 */
	public ReducStruct2() {
		super("Pré-Agglomeration of Transitions");
		setType(IReduction.FLAT);

		addCondition(new TypedTransitionCondition(ICondition.SELF, ITransition.STRUCTURAL));
		addCondition(new SingleSuccessor(ICondition.SELF));
		addCondition(new SinglePredecessor(ICondition.SELF));

		addCondition(new TypedPlaceCondition(ICondition.PRED, IPlace.NORMAL));
		addCondition(new NoMarkingValue(ICondition.PRED));
		addCondition(new SingleSuccessor(ICondition.PRED));
		addCondition(new HasPredecessors(ICondition.PRED));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void performReduction(IModel model, INode self) throws EvinrudeException {
		LOG.info(">\tReduction Rule: " + this.getName());

		INode pred = self.getPreviousNodes().get(0);

		// Création des liens entre :
		// - les places d'entrée de la transition PRED et toutes les transitions SUCC
		for (INode pre : pred.getPreviousNodes()) {
				IArc newArc = model.createArc(pre, self.getNextNodes().get(0), IArc.NORMAL);
				// Recopie des valuations
				newArc.setValuation(model.getFirstArc(pre, pred).getValuation());
		}

		// Suppression de la place prédécesseur
		model.removeNode(pred);
		LOG.fine("\tSuppression de la place " + pred.getName());

		// Suppression de la transition structurelle
		model.removeNode(self);
		LOG.fine("\tSuppression de la transition " + self.getName());
	}
}
