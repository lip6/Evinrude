package fr.lip6.move.evinrude.optimizer;

import fr.lip6.move.evinrude.commons.model.petri.interfaces.IModel;

import java.util.logging.Logger;

/**
 * Définition des comportements publics d'une réduction
 *
 * @author Jean-Baptiste Voron
 */
public interface IReduction {
	Logger LOG = Logger.getLogger(IReduction.class.getName());

	/** Réduction sur un modèle plat */
	int FLAT = 1 << 1;
	/** Réduction sur un modèle hiérachique */
	int HIERARCHICAL = 1 << 2;

	/**
	 * @return le nom de la règle de réduction
	 */
	String getName();

	/**
	 * Permet de définir le type de la réduction en utilisant une addition de flag.
	 * exemple : IReduction.FLAT | IReduction.HIERARCHICAL
	 * @param type combinaison avec l'opérateur | de type définie dans IReduction
	 */
	void setType(int type);

	/**
	 * @param type type à tester
	 * @return <code>true</code> si cette réduction est du type <code>type</code>
	 */
	boolean isTyped(int type);

	/**
	 * Applique la réduction sur le modèle
	 * @param model Le modèle qui doit être réduit
	 * @return <code>true</code> si la réduction a effectivement eu lieue
	 */
	boolean apply(IModel model);
}
