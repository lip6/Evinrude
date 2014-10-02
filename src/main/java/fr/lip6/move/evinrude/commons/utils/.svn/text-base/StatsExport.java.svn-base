package fr.lip6.move.evinrude.commons.utils;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IApplication;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ITransition;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Export de statistiques concernant les modèles en RdP
 *
 * @author Jean-Baptiste Voron
 */
public final class StatsExport {
	public static final int TIMER_BEGIN = 0;
	public static final int TIMER_PARSE_START = 1;
	public static final int TIMER_PARSE_STOP = 2;
	public static final int TIMER_BUILD_START = 3;
	public static final int TIMER_BUILD_STOP = 4;
	public static final int TIMER_OPTIMIZE_H_START = 5;
	public static final int TIMER_OPTIMIZE_H_STOP = 6;
	public static final int TIMER_FLATTENER_START = 7;
	public static final int TIMER_FLATTENER_STOP = 8;
	public static final int TIMER_OPTIMIZE_F_START = 9;
	public static final int TIMER_OPTIMIZE_F_STOP = 10;
	public static final int TIMER_GENERATE_START = 11;
	public static final int TIMER_GENERATE_STOP = 12;
	public static final int TIMER_COMPILER_START = 13;
	public static final int TIMER_COMPILER_STOP = 14;
	public static final int TIMER_END = 15;

	/** Le logger */
	private static Logger LOG = Logger.getLogger(StatsExport.class.getName());

	/** Constructeur */
	private StatsExport() { }

	/**
	 * Extrait des statistiques du modèle courant et l'écrit dans un fichier
	 * @param application L'application traitée
	 * @param outputDirectory Le dossier destination
	 */
	public static void dumpStats(IApplication application, File outputDirectory) {
		try {
			String filename = application.getName() + ".stats";
			PrintWriter statsExport = new PrintWriter(new BufferedWriter(new FileWriter(outputDirectory.getAbsolutePath() + "/" + filename)));
			statsExport.print(StatsExport.toStats(application));
			statsExport.close();
			LOG.config("\t   Statistics are available in: " + filename);
		} catch (IOException e) {
			System.err.println("Erreur lors de la construction du graphe dot " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Extrait les statistiques du modèle désigné
	 * @param application L'application concernée
	 * @return Une chaine de caractère contenant toutes les statistiques du modèle
	 */
	private static String toStats(IApplication application) {
		StringBuilder toReturn = new StringBuilder();

		/* Statistiques generales */
		toReturn.append("----- METRICS -----------------------\n");
		toReturn.append("Forward Indeterminism : \n");
		toReturn.append("Backward Indeterminism : \n");
		toReturn.append("Model Induced Indeterminism : \n");
		toReturn.append("-------------------------------------\n\n");

		// Récupération des timers
		Map<Integer, Long> timers = application.getTimers();

		toReturn.append("----- TIME -----------------------\n");
		toReturn.append("Parser: ").append(timers.get(TIMER_PARSE_STOP) - timers.get(TIMER_PARSE_START)).append("ms\n");
		toReturn.append("Builder: ").append(timers.get(TIMER_BUILD_STOP) - timers.get(TIMER_BUILD_START)).append("ms\n");
		toReturn.append("Optimizer H: ").append(timers.get(TIMER_OPTIMIZE_H_STOP) - timers.get(TIMER_OPTIMIZE_H_START)).append("ms\n");
		toReturn.append("Flattener: ").append(timers.get(TIMER_FLATTENER_STOP) - timers.get(TIMER_FLATTENER_START)).append("ms\n");
		toReturn.append("Optimizer F: ").append(timers.get(TIMER_OPTIMIZE_F_STOP) - timers.get(TIMER_OPTIMIZE_F_START)).append("ms\n");
		toReturn.append("Generator: ").append(timers.get(TIMER_GENERATE_STOP) - timers.get(TIMER_GENERATE_START)).append("ms\n");
		toReturn.append("Compiler: ").append(timers.get(TIMER_COMPILER_STOP) - timers.get(TIMER_COMPILER_START)).append("ms\n");
		toReturn.append("Total : ").append(timers.get(TIMER_END) - timers.get(TIMER_BEGIN)).append("ms\n");
		toReturn.append("-------------------------------------\n\n");

//		toReturn.append("----- STATS -------------------------\n");
//		toReturn.append("Number of models (executables) : ").append(application.getModels().size()).append("\n");
//
//		int cptStruct = 0;
//		int cptKey = 0;
//		int cptFunction = 0;
//		int cptPath = 0;
//		int cptGlobal = 0;
//		int nbPlaces = 0;
//		int nbTransitions = 0;
//		int nbArcs = 0;
//		for (IModel model : application.getModels()) {
//			nbPlaces += model.getPlaces().size();
//			nbTransitions += model.getTransitions().size();
//			nbArcs += model.getArcs().size();
//
//			for (IPlace p : model.getPlaces()) {
//				if (p.isTyped(IPlace.PATH)) { cptPath++; }
//				if (p.isTyped(IPlace.RESOURCE)) { cptGlobal++; }
//			}
//			for (ITransition t : model.getTransitions()) {
//				if (t.isTyped(ITransition.STRUCTURAL)) { cptStruct++; }
//				if (t.isTyped(ITransition.KEY)) { cptKey++; }
//				if (t.isTyped(ITransition.FUNCTIONCALL)) { cptFunction++; }
//			}
//		}
//
//		toReturn.append("Number of places : ").append(nbPlaces).append("\n");
//		toReturn.append("  > Path Places : ").append(cptPath).append("\n");
//		toReturn.append("  > Global Resources Places : ").append(cptGlobal).append("\n");
//		toReturn.append("Number of transitions : ").append(nbTransitions).append("\n");
//		toReturn.append("  > Structural Transitions : ").append(cptStruct).append("\n");
//		toReturn.append("  > Key Transitions : ").append(cptKey).append("\n");
//		toReturn.append("  > Function Transitions : ").append(cptFunction).append("\n");
//
//		toReturn.append("Number of arcs : ").append(nbArcs).append("\n");
//		toReturn.append("-------------------------------------\n");

		return toReturn.toString();
	}

}
