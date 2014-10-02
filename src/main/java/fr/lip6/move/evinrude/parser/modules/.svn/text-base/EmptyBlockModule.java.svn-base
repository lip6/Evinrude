package fr.lip6.move.evinrude.parser.modules;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IBlock;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IFunction;
import fr.lip6.move.evinrude.parser.AbstractModule;
import fr.lip6.move.evinrude.parser.IParser;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Module pour parser les blocs vide qui n'ont pas de "starting line".
 */
public class EmptyBlockModule extends AbstractModule {
	/** Le logger */
	private static Logger LOG = Logger.getLogger(EmptyBlockModule.class.getName());

	private static final String BLOCK_REGEX = "^\\s+#\\s?BLOCK\\s?(\\d+)";
	private static final Pattern BLOCK_PATTERN = Pattern.compile(BLOCK_REGEX);

	/**
	 * @param parser IParser object
	 */
	public EmptyBlockModule(IParser parser) {
		super(parser);
		// TODO Auto-generated constructor stub
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean match(String line) {
		return getParser().getCurrentFunction() != null	&& BLOCK_PATTERN.matcher(line).matches();
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean process(String line) {
		Matcher m = BLOCK_PATTERN.matcher(line);
		IFunction func = getParser().getCurrentFunction();
		if (func != null && m.matches()) {
			LOG.fine("\tAjout du block : " + m.group(1));
			IBlock block = func.createBlock(m.group(1).trim(), -1);
			getParser().setCurrentBlock(block);
			return true;
		}
		return false;
	}
}
