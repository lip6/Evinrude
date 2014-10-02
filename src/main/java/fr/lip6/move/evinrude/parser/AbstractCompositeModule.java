package fr.lip6.move.evinrude.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Design pattern composite pour les modules du parser.
 * Cette classe permet de représenter les interfaces ayant plusieurs implémentation comme IInstruction.
 */
public abstract class AbstractCompositeModule extends AbstractModule {

	private List<IModule> modules;

	/**
	 * Constructeur
	 * @param parser Objet parser permettant de récupérer certains paramètres courrant
	 */
	public AbstractCompositeModule(IParser parser) {
		super(parser);
		this.modules = new ArrayList<IModule>();
	}

	/**
	 * @param module module à ajouter
	 */
	protected final void addModule(IModule module) {
		modules.add(module);
	}

	/**
	 * @param line ligne à vérifier sur les modules contenus.
	 * @return <code>true</code> si un des modules retourne <code>true</code>, <code>false</code> sinon.
	 */
	protected final boolean internalMatch(String line) {
		Iterator<IModule> it = modules.iterator();
		while (it.hasNext()) {
			if (it.next().match(line)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param line ligne à faire traiter par les modules contenus
	 * @return <code>true</code> si la ligne a été traité <code>false</code> sinon
	 */
	protected final boolean internalProcess(String line) {
		boolean res = false;
		for (IModule module : modules) {
			res = res || module.process(line);
		}
		return res;
	}

}
