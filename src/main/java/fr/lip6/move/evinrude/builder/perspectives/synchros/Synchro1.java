package fr.lip6.move.evinrude.builder.perspectives.synchros;

import fr.lip6.move.evinrude.builder.AbstractRule;
import fr.lip6.move.evinrude.builder.IRule;
import fr.lip6.move.evinrude.builder.IRuleInstruction;
import fr.lip6.move.evinrude.builder.ResultDep;
import fr.lip6.move.evinrude.builder.perspectives.syscall.Syscall4;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICall;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IInstruction;
import fr.lip6.move.evinrude.commons.model.petri.Valuation;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IArc;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ISubModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ITransition;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IValuation;

import java.util.ArrayList;
import java.util.List;

/**
 * Définition d'une règle de construction dédiée à la gestion des synchronisations <code>pthread_mutex_unlock</code><br>
 *
 * @author Jean-Baptiste Voron
 */
public class Synchro1 extends AbstractRule implements IRule, IRuleInstruction {
	public static final String NAME = "Synchro1: Mutex UnLock";

	/**
	 * Constructeur
	 */
	public Synchro1() {
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
		if (!call.getFunctionName().equalsIgnoreCase("pthread_mutex_unlock")) { return results; }

		/* Parcours des sous-modèles potentiellement intéressants */
		for (ISubModel mutexSubModel : dependencies.getAssociatedSubModels(instruction)) {

			/* Recherche de la place qui contient les identificateurs de sémaphores */
			INode semaphores = mutexSubModel.getTopModel().getNode(SynchroPerspective.RESOURCE_THREADS_SEMAPHORES);
			if (semaphores == null) {
				// Création de la place si nécessaire
				semaphores = mutexSubModel.getTopModel().createPlace(SynchroPerspective.RESOURCE_THREADS_SEMAPHORES, IPlace.RESOURCE);
			}

			// La place virtuelle pour les ressources
			IPlace semaphoresResource = mutexSubModel.createPlace(SynchroPerspective.RESOURCE_THREADS_SEMAPHORES + "_" + mutexSubModel.getInstructionNumber() + "_ref", SynchroPerspective.RESOURCE_THREADS_SEMAPHORES);
			// La transition MUTEX_UNLOCK
			ITransition unlock = mutexSubModel.getTransition("sys_" + id(call));
			// L'arc qui permet de récupérer la valeur du mutex
			IArc fetch = mutexSubModel.createArc(semaphoresResource, unlock);
			fetch.setValuation(new Valuation(IValuation.EVENT_RESOURCE_IDENTIFIER, "mutex"));
			// L'arc qui permet d'incrémenter la valeur actuelle du MUTEX
			IArc put = mutexSubModel.createArc(unlock, semaphoresResource);
			put.setValuation(new Valuation("mutex->id", "mutex->value", IValuation.DECREMENT));

			results.add(mutexSubModel);
		}

		return results;
	}
}
