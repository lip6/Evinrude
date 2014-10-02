package fr.lip6.move.evinrude.builder;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IApplication;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IBlock;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICall;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICfg;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite pour simplifier la création d'une règle
 */
public abstract class AbstractRule implements IRule {

	private final String name;
	private final List<String> dependencies = new ArrayList<String>();
	private final List<IProperty> properties = new ArrayList<IProperty>();

	/**
	 * @param name Nom de la règle
	 */
	public AbstractRule(String name) {
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

	/**
	 * @param dependency dépendence à ajouter pour cette règle
	 */
	protected final void addDependency(String dependency) {
		dependencies.add(dependency);
	}

	/**
	 * @param property propriété à ajouter pour cette règle
	 */
	protected final void addProperty(IProperty property) {
		properties.add(property);
	}

	/**
	 * @param app application
	 * @return identifiant dans le modèle pour cette application
	 */
	protected final String id(IApplication app) {
		return app.getName();
	}

	/**
	 * @param cfg CFG
	 * @return identifiant dans le modèle pour ce CFG
	 */
	protected final String id(ICfg cfg) {
//		return cfg.getName();
		return cfg.getId() + "";
	}

	/**
	 * @param func fonction
	 * @return identifiant dans le modèle pour cette fonction
	 */
	protected final String id(IFunction func) {
//		return id(func.getCfg()) + "_" + func.getName();
		return id(func.getCfg()) + "_" + func.getId();
	}

	/**
	 * @param block block
	 * @return identifiant dans le modèle pour ce block
	 */
	protected final String id(IBlock block) {
		return id(block.getFunction()) + "_" + block.getId();
	}

	/**
	 * @param call appel de fonction
	 * @return identifiant dans le modèle pour cet appel de fonction
	 */
	protected final String id(ICall call) {
		// Attention des modifications de cette méthode doivent être répercutées sur la Struct6 (variable pathPlaceName) !
		return id(call.getBlock()) + "_" + call.getCfgLine() + "_" + call.getFunctionName();
	}

	/** {@inheritDoc}
	 */
	@Override
	public final String toString() {
		return "Rule[" + name + "]";
	}
}
