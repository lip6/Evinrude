package fr.lip6.move.evinrude.builder.perspectives.threads;

import fr.lip6.move.evinrude.builder.AbstractRule;
import fr.lip6.move.evinrude.builder.IRule;
import fr.lip6.move.evinrude.builder.IRuleInstruction;
import fr.lip6.move.evinrude.builder.ResultDep;
import fr.lip6.move.evinrude.builder.perspectives.syscall.Syscall4;
import fr.lip6.move.evinrude.commons.exceptions.EvinrudeException;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICall;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IFunction;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IInstruction;
import fr.lip6.move.evinrude.commons.model.petri.Valuation;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IArc;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ISubModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IValuation;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Définition d'une règle de construction dédiée à la création de threads <code>pthread_create</code><br>
 * @author Jean-Baptiste Voron
 */
public class Threads0 extends AbstractRule implements IRule, IRuleInstruction {
	public static final String NAME = "Threads0: Thread Creation";

	/** Le logger */
	private static Logger LOG = Logger.getLogger(Threads0.class.getName());

	/**
	 * Constructeur
	 */
	public Threads0() {
		super(NAME);
		addDependency(Syscall4.NAME);
	}

	/** {@inheritDoc}
	 */
	@Override
	public final List<ISubModel> process(ResultDep dependencies, IInstruction instruction) {
		List<ISubModel> results = new ArrayList<ISubModel>();

		/* Suis-je entrain de parcourir une instruction définissant un appel de fonction ? */
		if (!instruction.isCallSite()) { return results; }

		/* Détail de l'appel et vérification qu'il s'agit bien d'une création de thread */
		ICall call = instruction.getCallSite();
		if (!call.getFunctionName().equalsIgnoreCase("pthread_create")) { return results; }

		/* Parcours des sous-modèles potentiellement intéressants */
		for (ISubModel threadSubModel : dependencies.getAssociatedSubModels(instruction)) {

			// Récupération de la routine appelée par le pthread_create
			String routine = call.getParameters().get(2).getValue();
			List<IFunction> targetList = instruction.getBlock().getFunction().getCfg().getApplication().getFunction(routine);
			if (targetList.size() < 1) {
				LOG.severe("Undefined function : " + routine);
				throw new EvinrudeException("Undefined function : " + routine);
			} else if (targetList.size() > 1) {
				LOG.warning("Ambiguity upon the function's name : " + routine + " (considering the first occurence)");
			}

			/* Recherche de la place qui contient les identificateurs des threads attachés */
			INode attached = threadSubModel.getTopModel().getNode("attached_threads");
			if (attached == null) {
				// Création de la place si nécessaire
				attached = threadSubModel.getTopModel().createPlace(ThreadsPerspective.RESOURCE_ATTACHED_THREADS, IPlace.RESOURCE);
			}

			// Recupération de la fonction de la routine
			IFunction targetRoutine = targetList.get(0);
			// Si la fonction est hors-CFG... Il faut parser le CFG
			if (!call.getBlock().getFunction().getCfg().getId().equals(targetRoutine.getCfg().getId())) {
				threadSubModel.getTopModel().enqueueNewCfg(targetRoutine.getCfg());
			}

			// Creation de la place virtuelle pour la routine
			IPlace routineEntry = threadSubModel.createPlace("thread_" + id(call) + "_target_ref", id(targetRoutine) + "_ENTRY");
			// La place virtuelle pour les ressources
			IPlace attachedResource = threadSubModel.createPlace(ThreadsPerspective.RESOURCE_ATTACHED_THREADS + "_" + threadSubModel.getInstructionNumber() + "_ref", ThreadsPerspective.RESOURCE_ATTACHED_THREADS);

			// L'arc (avec sa valuation) pour la routine
			IArc son = threadSubModel.createArc(threadSubModel.getTransition("sys_" + id(call)), routineEntry);
			son.setValuation(new Valuation(IValuation.EVENT_RESOURCE_IDENTIFIER, null, IValuation.NO_OPERATOR));

			// L'arc (avec sa valuation) pour la place des threads attachés
			IArc resource = threadSubModel.createArc(threadSubModel.getTransition("sys_" + id(call)), attachedResource);
			resource.setValuation(new Valuation(IValuation.EVENT_RESOURCE_IDENTIFIER, "1", IValuation.NO_OPERATOR));

			results.add(threadSubModel);
		}

		return results;
	}
}
