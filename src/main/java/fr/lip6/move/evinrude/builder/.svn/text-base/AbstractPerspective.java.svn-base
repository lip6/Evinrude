package fr.lip6.move.evinrude.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstaite permettant de simplifier la création de perspective.
 */
public abstract class AbstractPerspective implements IPerspective {

	private final String name;
	private final List<String> dependencies = new ArrayList<String>();
	private final List<IProperty> properties = new ArrayList<IProperty>();
	private final List<IRule> rules = new ArrayList<IRule>();

	/**
	 * @param name Nom de la perspective
	 */
	public AbstractPerspective(String name) {
		this.name = name;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final List<String> getDependencies() {
		return dependencies;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final String getName() {
		return name;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final List<IProperty> getProperties() {
		return properties;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final List<IRule> getRules() {
		return rules;
	}

	/**
	 * @param dependency dépendence à ajouter pour cette perspective
	 */
	protected final void addDependency(String dependency) {
		dependencies.add(dependency);
	}

	/**
	 * @param property propriété à ajouter pour cette perspective
	 */
	protected final void addProperty(IProperty property) {
		properties.add(property);
	}

	/**
	 * @param rule règle à ajouter pour cette perspective
	 */
	protected final void addRule(IRule rule) {
		rules.add(rule);
	}
}
