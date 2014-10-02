package fr.lip6.move.evinrude.builder.perspectives.process;

import fr.lip6.move.evinrude.builder.AbstractRule;
import fr.lip6.move.evinrude.builder.IRule;
import fr.lip6.move.evinrude.builder.IRuleInstruction;
import fr.lip6.move.evinrude.builder.ResultDep;
import fr.lip6.move.evinrude.builder.perspectives.struct.StructuralPerspective;
import fr.lip6.move.evinrude.builder.perspectives.syscall.Syscall1;
import fr.lip6.move.evinrude.commons.exceptions.EvinrudeException;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICall;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IInstruction;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ISubModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Définition d'une règle de construction dédiée à la getion des appels <code>exit</code><br>
 * @author Jean-Baptiste Voron
 */
public class Process0 extends AbstractRule implements IRule, IRuleInstruction {
	public static final String NAME = "Process0 : Exit Points";

	/**
	 * Constructeur
	 */
	public Process0() {
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

		/* Détails de l'appel et vérification qu'il s'agit bien d'une fin de processus */
		ICall call = instruction.getCallSite();
		if (!call.getFunctionName().equalsIgnoreCase("exit")) { return results; }

		/* Parcours des sous-modèles potentiellement intéressants */
		for (ISubModel exitSubModel : depenpencies.getAssociatedSubModels(instruction)) {
			// La place virtuelle (de fin de vie des processus et threads)
			IPlace virtual = exitSubModel.createPlace("sys_" + id(call) + "_target_ref", StructuralPerspective.GLOBAL_EXIT);
			// Suppression de la place post existante
			exitSubModel.removeNode(exitSubModel.getPlace(id(call) + "_post"));
			// Création de l'arc, depuis la transition exit jusqu'à la place GLOBAL_EXIT
			exitSubModel.createArc(exitSubModel.getTransition("sys_" + id(call)), virtual);
			results.add(exitSubModel);
		}
		return results;
	}
}
