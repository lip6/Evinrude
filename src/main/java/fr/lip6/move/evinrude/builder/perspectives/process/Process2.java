package fr.lip6.move.evinrude.builder.perspectives.process;

import fr.lip6.move.evinrude.builder.AbstractRule;
import fr.lip6.move.evinrude.builder.IRule;
import fr.lip6.move.evinrude.builder.IRuleInstruction;
import fr.lip6.move.evinrude.builder.ResultDep;
import fr.lip6.move.evinrude.builder.perspectives.struct.StructuralPerspective;
import fr.lip6.move.evinrude.builder.perspectives.syscall.Syscall1;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICall;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IInstruction;
import fr.lip6.move.evinrude.commons.model.petri.Valuation;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IArc;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ISubModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Définition d'une règle de construction dédiée à la getion des appels <code>wait</code><br>
 * @author Jean-Baptiste Voron
 */
public class Process2 extends AbstractRule implements IRule, IRuleInstruction {
	public static final String NAME = "Process2: Process Collection";

	/**
	 * Constructeur
	 */
	public Process2() {
		super(NAME);
		addDependency(Syscall1.NAME);
	}

	/** {@inheritDoc}
	 */
	@Override
	public final List<ISubModel> process(ResultDep depenpencies, IInstruction instruction) {
		List<ISubModel> results = new ArrayList<ISubModel>();

		/* Suis-je entrain de parcourir une instruction définissant un appel de fonction ? */
		if (!instruction.isCallSite()) { return results; }

		/* Détail de l'appel et vérification qu'il s'agit bien d'une création de thread */
		ICall call = instruction.getCallSite();
		if (!call.getFunctionName().equalsIgnoreCase("wait")) { return results; }

		/* Parcours des sous-modèles potentiellement intéressants */
		for (ISubModel waitSubModel : depenpencies.getAssociatedSubModels(instruction)) {
			// La place virtuelle (de fin de vie des processus et threads)
			IPlace virtualExit = waitSubModel.createPlace("sys_" + id(call) + "_target_ref", StructuralPerspective.GLOBAL_EXIT);
			// L'arc (avec sa valuation)
			IArc waitingId = waitSubModel.createArc(virtualExit, waitSubModel.getTransition("sys_" + id(call)));
			waitingId.setValuation(new Valuation());
			results.add(waitSubModel);
		}

		return results;
	}
}
