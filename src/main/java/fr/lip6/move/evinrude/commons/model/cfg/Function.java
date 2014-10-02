package fr.lip6.move.evinrude.commons.model.cfg;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IBlock;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICfg;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Définition d'une <b>fonction</b><br>
 * Une fonction se compose d'un ensemble de variables et d'un ensemble de blocs.
 * @author Jean-Baptiste Voron
 */
public class Function implements IFunction {
	/** Le CFG */
	private ICfg cfg;

	/** Le nom de la fonction */
	private String name;

	/** L'identifiant de la fonction */
	private Integer id;

	/** La liste des blocs de la fonction */
	private Map<String, IBlock> blocks;

	/** La liste des variables de la fonction */
	private List<String> variables;

	/** La liste des paramètres de la fonction */
	private List<String> parameters;

	/**
	 * Constructeur
	 * @param id L'identifiant associé à la fonction
	 * @param name Le nom associé à la fonction
	 * @param cfg Le CFG associé à la fonction
	 */
	public Function(Integer id, String name, ICfg cfg) {
		this.id = id;
		this.name = name;
		this.blocks = new HashMap<String, IBlock>();
		this.cfg = cfg;
		this.variables = new ArrayList<String>();
		this.parameters = new ArrayList<String>();
	}

	/**
	 * {@inheritDoc}
	 */
	public final IBlock createBlock(String id) {
		return createBlock(id, -1);
	}

	/**
	 * {@inheritDoc}
	 */
	public final IBlock createBlock(String id, int startingLine) {
		IBlock block;
		// L'existence du bloc peut avoir été découverte auparavant à cause d'un PRED ou SUCC
		if (this.blocks.containsKey(id)) {
			block = this.blocks.get(id);
			block.setStartingLine(startingLine);
		} else {
			block = new Block(this, id, startingLine);
			this.blocks.put(id, block);
		}
		return block;
	}

	/**
	 * {@inheritDoc}
	 */
	public final Integer getId() {
		return this.id;
	}

	/**
	 * {@inheritDoc}
	 */
	public final String getName() {
		return this.name;
	}

	/**
	 * {@inheritDoc}
	 */
	public final IBlock getBlock(String id) {
		if (this.blocks.containsKey(id)) {
			return this.blocks.get(id);
		} else {
			return createBlock(id);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public final List<IBlock> getBlocks() {
		return new ArrayList<IBlock>(this.blocks.values());
	}

	/**
	 * {@inheritDoc}
	 */
	public final ICfg getCfg() {
		return this.cfg;
	}

	/**
	 * {@inheritDoc}
	 */
	public final List<String> getVariables() {
		return Collections.unmodifiableList(variables);
	}

	/**
	 * {@inheritDoc}
	 */
	public final List<String> getParameters() {
		return Collections.unmodifiableList(parameters);
	}

	/**
	 * {@inheritDoc}
	 */
	public final void addVariable(String variableName) {
		if (!this.variables.contains(variableName) && !this.parameters.contains(variableName)) {
			this.variables.add(variableName);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public final void addParameters(String parameterName) {
		if (!this.parameters.contains(parameterName)) {
			this.parameters.add(parameterName);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		StringBuffer pretty = new StringBuffer();
		pretty.append("Fonction " + name + " (ID=" + id + ")\n");
		pretty.append("\tParametres : ");
		for (String var : this.parameters) {
			pretty.append(var).append(" ");
		}
		pretty.append("\n");
		pretty.append("\tVariables locales : ");
		for (String var : this.variables) {
			pretty.append(var).append(" ");
		}
		pretty.append("\n");
		for (IBlock block : this.blocks.values()) {
			pretty.append(block.toString());
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
		for (i = 0; i < level; i++) {
			indentation.append("\t");
		}

		// Description de la fonction
		toReturn.append(indentation).append("<function name=\"").append(this.name);
		toReturn.append("\" id=\"").append(this.id).append("\">\n");

		// Description des parametres de la fonction
		if (this.parameters.size() > 0) {
			toReturn.append(indentation).append("\t<parameters>\n");
			for (String var : this.parameters) {
				toReturn.append(indentation).append("\t\t<parameter>").append(var).append("</parameter>\n");
			}
			toReturn.append(indentation).append("\t</parameters>\n");
		}

		// Description des variables locales de la fonction
		if (this.variables.size() > 0) {
			toReturn.append(indentation).append("\t<variables>\n");
			for (String var : this.variables) {
				toReturn.append(indentation).append("\t\t<variable>").append(var).append("</variable>\n");
			}
			toReturn.append(indentation).append("\t</variables>\n");
		}

		// Description des blocks qui compose la fonction
		toReturn.append(indentation).append("\t<blocks>\n");
		for (IBlock block : this.blocks.values()) {
			toReturn.append(block.toXML(level + 2));
		}
		toReturn.append(indentation).append("\t</blocks>\n");
		toReturn.append(indentation).append("</function>\n");
		return toReturn;
	}
}
