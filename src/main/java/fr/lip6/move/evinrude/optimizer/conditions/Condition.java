package fr.lip6.move.evinrude.optimizer.conditions;

import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;

import java.util.ArrayList;
import java.util.List;

/**
 * Description d'une condition à la réalisation d'une réduction.<br>
 * Il faut préciser le noeud surlequel s'applique la condition.
 *
 * @author Jean-Baptiste Voron
 */
public abstract class Condition implements ICondition {
	/** Le noeud sur lequel la condition doit s'appliquer */
	private int reference;

	/**
	 * Constructeur
	 * @param reference Le choix du noeud sur lequel s'applique la condition
	 */
	public Condition(int reference) {
		this.reference = reference;
	}

	/**
	 * Calcule le groupe de noeuds sur lequel s'applique la condition
	 * @param node Le noeud de référence
	 * @return Une liste de noeuds qui doivent satisfaire la condition
	 */
	protected final List<INode> computeSubSet(INode node) {
		List<INode> toReturn = new ArrayList<INode>();

		switch (this.reference) {
		case SELF:
			toReturn.add(node);
			break;
		case SUCC:
			toReturn.addAll(node.getNextNodes());
			break;
		case PRED:
			toReturn.addAll(node.getPreviousNodes());
			break;
		default:
			break;
		}
		return toReturn;
	}

	/**
	 * {@inheritDoc}
	 */
	public abstract boolean isVerify(INode node);
}
