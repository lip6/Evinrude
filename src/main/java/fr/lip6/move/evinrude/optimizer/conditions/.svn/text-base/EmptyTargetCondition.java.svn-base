package fr.lip6.move.evinrude.optimizer.conditions;

import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;

/**
 * Condition pour la réduction d'un modèle :
 * <ul>
 * 	<li>La place doit faire référence à un objet qui n'existe pas</li>
 * </ul>
 * @see IPlace
 *
 * @author Jean-Baptiste Voron
 */
public class EmptyTargetCondition extends Condition {
	/**
	 * Constructeur
	 * @param reference Le choix du noeud sur lequel s'applique la condition
	 */
	public EmptyTargetCondition(int reference) {
		super(reference);
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean isVerify(INode ref) {
		for (INode node : this.computeSubSet(ref)) {
			if (!node.isPlace()) { return false; }

			IPlace place = (IPlace) node;
			if (place.getSubModelContainer().getTopModel().findNode(place.getTargetName()) != null) { return false; }
		}
		return true;
	}
}
