package fr.lip6.move.evinrude.builder.perspectives.threads;

import fr.lip6.move.evinrude.builder.AbstractRule;
import fr.lip6.move.evinrude.builder.IRule;
import fr.lip6.move.evinrude.builder.IRuleInstruction;
import fr.lip6.move.evinrude.builder.ResultDep;
import fr.lip6.move.evinrude.builder.perspectives.struct.StructuralPerspective;
import fr.lip6.move.evinrude.builder.perspectives.syscall.Syscall4;
import fr.lip6.move.evinrude.commons.exceptions.EvinrudeException;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICall;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IInstruction;
import fr.lip6.move.evinrude.commons.model.petri.Valuation;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IArc;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ISubModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ITransition;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IValuation;

import java.util.ArrayList;
import java.util.List;

/**
 * Définition d'une règle de construction dédiée à la getion des appels <code>pthread_join</code><br>
 * @author Jean-Baptiste Voron
 */
public class Threads1 extends AbstractRule implements IRule, IRuleInstruction {
	public static final String NAME = "Threads1: Thread Deletion/Collection";

	/**
	 * Constructeur
	 */
	public Threads1() {
		super(NAME);
		addDependency(Syscall4.NAME);
	}

	/** {@inheritDoc}
	 */
	@Override
	public final List<ISubModel> process(ResultDep depenpencies, IInstruction instruction) throws EvinrudeException {
		List<ISubModel> results = new ArrayList<ISubModel>();

		/* Suis-je entrain de parcourir une instruction définissant un appel de fonction ? */
		if (!instruction.isCallSite()) {
			return results; // Instruction non valide
		}

		/* Détail de l'appel et vérification qu'il s'agit bien d'une création de thread */
		ICall call = instruction.getCallSite();
		if (!call.getFunctionName().equalsIgnoreCase("pthread_join")) {
			return results; // Appel de fonction non valide
		}

		/* Parcours des sous-modèles potentiellement intéressants */
		for (ISubModel threadSubModel : depenpencies.getAssociatedSubModels(instruction)) {

			// Création des places virtuelles
			IPlace virtual = threadSubModel.createPlace(ThreadsPerspective.RESOURCE_ATTACHED_THREADS + "_" + threadSubModel.getInstructionNumber() + "_ref", ThreadsPerspective.RESOURCE_ATTACHED_THREADS);
			IPlace globalExit = threadSubModel.createPlace(StructuralPerspective.GLOBAL_EXIT + "_" + threadSubModel.getInstructionNumber() + "_ref", StructuralPerspective.GLOBAL_EXIT);

			// Ajout de la guarde sur la transition
			ITransition eventCall = threadSubModel.getTransition("sys_" + id(call));

			// L'arc de pré-condition qui recherche une ressource
			IArc waitingId = threadSubModel.createArc(virtual, eventCall);
			waitingId.setValuation(new Valuation(IValuation.EVENT_RESOURCE_IDENTIFIER));

			// L'arc de pré-condition qui recherche le thread mort
			IArc consumeId = threadSubModel.createArc(globalExit, threadSubModel.getTransition("sys_" + id(call)));
			consumeId.setValuation(new Valuation(IValuation.EVENT_RESOURCE_IDENTIFIER));

			results.add(threadSubModel);
		}

		return results;
	}
}
