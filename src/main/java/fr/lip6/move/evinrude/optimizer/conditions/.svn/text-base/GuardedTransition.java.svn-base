package fr.lip6.move.evinrude.optimizer.conditions;

import fr.lip6.move.evinrude.commons.model.petri.interfaces.IGuard;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ITransition;

/**
 * @author Jean-Baptiste Voron
 */
public class GuardedTransition extends Condition {

	/**
	 * La transition est-elle une transition avec une garde ?
	 * @param reference Le noeud sur lequel s'applique la condition
	 */
	public GuardedTransition(int reference) {
		super(reference);
	}

	/** {@inheritDoc}
	 */
	@Override
	public final boolean isVerify(INode node) {
		if (node instanceof ITransition) {
			ITransition t = (ITransition) node;
			/* Dans le cas de plusieurs gaurdes, au moins une n'est pas standard */
			if (t.getGuards().size() > 1) { return true; }
			/* Si la seule guarde n'est pas standard, alors la transition est dites guardée */
			if ((t.getGuards().get(0).getType() != IGuard.DEFAULT_GUARD)) {	return true; }
		}
		return false;
	}

}
