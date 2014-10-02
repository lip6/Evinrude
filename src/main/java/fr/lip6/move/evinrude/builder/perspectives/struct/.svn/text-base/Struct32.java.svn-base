package fr.lip6.move.evinrude.builder.perspectives.struct;

import fr.lip6.move.evinrude.builder.AbstractRule;
import fr.lip6.move.evinrude.builder.IRule;
import fr.lip6.move.evinrude.builder.IRuleBlock;
import fr.lip6.move.evinrude.builder.ResultDep;
import fr.lip6.move.evinrude.commons.exceptions.EvinrudeException;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IBlock;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IArc;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ISubModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ITransition;

import java.util.ArrayList;
import java.util.List;

/**
 * Définition d'une règle de construction structurelle (STRUCT3.2)<br>
 * Un prédecesseur de bloc est représenté par une transition <b>depuis</b> une autre place du réseau<br>
 * La transition est nommée  struct_CFG_FUNCTION_BLOCK
 * <ul>
 * 	<li>CFG est l'identifiant du CFG du bloc</li>
 * 	<li>FUNCTION est l'identifiant de la fonction du bloc</li>
 * 	<li>BLOCK est l'identifiant du bloc</li>
 * </ul>
 *
 * @author Jean-Baptiste Voron
 */
public class Struct32 extends AbstractRule implements IRule, IRuleBlock {
	public static final String NAME = "Struct3.1 : Successor links";

	/**
	 * Constructeur
	 */
	public Struct32() {
		super(NAME);
		addDependency(Struct1.NAME);
	}

	/** {@inheritDoc}
	 */
	@Override
	public final List<ISubModel> process(ResultDep depenpencies, IBlock block) throws EvinrudeException {
		List<ISubModel> results = new ArrayList<ISubModel>();

		for (ISubModel model : depenpencies.getAssociatedSubModels(block)) {
			for (IBlock pred : block.getPredecessors()) {
				if (model.getTransition(id(pred) + "_to_" + id(block)) == null) {
					IPlace place = model.getPlace(id(block));
					ITransition struct = model.createTransition(id(pred) + "_to_" + id(block), ITransition.STRUCTURAL);
					IPlace predPlace = model.getPlace(id(pred));
					model.createArc(predPlace, struct, IArc.NORMAL);
					model.createArc(struct, place, IArc.NORMAL);
				}
			}
			results.add(model);
		}
		return results;
	}
}
