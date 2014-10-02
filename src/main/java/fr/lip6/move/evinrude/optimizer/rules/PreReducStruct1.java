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
import fr.lip6.move.evinrude.optimizer.conditions.SinglePredecessor;
import fr.lip6.move.evinrude.optimizer.conditions.SingleSuccessor;
import fr.lip6.move.evinrude.optimizer.conditions.TypedPlaceCondition;
import fr.lip6.move.evinrude.optimizer.conditions.TypedTransitionCondition;

/**
 * Définition d'une réduction structurelle de base utilisée avec applatissement du modèle.<br>
 * Cette réduction est très proche de la <b>Post-Agglomération</b>.
 * @see ReducStruct1
 *
 * @author Jean-Baptiste Voron
 */
public class PreReducStruct1 extends Reduction {

	/**
	 * Constructeur
	 */
	public PreReducStruct1() {
		super("Post-Agglomération Hiérarchique");
		setType(HIERARCHICAL);

		// La transition doit être hiérarchique
		addCondition(new TypedTransitionCondition(ICondition.SELF, ITransition.STRUCTURAL));
		addCondition(new SingleSuccessor(ICondition.SELF));
		addCondition(new HasPredecessors(ICondition.SELF));

		addCondition(new TypedPlaceCondition(ICondition.SUCC, IPlace.NORMAL));
		addCondition(new NoMarkingValue(ICondition.SUCC));
		addCondition(new SinglePredecessor(ICondition.SUCC));
		//addCondition(new HasSuccessors(ICondition.SUCC));
		addCondition(new FlatPlaceCondition(ICondition.SUCC));
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
			IArc newArc = self.getSubModelContainer().createArc(self.getPreviousNodes().get(0), post);
			// Recopie de la valuation
			newArc.setValuation(self.getSubModelContainer().getFirstArc(succ, post).getValuation());
		}

		// Suppression de la place en sortie (SUCC) de la transition
		self.getSubModelContainer().removeNode(succ);
		LOG.fine("\tSuppression de la place " + succ.getName());

		// Suppression de la transition SELF
		self.getSubModelContainer().removeNode(self);
		LOG.fine("\tSuppression de la transition " + self.getName());
	}
}
