package fr.lip6.move.evinrude.builder;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IExecutable;
import fr.lip6.move.evinrude.commons.utils.DotExport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Clément Démoulins
 * @author Jean-Baptiste Voron
 */
public class ResultManager {
	/** Le logger */
	private static Logger LOG = Logger.getLogger(ResultManager.class.getName());

	/**
	 * Structure indexant les résultats en fonction de l'élément du CFG utilisé pour générer ce résultat
	 * ainsi que du nom de la règle ayant généré ce résultat.
	 */
	private final Map<String, Map<Object, ResultEntry>> repository = new HashMap<String, Map <Object, ResultEntry>>();

	private IExecutable executable;

	private List<IPerspective> perspectives;

	/**
	 * Constructor
	 * @param executable executable
	 * @param perspectives liste des perspectives à gérer
	 */
	public ResultManager(IExecutable executable, List<IPerspective> perspectives) {
		this.executable = executable;
		this.perspectives = new ArrayList<IPerspective>(perspectives);
	}

	/**
	 * Permet de récupérer un ResultDep contenant les résultats de tous les résultats des
	 * dépendances de la règle passé en paramètre.
	 * @param rule règle concerné
	 * @return objet contenant les résultats
	 */
	public final ResultDep getResultDep(IRule rule) {
		ResultDep resultDep = new ResultDep(executable);

		// Si il n'y a pas de dependence
		if (rule.getDependencies() == null || rule.getDependencies().isEmpty()) {
			return resultDep;
		}

		// On ajoute les résultats de chaque dépendances
		for (String dep : rule.getDependencies()) {
			Map<Object, ResultEntry> depResult = repository.get(dep);
			if (depResult == null) {
				LOG.severe("Dependency [" + rule.getName() + " -> " + dep + "] not satisfied !");
				throw new IllegalArgumentException("Error in dependency tree, caused by the rule '" + rule + "'");
			}
			for (Object cfgObj : depResult.keySet()) {
				ResultEntry result = depResult.get(cfgObj);
				resultDep.putResultEntry(cfgObj, result);
			}
		}

		return resultDep;
	}

	/**
	 * Permet d'ajouter un résultat au gestionnaire
	 * @param rule règle ayant généré ce résultat
	 * @param cfgElement élément du CFG concerné par le résultat
	 * @param result résultat
	 */
	public final void putResult(IRule rule, Object cfgElement, ResultEntry result) {
		Map<Object, ResultEntry> ruleResults = repository.get(rule.getName());
		if (ruleResults == null) {
			ruleResults = new HashMap<Object, ResultEntry>();
		}
		ruleResults.put(cfgElement, result);
		repository.put(rule.getName(), ruleResults);
		checkEndOfPerspectives();
	}

	/**
	 * Export le model courrant lorsqu'une perspective a traité toutes ses règles
	 */
	private void checkEndOfPerspectives() {
		for (IPerspective perspective : new ArrayList<IPerspective>(perspectives)) {
			boolean isEnded = true;
			for (IRule rule : perspective.getRules()) {
				if (!repository.containsKey(rule.getName())) {
					isEnded = false;
					break;
				}
			}
			if (isEnded) {
				DotExport.dumpDotRepresentation(executable.getModel(), perspective.getName().toLowerCase(), executable.getFolder());
				perspectives.remove(perspective);
			}
		}
	}

	/**
	 * @param dependency dépendance à vérifier
	 * @return <code>true</code> si le gestionnaire possède des résultats associés à cette dépendance
	 */
	public final boolean containsDepenpency(String dependency) {
		return repository.containsKey(dependency);
	}
}
