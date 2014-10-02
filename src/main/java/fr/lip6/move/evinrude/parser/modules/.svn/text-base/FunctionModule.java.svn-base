package fr.lip6.move.evinrude.parser.modules;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IFunction;
import fr.lip6.move.evinrude.parser.AbstractModule;
import fr.lip6.move.evinrude.parser.IParser;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Module chargé de parser les déclarations de fonctions.
 */
public class FunctionModule extends AbstractModule {
	/** Le logger */
	private static Logger LOG = Logger.getLogger(FunctionModule.class.getName());

	private static final String FUNCTION_REGEX = "^(\\w.+)\\s\\((.*)\\)";
	private static final String PARAM_SPLIT_REGEX = ",\\s?";
	private static final Pattern FUNCTION_PATTERN = Pattern.compile(FUNCTION_REGEX);

	private int functionCpt = 0;

	/**
	 * @param parser IParser object
	 */
	public FunctionModule(IParser parser) {
		super(parser);
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean match(String line) {
		return FUNCTION_PATTERN.matcher(line).matches();
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean process(String line) {
		Matcher m = FUNCTION_PATTERN.matcher(line);
		if (m.matches()) {
			LOG.fine("Ajout de la fonction : " + m.group(1));
			IFunction function = getParser().getCfg().createFunction(functionCpt++, m.group(1));

			if (m.group(2).length() > 0) {
				for (String param : m.group(2).split(PARAM_SPLIT_REGEX)) {
					function.addParameters(param);
				}
			}

			getParser().setCurrentFunction(function);
			getParser().setCurrentBlock(null);
			return true;
		}
		return false;
	}

}
