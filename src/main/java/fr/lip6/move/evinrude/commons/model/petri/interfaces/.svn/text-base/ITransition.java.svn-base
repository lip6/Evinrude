package fr.lip6.move.evinrude.commons.model.petri.interfaces;

import java.util.List;

/**
 * Définition des comportements publics d'une transition.
 */
public interface ITransition extends INode {

	/** Type : Transition structurelle */
	int STRUCTURAL = 1 << 1;
	/** Type : Transition d'appel de fonction */
	int FUNCTIONCALL = 1 << 2;
	/** Type : Transition de retour de fonction */
	int FUNCTIONRETURN = 1 << 3;
	/** Type : Transition d'élément remarquables (clé) */
	int KEY = 1 << 4;

	/**
	 * Ajoute une guarde (condition) à la transition
	 * @param guard La nouvelle guarde
	 */
	void addGuard(IGuard guard);

	/**
	 * Ajoute plusieurs guardes (conditions) à la transition
	 * @param guards Une liste de guardes
	 */
	void addGuards(List<IGuard> guards);

	/**
	 * @return La liste des guardes associées à la transition
	 */
	List<IGuard> getGuards();

	/**
	 * @return La guarde événementielle ou <code>null</code> si elle n'existe pas
	 */
	IGuard getEventGuard();
}
