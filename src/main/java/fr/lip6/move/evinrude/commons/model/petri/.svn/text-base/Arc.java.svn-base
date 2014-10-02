package fr.lip6.move.evinrude.commons.model.petri;

import fr.lip6.move.evinrude.commons.model.petri.interfaces.IArc;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IValuation;

import java.util.logging.Logger;

/**
 * Définition d'un arc
 */
public class Arc implements IArc {
	/** Le logger */
	private static Logger LOG = Logger.getLogger(Arc.class.getName());

	private INode source;
	private INode target;
	private int type;
	private IValuation valuation = new Valuation();

	/**
	 * Constructeur
	 * @param source Le noeud source
	 * @param target Le noeud cible
	 * @param type Le type de l'arc à construire
	 */
	public Arc(INode source, INode target, int type) {
		this.source = source;
		this.target = target;
		this.type = type;
		LOG.finest("\t\t" + this.toString());
	}

	/** {@inheritDoc}
	 */
	@Override
	public final INode getSource() {
		return source;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final INode getTarget() {
		return target;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final int getType() {
		return type;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final IValuation getValuation() {
		return valuation;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final void setValuation(IValuation valuation) {
		if (valuation == null) {
			this.valuation = new Valuation();
			return;
		}
		this.valuation = valuation;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final boolean equals(Object obj) {
		if (!(obj instanceof Arc)) {
			return false;
		}
		Arc arc = (Arc) obj;
		if (this.getType() != arc.getType()) { return false; }
		if (!this.getValuation().equals(arc.getValuation())) { return false; }
		return source.equals(arc.getSource()) && target.equals(arc.getTarget());
	}

	/** {@inheritDoc}
	 */
	@Override
	public final int hashCode() {
		return source.hashCode() * target.hashCode();
	}

	/** {@inheritDoc}
	 */
	@Override
	public final String toString() {
		return "Arc(" + source + " —> " + target + ", " + type + ")";
	}
}
