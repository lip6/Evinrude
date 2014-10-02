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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Définiction d'une règle de construction structurelle (STRUCT6)<br>
 * La gestion des appels récursifs se fait via des liens inhibiteurs entre les place de contrôle de retour
 *
 * @author Jean-Baptiste Voron
 */
public class Struct6 extends AbstractRule implements IRule, IRuleInstruction {
	public static final String NAME = "Struct6 : Handle enclosed calls";

	/**
	 * Constructeur
	 */
	public Struct6() {
		super(NAME);
		addDependency(Struct4.NANE);
	}

	/** {@inheritDoc}
	 */
	@Override
	public final List<ISubModel> process(ResultDep depenpencies, IInstruction instruction) throws EvinrudeException {
		List<ISubModel> results = new ArrayList<ISubModel>();

		for (ISubModel model : depenpencies.getAssociatedSubModels(instruction)) {
			if (instruction.isCallSite()) {
				ICall call = instruction.getCallSite();
				// La fonction qui est appelée
				IFunction called = call.getBlock().getLocalFunctionCalls().get(call.getCfgLine());

				if (called == null) { continue; }

				Map<IBlock, Map<Integer, IFunction>> internalCallBlocks = new HashMap<IBlock, Map<Integer, IFunction>>();
				// On récupère tous ses appels internes
				for (IBlock internalBlock : called.getBlocks()) {
					Map<Integer, IFunction> internalCalls = internalBlock.getLocalFunctionCalls();
					if (internalCalls.size() > 0) {
						internalCallBlocks.put(internalBlock, internalCalls);
					}
				}

				// Le préfixe de tous les éléments du sous-réseau
				String fix = id(call); // + "_" + calledFunction.getId() + "_" + lineCall;

				for (IBlock internalCallBlock : internalCallBlocks.keySet()) {
					Map<Integer, IFunction> internalCalledFunction = internalCallBlocks.get(internalCallBlock);
					for (Integer internalCallSiteLine : internalCalledFunction.keySet()) {
						String pathPlaceName = id(internalCallBlock) + "_" + internalCallSiteLine + "_" + internalCalledFunction.get(internalCallSiteLine).getName() + "_path";
						// Création de la place référence à la place PATH de la fonction appelée
						IPlace pathPlace = model.createPlace(fix + "_" + pathPlaceName + "_ref", pathPlaceName);
						// Transition return dans ce sous-modèle
						ITransition returnTransition = model.getTransition("return_" + id(call));
						// Creation des liens
						model.createArc(pathPlace, returnTransition, IArc.INHIBITOR);
					}
				}
			}
		}
		return results;
	}
}
