package fr.lip6.move.evinrude.parser.modules;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICondition;
import fr.lip6.move.evinrude.parser.AbstractModule;
import fr.lip6.move.evinrude.parser.IParser;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Module pour parser le <code>default</code> d'un <code>switch</code>.
 */
public class DefaultCaseModule extends AbstractModule {
	/** Le logger */
	private static Logger LOG = Logger.getLogger(DefaultCaseModule.class.getName());

	private static final String DEFAULT_REGEX = "^default : goto.*;";
	private static final Pattern DEFAULT_PATTERN = Pattern.compile(DEFAULT_REGEX);

	/**
	 * Constructor
	 * @param parser parser
	 */
	public DefaultCaseModule(IParser parser) {
		super(parser);
	}

	/** {@inheritDoc}
	 */
	@Override
	public final boolean match(String line) {
		return getParser().getCurrentCondition() != null && DEFAULT_PATTERN.matcher(line).matches();
	}

	/** {@inheritDoc}
	 */
	@Override
	public final boolean process(String line) {
		Matcher m = DEFAULT_PATTERN.matcher(line);
		ICondition condition = getParser().getCurrentCondition();
		if (condition != null && m.matches()) {
			LOG.finer("\t\t  Fin du switch");
			getParser().setCurrentCondition(null);
			return true;
		}
		return false;
	}

}
