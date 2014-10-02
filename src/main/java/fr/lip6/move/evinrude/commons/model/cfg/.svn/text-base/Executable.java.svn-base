package fr.lip6.move.evinrude.commons.model.cfg;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IApplication;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICfg;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IExecutable;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IFunction;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IModel;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Un Executable référence une fonction main et une liste de cfg et représente un exécutable
 * @author Clément Démoulins
 * @author Jean-Baptiste Voron
 */
public class Executable implements IExecutable {
	private final IApplication application;
	private final IFunction main;
	private final Set<ICfg> cfgs = new HashSet<ICfg>();
	private IModel model;

	private File folder;
	private File biancaFolder;

	/**
	 * @param application application contenant cette exécutable
	 * @param main fonction main
	 */
	public Executable(IApplication application, IFunction main) {
		this.application = application;
		this.main = main;
		cfgs.add(main.getCfg());
		this.folder = new File(System.getProperty("evinrude.outputdir") + "/" + application.getName(), getName());
		if (!folder.exists()) {
			folder.mkdirs();
		}
		this.biancaFolder = new File(folder, "bianca");
	}

	/** {@inheritDoc}
	 */
	@Override
	public final String getName() {
		return main.getCfg().getName().split("\\.")[0];
	}

	/** {@inheritDoc}
	 */
	public final void addCfg(ICfg cfg) {
		cfgs.add(cfg);
	}

	/** {@inheritDoc}
	 */
	public final Collection<ICfg> getCfgs() {
		return Collections.unmodifiableCollection(cfgs);
	}

	/** {@inheritDoc}
	 */
	public final IApplication getApplication() {
		return application;
	}

	/** {@inheritDoc}
	 */
	public final IFunction getMain() {
		return main;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final void setModel(IModel model) {
		this.model = model;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final IModel getModel() {
		return model;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final File getFolder() {
		return folder;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final File getBiancaFolder() {
		return biancaFolder;
	}
}
