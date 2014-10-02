package fr.lip6.move.evinrude.commons.model.petri;

import fr.lip6.move.evinrude.commons.model.petri.interfaces.IGuard;

/**
 * Définition de la guarde d'un événement
 *
 * @author Jean-Baptiste Voron
 */
public class Guard implements IGuard {
	/** Le type de la guarde */
	private int type;

	/** La variable concernée */
	private String variable;

	/** L'opérateur de la guarde */
	private int operator;

	/** La valeur de contrôle */
	private String value;

	/**
	 * Constructeur d'une guarde par défaut
	 */
	public Guard() {
		this.type = DEFAULT_GUARD;
	}

	/**
	 * Constructeur d'une guarde de transition
	 * @param variable La variable concernée par la guarde
	 * @param operator L'opérateur de comparaison (@see IGuard)
	 * @param value La valeur de référence (potentiellement une autre variable)
	 */
	public Guard(String variable, int operator, String value) {
		this.variable = variable;
		this.operator = operator;
		this.value = value;
		this.type = OTHER_GUARD;
	}

	/**
	 * Constructeur d'une guarde léie à un événement
	 * @param event L'événement déclencheur du franchissement
	 */
	public Guard(String event) {
		this.type = EVENT_GUARD;
		this.variable = event;
	}

	/**
	 * {@inheritDoc}
	 */
	public final String toString() {
		StringBuffer toReturn = new StringBuffer();
		if (this.type == DEFAULT_GUARD) { return toReturn.toString(); }
		toReturn.append("[");
		switch (this.type) {
			case EVENT_GUARD: toReturn.append(this.variable.toLowerCase()); break;
			default: toReturn.append(getCondition());
		}
		toReturn.append("]");
		return toReturn.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	public final String getCondition() {
		if ((this.type == EVENT_GUARD) || (this.type == DEFAULT_GUARD)) { return ""; }

		StringBuffer toReturn = new StringBuffer();
		toReturn.append(this.variable);

		switch (this.operator) {
			case EQUALS : toReturn.append(" == "); break;
			case GT : toReturn.append(" > "); break;
			case GTE : toReturn.append(" >= "); break;
			case LT : toReturn.append(" < "); break;
			case LTE : toReturn.append(" <= "); break;
			default: toReturn.append("");
		}

		toReturn.append(this.value);
		return toReturn.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int getType() {
		return this.type;
	}

	/**
	 * {@inheritDoc}
	 */
	public final String getEvent() {
		if (this.type == EVENT_GUARD) {
			return this.variable;
		}
		return null;
	}
}
