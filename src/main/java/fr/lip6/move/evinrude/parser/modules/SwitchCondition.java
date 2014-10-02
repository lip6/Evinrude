package fr.lip6.move.evinrude.parser.modules;

import fr.lip6.move.evinrude.commons.model.cfg.Condition;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IBlock;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICondition;
import fr.lip6.move.evinrude.parser.AbstractModule;
import fr.lip6.move.evinrude.parser.IModule;
import fr.lip6.move.evinrude.parser.IParser;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Module pour parser les déclarations de switch.
 */
public class SwitchCondition extends AbstractModule {
	/** Le logger */
	private static Logger LOG = Logger.getLogger(SwitchCondition.class.getName());

	private static final String SWITCH_REGEX = "^switch\\s\\((.+)\\)$";
	private static final Pattern SWITCH_PATTERN = Pattern.compile(SWITCH_REGEX);

	private final IModule variable;

	/**
	 * Constructeur
	 * @param parser Objet parser permettant de récupérer certains paramètres courrant
	 */
	public SwitchCondition(IParser parser) {
		super(parser);
		this.variable = new VariableModule(parser);
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean match(String line) {
		return SWITCH_PATTERN.matcher(line).matches();
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean process(String line) {
		Matcher m = SWITCH_PATTERN.matcher(line);
		IBlock block = getParser().getCurrentBlock();
		if (block != null && m.matches()) {
			int cfgLine = getParser().getCfgLine();
			int lineNumber = getParser().getCurrentLine();
			ICondition condition = new Condition(cfgLine, lineNumber, "==", block);
			getParser().setCurrentCondition(condition);
			if (variable.process(m.group(1))) {
				LOG.finer("\t\t  Déclaration d'un switch sur " + m.group(1));
				return true;
			} else {
				getParser().setCurrentCondition(null);
			}
		}
		return false;
	}

}
