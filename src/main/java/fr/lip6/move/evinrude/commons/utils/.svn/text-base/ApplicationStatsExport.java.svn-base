package fr.lip6.move.evinrude.commons.utils;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IApplication;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IBlock;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICfg;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe utilitaire permettant d'extraire / d'afficher:
 * <ul>
 * 	<li>La liste des symboles de l'application</li>
 * 	<li>Le graphe des dépendances de l'application</li>
 * </ul>
 *
 * @author Jean-Baptiste Voron
 */
public final class ApplicationStatsExport {

	/** Constructeur */
	private ApplicationStatsExport() { }

	/**
	 * Extraction de la table des symboles
	 * @param application L'application concernée
	 * @return Une table associative de nom de symboles et de symboles
	 */
	private static Map<String, List<IFunction>> extractSymbolsTable(IApplication application) {
		Map<String, List<IFunction>> symbols = new HashMap<String, List<IFunction>>();

		// Parcours de tous les CFG pour extraire les fonctions
		for (ICfg cfg : application.getCfgs()) {
			for (IFunction function : cfg.getFunctions()) {
				if (symbols.get(function.getName()) == null) {
					List<IFunction> list = new ArrayList<IFunction>();
					list.add(function);
					symbols.put(function.getName(), list);
				} else {
					symbols.get(function.getName()).add(function);
				}
			}
		}
		return symbols;
	}

	/**
	 * Extrait la table des symboles de l'application<br>
	 * @param application L'application concernée
	 * @return La chaine de caratcère contenant tous les symboles
	 */
	public static StringBuffer computeSymbolsTable(IApplication application) {
		/** La table des symboles de l'application */
		Map<String, List<IFunction>> symbolsTable = extractSymbolsTable(application);

		StringBuffer symbols = new StringBuffer();

		symbols.append("------- Symbols Table : " + application.getName() + " --------\n\n");
		// Parcours et affichage de toutes les fonctions (et de leurs CFG)
		for (String name : symbolsTable.keySet()) {
			List<IFunction> list = symbolsTable.get(name);
			int nbOccur = list.size();
			if (nbOccur > 1) {
				symbols.append("Function: " + name + " (occurence=" + nbOccur + ")\n");
				int i = 1;
				for (IFunction f : list) {
					symbols.append("\t(" + i + ")  (id=" + f.getId() + ") -> " + f.getCfg().getName() + "\n");
					i++;
				}
			} else {
				symbols.append("Function: " + name + " (id=" + list.get(0).getId() + ") in CFG " + list.get(0).getCfg().getFileName() + "\n");
			}
		}
		symbols.append("\n----------------------------------\n");
		return symbols;
	}

	/**
	 * Export de statistiques<br>
	 * <ul>
	 * 	<li>Nombre de CFGs</li>
	 * 	<li>Détails des CFGs</li>
	 * 	  <ul>
	 * 		<li>Nombre de fonctions</li>
	 * 		<li>Nombre de blocs</li>
	 * 		<li>Nombre de mots-clé reconnus</li>
	 * 	  </ul>
	 * 	</ul>
	 * @param application L'application concernée
	 * @return La chaine de caractères contenant toutes les informations
	 */
	public static StringBuffer computeStatistics(IApplication application) {
		StringBuffer stats = new StringBuffer();

		stats.append("------- Statistics : " + application.getName() + " --------\n\n");
		stats.append("Number of processed CFGs : " + application.getCfgs().size()).append("\n");

		double globalLineNb = 0;
		double globalCoveredLineNb = 0;
		for (ICfg cfg : application.getCfgs()) {
			globalLineNb += cfg.getLineNb();
			globalCoveredLineNb += cfg.getCoveredLineNb();
		}
		stats.append("Global coverage : " + ((100 * globalCoveredLineNb) / globalLineNb) + "% (" + globalCoveredLineNb + "/" + globalLineNb + ")\n");

		for (ICfg cfg : application.getCfgs()) {
			stats.append("File : " + cfg.getFileName()).append(" (").append(cfg.getId()).append(")").append("\n");
			stats.append("\t # Functions : " + cfg.getFunctions().size()).append("\n");

			int blocs = 0;
			int instructions = 0;
			for (IFunction function : cfg.getFunctions()) {
				blocs += function.getBlocks().size();
				for (IBlock block : function.getBlocks()) {
					instructions += block.getInstructions().size();
				}
			}
			stats.append("\t # Blocks : " + blocs).append("\n");
			stats.append("\t # Instructions : " + instructions).append("\n");
			stats.append("\t # Coverage : " + ((100 * cfg.getCoveredLineNb()) / cfg.getLineNb()) + "%\n");
		}
		return stats;
	}


	/**
	 * Calcule le graphe des dépendances et indiques les ambiguités
	 * @param application L'application concernée
	 * @return La représentation DOT des dépendances.
	 */
	public static StringBuffer computeDependenciesGraph(IApplication application) {
		StringBuffer dependencies = new StringBuffer();

		dependencies.append("digraph model { \n");
		for (ICfg cfg : application.getCfgs()) {
			for (IFunction function : cfg.getFunctions()) {

				dependencies.append("\t");
				dependencies.append(function.getCfg().getName()).append("_").append(function.getId());
				dependencies.append(" [label=\"").append(function.getName()).append(" (").append(function.getCfg().getName()).append(")\"];\n");

				// On recherche les callsites
				for (IBlock block : function.getBlocks()) {
					for (IFunction called : block.getLocalFunctionCalls().values()) {
						dependencies.append("\t");
						dependencies.append(function.getCfg().getName()).append("_").append(function.getId());
						dependencies.append(" -> ").append(called.getCfg().getName()).append("_").append(called.getId()).append(";\n");
					}
				}
			}
		}

		dependencies.append("}\n");
		return dependencies;
	}
}
