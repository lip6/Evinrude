package fr.lip6.move.evinrude.parser.modules;

import fr.lip6.move.evinrude.parser.AbstractCompositeModule;
import fr.lip6.move.evinrude.parser.IParser;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Module pour parser les déclarations de variable en début de fonction. Attention l'initialisation des variables
 * statiques qui est faite dans cette partie du CFG est ignoré.<br>
 * <br>
 * Certaine déclaration ne sont pas parsé :<ul>
 * <li><code>voidD.38 (*&lt;T7a7&gt;) (intD.0) D.5792;</code></li>
 * <li><code>const unsigned charD.10 * {ref-all} D.7273;</code></li>
 * </ul>
 */
public class VariableDeclarationModule extends AbstractCompositeModule {
	/** Le logger */
	private static Logger LOG = Logger.getLogger(VariableDeclarationModule.class.getName());

	private static final String MEMBER_REGEX = "(?:[a-zA-Z_*&][0-9a-zA-Z_.]*)?D\\.\\d+(?:\\[[^\\]]+\\])*";
	private static final String DECLARATION_REGEX = "\\s{1,2}([\\w\\}][\\w\\. \\*\\[\\]]*)\\s(" + MEMBER_REGEX + ")(?:\\s=\\s.*)?;";
	private static final Pattern DECLARATION_PATTERN = Pattern.compile(DECLARATION_REGEX);

	/**
	 * Constructeur
	 * @param parser Objet parser permettant de récupérer certains paramètres courrant
	 */
	public VariableDeclarationModule(IParser parser) {
		super(parser);
		addModule(new VariableModule(parser));
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean match(String line) {
		Matcher m = DECLARATION_PATTERN.matcher(line);
		return getParser().getCurrentFunction() != null && m.matches() && internalMatch(m.group(2));
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean process(String line) {
		Matcher m = DECLARATION_PATTERN.matcher(line);
		if (getParser().getCurrentFunction() != null && m.matches() && internalMatch(m.group(2))) {
			LOG.finer("\t\t  Déclaration de variable du type : " + m.group(1));
			return internalProcess(m.group(2));
		}
		return false;
	}

}
