package fr.lip6.move.evinrude.optimizer;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IApplication;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IExecutable;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IModel;
import fr.lip6.move.evinrude.commons.utils.DotExport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.logging.Logger;

/**
 * Classe fournissant un mécanisme de réduction de modèle hiérarchique ou plat.
 *
 * @author Clément Démoulins
 * @author Jean-Baptiste Voron
 */
public class Reducer {
	/** Le logger */
	private static Logger LOG = Logger.getLogger(Reducer.class.getName());

	/** L'application en cours de traitement */
	private IApplication application;

	/**
	 * Constructeur
	 * @param application L'application en cours de construction
	 */
	public Reducer(IApplication application) {
		this.application = application;
	}

	/**
	 * Récupère une liste de règles de réduction
	 * @param type type de règle
	 * @return la liste des perspectives disponible.
	 */
	public final List<IReduction> getRules(int type) {
		List<IReduction> reductions = new ArrayList<IReduction>();

		// Chargement des perspectives
		ServiceLoader<IReduction> loader = ServiceLoader.load(IReduction.class);
		for (IReduction reduction : loader) {
			if (reduction.isTyped(type)) {
				LOG.info("\tReduction rule '" + reduction.getName() + "' loaded");
				reductions.add(reduction);
			}
		}

		return reductions;
	}

	/**
	 * @param executable Réducation du modèle associé à cet executable
	 * @param type type de réduction à utiliser
	 */
	public final void reduc(IExecutable executable, int type) {
		LOG.info("-- Reduction of application: " + application.getName());

		// Sélection des règles de réduction
		Map<IReduction, Boolean> reducs = new HashMap<IReduction, Boolean>();
		for (IReduction reduction : getRules(type)) {
			reducs.put(reduction, true);
		}

		// Réduction du modèle associé à l'executable
		IModel model = executable.getModel();
		LOG.info("  Reduction of model:" + model);

		for (IReduction reduction : reducs.keySet()) {
			reducs.put(reduction, true);
		}

		int cpt = 0, cpt2 = 0;
		while (true) {
			LOG.info("    Round " + cpt);

			for (IReduction reduc : reducs.keySet()) {
				LOG.info("\tReduction " + reduc.getName());
				Boolean ret = reduc.apply(model);
				reducs.put(reduc, ret);
				if (ret) {
					if (type == IReduction.FLAT) {
						DotExport.dumpDotRepresentation(model, "round" + cpt + "_reduc" + cpt2++, executable.getFolder());
					} else {
						DotExport.dumpDotRepresentation(model, "pre_round" + cpt + "_reduc" + cpt2++, executable.getFolder());
					}
				}
			}

			cpt2 = 0;
			cpt++;

			if (!reducs.values().contains(Boolean.TRUE)) {
				LOG.info(" End of reductions");
				break;
			}
		}
	}
}
