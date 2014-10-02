package fr.lip6.move.evinrude.optimizer.rules;

import fr.lip6.move.evinrude.commons.exceptions.EvinrudeException;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IArc;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ITransition;
import fr.lip6.move.evinrude.optimizer.Reduction;
import fr.lip6.move.evinrude.optimizer.conditions.FlatPlaceCondition;
import fr.lip6.move.evinrude.optimizer.conditions.HasPredecessors;
import fr.lip6.move.evinrude.optimizer.conditions.ICondition;
import fr.lip6.move.evinrude.optimizer.conditions.NoMarkingValue;
import fr.lip6.move.evinrude.optimizer.conditions.SingleSuccessor;
import fr.lip6.move.evinrude.optimizer.conditions.TypedPlaceCondition;
import fr.lip6.move.evinrude.optimizer.conditions.TypedTransitionCondition;

/**
 * Définition d'une réduction structurelle de base utilisée avec applatissement du modèle.<br>
 * Cette réduction est très proche de la <b>Pré-Agglomération</b>.
 * @see ReducStruct2
 *
 * @author Jean-Baptiste Voron
 */
public class PreReducStruct2 extends Reduction {

	/**
	 * Constructeur
	 */
	public PreReducStruct2() {
		super("Pré-Agglomeration Hiérarchique");
		setType(HIERARCHICAL);

		// La transition doit être hiérarchique
		addCondition(new TypedTransitionCondition(ICondition.SELF, ITransition.STRUCTURAL));
		addCondition(new SingleSuccessor(ICondition.SELF));
		addCondition(new HasPredecessors(ICondition.SELF));

		addCondition(new TypedPlaceCondition(ICondition.PRED, IPlace.NORMAL));
		addCondition(new NoMarkingValue(ICondition.PRED));
		addCondition(new SingleSuccessor(ICondition.PRED));
		//addCondition(new HasPredecessors(ICondition.PRED));
		addCondition(new FlatPlaceCondition(ICondition.PRED));
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
				IArc newArc = self.getSubModelContainer().createArc(pre, self.getNextNodes().get(0));
				// Recopie des valuations
				newArc.setValuation(self.getSubModelContainer().getFirstArc(pre, pred).getValuation());
		}

		// Suppression de la place prédécesseur
		self.getSubModelContainer().removeNode(pred);
		LOG.fine("\tSuppression de la place " + pred.getName());

		// Suppression de la transition structurelle
		self.getSubModelContainer().removeNode(self);
		LOG.fine("\tSuppression de la transition " + self.getName());


	}
}
