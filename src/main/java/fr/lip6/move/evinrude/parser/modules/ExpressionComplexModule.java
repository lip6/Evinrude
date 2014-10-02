package fr.lip6.move.evinrude.parser.modules;

import fr.lip6.move.evinrude.commons.model.cfg.ExpressionComplex;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IAssignement;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IExpressionComplex;
import fr.lip6.move.evinrude.parser.AbstractCompositeModule;
import fr.lip6.move.evinrude.parser.IParser;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Module pour parser les expressions complexe du type : ExpressionSimple OperateurBinaire ExpressionSimple;
 */
public class ExpressionComplexModule extends AbstractCompositeModule {
	/** Le logger */
	private static Logger LOG = Logger.getLogger(ExpressionComplexModule.class.getName());

	private static final String OPERATOR_REGEX = "(?:-|\\+|\\*|/|%|&|\\^|\\||<|>|(?:<<)|(?:>>)|(?:r>>)|(?:!=)|(?:==)|(?:<=)|(?:>=)|(?:&&)|(?:\\|\\|))";
	private static final String COMPLEX_REGEX = "^(\\S+)\\s(" + OPERATOR_REGEX + ")\\s(\\S+);$";
	private static final Pattern COMPLEX_PATTERN = Pattern.compile(COMPLEX_REGEX);

	/**
	 * Constructeur
	 * @param parser Objet parser permettant de récupérer certains paramètres courrant
	 */
	public ExpressionComplexModule(IParser parser) {
		super(parser);
		addModule(new ExpressionSimpleModule(parser));
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean match(String line) {
		Matcher m = COMPLEX_PATTERN.matcher(line);
		return m.matches() && internalMatch(m.group(1)) && internalMatch(m.group(3));
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean process(String line) {
		Matcher m = COMPLEX_PATTERN.matcher(line);
		IAssignement assignement = getParser().getCurrentAssignement();
		if (assignement != null && m.matches()) {
			int lineNumber = getParser().getCurrentLine();
			LOG.finer("\t\t  Nouvelle expression complexe : " + m.group(1) + " " + m.group(2) + " " + m.group(3) + " [" + lineNumber + "]");
			IExpressionComplex expr = new ExpressionComplex(m.group(2));
			getParser().setCurrentExpr(expr);
			assignement.setRight(expr);
			internalProcess(m.group(1));
			internalProcess(m.group(3));
			getParser().setCurrentExpr(null);
			return true;
		}
		return false;
	}

}
