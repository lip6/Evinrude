package fr.lip6.move.evinrude.parser.modules;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IBlock;
import fr.lip6.move.evinrude.parser.AbstractModule;
import fr.lip6.move.evinrude.parser.IParser;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Module pour parser les prédecesseurs d'un block.
 */
public class PredecessorModule extends AbstractModule {
	/** Le logger */
	private static Logger LOG = Logger.getLogger(PredecessorModule.class.getName());

	private static final String PREDECESSOR_REGEX = "^\\s+#\\s?PRED:\\s*(.+)\\s*$";
	private static final String LINKS_SPLIT_REGEX = "\\s";
	private static final Pattern PREDECESSOR_PATTERN = Pattern.compile(PREDECESSOR_REGEX);

	/**
	 * Constructeur
	 * @param parser Objet parser permettant de récupérer certains paramètres courrant
	 */
	public PredecessorModule(IParser parser) {
		super(parser);
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean match(String line) {
		return getParser().getCurrentBlock() != null && PREDECESSOR_PATTERN.matcher(line).matches();
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean process(String line) {
		Matcher m = PREDECESSOR_PATTERN.matcher(line);
		IBlock block = getParser().getCurrentBlock();
		if (block != null && m.matches()) {
			for (String pred : m.group(1).split(LINKS_SPLIT_REGEX)) {
				if (!pred.trim().startsWith("(")) {
					LOG.finer("\t\tPredecesseur localise: " + pred);
					block.addPredecessor(pred.trim());
				}
			}
			return true;
		}
		return false;
	}

}
