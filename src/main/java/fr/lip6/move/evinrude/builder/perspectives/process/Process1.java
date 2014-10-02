package fr.lip6.move.evinrude.builder.perspectives.process;

import fr.lip6.move.evinrude.builder.AbstractRule;
import fr.lip6.move.evinrude.builder.IRule;
import fr.lip6.move.evinrude.builder.IRuleInstruction;
import fr.lip6.move.evinrude.builder.ResultDep;
import fr.lip6.move.evinrude.builder.perspectives.syscall.Syscall1;
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
 * Définition d'une règle de construction dédiée à la getion des appels <code>fork</code><br>
 * @author Jean-Baptiste Voron
 */
public class Process1 extends AbstractRule implements IRule, IRuleInstruction {
	public static final String NAME = "Process1 : Process Creation";

	/**
	 * Constructeur
	 */
	public Process1() {
		super(NAME);
		addDependency(Syscall1.NAME);
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
		if (!call.getFunctionName().equalsIgnoreCase("fork")) { return results; }

		/* Parcours des sous-modèles potentiellement intéressants */
		for (ISubModel forkSubModel : depenpencies.getAssociatedSubModels(instruction)) {
			ITransition fork = forkSubModel.getTransition("sys_" + id(call));

			// Creation d'une place OUTPUT pour le fils
			IPlace sonPlace = forkSubModel.createPlace(id(call) + "_son", IPlace.OUTPUT);

			// Creation de l'arc entre la transition FORK et la place de sortie du fils
			IArc sonArc = forkSubModel.createArc(fork, sonPlace);
			// L'arc doit permettre la transmission de l'identité du fils
			sonArc.setValuation(new Valuation(IValuation.EVENT_RESOURCE_IDENTIFIER, null, IValuation.NO_OPERATOR));
			results.add(forkSubModel);
		}
		return results;
	}
}
