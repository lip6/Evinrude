package fr.lip6.move.evinrude.commons.model.cfg;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IApplication;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICfg;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IFunction;
import fr.lip6.move.evinrude.commons.utils.IdGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Définition d'un objet CFG (Control Flow Graph).<br>
 * Cet objet est construit à part des informations fournies par le parser.
 * @author Jean-Baptiste Voron
 */
public class Cfg implements ICfg {

	/** Identifiant numérique du CFG */
	private Integer id = 0;

	/** La liste des fonctions du CFG */
	private Map<Integer, IFunction> functions;

	/** Le nom du fichier duquel est extrait le CFG */
	private String fileName;

	/** Application dont dépend le CFG */
	private IApplication application;

	/** Données sur la couverture du fichier CFG */
	private int lineNb = 0;
	private int coveredLineNb = 0;

	/**
	 * Constructeur du CFG<br>
	 * <i>Initialisation des listes</i>
	 * @param application L'application dont dépend le CFG
	 * @param fileName Le nom du fichier duquel est extrait le CFG
	 * @param idGen générateur d'id
	 */
	public Cfg(IApplication application, String fileName, IdGenerator idGen) {
		this.id = idGen.next();
		this.fileName = fileName;
		this.application = application;
		this.functions = new HashMap<Integer, IFunction>();
	}

	/**
	 * {@inheritDoc}
	 */
	public final String getFileName() {
		return fileName;
	}

	/**
	 * {@inheritDoc}
	 */
	public final String getName() {
		return fileName.substring(0, fileName.indexOf('.'));
	}

	/**
	 * {@inheritDoc}
	 */
	public final Integer getId() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	public final IApplication getApplication() {
		return this.application;
	}

	/**
	 * {@inheritDoc}
	 */
	public final IFunction createFunction(Integer id, String name) {
		IFunction function = new Function(id, name, this);
		this.functions.put(id, function);
		return function;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		StringBuffer pretty = new StringBuffer();
		pretty.append("-- Name: ").append(this.fileName).append("\n");
		for (IFunction function : this.functions.values()) {
			pretty.append(function.toString());
		}
		return pretty.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	public final List<IFunction> getFunctions() {
		return new ArrayList<IFunction>(this.functions.values());
	}

	/**
	 * {@inheritDoc}
	 */
	public final IFunction getFunction(String name) {
		for (IFunction f : this.functions.values()) {
			if (f.getName().equals(name)) {
				return f;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void removeFunction(String name) {
		this.functions.remove(getFunction(name).getId());
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

		// Description du CFG
		toReturn.append(indentation).append("<cfg name=\"").append(this.fileName);
		toReturn.append("\" id=\"").append(this.getId()).append("\">\n");
		for (IFunction function : this.functions.values()) {
			toReturn.append(function.toXML(level + 1));
		}
		toReturn.append(indentation).append("</cfg>\n");
		return toReturn;
	}

	/**
	 * {@inheritDoc}
	 */
	public final int getLineNb() {
		return lineNb;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void setLineNb(int lineNb) {
		this.lineNb = lineNb;
	}

	/**
	 * {@inheritDoc}
	 */
	public final int getCoveredLineNb() {
		return coveredLineNb;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void setCoveredLineNb(int coveredLineNb) {
		this.coveredLineNb = coveredLineNb;
	}
}
