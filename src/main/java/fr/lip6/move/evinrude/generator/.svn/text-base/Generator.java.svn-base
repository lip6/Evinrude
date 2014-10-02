package fr.lip6.move.evinrude.generator;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IApplication;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IBlock;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICall;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICfg;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IExecutable;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IFunction;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IArc;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IGuard;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ITransition;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IValuation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

/**
 * Générateur de code pour la seconde version de BIANCA
 * @author Jean-Baptiste Voron
 */
public final class Generator {
	/** Le logger */
	private static Logger LOG = Logger.getLogger(Generator.class.getName());

	/** La liste des post-traitements à appliquer */
	private List<StringBuilder> postProcesses;

	/** La liste des événements à intercepter */
	private Set<String> events;

	/** Génération de bianca pour cette application */
	private IApplication application;

	/**
	 * Constructeur
	 * @param application application
	 */
	public Generator(IApplication application) {
		this.application = application;
		this.postProcesses = new ArrayList<StringBuilder>();
		this.events = new HashSet<String>();
	}

	/**
	 * Génération du fichier de constantes CONST.H contenant:
	 * <ul>
	 * 	<li>Le nombre de places de ressources locales</li>
	 * 	<li>Le nombre de places de ressources globales</li>
	 * </ul>
	 * @param writer Le writer du fichier approprié
	 * @param model Le modèle en cours d'écriture
	 * @throws IOException En cas de problème lors de l'écriture
	 */
	private void generateConstHeader(FileWriter writer, IModel model) throws IOException {
		LOG.fine("    Generate const.h");

		writer.write("#ifndef CONST_H\n");
		writer.write("#define CONST_H\n\n");
		writer.write("/*************** GENERATED ***************/\n\n");
		writer.write("#define GLOBAL_RESOURCES 10\n");
		writer.write("#define LOCAL_RESOURCES 10\n\n");
		writer.write("/*****************************************/\n\n");
		writer.write("#endif /* end of include guard: CONST_H */\n");
	}

	/**
	 * Génération du fichier de symboles DEBUG.H contenant:
	 * <ul>
	 * 	<li>Le nom des places du réseau</li>
	 * 	<li>Le nom des événements</li>
	 * </ul>
	 * @param writer Le writer du fichier approprié
	 * @param model Le modèle en cours d'écriture
	 * @throws IOException En cas de problème lors de l'écriture
	 */
	private void generateDebugHeader(FileWriter writer, IModel model) throws IOException {
		LOG.fine("    Generate debug.h");

		writer.write("#ifndef DEBUG_H\n");
		writer.write("#define DEBUG_H\n\n");

		writer.write("/*************** GENERATED ***************/\n");
		writer.write("\n/** Description des PLACES **/\n");
		writer.write("static const char* places[] = {\n");
		writer.write("  \"\",\n");
		for (IPlace p : model.getPlaces()) {
			writer.write("  \"" + p.getName().toUpperCase() + "\",\n");
		}
		writer.write("  \"\"\n");
		writer.write("};\n\n");

		writer.write("\n/** Description des EVENEMENTS **/\n");
		writer.write("static const char* events[] = {\n");
		writer.write("  \"\",\n");
		for (String e : this.events) {
			writer.write("  \"" + e.toUpperCase() + "\",\n");
		}
		writer.write("  \"\"\n");
		writer.write("};\n\n");
		writer.write("/*****************************************/\n\n");
		writer.write("#endif /* end of include guard: DEBUG_H */\n");
	}

	/**
	 * Ecriture du fichier NET.H
	 * @param writer Le writer du fichier approprié
	 * @param model Le modèle en cours d'écriture
	 * @throws IOException En cas de problème lors de l'écriture
	 */
	private void generateNetHeader(FileWriter writer, IModel model) throws IOException {
		LOG.fine("    Generate net.h");

		/* HEADER */
		writer.write("#ifndef NET_H\n");
		writer.write("#define NET_H\n\n");

		writer.write("#include \"events.h\"\n");
		writer.write("#include \"../process.h\"\n");
		writer.write("#include \"../state.h\"\n");
		writer.write("#include \"../global_resource.h\"\n");
		writer.write("#include \"../global_table.h\"\n\n");

		writer.write("void init_resources(global_table_t* global_table);\n");
		writer.write("unsigned int next(global_table_t* global_table, process_t* process, state_t* current, event_t event);\n");
		writer.write("void update(global_table_t* global_table, state_t* current, event_t event);\n\n");

		writer.write("/*************** GENERATED ***************/\n");
		/* Ecriture des symboles */
		int k = 1;
		for (IPlace p : model.getPlaces()) {
			writer.write("#define P" + p.getName().toUpperCase() + " " + k++ + "\n");
		}
		writer.write("/*****************************************/\n\n");
		writer.write("#endif /* end of include guard: NET_H */\n\n");
	}

	/**
	 * Ecriture du fichier EVENTS.H
	 * @param writer Le writer du fichier approprié
	 * @param model Le modèle en cours d'écriture
	 * @throws IOException En cas de problème lors de l'écriture
	 */
	private void generateEventsHeader(FileWriter writer, IModel model) throws IOException {
		LOG.fine("    Generate events.h");

		/* HEADER */
		writer.write("#ifndef EVENTS_H\n");
		writer.write("#define EVENTS_H\n\n");

		writer.write("#include \"../process.h\"\n\n");

		writer.write("typedef struct {\n");
		writer.write("  process_t* owner;\n");
		writer.write("  unsigned int type;\n");
		writer.write("  unsigned int id;\n");
		writer.write("  unsigned int value;\n");
		writer.write("} event_t;\n\n");

		writer.write("/*************** GENERATED ***************/\n");
		/* Ecriture des symboles */
		int k = 1;
		for (String e : this.events) {
			writer.write("#define " + e.toUpperCase() + " " + k++ + "\n");
		}
		writer.write("/*****************************************/\n\n");
		writer.write("#endif /* End of include guard: EVENTS_H */\n\n");
	}

	/**
	 * Génération du code associé au franchissement immédiat des transition structurelle et pseudo-structurelle
	 * @param currentPlace Place courante
	 * @param model Le modèle encours de traitement
	 * @return Le code généré
	 */
	private StringBuilder immediateTransitions(IPlace currentPlace, IModel model) {
		StringBuilder toReturn = new StringBuilder();
		/* --------------------------------------------------------------- */
		/* Création d'un nouvel état si une transition STRUCT est détectée */
		boolean first = false;
		for (INode outputTransition : currentPlace.getNext()) {
			if (((ITransition) outputTransition).isTyped(ITransition.STRUCTURAL)) {
				if (!first) { toReturn.append("      state_t struct_state;\n"); first = true; }
				toReturn.append("      /* Duplique l'état courant du process (Pour la transition structurelle " + outputTransition.getName() + " */\n");
				toReturn.append("      struct_state = dup_state(*current);\n");
				// Le .get(0) est possible car une transition structurelle n'a qu'un arc de sortie
				toReturn.append("      struct_state.place = P" + outputTransition.getNextNodes().get(0).getName().toUpperCase() + ";\n");
				toReturn.append("      add_state(process, struct_state);\n");
			}
		}
		/* --------------------------------------------------------------- */



		/* ----------------------------------------------------------------------- */
		/* Création d'un nouvel état si une transition FUNCTIONCALL est détectée   */
		first = false;
		for (INode outpoutTransition : currentPlace.getNext()) {
			if (((ITransition) outpoutTransition).isTyped(ITransition.FUNCTIONCALL)) {
				if (!first) {
					toReturn.append("      state_t caller_state;\n\n");
					first = true;
				}

				ITransition caller = (ITransition) outpoutTransition;
				IPlace functionPath = null;
				IPlace functionEntry = null;

				// Recherche de la place PATH
				for (INode next : caller.getNextNodes()) {
					if (((IPlace) next).isTyped(IPlace.PATH)) {
						functionPath = (IPlace) next;
						break;
					}
				}

				// Recherche de la place ENTRY
				for (INode next : caller.getNextNodes()) {
					if (!((IPlace) next).isTyped(IPlace.PATH)) {
						functionEntry = (IPlace) next;
						break;
					}
				}

				// Vérifie que la place d'entrée et la place de path existe bien
				if ((functionEntry == null) || (functionPath == null)) {
					LOG.severe("L'appel de fonction " + caller.getName() + " n'est pas correct !");
					break;
				}

				// Ecriture de l'appel de fonction
				toReturn.append("      caller_state = dup_state(*current);\n");
				toReturn.append("      caller_state.place = P" + functionEntry.getName().toUpperCase() + ";\n");
				toReturn.append("      add_local_resource(&caller_state, P" + functionPath.getName().toUpperCase() + ", 1);\n");
				toReturn.append("      add_state(process, caller_state);\n");
			}
		}
		/* ----------------------------------------------------------------------- */

		/* ------------------------------------------------------------ */
		/* Gestion des transitions FUNCTIONRETURN (plusieurs input arcs)        */
		Map<INode, Integer> resources = new HashMap<INode, Integer>();
		int countPred = 0; // Compteur de préconditions

		// Pour toutes les transitions suivantes
		for (INode outpoutTransition : currentPlace.getNext()) {
			// Vérifie que la transition est bien une transition de RETOUR de fonction
			if (((ITransition) outpoutTransition).isTyped(ITransition.FUNCTIONRETURN)) {
				for (INode pred : outpoutTransition.getPreviousNodes()) {
					// Si la place d'entrée est la current... on passe
					if (pred.equals(currentPlace)) { continue; }
					// Sinon on indique qu'une ressource supplémentaire est à considérer pour cette transition
					if (resources.get(pred) == null) { resources.put(pred, countPred++); }
				}
			}
		}

		/* Ecriture des récupérations de ressources */
		for (INode condition : resources.keySet()) {
			toReturn.append("      local_resource_t* local_resource" + resources.get(condition) + " = get_local_resource(current, P" + condition.getName().toUpperCase() + ");\n");
		}
		toReturn.append("\n");

		/* Ecriture des conditions de franchissement pour chaque condition */
		for (INode outpoutTransition : currentPlace.getNext()) {
			if (((ITransition) outpoutTransition).isTyped(ITransition.FUNCTIONRETURN)) {
				List<INode> trueConditions = new ArrayList<INode>();
				List<INode> falseConditions = new ArrayList<INode>();
				for (INode pred : outpoutTransition.getPreviousNodes()) {
					if (pred.equals(currentPlace)) { continue; }

					// Recherche du type d'arc entre la ressource et la transition RETURN
					if (model.getFirstArc(pred, outpoutTransition).getType() == IArc.NORMAL) {
						trueConditions.add(pred);
					} else {
						falseConditions.add(pred);
					}
				}

				// Ecriture de la composition
				toReturn.append("      /* Transition " + outpoutTransition.getName() + " */\n");
				toReturn.append("      if (");
				for (INode c : trueConditions) {
					toReturn.append("(local_resource" + resources.get(c) + " != NULL) && ");
				}
				for (INode c : falseConditions) {
					toReturn.append("(local_resource" + resources.get(c) + " == NULL) && ");
				}
				toReturn.append("1) {\n");

				toReturn.append("        VERBOSE(\"Franchissement de la transition : " + outpoutTransition.getName() + "\\n\");\n");
				toReturn.append("        state_t return_state = dup_state(*current);\n\n");

				// Les ressources liés avec des arcs normaux doivent être consommés
				for (INode c : trueConditions) {
					toReturn.append("        /* On consomme la ressource (" + c.getName() + ") */\n");
					toReturn.append("        delete_local_resource(&return_state, local_resource" + resources.get(c) + ", 1);\n");
				}
				toReturn.append("\n      /* On change l'état... */\n");
				toReturn.append("        return_state.place = P" + outpoutTransition.getNextNodes().get(0).getName().toUpperCase() + ";\n");
				toReturn.append("        add_state(process, return_state);\n");
				toReturn.append("      }\n\n");
			}
		}
		return toReturn;
	}

	/**
	 * Génération des instructions pour le post-franchissement<br>
	 * @param currentPlace La place courante
	 * @param nextTransition La transition nécessitant un post-franchissement
	 */
	private void generatePostProcessing(IPlace currentPlace, ITransition nextTransition) {
		StringBuilder post = new StringBuilder();
		post.append("    /* Place " + currentPlace.getName().toUpperCase() + " */\n");
		post.append("    case P" + currentPlace.getName().toUpperCase() + ": {\n");
		post.append("      switch (event.type) {\n");
		post.append("        case " + nextTransition.getEventGuard().getEvent().toUpperCase() + ": {\n");

		// Pour tous les arcs sortants de cette transition spéciale
		for (IArc outArc : nextTransition.getOutgoingArcs()) {

			// Si l'arc est valué de façon standard... On ne traite pas
			if (outArc.getValuation().getType() == IValuation.DEFAULT_VALUATION) { continue; }

			// SI l'arc n'est pas valué de façon dynamique... On ne traite pas (sera traité plus tard)
			if (!outArc.getValuation().isDynamic()) { continue; }

			// Si la cible de l'arc est une place de ressource, on doit créer une ressource
			if (outArc.getTarget().isTyped(IPlace.RESOURCE)) {
				post.append("          global_resource_t* global_resource = fetch_global_resource(global_table, P" + outArc.getTarget().getName().toUpperCase() + ");\n");
				post.append("          assert(global_resource != NULL);\n");
				post.append("          set_global_token(global_resource, " + outArc.getValuation().getIdentifier() + ", " + outArc.getValuation().getValue() + ");\n");
			} else {
				post.append("          process_t* newprocess = find_process_id(global_table, " + outArc.getValuation().getIdentifier() + ");\n");
				post.append("          if (newprocess == NULL) {\n");
				post.append("            newprocess = init_process(" + outArc.getValuation().getIdentifier() + ", P" + outArc.getTarget().getName().toUpperCase() + ");\n");
				post.append("            add_process(global_table, newprocess);\n");
				post.append("          } else {\n");
				post.append("            newprocess->context = TRUE;\n");
				post.append("            state_t new_state = create_state(P" + outArc.getTarget().getName().toUpperCase() + ");\n");
				post.append("            add_state(newprocess, new_state);\n");
				post.append("          }\n");
			}
		}
		post.append("          return;\n");
		post.append("        }\n");
		post.append("        default: return;\n");
		post.append("      }\n");
		post.append("    }\n");
		postProcesses.add(post);
	}

	/**
	 * Génération du code associé à la déclaration statique de ressources
	 * @param model Le modèle courant
	 * @return Le code généré
	 */
	private StringBuilder initResources(IModel model) {
		StringBuilder toReturn = new StringBuilder();
		toReturn.append("/*********************************************************************************\n");
		toReturn.append(" * Déclaration statiques des ressources globales utilisées par l'application\n");
		toReturn.append(" */\n");
		toReturn.append("void init_resources(global_table_t* global_table) {\n");
		for (IPlace place : model.getPlaces()) {
			if (place.isTyped(IPlace.RESOURCE)) {
				toReturn.append("  declare_global_resource(global_table, P" + place.getName().toUpperCase() + ");\n");
			}
		}
		toReturn.append("}\n\n");
		return toReturn;
	}

	/**
	 * Génération du code associé au calcul des état successeurs
	 * @param model Le modèle courant
	 * @return Le code généré
	 */
	private StringBuilder nextFunction(IModel model) {
		StringBuilder toReturn = new StringBuilder();

		toReturn.append("/*********************************************************************************\n");
		toReturn.append(" * Détermine le marquage après le franchissement d'une transition sensible à EVENT\n");
		toReturn.append(" */\n");
		toReturn.append("unsigned int next(global_table_t* global_table, process_t* process, state_t* current, event_t event) {\n\n");
		toReturn.append("  switch (current->place) {\n\n");

		/* Parcours de toutes les places du modèle */
		for (IPlace currentPlace : model.getPlaces()) {

			// On ne considère pas les places particulières
			if (currentPlace.isTyped(IPlace.RESOURCE) || currentPlace.isTyped(IPlace.VIRTUAL) || currentPlace.isTyped(IPlace.PATH) || currentPlace.isTyped(IPlace.SPECIAL)) { continue; }

			toReturn.append("    /* Place " + currentPlace.getName() + " */\n");
			toReturn.append("    case P" + currentPlace.getName().toUpperCase() + ": {\n");

			/* Gestion des transitions immédiates */
			toReturn.append(this.immediateTransitions(currentPlace, model));


			/* Cas de place standard : Evenement normal */
			if (currentPlace.getNext().size() > 0) {
				toReturn.append("      switch (event.type) {\n");

				/* Parcours de toutes les transitions accessibles depuis cette place*/
				for (INode outputNode : currentPlace.getNext()) {
					ITransition nextTransition = (ITransition) outputNode;

					/* Si la transition n'est pas evenementielle... On passe (ne doit pas arriver...) */
					if ((nextTransition.isTyped(ITransition.STRUCTURAL)) || (nextTransition.isTyped(ITransition.FUNCTIONCALL)) || (nextTransition.isTyped(ITransition.FUNCTIONRETURN))) {
						continue;
					}

					/* Si la transition produit plusieurs jetons... Elle nécessite (en général) un post-traitement */
					if (nextTransition.getNextNodes().size() > 1) { this.generatePostProcessing(currentPlace, nextTransition); }

					/* Traitement de l'événement attendu */
					toReturn.append("        case " + nextTransition.getEventGuard().getEvent().toUpperCase() + ": {\n");
					/* Ajout de l'événément dans une liste utilisé par EVENT.H */
					this.events.add(nextTransition.getEventGuard().getEvent().toUpperCase());

					/* Si la transition a plusieurs pré-conditions */
					if (nextTransition.getPreviousNodes().size() > 1) {

						/* Parcours de toutes les places en entrée de la transition */
						int i = 0; // Counter for preconditions
						Map<Integer, IArc> conditions = new HashMap<Integer, IArc>();
						for (IArc preCondArc : nextTransition.getIncomingArcs()) {
							if (preCondArc.getSource().equals(currentPlace)) { continue; }
							conditions.put(i, preCondArc);
							if (preCondArc.getSource().getType() == IPlace.RESOURCE) {
								toReturn.append("          global_resource_t* global_resource" + i + " = fetch_global_resource(global_table, P" + preCondArc.getSource().getName().toUpperCase() + ");\n");
								toReturn.append("          assert(global_resource" + i + " != NULL);\n");
								toReturn.append("          global_token_t* cond" + i + " = get_global_token(global_resource" + i + ", " + preCondArc.getValuation().getIdentifier() + ");\n");
								if (preCondArc.getValuation().getVariable() != null) {
									toReturn.append("          global_token_t* " + preCondArc.getValuation().getVariable() + " = cond" + i + ";\n");
								}
							} else {
								toReturn.append("          unsigned int state" + i + " = P" + preCondArc.getSource().getName().toUpperCase() + ";\n");
								toReturn.append("          process_t* cond" + i + " = find_process_state(global_table, " + preCondArc.getValuation().getIdentifier() + ", state" + i + ");\n");
								toReturn.append("          /* Gestion des noeuds early */\n");
								for (INode earlyNode : this.computeEarlyNodes(preCondArc.getSource())) {
									toReturn.append("          if (cond" + i + " == NULL) { \n");
									toReturn.append("             state" + i + " = P" + earlyNode.getName().toUpperCase() + ";\n");
									toReturn.append("             cond" + i + " = find_process_state(global_table, " + preCondArc.getValuation().getIdentifier() + ", state" + i + ");\n");
									toReturn.append("          }\n");
								}
							}
							i++;
						}

						// Ecriture de la condition composée
						toReturn.append("\n          if (");
						// Vérification de la non-nulité des conditions (pour toutes les conditions)
						for (int j = 0; j < i; j++) { toReturn.append("(cond" + j + " != NULL) && "); }
						// Vérification des guardes
						for (IGuard guard : nextTransition.getGuards()) {
							if (guard.getType() == IGuard.EVENT_GUARD) { continue; }
							if (guard.getType() == IGuard.DEFAULT_GUARD) { continue; }
							toReturn.append("(" + guard.getCondition() + ")");
							toReturn.append(" && ");
						}
						toReturn.append("1) {\n");

						// Consommation des ressource connectées avec un arc normal
						toReturn.append("            /* On consomme les ressources */\n");
						for (int condition : conditions.keySet()) {
							if (conditions.get(condition).getSource().getType() == IPlace.RESOURCE) {
								toReturn.append("            delete_global_token(global_resource" + condition + ", cond" + condition + ");\n");
							} else {
								toReturn.append("            delete_state(cond" + condition + ", find_state(cond" + condition + ", state" + condition + "));\n");
							}
						}

						// Evaluation des post-conditions non dyamiques
						toReturn.append("            /* On produit les ressources (non-dynamiques) */\n");
						for (IArc postCondArc : nextTransition.getOutgoingArcs()) {
							if (postCondArc.getValuation().isDynamic()) { continue; }
							if (postCondArc.getValuation().getType() == IValuation.DEFAULT_VALUATION) { continue; }

							if (postCondArc.getTarget().isTyped(IPlace.RESOURCE)) {
								toReturn.append("            global_resource_t* global_resource" + i + " = fetch_global_resource(global_table, P" + postCondArc.getTarget().getName().toUpperCase() + ");\n");
								toReturn.append("            assert(global_resource" + i + " != NULL);\n");
								toReturn.append("            set_global_token(global_resource" + i + ", " + postCondArc.getValuation().getIdentifier() + ", " + postCondArc.getValuation().getValue() + ");\n");
							} else {
								LOG.severe("Post-condition liee a une place non-ressource");
							}
						}
						toReturn.append("            /* On change l'état... */\n");
						toReturn.append(updateState(currentPlace, nextTransition).toString());
						toReturn.append("          }\n");
					} else {
						toReturn.append("          /* On change l'état... */\n");
						toReturn.append(updateState(currentPlace, nextTransition).toString());
					}
					toReturn.append("        }\n");
				}

				// Bloc par défaut du switch global
				toReturn.append("        default: return ERROR;\n");
				toReturn.append("      }\n");
			}
			toReturn.append("    }\n\n");
		}
		toReturn.append("    case PGLOBAL_EXIT: return ERROR;\n");

		toReturn.append("  }\n");
		toReturn.append("  assert(0);\n");
		toReturn.append("}\n\n\n");

		return toReturn;
	}

	/**
	 * Détermine la liste des noeuds <i>early</i><br>
	 * Ces noeuds sont séparés du noeud <b>source</b> que d'une transition strcutrelle.
	 * @param source Le noeud à partir duquel les noeuds early doivent être calculés
	 * @return La liste des noeuds early
	 */
	private List<INode> computeEarlyNodes(INode source) {
		List<INode> toReturn = new ArrayList<INode>();
		for (INode inputNode : source.getPreviousNodes()) {
			if ((!inputNode.isTransition()) || (!inputNode.isTyped(ITransition.STRUCTURAL))) { continue; }
			toReturn.add(inputNode.getPreviousNodes().get(0));
		}
		return toReturn;
	}

	/**
	 * Génération du bout de code qui met à jour l'état d'un processus
	 * @param currentPlace La place courante
	 * @param nextTransition La transition événementielle traitée
	 * @return Le code généré
	 */
	private StringBuilder updateState(IPlace currentPlace, ITransition nextTransition) {
		StringBuilder toReturn = new StringBuilder();
		INode next = null;
		for (IArc postCondArc : nextTransition.getOutgoingArcs()) {
			if (postCondArc.getValuation().getType() == IValuation.DEFAULT_VALUATION) {
				next = postCondArc.getTarget();
			}
		}
		if (next != null) {
			toReturn.append("            current->place = P" + next.getName().toUpperCase() + ";\n");
			toReturn.append("            return P" + currentPlace.getName().toUpperCase() + ";\n");
		} else {
			LOG.severe("Mise a jour de l'état impossible pour la place " + currentPlace.getName() + "(" + nextTransition.getName() + ")");
		}
		return toReturn;
	}

	/**
	 * Ecriture du fichier NET.C
	 * @param writer Le writer dans le bon fichier
	 * @param model Le modèle en cours d'écriture
	 * @throws IOException En cas de problème lors de l'écriture
	 */
	private void generateNet(FileWriter writer, IModel model) throws IOException {
		LOG.fine("    Generate net.c");

		/* HEADER du fichier net.c*/
		writer.write("#include \"net.h\"\n\n");

		/* Déclaration des ressources globales */
		writer.write(this.initResources(model).toString());

		/* Fonction de calcul des états successeurs */
		writer.write(this.nextFunction(model).toString());

		/* Fonction de post-franchissement */
		writer.write(this.updateFunction().toString());
	}

	/**
	 * Génération du code associée au post-franchissement de transitions
	 * @return Le code généré
	 * @throws IOException En cas de problème d'écriture
	 */
	private StringBuilder updateFunction() throws IOException {
		StringBuilder toReturn = new StringBuilder();

		toReturn.append("/***************************************************************************\n");
		toReturn.append(" * Met à jour le marquage après le franchissement d'après les informations récupérées\n");
		toReturn.append(" */\n");
		toReturn.append("void update(global_table_t* global_table, state_t* current, event_t event) {\n");
		toReturn.append("  switch (current->previous) {\n");

		for (StringBuilder post : postProcesses) { toReturn.append(post.toString()); }

		toReturn.append("    default: return;\n");
		toReturn.append("  }\n");
		toReturn.append("}\n\n");

		return toReturn;
	}

	/**
	 * Récupère directement sur le svn la dernière version de Bianca
	 * @param biancaPath dossier où est fait le checkout
	 * @throws SVNException erreur dans l'url ou de connexion
	 */
	private void checkOutBianca(File biancaPath) throws SVNException {
		// Option par défaut de svn
		ISVNOptions options = SVNWCUtil.createDefaultOptions(true);

		// Parametres d'authentification
		ISVNAuthenticationManager auth = new BasicAuthenticationManager(System.getProperty("evinrude.svn.user"), System.getProperty("evinrude.svn.password"));

		// URL du serveur svn de Bianca
		SVNURL svnUrl = SVNURL.parseURIDecoded(System.getProperty("evinrude.svn.bianca"));

		LOG.info("    Check out of Bianca [" + svnUrl + "]");
		SVNClientManager svnManager = SVNClientManager.newInstance(options, auth);
		SVNUpdateClient client = svnManager.getUpdateClient();
		long revision = client.doCheckout(svnUrl, biancaPath, SVNRevision.HEAD, SVNRevision.HEAD, SVNDepth.INFINITY, false);
		LOG.fine("       revision " + revision);
	}

	/**
	 * Mise à jour de bianca
	 * @param biancaPath dossier contenant Bianca
	 * @throws SVNException erreur de connexion
	 */
	private void updateBianca(File biancaPath) throws SVNException {
		// Option par défaut de svn
		ISVNOptions options = SVNWCUtil.createDefaultOptions(true);

		// Parametres d'authentification
		ISVNAuthenticationManager auth = new BasicAuthenticationManager(System.getProperty("evinrude.svn.user"), System.getProperty("evinrude.svn.password"));

		LOG.info("    Update Bianca");
		SVNClientManager svnManager = SVNClientManager.newInstance(options, auth);
		SVNUpdateClient client = svnManager.getUpdateClient();
		long revision = client.doUpdate(biancaPath, SVNRevision.HEAD, SVNDepth.INFINITY, false, false);
		LOG.fine("       revision " + revision);
	}

	/**
	 * @param fileName nom de fichier
	 * @return String contenant le fichier
	 * @throws IOException erreur de lecture ou d'ouverture
	 */
	private String loadFile(String fileName) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		StringBuilder sb = new StringBuilder();

		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line).append('\n');
		}
		reader.close();

		return sb.toString();
	}

	/**
	 * @param fileName nom du fichier à découper
	 * @return map de type tag:valeur
	 * @throws IOException erreur d'entrée/sortie
	 */
	private Map<String, String> splitFile(String fileName) throws IOException {
		Map<String, String> split = new HashMap<String, String>();

		try {
			SAXBuilder builder = new SAXBuilder();
			String fileContent = loadFile(fileName);
			fileContent = fileContent.replaceAll("&", "&amp;");
			Document doc = builder.build(new StringReader(fileContent));
			Element root = doc.getRootElement();
			for (Iterator< ? > iterator = root.getChildren().iterator(); iterator.hasNext();) {
				Element child = (Element) iterator.next();
				split.put(child.getName(), child.getText());
			}
		} catch (JDOMException e) {
			LOG.warning(e.getMessage());
		}

		return split;
	}

	/**
	 * @param path dossier dans lequel on cherche récursivement
	 * @param fileName fichier à chercher
	 * @return Chemin vers le fichier trouvé ou <code>null</code>
	 */
	private String findFile(String path, String fileName) {
		LOG.finest("        Recherche de '" + fileName + "' dans le dossier " + path);
		File dir = new File(path);

		if (!dir.isDirectory()) {
			LOG.warning(path + " is not a directory");
			return null;
		}

		for (File child : dir.listFiles()) {
			if (child.getName().equals(fileName)) {
				return child.getAbsolutePath();
			} else if (child.isDirectory() && !child.getName().startsWith(".")) {
				String result = findFile(child.getAbsolutePath(), fileName);
				if (result != null) {
					return result;
				}
			}
		}

		return null;
	}

	/**
	 * @param writer writer sur le fichier capsule.h
	 * @param executable executable
	 * @throws IOException erreur d'entrée/sortie
	 */
	private void generateCapsuleHeader(Writer writer, IExecutable executable) throws IOException {
		LOG.fine("    Generate capsule.h");

		String header = loadFile(executable.getBiancaFolder().getAbsolutePath() + "/stubs/capsule.h");
		StringBuilder sb = new StringBuilder();
		for (String event : events) {
			try {
				Map<String, String> split = splitFile(findFile(executable.getBiancaFolder().getAbsolutePath() + "/stubs", event.toLowerCase() + ".stub"));
				if (split.containsKey("header")) {
					sb.append(split.get("header"));
				} else {
					LOG.warning("Erreur lors de la génération de capsule.h pour '" + event + "'");
				}
				//				sb.append(loadFile(biancaPath.getAbsolutePath() + "/stubs/" + event.toLowerCase() + ".header"));
			} catch (IOException e) {
				LOG.warning("Erreur lors de la génération de capsule.h pour '" + event + "'");
			} catch (NullPointerException e) {
				LOG.warning("Erreur lors de la génération de capsule.h pour '" + event + "'");
			}
		}

		// On ajoute un jeton dans la place main
		sb.append("\n");

		sb.append("#define MAIN_ENTRY P");
		sb.append(executable.getMain().getCfg().getId()).append("_");
		sb.append(executable.getMain().getId()).append("_ENTRY\n");

		header = header.replace("EVENTS_HEADER_GENERATED", sb.toString());
		writer.write(header);
	}

	/**
	 * @param writer writer sur le fichier capsule.h
	 * @param biancaPath racine de Bianca
	 * @throws IOException erreur d'entrée/sortie
	 */
	private void generateCapsule(Writer writer, File biancaPath) throws IOException {
		LOG.fine("    Generate capsule.c");

		String header = loadFile(biancaPath.getAbsolutePath() + "/stubs/capsule.c");

		// Génération des fonctions pour chaque évenements
		StringBuilder functions = new StringBuilder();
		for (String event : events) {
			try {
				Map<String, String> split = splitFile(findFile(biancaPath.getAbsolutePath() + "/stubs", event.toLowerCase() + ".stub"));
				if (split.containsKey("function")) {
					functions.append(split.get("function"));
				} else {
					LOG.warning("Erreur lors de la génération de capsule.h pour '" + event + "'");
				}
			} catch (IOException e) {
				LOG.warning("Erreur lors de la génération de la fonction dans capsule.c pour '" + event + "'");
			} catch (NullPointerException e) {
				LOG.warning("Erreur lors de la génération de la fonction dans capsule.c pour '" + event + "'");
			}
		}
		header = header.replace("EVENTS_FUNCTION_GENERATED", functions.toString());

		// Génération de l'initialisation des fonctions pour chaque évenements
		StringBuilder inits = new StringBuilder();
		for (String event : events) {
			try {
				Map<String, String> split = splitFile(findFile(biancaPath.getAbsolutePath() + "/stubs", event.toLowerCase() + ".stub"));
				if (split.containsKey("init")) {
					inits.append(split.get("init"));
				} else {
					LOG.warning("Erreur lors de la génération de capsule.h pour '" + event + "'");
				}
			} catch (IOException e) {
				LOG.warning("Erreur lors de la génération de l'init dans capsule.c pour '" + event + "'");
			} catch (NullPointerException e) {
				LOG.warning("Erreur lors de la génération de l'init dans capsule.c pour '" + event + "'");
			}
		}
		header = header.replace("EVENTS_INIT_GENERATED", inits.toString());

		writer.write(header);
	}

	/**
	 * Generation du fichier de configuration de BIANCA
	 * @param executable Génération pour cet executable
	 */
	public void generateNets(IExecutable executable) {
		LOG.info("Generate Bianca for executable: " + executable.getName());

		// Initialisation de SVN
		DAVRepositoryFactory.setup();

		// Checkout ou Update de Bianca
		try {
			if (!executable.getBiancaFolder().exists()) {
				executable.getBiancaFolder().mkdirs();
				checkOutBianca(executable.getBiancaFolder());
			} else {
				updateBianca(executable.getBiancaFolder());
			}
		} catch (SVNException e) {
			executable.getBiancaFolder().delete();
			LOG.severe(e.getErrorMessage().getFullMessage());
		}

		FileWriter writer = null;
		FileWriter writer2 = null;
		FileWriter writer3 = null;
		FileWriter writer4 = null;
		FileWriter writer5 = null;
		FileWriter writer6 = null;
		FileWriter writer7 = null;
		try {
			File generatedPath = new File(executable.getBiancaFolder(), "generated");
			generatedPath.mkdirs();
			writer = new FileWriter(generatedPath.getAbsolutePath() + "/net.c", false);
			writer2 = new FileWriter(generatedPath.getAbsolutePath() + "/net.h", false);
			writer3 = new FileWriter(generatedPath.getAbsolutePath() + "/events.h", false);
			writer4 = new FileWriter(generatedPath.getAbsolutePath() + "/capsule.h", false);
			writer5 = new FileWriter(generatedPath.getAbsolutePath() + "/capsule.c", false);
			writer6 = new FileWriter(generatedPath.getAbsolutePath() + "/debug.h", false);
			writer7 = new FileWriter(generatedPath.getAbsolutePath() + "/const.h", false);
			this.generateNet(writer, executable.getModel());
			this.generateConstHeader(writer7, executable.getModel());
			this.generateDebugHeader(writer6, executable.getModel());
			this.generateNetHeader(writer2, executable.getModel());
			this.generateEventsHeader(writer3, executable.getModel());
			this.generateCapsuleHeader(writer4, executable);
			this.generateCapsule(writer5, executable.getBiancaFolder());
		} catch (IOException error) {
			LOG.severe("Erreur lors de l'écriture du code généré: " + error.getMessage());
		} finally {
			try {
				writer.close();
				writer2.close();
				writer3.close();
				writer4.close();
				writer5.close();
				writer6.close();
				writer7.close();
			} catch (IOException e) {
				// On est foutu
				e.printStackTrace();
			}
		}

		Set<String> calls = new HashSet<String>();
		for (ICfg cfg : application.getCfgs()) {
			for (IFunction function : cfg.getFunctions()) {
				for (IBlock block : function.getBlocks()) {
					for (ICall call : block.getLibraryCalls().values()) {
						calls.add(call.getFunctionName().toUpperCase());
					}
				}
			}
		}
		calls.removeAll(events);
		if (calls.isEmpty()) {
			LOG.fine("    Tous les appels système ont été traité");
		} else {
			LOG.fine("Appels système non traité :");
			for (String call : calls) {
				LOG.fine("\t- " + call);
			}
		}
		LOG.info("End of generation");
	}
}
