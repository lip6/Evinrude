package fr.lip6.move.evinrude.commons.model.cfg.interfaces;


import fr.lip6.move.evinrude.commons.model.petri.interfaces.IModel;

import java.io.File;
import java.util.Collection;

/**
 * Un IExecutable référence une fonction main et une liste de cfg et représente un exécutable
 * @author Clément Démoulins
 * @author Jean-Baptiste Voron
 */
public interface IExecutable {

	/**
	 * @param cfg cfg utilisé par cette exécutable
	 */
	void addCfg(ICfg cfg);

	/**
	 * @return liste des cfg associés à cette exécutable, c'est liste est construite par le builder par la perspective structurelle
	 */
	Collection<ICfg> getCfgs();

	/**
	 * @return application
	 */
	IApplication getApplication();

	/**
	 * @return fonction main
	 */
	IFunction getMain();

	/**
	 * @return nom de l'exécutable
	 */
	String getName();

	/**
	 * @param model model associé
	 */
	void setModel(IModel model);

	/**
	 * @return model associé
	 */
	IModel getModel();

	/**
	 * @return dossier contenant les tous les fichiers générés pour cette executable
	 */
	File getFolder();

	/**
	 * @return dossier contenant les sources de bianca pour cette executable
	 */
	File getBiancaFolder();

}
