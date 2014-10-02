package fr.lip6.move.evinrude.parser.modules;

import fr.lip6.move.evinrude.commons.model.cfg.Call;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IAssignement;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IBlock;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICall;
import fr.lip6.move.evinrude.parser.AbstractModule;
import fr.lip6.move.evinrude.parser.IParser;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Module qui parse les appels de fonctions.
 */
public class CallModule extends AbstractModule {
	/** Le logger */
	private static Logger LOG = Logger.getLogger(CallModule.class.getName());

	private static final String CALL_REGEX = "^([a-zA-Z_][0-9a-zA-Z_]*)(?:D\\.\\d+)?\\s\\((.*)\\)(?: [return slot optimization])?;";
	private static final Pattern CALL_PATTERN = Pattern.compile(CALL_REGEX);

	private static final String PARAM_REGEX = "(?:&?\"(?:(?:\\\")|[^\"])*\"(?:\\[[^\\]]+\\])*)|(?:[^,\"]+)";
	private static final String PARAMS_REGEX = "\\s*(" + PARAM_REGEX + ")\\s*";
	private static final Pattern PARAMS_PATTERN = Pattern.compile(PARAMS_REGEX);

	private final ExpressionSimpleModule module;

	/**
	 * Constructeur
	 * @param parser Objet parser permettant de récupérer certains paramètres courrant
	 */
	public CallModule(IParser parser) {
		super(parser);
		module = new ExpressionSimpleModule(parser);
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean match(String line) {
		Matcher m = CALL_PATTERN.matcher(line);
		if (getParser().getCurrentBlock() != null && m.matches()) {
			Matcher m2 = PARAMS_PATTERN.matcher(m.group(2));
			while (m2.find()) {
				if (!module.match(m2.group(1))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * @param cfgLine Le numero de ligne dans le CFG
	 * @param lineNumber Le numero de ligne utilisé lors de l'appel
	 * @param name Nom de la fonction
	 * @param args Arguments de la fonction
	 * @param block Bloc contenant cet appel
	 * @return un objet ICall
	 */
	private ICall createCall(int cfgLine, int lineNumber, String name, String args, IBlock block) {
		LOG.finer("\t\t    Appel de fonction : " + name + " (" + args + ") [" + lineNumber + "]");
		ICall call = new Call(cfgLine, lineNumber, name, block);
		getParser().setCurrentCall(call);
		Matcher m = PARAMS_PATTERN.matcher(args);
		while (m.find()) {
			module.process(m.group(1));
		}
		getParser().setCurrentCall(null);
		return call;
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean process(String line) {
		Matcher m = CALL_PATTERN.matcher(line);
		if (m.matches()) {
			int lineNumber = getParser().getCurrentLine();
			int cfgLine = getParser().getCfgLine();
			IBlock block = getParser().getCurrentBlock();
			IAssignement assign = getParser().getCurrentAssignement();

			if (block != null) {
				if (assign != null) {
					if (assign.getRight() == null) {
						assign.setRight(createCall(cfgLine, lineNumber, m.group(1), m.group(2), block));
						return true;
					}
				} else {
					block.addCall(createCall(cfgLine, lineNumber, m.group(1), m.group(2), block));
					return true;
				}
			}
		}
		return false;
	}

}
