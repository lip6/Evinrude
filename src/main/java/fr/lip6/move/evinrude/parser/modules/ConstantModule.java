package fr.lip6.move.evinrude.parser.modules;

import fr.lip6.move.evinrude.commons.model.cfg.Constant;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IAssignement;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICall;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICondition;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IExpressionComplex;
import fr.lip6.move.evinrude.parser.AbstractModule;
import fr.lip6.move.evinrude.parser.IParser;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Module pour parser les constantes.
 */
public class ConstantModule extends AbstractModule {
	/** Le logger */
	private static Logger LOG = Logger.getLogger(ConstantModule.class.getName());

	private static final String NUM_CONSTANT_REGEX = "^(-?\\+?\\d.*);?$";
	private static final Pattern NUM_CONSTANT_PATTERN = Pattern.compile(NUM_CONSTANT_REGEX);
	private static final String CHAR_CONSTANT_REGEX = "^&?\"(.*)\"(?:\\[[^\\]]*\\])?;?";
	private static final Pattern CHAR_CONSTANT_PATTERN = Pattern.compile(CHAR_CONSTANT_REGEX);
	private static final String TAB_CONSTANT_REGEX = "^(\\{.*\\});?$";
	private static final Pattern TAB_CONSTANT_PATTERN = Pattern.compile(TAB_CONSTANT_REGEX);

	/**
	 * Constructeur
	 * @param parser Objet parser permettant de récupérer certains paramètres courrant
	 */
	public ConstantModule(IParser parser) {
		super(parser);
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean match(String line) {
		return NUM_CONSTANT_PATTERN.matcher(line).matches() || CHAR_CONSTANT_PATTERN.matcher(line).matches() || TAB_CONSTANT_PATTERN.matcher(line).matches();
	}

	/**
	 * @param m Matcher à utiliser
	 * @param line ligne à parser
	 * @return <code>true</code> si un traitement à été fait avec ce Matcher <code>false</code> sinon.
	 */
	private boolean internalProcess(Matcher m, String line) {
		if (m.matches()) {
			IAssignement assignement = getParser().getCurrentAssignement();
			ICall call = getParser().getCurrentCall();
			ICondition condition = getParser().getCurrentCondition();
			IExpressionComplex expr = getParser().getCurrentExpr();
			if (expr != null) {
				if (expr.getLeftOperand() == null) {
					LOG.finest("\t\t    Ajout d'une constante : " + m.group(1));
					expr.setLeftOperand(new Constant(m.group(1)));
				} else if (expr.getRightOperand() == null) {
					LOG.finest("\t\t    Ajout d'une constante : " + m.group(1));
					expr.setRightOperand(new Constant(m.group(1)));
				}
			} else if (call != null) {
				LOG.finest("\t\t    Ajout d'une constante : " + m.group(1));
				call.getParameters().add(new Constant(m.group(1)));
			} else if (assignement != null) {
				if (assignement.getRight() == null) {
					LOG.finest("\t\t    Ajout d'une constante : " + m.group(1));
					assignement.setRight(new Constant(m.group(1)));
					return true;
				}
			} else if (condition != null) {
				if (condition.getLeftMember() == null) {
					LOG.finest("\t\t    Ajout d'une constante : " + m.group(1));
					condition.setLeftMember(new Constant(m.group(1)));
					return true;
				} else if (condition.getRightMember() == null) {
					LOG.finest("\t\t    Ajout d'une constante : " + m.group(1));
					condition.setRightMember(new Constant(m.group(1)));
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean process(String line) {
		Matcher mNum = NUM_CONSTANT_PATTERN.matcher(line);
		Matcher mChar = CHAR_CONSTANT_PATTERN.matcher(line);
		Matcher mTab = TAB_CONSTANT_PATTERN.matcher(line);
		if (!internalProcess(mNum, line)) {
			if (!internalProcess(mChar, line)) {
				if (!internalProcess(mTab, line)) {
					return false;
				}
			}
		}
		return true;
	}

}
