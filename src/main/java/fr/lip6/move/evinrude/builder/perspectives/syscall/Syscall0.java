package fr.lip6.move.evinrude.builder.perspectives.syscall;

import fr.lip6.move.evinrude.builder.AbstractRule;
import fr.lip6.move.evinrude.builder.IRule;
import fr.lip6.move.evinrude.builder.IRuleInstruction;
import fr.lip6.move.evinrude.builder.ResultDep;
import fr.lip6.move.evinrude.builder.perspectives.struct.Struct1;
import fr.lip6.move.evinrude.commons.exceptions.EvinrudeException;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICall;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IInstruction;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ISubModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ITransition;

import java.util.ArrayList;
import java.util.List;

/**
 * Définition d'une règle de construction dédiée aux appels système<br>
 *
 * @author Jean-Baptiste Voron
 */
public class Syscall0 extends AbstractRule implements IRule, IRuleInstruction {
	public static final String NAME = "Syscall0 : Remarquable Elements";

	/**
	 * Constructeur
	 */
	public Syscall0() {
		super(NAME);
		addDependency(Struct1.NAME);
	}

	/** {@inheritDoc}
	 */
	@Override
	public final List<ISubModel> process(ResultDep depenpencies, IInstruction instruction) throws EvinrudeException {
		List<ISubModel> results = new ArrayList<ISubModel>();

		if (instruction.isCallSite()) {
			ICall call = instruction.getCallSite();

			// Si le callsite n'existe pas, c'est un appel à une fonction système
			if (instruction.getBlock().getLibraryCalls().containsKey(instruction.getCfgLine())) {

				for (ISubModel submodel : depenpencies.getAssociatedSubModels(instruction.getBlock())) {
					IPlace bloc = submodel.getPlace(id(instruction.getBlock()));
					ISubModel callSubModel = bloc.createSubModel(instruction.getCfgLine());
					IPlace entry = callSubModel.createPlace(id(call) + "_pre", IPlace.INPUT);
					IPlace exit = callSubModel.createPlace(id(call) + "_post", IPlace.OUTPUT);
					ITransition event = callSubModel.createTransition("sys_" + id(call), ITransition.STRUCTURAL);
					callSubModel.createArc(entry, event);
					callSubModel.createArc(event, exit);
					results.add(callSubModel);
				}
			}
		}
		return results;
	}
}
