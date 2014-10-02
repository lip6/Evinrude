package fr.lip6.move.evinrude.parser.modules;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IBlock;
import fr.lip6.move.evinrude.parser.AbstractModule;
import fr.lip6.move.evinrude.parser.IParser;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Module pour parser les successeurs d'un block.
 */
public class SuccessorModule extends AbstractModule {
	/** Le logger */
	private static Logger LOG = Logger.getLogger(SuccessorModule.class.getName());

	private static final String SUCCESSOR_REGEX = "^\\s+#\\s?SUCC:\\s*(.*)\\s*$";
	private static final String LINKS_SPLIT_REGEX = "\\s";
	private static final Pattern SUCCESSOR_PATTERN = Pattern.compile(SUCCESSOR_REGEX);

	/**
	 * Constructeur
	 * @param parser Objet parser permettant de récupérer certains paramètres courrant
	 */
	public SuccessorModule(IParser parser) {
		super(parser);
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean match(String line) {
		return getParser().getCurrentBlock() != null && SUCCESSOR_PATTERN.matcher(line).matches();
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean process(String line) {
		Matcher m = SUCCESSOR_PATTERN.matcher(line);
		IBlock block = getParser().getCurrentBlock();
		if (block != null && m.matches()) {
			for (String succ : m.group(1).split(LINKS_SPLIT_REGEX)) {
				if (!succ.isEmpty() && !succ.trim().startsWith("(")) {
					LOG.finer("\t\tSuccesseur localise: " + succ);
					block.addSuccessor(succ.trim());
				}
			}
			// Fin du bloc
			getParser().setCurrentBlock(null);

			// Fermeture du switch si il y en avait un dans le bloc
			getParser().setCurrentCondition(null);

			return true;
		}
		return false;
	}

}
