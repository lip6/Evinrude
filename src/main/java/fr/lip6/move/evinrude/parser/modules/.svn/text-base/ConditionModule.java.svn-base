package fr.lip6.move.evinrude.parser.modules;

import fr.lip6.move.evinrude.commons.model.cfg.Condition;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IBlock;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICondition;
import fr.lip6.move.evinrude.parser.AbstractCompositeModule;
import fr.lip6.move.evinrude.parser.IParser;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Module pour parser les conditionnements : if (...) goto <1>; else goto <2>;
 */
public class ConditionModule extends AbstractCompositeModule {
	/** Le logger */
	private static Logger LOG = Logger.getLogger(ConditionModule.class.getName());

	private static final String OPERATOR_REGEX = "(?:(?:==)|(?:!=)|<|>|(?:>=)|(?:<=))";
	private static final String CONDITION_REGEX = "^if \\((\\S+) (" + OPERATOR_REGEX + ") (\\S+)\\).*";
	private static final Pattern CONDITION_PATTERN = Pattern.compile(CONDITION_REGEX);

	/**
	 * Constructeur
	 * @param parser Objet parser permettant de récupérer certains paramètres courrant
	 */
	public ConditionModule(IParser parser) {
		super(parser);
		addModule(new ExpressionSimpleModule(parser));
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean match(String line) {
		Matcher m = CONDITION_PATTERN.matcher(line);
		return getParser().getCurrentBlock() != null && m.matches() && internalMatch(m.group(1)) && internalMatch(m.group(3));
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean process(String line) {
		Matcher m = CONDITION_PATTERN.matcher(line);
		IBlock block = getParser().getCurrentBlock();
		if (block != null && m.matches()) {
			int cfgLine = getParser().getCfgLine();
			int lineNumber = getParser().getCurrentLine();
			LOG.finer("\t\t  Nouvelle condition : " + m.group(1) + " " + m.group(2) + " " + m.group(3) + " [" + lineNumber + "]");
			ICondition condition = new Condition(cfgLine, lineNumber, m.group(3), block);
			block.addConditionnal(condition);
			getParser().setCurrentCondition(condition);
			internalProcess(m.group(1));
			internalProcess(m.group(3));
			getParser().setCurrentCondition(null);
			return true;
		}
		return false;
	}

}
