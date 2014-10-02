package fr.lip6.move.evinrude.commons.model.cfg;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IBlock;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICall;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IExpressionSimple;

import java.util.ArrayList;
import java.util.List;

/**
 * Description d'un appel de fonction
 *
 * @author Jean-Baptiste Voron
 *
 */
public class Call extends Instruction implements ICall {
	/** Le nom de la fonction appellée */
	private String name;
	/** La liste des paramètres utilisés lors de l'appel */
	private List<IExpressionSimple> parameters;

	/**
	 * Constructeur d'un appel de fonction
	 * @param cfgLine Le numero de ligne dans le cfg
	 * @param lineNumber Le numero de ligne utilisé lors de l'appel
	 * @param name Le nom de l'appel
	 * @param block Block contenant cette instruction
	 */
	public Call(int cfgLine, int lineNumber, String name, IBlock block) {
		super(cfgLine, lineNumber, block);
		this.name = name;
		this.parameters = new ArrayList<IExpressionSimple>();
	}

	/**
	 * {@inheritDoc}
	 */
	public final String getFunctionName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	public final List<IExpressionSimple> getParameters() {
		return parameters;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		StringBuffer pretty = new StringBuffer();
		pretty.append("FunctionCall: [").append(this.name).append("]:::");
		for (IExpressionSimple param : this.parameters) {
			pretty.append("[").append(param).append("]");
		}
		return pretty.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	public final StringBuffer toXML(int level) {
		int i = 0;
		StringBuffer toReturn = new StringBuffer();
		StringBuffer indentation = new StringBuffer();

		// Indentation
		for (i = 0; i < level; i++) { indentation.append("\t"); }

		toReturn.append(indentation).append("<call line=\"").append(this.getCfgLine()).append("\" name=\"").append(this.name).append("\">\n");

		// Description des parametres de la fonction
		toReturn.append(indentation).append("\t<parameters>\n");
		for (IExpressionSimple var : this.parameters) {
			toReturn.append(indentation).append("\t\t<parameter>").append(var).append("</parameter>\n");
		}
		toReturn.append(indentation).append("\t</parameters>\n");
		toReturn.append(indentation).append("</call>\n");

		return toReturn;
	}
}
