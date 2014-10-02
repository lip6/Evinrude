package fr.lip6.move.evinrude.optimizer.conditions;

import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;

/**
 * Description des comportements publics d'une condition
 *
 * @author Jean-Baptiste Voron
 */
public interface ICondition {
	int SELF = 0;
	int SUCC = 1;
	int PRED = 2;

	/**
	 * Indique si la condition est vérifiée pour cet objet
	 * @param node Le noeud pour lequel doit être vérifiée la condition
	 * @return <code>true</code> si la condition est vérifiée; <code>false</code> sinon.
	 */
	boolean isVerify(INode node);
}
