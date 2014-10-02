package fr.lip6.move.evinrude.commons.model.petri.interfaces;

/**
 * Description des comportements publics d'une valuation d'arc
 * @see Valuation
 * @see Arc
 *
 * @author Jean-Baptiste Voron
 */
public interface IValuation {
	String EVENT_RESOURCE_IDENTIFIER = "event.id";
	String EVENT_RESOURCE_VALUE = "event.value";

	/** Valuation par défaut */
	int DEFAULT_VALUATION = 0;

	/** Valuation pour un arc de pré-condition */
	int PRECOND_VALUATION = 1;

	/** Valuation pour un arc de post-condition */
	int POSTCOND_VALUATION = 2;

	/** Identifiant par défaut des valuation standards */
	String DEFAULT_IDENTIFIER = "";

	/** Modificateur de post-condition () */
	int NO_OPERATOR = 0;
	/** Modificateur de post-condition (++) */
	int INCREMENT = 1;
	/** Modificateur de post-condition (--) */
	int DECREMENT = 2;


	/**
	 * @return Le type de la valuation de l'arc
	 */
	int getType();

	/**
	 * @return L'identifiant de la variable associée au jeton
	 */
	String getIdentifier();

	/**
	 * @return L'identifiant de la variable associée au jeton
	 */
	String getValue();

	/**
	 * @return La variable qui doit etre utilisée pour stocker la valeur du token demandé
	 */
	String getVariable();

	/**
	 * @return La valuation
	 */
	String toString();

	/**
	 * Indique si la valuation de l'arc dépend de valeurs dynamique (<i>ie. calculée pendant la réalisation de l'événement</i>)
	 * @return <code>true</code> si la valuation est dynamique; <code>false</code> sinon.
	 */
	boolean isDynamic();
}
