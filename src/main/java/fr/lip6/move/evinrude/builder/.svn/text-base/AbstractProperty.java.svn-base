package fr.lip6.move.evinrude.builder;

import fr.lip6.move.evinrude.commons.model.petri.interfaces.ISubModel;

/**
 * Classe abstraite permettant de simplifier la définition d'une propriété.
 */
public abstract class AbstractProperty implements IProperty {

	private final String name;

	/**
	 * @param name Nom de la propriété
	 */
	public AbstractProperty(String name) {
		this.name = name;
	}

	/** {@inheritDoc}
	 */
	@Override
	public abstract boolean check(ISubModel subModel);

	/** {@inheritDoc}
	 */
	@Override
	public final String getName() {
		return name;
	}

}
