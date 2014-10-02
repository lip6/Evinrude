package fr.lip6.move.evinrude.builder.perspectives.struct;

import fr.lip6.move.evinrude.builder.AbstractRule;
import fr.lip6.move.evinrude.builder.IRule;
import fr.lip6.move.evinrude.builder.IRuleExecutable;
import fr.lip6.move.evinrude.builder.ResultDep;
import fr.lip6.move.evinrude.commons.exceptions.EvinrudeException;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IExecutable;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ISubModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Définition d'une règle de construction structurelle (STRUCT0)<br>
 * @author Jean-Baptiste Voron
 */
public class Struct0 extends AbstractRule implements IRule, IRuleExecutable {
	public static final String NAME = "Struct0 : Model Configuration";

	/**
	 * Constructeur
	 */
	public Struct0() {
		super(NAME);
	}

	/** {@inheritDoc}
	 */
	@Override
	public final List<ISubModel> process(ResultDep dependencies, IExecutable executable) throws EvinrudeException {
		List<ISubModel> results = new ArrayList<ISubModel>();

		/* Recherche de la place GLOBAL_EXIT */
		INode globalExit = executable.getModel().getNode(StructuralPerspective.GLOBAL_EXIT);
		if (globalExit == null) {
			// Création de la place si nécessaire
			globalExit = executable.getModel().createPlace(StructuralPerspective.GLOBAL_EXIT, IPlace.SPECIAL);
		}
		results.add(executable.getModel());
		return results;
	}
}
