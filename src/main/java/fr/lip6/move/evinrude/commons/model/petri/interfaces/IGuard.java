package fr.lip6.move.evinrude.commons.model.petri.interfaces;

/**
 * @author Jean-Baptiste Voron
 *
 */
public interface IGuard {

	/** Guarde par défaut */
	int DEFAULT_GUARD = 0;
	/** Guarde événementielle */
	int EVENT_GUARD = 1;
	/** Guarde événementielle */
	int OTHER_GUARD = 2;

	/** Opérateur = */
	int EQUALS = 1;
	/** Opérateur > */
	int GT = 2;
	/** Opératéur >= */
	int GTE = 3;
	/** Opérateur < */
	int LT = 4;
	/** Opérateur <= */
	int LTE = 5;

	/**
	 * @return La chaine correspondant à la guarde
	 */
	String toString();

	/**
	 * @return Le type de la guarde
	 */
	int getType();

	/**
	 * @return L'événement associée à la guarde ou <code>null</code> si la guard n'est pas événementielle
	 */
	String getEvent();

	/**
	 * @return La condition déduite de la garde
	 */
	String getCondition();

}
