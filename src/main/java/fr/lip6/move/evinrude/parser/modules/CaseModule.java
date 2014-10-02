package fr.lip6.move.evinrude.parser.modules;

import fr.lip6.move.evinrude.commons.model.cfg.Condition;
import fr.lip6.move.evinrude.commons.model.cfg.Variable;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICondition;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IVariable;
import fr.lip6.move.evinrude.parser.AbstractModule;
import fr.lip6.move.evinrude.parser.IModule;
import fr.lip6.move.evinrude.parser.IParser;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Module pour parser les cases d'un switch/case.
 * Attention : Les 'case INT ... INT : goto ...' ne sont pas géré.
 */
public class CaseModule extends AbstractModule {
	/** Le logger */
	private static Logger LOG = Logger.getLogger(CaseModule.class.getName());

	private static final String CASE_REGEX = "^case (.+): goto.*;";
	private static final Pattern CASE_PATTERN = Pattern.compile(CASE_REGEX);

	private final IModule constant;

	/**
	 * Constructeur
	 * @param parser Objet parser permettant de récupérer certains paramètres courrant
	 */
	public CaseModule(IParser parser) {
		super(parser);
		this.constant = new ConstantModule(parser);
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean match(String line) {
		return getParser().getCurrentCondition() != null && CASE_PATTERN.matcher(line).matches();
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean process(String line) {
		Matcher m = CASE_PATTERN.matcher(line);
		ICondition condition = getParser().getCurrentCondition();
		if (condition != null && m.matches()) {
			if (constant.process(m.group(1)) && condition.getLeftMember() instanceof IVariable) {
				LOG.finer("\t\t    Case " + m.group(1));
				ICondition nextCaseCondition = new Condition(getParser().getCfgLine(), getParser().getCurrentLine(), condition.getComparator(), condition.getBlock());
				nextCaseCondition.setLeftMember(new Variable(condition.getLeftMember().getValue(), ((IVariable) condition.getLeftMember()).getRegister()));
				getParser().setCurrentCondition(nextCaseCondition);
				return true;
			}
		}
		return false;
	}

}
