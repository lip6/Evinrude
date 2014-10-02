package fr.lip6.move.evinrude.builder.perspectives.struct;

import fr.lip6.move.evinrude.builder.AbstractRule;
import fr.lip6.move.evinrude.builder.IRule;
import fr.lip6.move.evinrude.builder.IRuleInstruction;
import fr.lip6.move.evinrude.builder.ResultDep;
import fr.lip6.move.evinrude.commons.exceptions.EvinrudeException;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IBlock;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICall;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IFunction;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IInstruction;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IArc;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ISubModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ITransition;

import java.util.ArrayList;
import java.util.List;

/**
 * Définition d'une règle de construction structurelle (STRUCT4)<br>
 * Un lien entre 2 fonctions est définie comme un ensemble :
 * <ul>
 * 	<li>Une transition d'appel</li>
 * 	<li>Une place virtuelle pour l'appel</li>
 * 	<li>Une transition de retour</li>
 * 	<li>Une place virtuelle pour le retour</li>
 * </ul>
 *
 * @author Jean-Baptiste Voron
 */
public class Struct4 extends AbstractRule implements IRule, IRuleInstruction {
	public static final String NANE = "Struct4 : Links between functions";

	/**
	 * Constructeur
	 */
	public Struct4() {
		super(NANE);
		addDependency(Struct1.NAME);
	}

	/** {@inheritDoc}
	 */
	@Override
	public final List<ISubModel> process(ResultDep depenpencies, IInstruction instruction) throws EvinrudeException {
		List<ISubModel> results = new ArrayList<ISubModel>();

		if (instruction.isCallSite()) {
			ICall call = instruction.getCallSite();
			IBlock block = call.getBlock();
			IFunction called = block.getLocalFunctionCalls().get(call.getCfgLine());

			// L'appel est un appel sur une bibliothèque externe
			if (called == null) { return null; }

			for (ISubModel model : depenpencies.getAssociatedSubModels(block)) {
				IPlace blockPlace = model.getPlace(id(block));

				// Si la fonction est hors-CFG... Il faut parser le CFG ajout dans une liste d'attente)
				if (!block.getFunction().getCfg().getId().equals(called.getCfg().getId())) {
					model.getTopModel().enqueueNewCfg(called.getCfg());
				}

				if (blockPlace != null) {
					ISubModel callSubModel = blockPlace.createSubModel(call.getCfgLine());

					IPlace callPlace = callSubModel.createPlace(id(call) + "_call", IPlace.INPUT);
					ITransition callTransition = callSubModel.createTransition("call_" + id(call), ITransition.FUNCTIONCALL);

					IPlace pathPlace = callSubModel.createPlace(id(call) + "_path", IPlace.PATH);

					IPlace virtualFunctionEntryPlace = callSubModel.createPlace(id(call) + "_entry_ref", id(called) + "_ENTRY");
					IPlace virtualFunctionExitPlace = callSubModel.createPlace(id(call) + "_exit_ref", id(called) + "_EXIT");

					ITransition returnTransition = callSubModel.createTransition("return_" + id(call), ITransition.FUNCTIONRETURN);
					IPlace returnPlace = callSubModel.createPlace(id(call) + "_return", IPlace.OUTPUT);

					callSubModel.createArc(callPlace, callTransition, IArc.NORMAL);
					callSubModel.createArc(callTransition, virtualFunctionEntryPlace, IArc.NORMAL);
					callSubModel.createArc(virtualFunctionExitPlace, returnTransition, IArc.NORMAL);
					callSubModel.createArc(callTransition, pathPlace, IArc.NORMAL);
					callSubModel.createArc(pathPlace, returnTransition, IArc.NORMAL);
					callSubModel.createArc(returnTransition, returnPlace, IArc.NORMAL);

					results.add(callSubModel);
				}
			}
		}
		return results;
	}
}
