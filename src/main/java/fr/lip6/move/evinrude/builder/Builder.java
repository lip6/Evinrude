package fr.lip6.move.evinrude.builder;

import fr.lip6.move.evinrude.builder.perspectives.struct.StructuralPerspective;
import fr.lip6.move.evinrude.commons.exceptions.EvinrudeException;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IApplication;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IBlock;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICfg;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IExecutable;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IFunction;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IInstruction;
import fr.lip6.move.evinrude.commons.model.petri.Model;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ISubModel;
import fr.lip6.move.evinrude.commons.utils.log.EvinrudeLogFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Cette classe est responsable de la construction du réseau de petri en utilisant un système de perspectives.<br>
 * Les perspectives sont chargés à l'aide du ServiceLoader de java.
 *
 * @author Clément Démoulins
 * @author Jean-Baptiste Voron
 */
public class Builder {
	/** Le logger */
	private static Logger LOG = Logger.getLogger(Builder.class.getName());

	/** L'application en cours de traitement */
	private IApplication application;

	/** Le modèle de l'application */
	private IModel model;

	/** Gestionnaire des résultats réinitialiser pour chaque CFG */
	private ResultManager resultManager;

	/** Gestion particulière pour les IRuleExecutable qui ne doivent être traitées qu'une fois pour tous les CFG de l'exécutable */
	private Map<IRuleExecutable, ResultEntry> ruleExecutables;

	/** Liste des perspectives chargées */
	private List<IPerspective> perspectives;

	/**
	 * Constructeur
	 * @param application L'application en cours de construction
	 */
	public Builder(IApplication application) {
		this.application = application;
		this.perspectives = getPerspectives();
	}

	/**
	 * Chargement des perspectives.
	 * @return la liste des perspectives disponible.
	 */
	public final List<IPerspective> getPerspectives() {
		List<IPerspective> perspectives = new ArrayList<IPerspective>();

		// Chargement de la perspective structurelle
		perspectives.add(new StructuralPerspective());

		// Chargement des perspectives à l'aide du ServiceLoader de java
		ServiceLoader<IPerspective> loader = ServiceLoader.load(IPerspective.class);
		for (IPerspective perspective : loader) {
			LOG.info("Perspective " + perspective.getName() + " loaded !");
			perspectives.add(perspective);
		}

		// Vérification des dépendances, si une dépendance ne peut pas être satisfaite on quitte
		Set<String> deps = new HashSet<String>();
		for (IPerspective perspective : perspectives) {
			deps.add(perspective.getName());
			for (IRule rule : perspective.getRules()) {
				deps.add(rule.getName());
			}
		}
		for (IPerspective perspective : perspectives) {
			if (!deps.containsAll(perspective.getDependencies())) {
				LOG.severe("Dependency not satisfied in perspective : " + perspective.getName());
				System.exit(1);
			}
			for (IRule rule : perspective.getRules()) {
				if (!deps.containsAll(rule.getDependencies())) {
					LOG.severe("Dependency not satisfied in rule : " + rule.getName());
					System.exit(1);
				}
			}
		}

		return perspectives;
	}

	/**
	 * Traite une liste de règles en gérant les dépendances.<br>
	 * À la fin de cette méthode le réseau de Petri est complété en fonction du CFG et des règles passées en paramètres.
	 *
	 * @param executable executable
	 * @param cfg CFG sur lequel les règles doivent être appliquées
	 * @param rules règles à traiter
	 * @throws EvinrudeException Erreur du modèle
	 */
	private void process(IExecutable executable, ICfg cfg, List<IRule> rules) throws EvinrudeException {
		List<IRule> notProcessed = new ArrayList<IRule>(rules);

		while (!notProcessed.isEmpty()) {
			// Création de la liste des règles pouvant être traité
			List<IRule> toProcess = new ArrayList<IRule>(notProcessed);
			LOG.finer("Not processed : " + toProcess);
			for (IRule rule : notProcessed) {
				for (String dep : rule.getDependencies()) {
					if (!resultManager.containsDepenpency(dep)) {
						toProcess.remove(rule);
					}
				}
			}

			// Impossible de faire passer les règles, il y a un problème de dépendance
			if (toProcess.isEmpty()) {
				throw new EvinrudeException("Can't resolv dependencies !");
			}

			// Traitement de la liste de règles
			for (IRule rule : toProcess) {
				LOG.finer("Processing rule [" + rule.getName() + "] on " + cfg.getFileName());
				EvinrudeLogFormatter.getInstance().increaseIndent();

				ResultEntry resultEntry;

				// Résultats des dépendances de la règle en cours de traitement
				ResultDep depenpencies = resultManager.getResultDep(rule);

				// Sous modèles à vérifier
				Set<ISubModel> toCheck = new HashSet<ISubModel>();

				// Traitement de l'executable si il n'a pas déjà été traité
				if (rule instanceof IRuleExecutable) {
					resultEntry = ruleExecutables.get(rule);
					if (resultEntry == null) {
						LOG.finer("    Traitement de " + rule.getName() + " sur executable : " + executable.getName());
						resultEntry = new ResultEntry(rule, executable, ((IRuleExecutable) rule).process(depenpencies, executable));
						ruleExecutables.put((IRuleExecutable) rule, resultEntry);
					}
					toCheck.addAll(resultEntry.getSubModels());
					resultManager.putResult(rule, executable, resultEntry);
				}

				// Traitement du CFG
				if (rule instanceof IRuleCfg) {
					LOG.finer("    Traitement de " + rule.getName() + " sur CFG : " + cfg.getName());
					resultEntry = new ResultEntry(rule, cfg, ((IRuleCfg) rule).process(depenpencies, cfg));
					toCheck.addAll(resultEntry.getSubModels());
					resultManager.putResult(rule, cfg, resultEntry);
				}

				for (IFunction func : cfg.getFunctions()) {
					// Traitement de la fonction
					if (rule instanceof IRuleFunction) {
						LOG.finer("    Traitement de " + rule.getName() + " sur function : " + func.getName());
						resultEntry = new ResultEntry(rule, func, ((IRuleFunction) rule).process(depenpencies, func));
						toCheck.addAll(resultEntry.getSubModels());
						resultManager.putResult(rule, func, resultEntry);
					}
					for (IBlock block : func.getBlocks()) {
						// Traitement du block
						if (rule instanceof IRuleBlock) {
							LOG.finer("    Traitement de " + rule.getName() + " sur block : " + block.getId());
							resultEntry = new ResultEntry(rule, block, ((IRuleBlock) rule).process(depenpencies, block));
							toCheck.addAll(resultEntry.getSubModels());
							resultManager.putResult(rule, block, resultEntry);
						}
						// Traitement des instructions
						if (rule instanceof IRuleInstruction) {
							// Cas particulier lorsqu'il n'y a pas d'instructions
							if (block.getInstructions().isEmpty()) {
								// Résultat vide
								resultEntry = new ResultEntry(rule, new Object(), new ArrayList<ISubModel>());
								resultManager.putResult(rule, resultEntry.getCfgObj(), resultEntry);
							}
							for (IInstruction instruction : block.getInstructions().values()) {
								LOG.finest("    Traitement de " + rule.getName() + " sur l'instruction : " + instruction.getCfgLine());
								resultEntry = new ResultEntry(rule, instruction, ((IRuleInstruction) rule).process(depenpencies, instruction));
								toCheck.addAll(resultEntry.getSubModels());
								resultManager.putResult(rule, instruction, resultEntry);
							}
						}
					}
				}

				// Vérification des propriétés de la règle pour tous les sous-modèles (locales)
				for (ISubModel subModel : toCheck) {
					for (IProperty property : rule.getProperties()) {
						if (!property.check(subModel)) {
							LOG.severe("Local property " + property.getName() + " has failed !");
							System.exit(1);
						}
					}
				}

				// La règle est marquée comme traitée
				notProcessed.remove(rule);

				EvinrudeLogFormatter.getInstance().decreaseIndent();
			}
		}
	}

	/**
	 * Construction d'un réseau de Petri à partir de la représentation de son CFG.<br>
	 *
	 * @param executable Le réseau de Petri sera construit pour cet executable
	 * @return Réseau de Petri construit
	 * @throws EvinrudeException Erreur du modèle
	 */
	public final IModel process(IExecutable executable) throws EvinrudeException {
		LOG.info("Build application " + application.getName());
		EvinrudeLogFormatter.getInstance().increaseIndent();

		LOG.info("Processing executable " + executable.getName());
		EvinrudeLogFormatter.getInstance().increaseIndent();

		// Création du model
		model = new Model(executable);
		ruleExecutables = new HashMap<IRuleExecutable, ResultEntry>();

		// Liste des propriétés globales à vérifier sur le modèle en fin de traitement
		List<IProperty> properties = new ArrayList<IProperty>();

		// Tant qu'il y a des CFG à traiter, sachant que les traitements peuvent ajouter de nouveaux CFG.
		while (model.hasNextNewCfg()) {
			ICfg cfg = model.nextNewCfg();
			LOG.fine("Processing cfg " + cfg.getName());
			EvinrudeLogFormatter.getInstance().increaseIndent();

			// Initialisation du gestionnaire de résultat
			resultManager = new ResultManager(executable, perspectives);

			// Traitement de toutes les règles des perspectives
			List<IRule> rules = new ArrayList<IRule>();
			for (IPerspective perspective : perspectives) {
				rules.addAll(perspective.getRules());
				properties.addAll(perspective.getProperties());

			}
			process(executable, cfg, rules);
			EvinrudeLogFormatter.getInstance().decreaseIndent();
		}

		// Vérification des propriétés globales
		for (IProperty property : properties) {
			if (!property.check(model)) {
				LOG.severe("Property " + property.getName() + " fail !");
				System.exit(1);
			}
		}

		EvinrudeLogFormatter.getInstance().decreaseIndent();
		LOG.info("End processing executable " + executable.getName());

		EvinrudeLogFormatter.getInstance().decreaseIndent();
		LOG.info("End processing application " + application.getName());
		return model;
	}
}
