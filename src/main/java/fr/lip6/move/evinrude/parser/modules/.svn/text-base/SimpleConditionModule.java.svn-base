package fr.lip6.move.evinrude.parser.modules;

import fr.lip6.move.evinrude.commons.model.cfg.Condition;
import fr.lip6.move.evinrude.commons.model.cfg.Constant;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IBlock;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICondition;
import fr.lip6.move.evinrude.parser.AbstractCompositeModule;
import fr.lip6.move.evinrude.parser.IParser;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Module pour parser les conditionnements pour des variables boolean sans operateurs.
 */
public class SimpleConditionModule extends AbstractCompositeModule {
	/** Le logger */
	private static Logger LOG = Logger.getLogger(SimpleConditionModule.class.getName());

	private static final String CONDITION_REGEX = "^if \\((\\S+)\\).*";
	private static final Pattern CONDITION_PATTERN = Pattern.compile(CONDITION_REGEX);

	/**
	 * Constructeur
	 * @param parser Objet parser permettant de récupérer certains paramètres courrant
	 */
	public SimpleConditionModule(IParser parser) {
		super(parser);
		addModule(new VariableModule(parser));
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean match(String line) {
		Matcher m = CONDITION_PATTERN.matcher(line);
		return getParser().getCurrentBlock() != null && m.matches() && internalMatch(m.group(1));
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
			LOG.finer("\t\t  Nouvelle condition : 0 != " + m.group(1) + " [" + lineNumber + "]");
			ICondition condition = new Condition(cfgLine, lineNumber, "!=", block);
			condition.setLeftMember(new Constant("0"));
			block.addConditionnal(condition);
			getParser().setCurrentCondition(condition);
			internalProcess(m.group(1));
			return true;
		}
		return false;
	}

}
