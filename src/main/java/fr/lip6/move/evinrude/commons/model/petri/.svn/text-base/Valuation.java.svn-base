package fr.lip6.move.evinrude.commons.model.petri;

import fr.lip6.move.evinrude.commons.model.petri.interfaces.IValuation;

/**
 * @author Jean-Baptiste Voron
 *
 */
public class Valuation implements IValuation {
	/** Type de la valuation */
	private int type;
	/** Identifiant de la ressource concernée */
	private String identifier;
	/** Variable de stockage */
	private String variable;
	/** Modificateur pour les valuation de post-condition */
	private int modifier;

	/**
	 * Construction d'une valuation par défaut
	 */
	public Valuation() {
		this.identifier = DEFAULT_IDENTIFIER;
		this.type = DEFAULT_VALUATION;
	}

	/**
	 * Construction d'une pré-valuation
	 * @param identifier L'identifiant du jeton demandé
	 * L'identifiant du jeton peut-être dynamique @see {@link IEvent}
	 */
	public Valuation(String identifier) {
		this.type = PRECOND_VALUATION;
		this.identifier = identifier;
		this.variable = null;
	}

	/**
	 * Construction d'une pré-valuation
	 * @param identifier L'identifiant du jeton demandé
	 * @param variable Variable temporaire utilisée pour le stockage du jeton
	 * L'identifiant du jeton peut-être dynamique @see {@link IEvent}
	 */
	public Valuation(String identifier, String variable) {
		this.type = PRECOND_VALUATION;
		this.identifier = identifier;
		this.variable = variable;
	}

	/**
	 * Construction d'une post-valuation
	 * @param identifier L'identifiant du jeton manipulé
	 * @param variable Variable ou Valeur devant être affectée à la ressource désignée
	 * @param modifier Opération à appliquer à la variable (ou valeur) {@link IValuation}
	 * L'identifiant du jeton peut-être dynamique @see {@link IEvent}
	 * La variable peut-être dynamique @see {@link IEvent}
	 */
	public Valuation(String identifier, String variable, int modifier) {
		this.type = POSTCOND_VALUATION;
		this.identifier = identifier;
		this.variable = variable;
		this.modifier = modifier;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int getType() {
		return this.type;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final String getIdentifier() {
		if (this.identifier.equals("")) {
			return "0";
		}
		return this.identifier;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final String toString() {
		StringBuffer toReturn = new StringBuffer();

		switch (this.type) {
			case DEFAULT_VALUATION: break;
			case PRECOND_VALUATION:
				if (this.variable != null) { toReturn.append(this.variable).append(" = "); }
				toReturn.append("<").append(this.identifier);
				toReturn.append(">");
				break;
			case POSTCOND_VALUATION:
				toReturn.append("<").append(this.identifier);
				if (this.variable != null) {
					toReturn.append(", ").append(this.variable);
					switch (this.modifier) {
						case NO_OPERATOR: break;
						case INCREMENT: toReturn.append("++"); break;
						case DECREMENT: toReturn.append("--"); break;
						default: break;
					}
				}
				toReturn.append(">");
				break;
			default: break;
		}
		return toReturn.toString();
	}

	/** {@inheritDoc}
	 */
	@Override
	public final String getValue() {
		StringBuffer variable = new StringBuffer();
		variable.append(this.variable);
		switch (this.modifier) {
			case NO_OPERATOR: break;
			case INCREMENT: variable.append("++"); break;
			case DECREMENT: variable.append("--"); break;
			default: break;
		}
		return variable.toString();
	}

	/** {@inheritDoc}
	 */
	@Override
	public final String getVariable() {
		return this.variable;
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean isDynamic() {
		return (this.identifier == EVENT_RESOURCE_IDENTIFIER) || (this.variable == EVENT_RESOURCE_VALUE);
	}
}
