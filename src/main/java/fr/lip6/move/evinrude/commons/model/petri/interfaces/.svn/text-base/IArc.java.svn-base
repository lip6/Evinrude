package fr.lip6.move.evinrude.commons.model.petri.interfaces;

/**
 * Définition des comportements publics d'un arc.
 */
public interface IArc {
	/** Type : Arc Normal */
	int NORMAL = 0;
	/** Type : Arc Inhibiteur */
	int INHIBITOR = 1;

	/**
	 * @return source de l'arc
	 */
	INode getSource();

	/**
	 * @return cible de l'arc
	 */
	INode getTarget();

	/**
	 * @return la valuation associée à l'arc
	 */
	IValuation getValuation();

	/**
	 * Indique la valuation que doit prendre l'arc.<br>
	 * La valuation d'un arc ne devrait jamais être nulle ou ""
	 * @param valuation La valuation en question
	 */
	void setValuation(IValuation valuation);

	/**
	 * @return le type de l'arc
	 */
	int getType();

}
