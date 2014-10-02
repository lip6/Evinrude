package fr.lip6.move.evinrude.commons.model.cfg;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IConstant;

/**
 * Définition d'une constante.<br>
 *
 * @author Jean-Baptiste Voron
 */
public class Constant implements IConstant {
	/** La valeur de la constante */
	private String value;

	/**
	 * Constructeur de la constante
	 * @param value La valeur de la constante
	 */
	public Constant(String value) {
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	public final String getValue() {
		return this.value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		return "Constante: " + this.value;
	}

	/**
	 * {@inheritDoc}
	 */
	public final StringBuffer toXML(int level) {
		int i = 0;
		StringBuffer toReturn = new StringBuffer();

		// Indentation
		for (i = 0; i < level; i++) { toReturn.append("\t"); }
		toReturn.append("<constant>").append(this.value).append("</constant>\n");
		return toReturn;
	}
}
