package fr.lip6.move.evinrude.commons.model.cfg;

import fr.lip6.move.evinrude.commons.exceptions.EvinrudeException;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IApplication;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICfg;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IExecutable;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IFunction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Définition d'une application.<br>
 * C'est le conteneur de niveau le plus haut.<br>
 * Une application contient plusieurs CFG provenant de plusieurs fichiers de code source.
 * @author Jean-Baptiste Voron
 */
public class Application implements IApplication {
	/** La liste des CFGs contenu dans l'application */
	private List<ICfg> cfgs;

	/** Le nom de l'application */
	private String name;

	/** Dossier contenant les tous les fichiers générés pour cette executable */
	private File folder;

	/** Une liste d'exécutables contenus dans l'application */
	private List<IExecutable> executables;
	private List<String> executablesFilter = new ArrayList<String>();

	/** Une map de timer pour les stats */
	private Map<Integer, Long> timers = new HashMap<Integer, Long>();

	/**
	 * Constructeur
	 * @param name Le nom de l'application
	 * @throws EvinrudeException En cas de problème lors de la création du dossier contenant les données de l'application
	 */
	public Application(String name) throws EvinrudeException {
		this.name = name;
		this.cfgs = new ArrayList<ICfg>();

		this.folder = new File(System.getProperty("evinrude.outputdir") + "/" + name);
		if (!folder.exists()) {
			folder.mkdirs();
		} else if (!folder.isDirectory()) {
			throw new EvinrudeException("Cannot create folder : " + folder.getAbsolutePath());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public final String getName() {
		return name;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final File getFolder() {
		return folder;
	}

	/**
	 * {@inheritDoc}
	 */
	public final List<ICfg> getCfgs() {
		return cfgs;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void addCfg(ICfg cfg) {
		if (cfg == null) { return; }
		this.cfgs.add(cfg);
	}

	/**
	 * {@inheritDoc}
	 */
	public final List<IFunction> getFunction(String name) {
		List<IFunction> toReturn = new ArrayList<IFunction>();
		for (ICfg cfg : this.cfgs) {
			IFunction f = cfg.getFunction(name);
			if (f != null) {
				toReturn.add(f);
			}
		}
		return toReturn;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final List<IExecutable> getExecutables() {
		if (executables == null) {
			executables = new ArrayList<IExecutable>();

			for (IFunction main : getFunction("main")) {
				IExecutable exe = new Executable(this, main);
				if (executablesFilter.isEmpty() || executablesFilter.contains(exe.getName())) {
					executables.add(exe);
				}
			}
		}

		return Collections.unmodifiableList(executables);
	}

	/** {@inheritDoc}
	 */
	@Override
	public final void addExecutableFilter(String filter) {
		executablesFilter.add(filter);
	}

	/** {@inheritDoc}
	 */
	@Override
	public final void addExecutablesFilter(List<String> filters) {
		executablesFilter.addAll(filters);
	}

	/**
	 * {@inheritDoc}
	 */
	public final void removeGnuFunctions() {
		// Parcours de tous les CFG pour extraire les fonctions
		for (ICfg cfg : this.cfgs) {
			// TODO: Définir les fonctions GNU et autres dans un fichier externe (de configuration ?)
			for (IFunction function : cfg.getFunctions()) {
				// Suppression des fonctions GNU
				if (function.getName().equalsIgnoreCase("gnu_dev_makedev")) {
					cfg.removeFunction(function.getName());
				} else if (function.getName().equalsIgnoreCase("gnu_dev_major")) {
					cfg.removeFunction(function.getName());
				} else if (function.getName().equalsIgnoreCase("gnu_dev_minor")) {
					cfg.removeFunction(function.getName());
				} else if (function.getName().equalsIgnoreCase("lstat")) {
					cfg.removeFunction(function.getName());
				} else if (function.getName().equalsIgnoreCase("stat")) {
					cfg.removeFunction(function.getName());
				} else if (function.getName().equalsIgnoreCase("mknod")) {
					cfg.removeFunction(function.getName());
				} else if (function.getName().equalsIgnoreCase("fstat")) {
					cfg.removeFunction(function.getName());
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		StringBuffer pretty = new StringBuffer();
		pretty.append("Application: ").append(this.name).append("\n");
		for (ICfg cfg : this.cfgs) {
			pretty.append(cfg.toString());
		}
		return pretty.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	public final StringBuffer toXML(FileWriter writer) throws IOException {
		StringBuffer toReturn = new StringBuffer();
		toReturn.append("<application name=\"").append(this.name).append("\">\n");
		for (ICfg cfg : this.cfgs) {
			writer.write(cfg.toXML(1).toString());
		}
		toReturn.append("</application>\n");
		return toReturn;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void setTimer(int timerType, long timerValue) {
		this.timers.put(timerType, timerValue);
	}

	/**
	 * {@inheritDoc}
	 */
	public final Map<Integer, Long> getTimers() {
		return this.timers;
	}
}
