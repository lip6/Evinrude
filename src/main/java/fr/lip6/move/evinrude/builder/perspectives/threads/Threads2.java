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
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ISubModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Définition d'une règle de construction dédiée à la gestion des fins de threads <code>pthread_exit</code><br>
 * @author Jean-Baptiste Voron
 */
public class Threads2 extends AbstractRule implements IRule, IRuleInstruction {
	public static final String NAME = "Threads2: Thread Ending";

	/**
	 * Constructeur
	 */
	public Threads2() {
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
		if (!call.getFunctionName().equalsIgnoreCase("pthread_exit")) { return results; }

		/* Parcours des sous-modèles potentiellement intéressants */
		for (ISubModel threadSubModel : depenpencies.getAssociatedSubModels(instruction)) {
			// La place virtuelle (référence de la sortie générale)
			IPlace virtualExit = threadSubModel.createPlace("thread_" + id(call) + "_target_ref", StructuralPerspective.GLOBAL_EXIT);
			// Suppression de la place post existante
			threadSubModel.removeNode(threadSubModel.getPlace(id(call) + "_post"));
			// Création de l'arc menant vers la place de sortie générale
			threadSubModel.createArc(threadSubModel.getTransition("sys_" + id(call)), virtualExit);
			results.add(threadSubModel);
		}
		return results;
	}
}
