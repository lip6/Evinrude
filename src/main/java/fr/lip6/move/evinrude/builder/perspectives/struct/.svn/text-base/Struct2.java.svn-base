package fr.lip6.move.evinrude.builder.perspectives.struct;

import fr.lip6.move.evinrude.builder.AbstractRule;
import fr.lip6.move.evinrude.builder.IRule;
import fr.lip6.move.evinrude.builder.IRuleFunction;
import fr.lip6.move.evinrude.builder.ResultDep;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IFunction;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ISubModel;

import java.util.Collections;
import java.util.List;

/**
 * Définition d'une règle de construction structurelle (STRUCT2)<br>
 * Une fonction de CFG correspond à une place nomée CFG_FUNCTION_BLOCK_entry et une place CFG_FUNCTION_BLOCK_exit où :
 * <ul>
 * 	<li>CFG est l'identifiant du CFG du bloc</li>
 * 	<li>FUNCTION est l'identifiant de la fonction du bloc</li>
 * 	<li>BLOCK est l'identifiant du bloc</li>
 * </ul>
 *
 * @author Jean-Baptiste Voron
 */
public class Struct2 extends AbstractRule implements IRule, IRuleFunction {
	public static final String NAME = "Struct2 : CFG Functions";

	/**
	 * Constructeur
	 */
	public Struct2() {
		super(NAME);
	}

	/** {@inheritDoc}
	 */
	@Override
	public final List<ISubModel> process(ResultDep depenpencies, IFunction function) {
		IModel model = depenpencies.getExecutable().getModel();
		model.createPlace(id(function) + "_ENTRY", IPlace.FUNCTIONENTRY);
		model.createPlace(id(function) + "_EXIT", IPlace.FUNCTIONEXIT);
		return Collections.singletonList((ISubModel) model);
	}
}
