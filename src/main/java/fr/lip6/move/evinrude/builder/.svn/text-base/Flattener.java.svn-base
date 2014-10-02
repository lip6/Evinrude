package fr.lip6.move.evinrude.builder;

import fr.lip6.move.evinrude.builder.perspectives.struct.StructuralPerspective;
import fr.lip6.move.evinrude.commons.exceptions.EvinrudeException;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IExecutable;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IArc;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ISubModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ITransition;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IValuation;
import fr.lip6.move.evinrude.commons.utils.DotExport;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


/**
 * Classe utilitaire chargée de la mise à plat de réseaux "hiérarchiques".<br>
 * La mise à plat, considère toutes les noeuds qui contiennent des sous-réseaux.<br>
 * Dans la suite, on traite les sous-réseaux <i>dans l'ordre de leur attachement au noeud parent (noté <code>p</code>).</i><br>
 *
 * Le traitement suivant est répété pour chaque sous réseau (<code>S</code>) :
 * <ul>
 * 	<li>On note <code>L</code> un ensemble de noeuds initialisés à <code>p</code></li>
 * 	<li>On note <code>T</code> l'ensemble des noeuds successeurs de <code>p</code></li>
 * 	<li>On recopie tous les noeuds de <code>S</code> dans le réseau de plus haut niveau.</li>
 * 	<li><i>On note par <code>I</code> l'ensemble des noeuds d'entrée recopiés.</i><br>
 * 		Pour chaque noeud <code>l</code> de <code>L</code> on crée un arc reliant <code>l</code> à chaque noeud de <code>I</code><br>
 * 		Ce lien peut faire l'objet de la création de noeuds temporaires (identifiés comme noeuds structurels)</li>
 * 	<li><i>On note par O l'ensemble des noeuds de sortie recopiés.</i><br>
 * 		On ajoute tous les noeuds de <code>O</code> dans <code>L</code>.<br></li>
 * 	<li>On boucle tant qu'il y a des réseaux</li>
 * 	<li>Une fois le dernier sous-réseau traité, on relie chaque noeud de <code>L</code> à chaque noeud de <code>T</code></li>
 * </ul>
 *
 * @author Jean-Baptiste Voron
 *
 */
public final class Flattener {
	/** Le logger */
	private static Logger LOG = Logger.getLogger(Flattener.class.getName());

	/**
	 * Constructeur
	 */
	public Flattener() {
	}

	/**
	 * Mise à plat du modèle
	 * @param executable Le modèle de cette executable sera mis à plat
	 * @throws EvinrudeException Problème de construction sur le modèle
	 */
	public void flatten(IExecutable executable) throws EvinrudeException {
		flatten(executable.getModel());

		// Export DOT
		DotExport.dumpDotRepresentation((IModel) executable.getModel(), "flat", executable.getFolder());
	}

	/**
	 * Mise à plat du modèle
	 * @param model Le modèle complet
	 * @return Le modèle applatit
	 * @throws EvinrudeException Problème de construction sur le modèle
	 */
	private ISubModel flatten(ISubModel model) throws EvinrudeException {

		/* Parcours du modèle à la recherche de noeuds susceptibles d'être applatis */
		for (IPlace currentPlace : new ArrayList<IPlace>(model.getPlaces())) {
			LOG.fine("Browsing place: " + currentPlace.getName());

			// Si la place courante n'est pas hiérarchique, on ne va pas plus loin et on passe à la suivante.
			if (currentPlace.getSubModels().size() <= 0) { continue; }

			LOG.finer("> Hierarchy detected!");
			// Le noeud de fin de chaîne est le noeud parent
			List<INode> startingNodes = new ArrayList<INode>();
			startingNodes.add(currentPlace);
			// Sauvegarde des successeurs du noeud courant pour pouvoir supprimer les bons noeuds après le merge
			List<INode> endingNodes = new ArrayList<INode>(currentPlace.getNext());

			// Sinon, on appelle récursivement la fonction d'applatissement sur tous les sous-modèles de la place courante
			for (ISubModel submodel : currentPlace.getSubModels()) {
				flatten(submodel);
				LOG.finer("All these elements are flat. The submodel " + submodel.toString() + "can be flatten !");
				LOG.fine("Processing the place: " + currentPlace.getName());

				// Sauvegarde de la place d'entrée si elle existe
				IPlace entry = submodel.getInputPlace();
				// Sauvegarde des places de sorties
				List<IPlace> outputPlaces = submodel.getOutputPlaces();

				// Intégration des éléments du sous-modèle dans le modèle qui contient la place courante
				currentPlace.getSubModelContainer().integrateSubModel(submodel);
				LOG.finer("All submodel elements have been copied into " + currentPlace.getSubModelContainer());

				// Création des liens entre les noeuds de bout de chaine et tous les points d'entrée
				if (entry != null) {
					for (INode end : startingNodes) {
						String  transitionName = "sub_entry_" + end.getName() + "_to_" + entry.getName();
						LOG.finest("Creating transition: " + transitionName);
						INode subEntryTransition = currentPlace.getSubModelContainer().createTransition(transitionName, ITransition.STRUCTURAL);
						currentPlace.getSubModelContainer().createArc(subEntryTransition, currentPlace.getSubModelContainer().getTopModel().findNode(entry.getName()), IArc.NORMAL);
						currentPlace.getSubModelContainer().createArc(currentPlace.getSubModelContainer().getTopModel().findNode(end.getName()), subEntryTransition, IArc.NORMAL);
						LOG.finest("Linking: " + end.getName() + " -> " + subEntryTransition.getName() + " -> " + entry.getName());
					}
				}

				// Les noeuds de sortie du sous-modèle deviennent les noeuds de fin de chaine
				startingNodes.clear();
				startingNodes.addAll(outputPlaces);
			}

			// Gestion des noeud de sortie multiples
			if (startingNodes.size() > 1) {
				String  oupoutPlaceName = "sub_" + currentPlace.getName() + "_" + currentPlace.getSubModelContainer().getInstructionNumber() + "_output";
				LOG.finest("Creating place: " + oupoutPlaceName);
				INode outputPlace = currentPlace.getSubModelContainer().createPlace(oupoutPlaceName, IPlace.NORMAL);

				for (INode last : startingNodes) {
					String  transitionName = "sub_exit_" + last.getName() + "_" + last.getName();
					LOG.finest("Creating transition: " + transitionName);
					INode subExitTransition = currentPlace.getSubModelContainer().createTransition(transitionName, ITransition.STRUCTURAL);
					currentPlace.getSubModelContainer().createArc(currentPlace.getSubModelContainer().getTopModel().findNode(last.getName()), subExitTransition, IArc.NORMAL);
					currentPlace.getSubModelContainer().createArc(subExitTransition, outputPlace, IArc.NORMAL);
				}
				startingNodes.clear();
				startingNodes.add(outputPlace);
			}

			// Construction des liens entre les noeuds de fin de chaine et les noeuds cibles du noeud courant
			for (INode targetNode : endingNodes) {
				for (INode last : startingNodes) {
					if (targetNode.isPlace()) {
						String  transitionName = "sub_exit_" + last.getName() + "_" + targetNode.getName();
						LOG.finest("Creating transition: " + transitionName);
						INode subExitTransition = currentPlace.getSubModelContainer().createTransition(transitionName, ITransition.STRUCTURAL);
						currentPlace.getSubModelContainer().createArc(currentPlace.getSubModelContainer().getTopModel().findNode(last.getName()), subExitTransition, IArc.NORMAL);
						LOG.finest("Linking: " + last.getName() + " -> " + subExitTransition.getName());
						last = subExitTransition; // The last node is now the new transition !
					}
					currentPlace.getSubModelContainer().createArc(currentPlace.getSubModelContainer().getTopModel().findNode(last.getName()), currentPlace.getSubModelContainer().getTopModel().findNode(targetNode.getName()), IArc.NORMAL);
					LOG.finest("Linking: " + last.getName() + " -> " + targetNode.getName() + " -> " + targetNode.getName());
				}
			}

			// Suppression de tous les liens entre le noeud courant et ses anciens successeurs (ceux précédant la fusion)
			for (INode oldNext : endingNodes) {
				for (IArc oldArc : currentPlace.getSubModelContainer().getArcs(currentPlace, oldNext)) {
					currentPlace.getSubModelContainer().removeArc(oldArc);
					LOG.finest("Delete link: " + currentPlace.getName() + " -> " + oldNext.getName());
				}
			}

			// Suppression des sous-modèles qui viennent d'être applatis
			LOG.fine("Delete all submodels attached to: " + currentPlace.getName());
			currentPlace.removeSubModels();

			// Remise à zéro de la liste de bout de chaine
			startingNodes.clear();
		}

		/* Le traitement des sous-modèles s'arrête ici ! */
		if (model.getParentPlace() != null) { return model; }


		/* Le modèle doit maintenant être plat ! */
		List<INode> markToBeDeleted = new ArrayList<INode>();

		// Suppression des places VIRTUAL
		markToBeDeleted.addAll(mergeVirtualPlaces(model));
		// Suppression des places EXIT surnuméraires
		markToBeDeleted.addAll(mergeExitPlaces(model));

		// Suppression de toutes les places marquées pour la suppression
		for (INode toDelete : markToBeDeleted) {
			model.removeNode(toDelete);
		}

		return model;
	}

	/**
	 * Fusion de toutes les places EXIT.<br>
	 * Les places EXIT ne doivent pas avoir d'arcs sortants.<br>
	 * Cette fusion permet la récupération des process/threads morts.
	 * @param model Le modèle
	 * @return La liste des noeuds à supprimer
	 */
	private List<INode> mergeExitPlaces(ISubModel model) {
		List<INode> markToBeDeleted = new ArrayList<INode>();

		// Parcours de toutes les places EXIT (pour fusion générale)
		INode exitReference = model.getNode(StructuralPerspective.GLOBAL_EXIT);
		assert (exitReference != null);
		for (IPlace place : model.getPlaces()) {
			// On ne considère évidemment pas la place GLOBAL_EXIT
			if (place.equals(exitReference)) { continue; }
			// On ne considère que les places EXIT
			if (!place.isTyped(IPlace.FUNCTIONEXIT)) { continue; }

			// On doit compte le nombre d'arcs sortants non valués
			int cpt = 0;
			for (IArc outgoingArc : place.getOutgoingArcs()) {
				if (outgoingArc.getValuation().getType() == IValuation.DEFAULT_VALUATION) { cpt++; }
			}

			// Si la place exit a un ou plusieurs arcs sortant non valués... Alors on ne fusionne pas cette place
			if (cpt > 0) { continue; }

			IPlace exitPlace = place;
			markToBeDeleted.add(place);
			// Tous les prédécesseurs de la place exit doivent être liés à la place exit de référence
			for (INode pred : place.getPrevious()) {
				for (IArc ancestorLink : model.getArcs(pred, exitPlace)) {
					model.createArc(pred, exitReference, ancestorLink.getType()).setValuation(ancestorLink.getValuation());
				}
			}

			// Tous les successeurs de la place référence doivent être liés à la cible de la référence
			for (INode succ : place.getNext()) {
				for (IArc link : model.getArcs(exitPlace, succ)) {
					model.createArc(exitReference, succ, link.getType()).setValuation(link.getValuation());
				}
			}
		}
		return markToBeDeleted;
	}

	/**
	 * Suppression des places virtuelles<br>
	 * Résolution des liens.
	 * @param model Le modèle en cours de traitement
	 * @return La liste des noeuds à supprimer
	 */
	private List<INode> mergeVirtualPlaces(ISubModel model) {
		List<INode> markToBeDeleted = new ArrayList<INode>();

		/* Parcours des places susceptibles d'être liées (VIRTUAL) */
		LOG.fine("Processing virtual places");
		for (IPlace place : model.getPlaces()) {
			if (place.isTyped(IPlace.VIRTUAL)) {
				IPlace virtualPlace = place;
				LOG.finer("  Processing virtual place: " + virtualPlace.getName() + "(ref: " + virtualPlace.getTargetName() + ")");
				markToBeDeleted.add(virtualPlace);
				// Tous les prédécesseurs de la place virtuelle doivent être liés à la cible de la référence
				for (INode pred : virtualPlace.getPrevious()) {
					for (IArc ancestorArc : model.getArcs(pred, virtualPlace)) {
						IArc link = model.createArc(pred, model.getTopModel().findNode(virtualPlace.getTargetName()), ancestorArc.getType());
						link.setValuation(ancestorArc.getValuation());
						LOG.finest("    Linking: " + pred.getName() + " -> " + model.getTopModel().findNode(virtualPlace.getTargetName()));
					}
				}

				// Tous les successeurs de la place référence doivent être liés à la cible de la référence
				for (INode succ : virtualPlace.getNext()) {
					for (IArc ancestorArc : model.getArcs(virtualPlace, succ)) {
						IArc link = model.createArc(model.getTopModel().findNode(virtualPlace.getTargetName()), succ, ancestorArc.getType());
						link.setValuation(ancestorArc.getValuation());
						LOG.finest("    Linking: " + model.getTopModel().findNode(virtualPlace.getTargetName()) + " -> " + succ.getName());
					}
				}
			}

			// Suppression du type des places input / output
			if (place.isTyped(IPlace.INPUT) || place.isTyped(IPlace.OUTPUT)) { place.setType(IPlace.NORMAL); }
		}
		return markToBeDeleted;
	}

}
