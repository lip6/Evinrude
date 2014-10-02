package fr.lip6.move.evinrude.parser.modules;

import fr.lip6.move.evinrude.commons.model.cfg.Assignement;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IAssignement;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IBlock;
import fr.lip6.move.evinrude.parser.AbstractModule;
import fr.lip6.move.evinrude.parser.IModule;
import fr.lip6.move.evinrude.parser.IParser;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Module pour parser les affectations.
 */
public class AssignementModule extends AbstractModule {
	/** Le logger */
	private static Logger LOG = Logger.getLogger(AssignementModule.class.getName());

	private static final String ASSIGN_REGEX = "(\\S+)\\s=\\s(.*)";
	private static final Pattern ASSIGN_PATTERN = Pattern.compile(ASSIGN_REGEX);

	private final IModule left;
	private final IModule right;

	/**
	 * Constructeur
	 * @param parser Objet parser permettant de récupérer certains paramètres courrant
	 */
	public AssignementModule(IParser parser) {
		super(parser);
		left = new VariableModule(parser);
		right = new OperandModule(parser);
	}

	/**
	 * @param line ligne à parser
	 * @return String sans cast
	 */
	private String removeCast(String line) {
		if (!line.startsWith("(")) {
			return line;
		}
		int count = 1;
		int index = 1;
		while (count > 0) {
			if (index >= line.length()) {
				LOG.warning("Il manque des parenthèses : " + line);
				return line;
			}
			char c = line.charAt(index);
			if (c == '(') {
				count++;
			} else if (c == ')') {
				count--;
			}
			index++;
		}
		return line.substring(index + 1);
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean match(String line) {
		Matcher m = ASSIGN_PATTERN.matcher(line);
		return getParser().getCurrentBlock() != null && m.matches() && left.match(m.group(1)) && right.match(removeCast(m.group(2)));
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean process(String line) {
		Matcher m = ASSIGN_PATTERN.matcher(line);
		IBlock block = getParser().getCurrentBlock();
		if (block != null && m.matches() && left.match(m.group(1)) && right.match(removeCast(m.group(2)))) {
			int cfgLine = getParser().getCfgLine();
			int lineNumber = getParser().getCurrentLine();
			LOG.finer("\t\t  Ajout d'une affectation : " + m.group(1) + " = " + removeCast(m.group(2)) + " [" + lineNumber + "]");
			IAssignement assign = new Assignement(cfgLine, lineNumber, block);
			getParser().setCurrentAssignement(assign);
			left.process(m.group(1));
			right.process(removeCast(m.group(2)));
			getParser().setCurrentAssignement(null);
			block.addAssignement(assign);
			return true;
		}
		return false;
	}

}
