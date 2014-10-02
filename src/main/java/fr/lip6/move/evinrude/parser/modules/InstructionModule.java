package fr.lip6.move.evinrude.parser.modules;

import fr.lip6.move.evinrude.parser.AbstractCompositeModule;
import fr.lip6.move.evinrude.parser.IParser;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Module pour parser les instructions. Une instruction est soit une affectation, soit un appel de fonction ou soit une
 * condition.
 */
public class InstructionModule extends AbstractCompositeModule {
	/** Le logger */
	private static Logger LOG = Logger.getLogger(InstructionModule.class.getName());

	private static final String LINE_REGEX = "\\[[^:]+\\s:\\s(\\d+)\\]\\s";
	private static final String INSTRUCTION_REGEX = "^\\s+" + LINE_REGEX + "(.+)";
	private static final Pattern INSTRUCTION_PATTERN = Pattern.compile(INSTRUCTION_REGEX);

	/**
	 * Constructeur
	 * @param parser Objet parser permettant de récupérer certains paramètres courrant
	 */
	public InstructionModule(IParser parser) {
		super(parser);
		addModule(new CallModule(parser));
		addModule(new ConditionModule(parser));
		addModule(new SimpleConditionModule(parser));
		addModule(new SwitchCondition(parser));
		addModule(new CaseModule(parser));
		addModule(new DefaultCaseModule(parser));
		addModule(new AssignementModule(parser));
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean match(String line) {
		Matcher m = INSTRUCTION_PATTERN.matcher(line);
		return m.matches() && internalMatch(m.group(2).replaceAll(LINE_REGEX, ""));
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean process(String line) {
		Matcher m = INSTRUCTION_PATTERN.matcher(line);
		if (m.matches()) {
			String instruction = m.group(2).replaceAll(LINE_REGEX, "");
			if (internalMatch(instruction)) {
				int lineNumber = -1;
				try {
					lineNumber = Integer.valueOf(m.group(1));
				} catch (NumberFormatException e) {
					LOG.warning("Impossible de lire le numero de la ligne [" + e.getMessage());
				}
				getParser().setCurrentLine(lineNumber);
				internalProcess(instruction);
				return true;
			}
		}
		return false;
	}

}
