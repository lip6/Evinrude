package fr.lip6.move.evinrude.builder.perspectives.struct;

import fr.lip6.move.evinrude.builder.AbstractRule;
import fr.lip6.move.evinrude.builder.IRule;
import fr.lip6.move.evinrude.builder.IRuleBlock;
import fr.lip6.move.evinrude.builder.ResultDep;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IBlock;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ISubModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Définition d'une règle de construction structurelle (STRUCT1)<br>
 * Un bloc de CFG correspond à une place nomée CFG_FUNCTION_BLOCK où :
 * <ul>
 * 	<li>CFG est l'identifiant du CFG du bloc</li>
 * 	<li>FUNCTION est l'identifiant de la fonction du bloc</li>
 * 	<li>BLOCK est l'identifiant du bloc</li>
 * </ul>
 *
 * @author Jean-Baptiste Voron
 */
public class Struct1 extends AbstractRule implements IRule, IRuleBlock {
	public static final String NAME = "Struct1 : CFG Blocks";

	/**
	 * Constructeur
	 */
	public Struct1() {
		super(NAME);
		addDependency(Struct2.NAME);
	}

	/** {@inheritDoc}
	 */
	@Override
	public final List<ISubModel> process(ResultDep depenpencies, IBlock block) {
		List<ISubModel> results = new ArrayList<ISubModel>();

		if (block.getId().equalsIgnoreCase("ENTRY") || block.getId().equalsIgnoreCase("EXIT")) {
			return results;
		}

		for (ISubModel model : depenpencies.getAssociatedSubModels(block.getFunction())) {
			model.createPlace(id(block), IPlace.NORMAL);
			results.add(model);
		}
		return results;
	}
}
