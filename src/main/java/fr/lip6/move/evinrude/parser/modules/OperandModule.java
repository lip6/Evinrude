package fr.lip6.move.evinrude.parser.modules;

import fr.lip6.move.evinrude.parser.AbstractCompositeModule;
import fr.lip6.move.evinrude.parser.IParser;

/**
 * Module pour parser les opérandes.
 */
public class OperandModule extends AbstractCompositeModule {

	/**
	 * Constructeur
	 * @param parser Objet parser permettant de récupérer certains paramètres courrant
	 */
	public OperandModule(IParser parser) {
		super(parser);
		addModule(new CallModule(parser));
		addModule(new ExpressionComplexModule(parser));
		addModule(new UnaryExpressionComplexModule(parser));
		addModule(new ExpressionSimpleModule(parser));
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
