package fr.lip6.move.evinrude.parser.modules;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IBlock;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IFunction;
import fr.lip6.move.evinrude.parser.AbstractModule;
import fr.lip6.move.evinrude.parser.IParser;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Module pour parser les blocks.
 */
public class BlockModule extends AbstractModule {
	/** Le logger */
	private static Logger LOG = Logger.getLogger(BlockModule.class.getName());

	private static final String BLOCK_REGEX = "^\\s+#\\s?BLOCK\\s?(\\d+), starting at line (\\d+)";
	private static final Pattern BLOCK_PATTERN = Pattern.compile(BLOCK_REGEX);

	/**
	 * @param parser IParser object
	 */
	public BlockModule(IParser parser) {
		super(parser);
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
			LOG.fine("\tAjout du block : " + m.group(1) + " [Ligne : " + m.group(2) + "]");
			int startingLine = -1;
			try {
				startingLine = Integer.valueOf(m.group(2));
			} catch (NumberFormatException e) {
				LOG.warning(e.getMessage());
			}
			IBlock block = func.createBlock(m.group(1).trim(), startingLine);
			getParser().setCurrentBlock(block);
			return true;
		}
		return false;
	}
}
