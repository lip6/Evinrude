package fr.lip6.move.evinrude.commons.model.cfg;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IVariable;

/**
 * Définition d'une <b>variable</b>.<br>
 * Elle se compose d'un <b>type</b> et d'un <b>nom</b>.<br>
 * Les types sont définis dans le fichier {@link IVariable}.
 * @author Jean-Baptiste Voron
 */
public class Variable implements IVariable {

	/** Le nom de la variable */
	private String name;

	private String register;

	/**
	 * Constructeur d'une variable
	 * @param name Le nom de la variable
	 * @param register Le registre utilisé.
	 */
	public Variable(String name, String register) {
		this.name = name;
		this.register = register;
	}

	/**
	 * {@inheritDoc}
	 */
	public final String getValue() {
		if (!name.equals("")) {
			return this.name;
		} else {
			return this.register;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		return "Variable : " + getValue();
	}

	/**
	 * {@inheritDoc}
	 */
	public final StringBuffer toXML(int level) {
		int i = 0;
		StringBuffer toReturn = new StringBuffer();

		// Indentation
		for (i = 0; i < level; i++) {
			toReturn.append("\t");
		}

		toReturn.append("<variable>");
		toReturn.append(getValue());
		toReturn.append("</variable>\n");
		return toReturn;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String getRegister() {
		return register;
	}
}
