package fr.lip6.move.evinrude.builder;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IExecutable;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ISubModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Clément Démoulins
 * @author Jean-Baptiste Voron
 */
public class ResultDep {
	/** Le logger */
	private static final Logger LOG = Logger.getLogger(ResultDep.class.getName());

	private static final List<ISubModel> EMPTY_LIST = Collections.unmodifiableList(new ArrayList<ISubModel>(0));

	private final Map<Object, List<ISubModel>> subModels = new HashMap<Object, List<ISubModel>>();

	private final IExecutable executable;

	/**
	 * @param executable executable
	 */
	public ResultDep(IExecutable executable) {
		this.executable = executable;
	}

	/**
	 * Ajoute un résultat associé à un élément du CFG
	 * @param cfgElement élément du CFG concerné par le resultat
	 * @param resultEntry resultat
	 */
	public final void putResultEntry(Object cfgElement, ResultEntry resultEntry) {
		List<ISubModel> list = subModels.get(cfgElement);
		if (list == null) {
			list = new ArrayList<ISubModel>();
		}
		list.addAll(resultEntry.getSubModels());
		subModels.put(cfgElement, list);
	}

	/**
	 * @param cfgElement Élément du CFG
	 * @return Une liste de modèle créée ou modifiée par des dépendances pour l'élément du CFG passé en paramètre
	 */
	public final List<ISubModel> getAssociatedSubModels(Object cfgElement) {
		List<ISubModel> list = subModels.get(cfgElement);
		if (list == null) {
			LOG.warning("No associated sub-model found for the element " + cfgElement);
			return EMPTY_LIST;
		}
		return list;
	}

	/**
	 * @return the executable
	 */
	public final IExecutable getExecutable() {
		return executable;
	}
}
