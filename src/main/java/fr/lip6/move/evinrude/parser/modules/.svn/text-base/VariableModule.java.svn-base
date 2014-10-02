package fr.lip6.move.evinrude.parser.modules;

import fr.lip6.move.evinrude.commons.model.cfg.Variable;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IAssignement;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICall;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICondition;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IExpressionComplex;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IFunction;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IVariable;
import fr.lip6.move.evinrude.parser.AbstractModule;
import fr.lip6.move.evinrude.parser.IParser;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Module pour parser les variables.
 */
public class VariableModule extends AbstractModule {
	/** Le logger */
	private static Logger LOG = Logger.getLogger(VariableModule.class.getName());

	private static final String REGISTER_REGEX = "D\\.\\d+(?:\\[[^\\]]+\\])*";
	private static final String MEMBER_REGEX = "(?:[a-zA-Z_][0-9a-zA-Z_.]*)?(?:" + REGISTER_REGEX + ")?";
	private static final String VARIABLE_REGEX = "^[*&]?(" + MEMBER_REGEX + "(?:(?:(?:->)|\\.)" + MEMBER_REGEX + ")*)(" + REGISTER_REGEX + ");?$";
	private static final Pattern VARIABLE_PATTERN = Pattern.compile(VARIABLE_REGEX);

	/**
	 * Constructeur
	 * @param parser Objet parser permettant de récupérer certains paramètres courrant
	 */
	public VariableModule(IParser parser) {
		super(parser);
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean match(String line) {
		return VARIABLE_PATTERN.matcher(line.replaceAll("\\.\\d+D", "D")).matches();
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean process(String line) {
		Matcher m = VARIABLE_PATTERN.matcher(line.replaceAll("\\.\\d+D", "D"));
		if (m.matches()) {
			IVariable var = new Variable(m.group(1).trim(), m.group(2).trim());
			ICall call = getParser().getCurrentCall();
			IAssignement assignement = getParser().getCurrentAssignement();
			ICondition condition = getParser().getCurrentCondition();
			IExpressionComplex expr = getParser().getCurrentExpr();
			IFunction function = getParser().getCurrentFunction();
			if (expr != null) {
				if (expr.getLeftOperand() == null) {
					LOG.finest("\t\t    Variable : " + var.getValue());
					expr.setLeftOperand(var);
					return true;
				} else if (expr.getRightOperand() == null) {
					LOG.finest("\t\t    Variable : " + var.getValue());
					expr.setRightOperand(var);
					return true;
				}
			} else if (call != null) {
				LOG.finest("\t\t      Variable : " + var.getValue());
				call.getParameters().add(var);
			} else if (assignement != null) {
				if (assignement.getLeft() == null) {
					LOG.finest("\t\t    Variable : " + var.getValue());
					assignement.setLeft(var);
					return true;
				} else if (assignement.getRight() == null) {
					LOG.finest("\t\t    Variable : " + var.getValue());
					assignement.setRight(var);
					return true;
				}
			} else if (condition != null) {
				if (condition.getLeftMember() == null) {
					LOG.finest("\t\t    Variable : " + var.getValue());
					condition.setLeftMember(var);
					return true;
				} else if (condition.getRightMember() == null) {
					LOG.finest("\t\t    Variable : " + var.getValue());
					condition.setRightMember(var);
					return true;
				}
			} else if (function != null) {
				LOG.finest("\t\t    Variable : " + var.getValue());
				function.addVariable(var.getValue());
				return true;
			}
		}
		return false;
	}

}
