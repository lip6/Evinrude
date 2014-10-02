package fr.lip6.move.evinrude.parser.modules;

import fr.lip6.move.evinrude.parser.AbstractCompositeModule;
import fr.lip6.move.evinrude.parser.IParser;

/**
 * Module pour parser les expressions simples.
 */
public class ExpressionSimpleModule extends AbstractCompositeModule {

	/**
	 * Constructeur
	 * @param parser Objet parser permettant de récupérer certains paramètres courrant
	 */
	public ExpressionSimpleModule(IParser parser) {
		super(parser);
		addModule(new VariableModule(parser));
		addModule(new ConstantModule(parser));
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean match(String line) {
		return internalMatch(line);
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean process(String line) {
		return internalProcess(line);
	}

}
