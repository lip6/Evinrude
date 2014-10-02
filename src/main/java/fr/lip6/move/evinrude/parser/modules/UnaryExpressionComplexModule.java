package fr.lip6.move.evinrude.parser.modules;

import fr.lip6.move.evinrude.commons.model.cfg.Constant;
import fr.lip6.move.evinrude.commons.model.cfg.ExpressionComplex;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IAssignement;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IExpressionComplex;
import fr.lip6.move.evinrude.parser.AbstractCompositeModule;
import fr.lip6.move.evinrude.parser.IParser;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Module pour parser les expressions du type : !ExpressionSimple
 */
public class UnaryExpressionComplexModule extends AbstractCompositeModule {
	/** Le logger */
	private static Logger LOG = Logger.getLogger(UnaryExpressionComplexModule.class.getName());

	private static final String EXPRESSION_REGEX = "[!~-](.+);";
	private static final Pattern EXPRESSION_PATTERN = Pattern.compile(EXPRESSION_REGEX);

	/**
	 * Constructeur
	 * @param parser Objet parser permettant de récupérer certains paramètres courrant
	 */
	public UnaryExpressionComplexModule(IParser parser) {
		super(parser);
		addModule(new ExpressionSimpleModule(parser));
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean match(String line) {
		Matcher m = EXPRESSION_PATTERN.matcher(line);
		return m.matches() && internalMatch(m.group(1));
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean process(String line) {
		Matcher m = EXPRESSION_PATTERN.matcher(line);
		IAssignement assignement = getParser().getCurrentAssignement();
		if (m.matches()) {
			int lineNumber = getParser().getCurrentLine();
			LOG.finer("\t\t  Nouvelle expression complexe : " + m.group(1) + " == 0 [" + lineNumber + "]");
			IExpressionComplex expr = new ExpressionComplex("==");
			getParser().setCurrentExpr(expr);
			assignement.setRight(expr);
			internalProcess(m.group(1));
			expr.setRightOperand(new Constant("0"));
			getParser().setCurrentExpr(null);
			return true;
		}
		return false;
	}

}
