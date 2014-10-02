package fr.lip6.move.evinrude.optimizer.rules;

import fr.lip6.move.evinrude.commons.exceptions.EvinrudeException;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ITransition;
import fr.lip6.move.evinrude.optimizer.Reduction;
import fr.lip6.move.evinrude.optimizer.conditions.ICondition;
import fr.lip6.move.evinrude.optimizer.conditions.SiblingCondition;
import fr.lip6.move.evinrude.optimizer.conditions.SinglePredecessor;
import fr.lip6.move.evinrude.optimizer.conditions.SingleSuccessor;
import fr.lip6.move.evinrude.optimizer.conditions.TypedTransitionCondition;

/**
 * Définition d'une réduction structurelle de base : <b>Transitions avec une même garde</b><br>
 *
 * @author Jean-Baptiste Voron
 * @author Clément Démoulins
 */

public class ReducStruct5 extends Reduction {

	/**
	 * Constructeur
	 */
	public ReducStruct5() {
		super("Same Guarded Transition");
		setType(FLAT);

		addCondition(new TypedTransitionCondition(ICondition.SELF, ITransition.KEY));
		addCondition(new SingleSuccessor(ICondition.SELF));
		addCondition(new SinglePredecessor(ICondition.SELF));
		addCondition(new SiblingCondition(ICondition.SELF));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void performReduction(IModel model, INode selfNode) throws EvinrudeException {
		LOG.info(">\tReduction Rule: " + this.getName());

		ITransition self = (ITransition) selfNode;
		INode pred = self.getPreviousNodes().get(0);
		INode succ = self.getNextNodes().get(0);
		ITransition brother = null;

		// Recherche du frère
		for (INode bros : pred.getNextNodes()) {
			if (bros.equals(self)) { continue; }
			if (!bros.isTransition()) { continue; }
			// Les 2 objets doivent être du même type (type (place|transition), type(struct|event), garde)
			if (self.getType() != bros.getType()) { continue; }

			// Si les guardes evenementielles sont nulles toutes les 2... Ce sont des transitions structurelles
			if ((self.getEventGuard() == null) && (((ITransition) bros).getEventGuard() == null)) { brother = (ITransition) bros; break; }
			// Pour traiter le cas de 2 transitions événementielles
			if ((self.getEventGuard() != null) && (((ITransition) bros).getEventGuard() != null) && (self.getEventGuard().getEvent().equals(((ITransition) bros).getEventGuard().getEvent()))) { brother = (ITransition) bros; break; }
		}

		if (brother == null) {
			throw new EvinrudeException("Reduc5: Impossible de retrouver le frere de " + self.getName());
		}

		// Successeur de BROTHER
		INode brotherSucc = brother.getNextNodes().get(0);

		// Creation d'une place temporaire post-transition gardée
		IPlace tempo = model.createPlace(self.getName() + "_reduc5_post", IPlace.NORMAL);
		ITransition tempoPostSelf = model.createTransition("reduc5_" + self.getName() + "_post", ITransition.STRUCTURAL);
		ITransition tempoPostBrother = model.createTransition("reduc5_" + self.getName() + "brother_post", ITransition.STRUCTURAL);

		// Liens entre les éléments temporaires
		model.createArc(tempo, tempoPostSelf);
		model.createArc(tempo, tempoPostBrother);

		model.createArc(brother, tempo); // Creation du lien entre la transition gardée restante et la place temporaire
		model.createArc(tempoPostSelf, succ); // Creation des liens entre la transition temporaire de SELF et son successeur
		model.createArc(tempoPostBrother, brotherSucc); // Creation des liens entre la transition temporaire de BROTHER et son successeur

		// Suppression de l'arc entre BROTHER et son successeur
		model.removeArc(model.getFirstArc(brother, brotherSucc));

		// Suppresion de SELF
		LOG.fine("\tSuppression de la transition " + self.getName());
		model.removeNode(self);
	}
}
