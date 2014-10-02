package fr.lip6.move.evinrude.commons.utils;

import fr.lip6.move.evinrude.commons.model.petri.interfaces.IArc;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IGuard;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ISubModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ITransition;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

/**
 * Classe utilitaire pour l'export en DOT d'un modèle.<br>
 * La hiérarchie du modèle est retranscrite sous forme de clusters DOT.
 *
 * @author Jean-Baptiste Voron
 */
public final class DotExport {
	/** Le logger */
	private static Logger LOG = Logger.getLogger(DotExport.class.getName());

	/** Constructeur */
	private DotExport() { }

	/**
	 * Exporte le modèle en format DOT
	 * @param model Le modèle à exporter
	 * @return Une chaine de caractères décrivant le format DOT du modèle
	 */
	private static String toDot(ISubModel model) {
		StringBuffer dot = new StringBuffer();

		/* Si le modèle est celui de plus haut niveau (ou un sous-modèle imbriqué) */
		if (model.getParentPlace() == null) {
			dot.append("digraph model { \n");
			dot.append("compound=true; \n");
			dot.append("node  [style=\"filled\", colorscheme=\"paired12\"];");
		} else {
			dot.append("subgraph \"cluster_" + model.getParentPlace().getName() + "_" + model.getInstructionNumber() + "\" { \n");
			dot.append("label=\"" + model.getInstructionNumber() + "\"");
		}

		/* Parcours des places */
		for (IPlace place : model.getPlaces()) {
			// Traitement graphique différent si la place est une référence
			if (place.isTyped(IPlace.VIRTUAL)) {
				dot.append("  \"" + place.getName() + "\" [label=\"" + place.getTargetName() + "\", shape=ellipse, color=11, style=filled];\n");
			} else if (place.isTyped(IPlace.FUNCTIONENTRY)) {
				dot.append("  \"" + place.getName() + "\" [shape=ellipse, color=4, style=filled];\n");
			} else if (place.isTyped(IPlace.FUNCTIONEXIT)) {
				dot.append("  \"" + place.getName() + "\" [shape=ellipse, color=6, style=filled];\n");
			} else if (place.isTyped(IPlace.RESOURCE)) {
				dot.append("  \"" + place.getName() + "\" [shape=ellipse, color=10, style=filled];\n");
			} else if (place.isTyped(IPlace.PATH)) {
				dot.append("  \"" + place.getName() + "\" [shape=ellipse, color=9, style=filled];\n");
			} else if (place.isTyped(IPlace.SPECIAL)) {
				dot.append("  \"" + place.getName() + "\" [shape=ellipse, color=12, style=filled];\n");
			} else {
				dot.append("  \"" + place.getName() + "\" [shape=ellipse, style=\"\"];\n");
			}

			// Traitement des sous-modèles
			for (ISubModel submodel : place.getSubModels()) {
				dot.append(toDot(submodel));

				// TODO : attention null pointer possible
				dot.append("  \"" + place.getName() + "\" -> \"" + submodel.getPlaces().iterator().next().getName() + "\" [lhead=\"cluster_" + place.getName() + "_" + submodel.getInstructionNumber() + "\"];\n");

				// Les ports d'entrée
				if (submodel.getInputPlace() != null) {
					dot.append("  \"" + submodel.getInputPlace().getName() + "\" [color=3, style=filled];\n");
				}

				// Les ports de sortie
				for (INode out : submodel.getOutputPlaces()) {
					dot.append("  \"" + out.getName() + "\" [color=5, style=filled];\n");
					//dot.append("  \"" + out.getName() + "\" -> \"" + place.getName() + "\" [lhead=\"cluster_" + place.getName() + "\"];\n");
				}
			}
		}

		/* Parcours des transitions */
		for (ITransition transition : model.getTransitions()) {
			dot.append("  \"" + transition.getName() + "\" [shape=box, style=\"\"];\n");
			if (transition.isTyped(ITransition.KEY)) {
				dot.append("  \"" + transition.getName() + "\" [color=2, style=filled];\n");
			} else  if (transition.isTyped(ITransition.FUNCTIONCALL)) {
				dot.append("  \"" + transition.getName() + "\" [color=8, style=filled];\n");
			} else  if (transition.isTyped(ITransition.FUNCTIONRETURN)) {
				dot.append("  \"" + transition.getName() + "\" [color=7, style=filled];\n");
			}

			StringBuffer guards = new StringBuffer();
			for (IGuard guard : transition.getGuards()) {
				if (guard.toString().equals("")) { continue; }
				if (guards.length() != 0) { guards.append("&&"); }
				guards.append(guard.toString());
			}
			dot.append("  \"" + transition.getName() + "\" [label=\"" + transition.getName() + "\\n" + guards.toString() + "\"];\n");
		}

		/* Parcours des arcs */
		for (IArc arc : model.getArcs()) {
			dot.append("  \"" + arc.getSource().getName() + "\" -> \"" + arc.getTarget().getName() + "\"");
			String style = "normal";
			if (arc.getType() == IArc.INHIBITOR) { style = "odot"; }
			if (!arc.getValuation().equals("1")) {
				dot.append(" [label=\"" + arc.getValuation() + "\", arrowhead=\"" + style + "\"]");
			} else {
				dot.append(" [arrowhead=\"" + style + "\"]");
			}
			dot.append(";\n");
		}

		dot.append("}\n");
		return dot.toString();
	}

	/**
	 * Extrait une représentation DOT du modèle courant et l'écrit dans un fichier
	 * @param id L'identifiant de l'étape en cours d'execution pour le modèle traité
	 * @param model Le modèle traité (à dumper en DOT)
	 * @param outputDirectory Le dossier destination
	 */
	public static void dumpDotRepresentation(IModel model, String id, File outputDirectory) {
		try {
			String filename = model.getHead().getName() + "_" + id + ".dot";
			PrintWriter dotExport = new PrintWriter(new BufferedWriter(new FileWriter(outputDirectory.getAbsolutePath() + "/" + filename)));
			dotExport.print(DotExport.toDot(model));
			dotExport.close();
			LOG.config("\t   The DOT representation :: " + id + " :: is available in: " + filename);
		} catch (IOException e) {
			System.err.println("Erreur lors de la construction du graphe dot " + e.getMessage());
			e.printStackTrace();
		}
	}
}
