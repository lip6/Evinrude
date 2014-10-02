package fr.lip6.move.evinrude.builder.perspectives.syscall;

import fr.lip6.move.evinrude.builder.AbstractRule;
import fr.lip6.move.evinrude.builder.IRule;
import fr.lip6.move.evinrude.builder.IRuleInstruction;
import fr.lip6.move.evinrude.builder.ResultDep;
import fr.lip6.move.evinrude.commons.exceptions.EvinrudeException;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICall;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IInstruction;
import fr.lip6.move.evinrude.commons.model.petri.Guard;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ISubModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ITransition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Définition d'une règle de construction dédiée aux appels système<br>
 * Spécialisation de la règle Syscall0 pour traiter les appels relatifs à la gestion des processus.
 *
 * @see Syscall0
 *
 * @author Jean-Baptiste Voron
 */
public class Syscall1 extends AbstractRule implements IRule, IRuleInstruction {
	public static final String NAME = "Syscall1 : Processes Calls";

	private Set<String> dictionnary;

	/**
	 * Constructeur
	 */
	public Syscall1() {
		super(NAME);
		addDependency(Syscall0.NAME);

		this.dictionnary = new HashSet<String>();
		this.dictionnary.add("exit");
		this.dictionnary.add("fork");
		this.dictionnary.add("join");
		this.dictionnary.add("wait");
		this.dictionnary.add("waitpid");
	}

	/** {@inheritDoc}
	 */
	@Override
	public final List<ISubModel> process(ResultDep depenpencies, IInstruction instruction) throws EvinrudeException {
		List<ISubModel> results = new ArrayList<ISubModel>();

		// Si l'instruction n'est pas un callsite
		if (!instruction.isCallSite()) {
			return results;
		}

		// Si le callsite n'existe pas, c'est un appel à une fonction système
		if (!instruction.getBlock().getLibraryCalls().containsKey(instruction.getCfgLine())) {
			return results;
		}

		ICall call = instruction.getCallSite();
		if (this.dictionnary.contains(call.getFunctionName())) {
			for (ISubModel submodel : depenpencies.getAssociatedSubModels(instruction)) {
				ITransition event = submodel.getTransition("sys_" + id(call));
				event.setType(ITransition.KEY);
				event.addGuard(new Guard(call.getFunctionName()));
				results.add(submodel);
			}
		}
		return results;
	}
}
