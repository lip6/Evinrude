package fr.lip6.move.evinrude.builder.perspectives.threads;

import fr.lip6.move.evinrude.builder.AbstractRule;
import fr.lip6.move.evinrude.builder.IRule;
import fr.lip6.move.evinrude.builder.IRuleInstruction;
import fr.lip6.move.evinrude.builder.ResultDep;
import fr.lip6.move.evinrude.builder.perspectives.syscall.Syscall4;
import fr.lip6.move.evinrude.commons.exceptions.EvinrudeException;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICall;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IInstruction;
import fr.lip6.move.evinrude.commons.model.petri.Valuation;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IArc;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ISubModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IValuation;

import java.util.ArrayList;
import java.util.List;

/**
 * Définition d'une règle de construction dédiée à la gestion des fins de threads <code>pthread_exit</code><br>
 *
 * @author Jean-Baptiste Voron
 */
public class Threads3 extends AbstractRule implements IRule, IRuleInstruction {
	public static final String NAME = "Threads3: Thread Detach";

	/**
	 * Constructeur
	 */
	public Threads3() {
		super(NAME);
		addDependency(Syscall4.NAME);
	}

	/** {@inheritDoc}
	 */
	@Override
	public final List<ISubModel> process(ResultDep depenpencies, IInstruction instruction) throws EvinrudeException {
		List<ISubModel> results = new ArrayList<ISubModel>();

		/* Suis-je entrain de parcourir une instruction définissant un appel de fonction ? */
		if (!instruction.isCallSite()) { return results; }

		/* Détail de l'appel et vérification qu'il s'agit bien d'une création de thread */
		ICall call = instruction.getCallSite();
		if (!call.getFunctionName().equalsIgnoreCase("pthread_detach")) { return results; }

		/* Parcours des sous-modèles potentiellement intéressants */
		for (ISubModel threadSubModel : depenpencies.getAssociatedSubModels(instruction)) {
			// La place virtuelle des threads attachés
			IPlace attached = threadSubModel.createPlace(ThreadsPerspective.RESOURCE_ATTACHED_THREADS + "_" + threadSubModel.getInstructionNumber() + "_ref", ThreadsPerspective.RESOURCE_ATTACHED_THREADS);
			// L'arc et sa valuation depuis la place des threads attachés vers la transition DETACH
			IArc consumeArc = threadSubModel.createArc(attached, threadSubModel.getTransition("sys_" + id(call)));
			consumeArc.setValuation(new Valuation(IValuation.EVENT_RESOURCE_IDENTIFIER));
			results.add(threadSubModel);
		}
		return results;
	}
}
