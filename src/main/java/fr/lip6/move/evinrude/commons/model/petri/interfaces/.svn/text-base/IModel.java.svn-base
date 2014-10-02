package fr.lip6.move.evinrude.commons.model.petri.interfaces;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICfg;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IExecutable;

import java.util.NoSuchElementException;


/**
 */
public interface IModel extends ISubModel {
	/**
	 * @return l'executable associée au modèle
	 */
	IExecutable getExecutable();

	/**
	 * @return le CFG qui contient la fonction <code>main</code>
	 */
	ICfg getHead();

	/**
	 * @param iCfg ajoute un CFG à traiter, si il est déjà en file d'attente ou si il a déjà été traité il est ignoré.
	 */
	void enqueueNewCfg(ICfg iCfg);

	/**
	 * @return <code>true</code> si il reste des CFG en file d'attente
	 */
	boolean hasNextNewCfg();

	/**
	 * @return retourne le prochain CFG à traiter
	 * @throws NoSuchElementException Si il n'y a plus de CFG à traiter.
	 */
	ICfg nextNewCfg() throws NoSuchElementException;

	/**
	 * Recherche un nœud dans tout le modèle
	 * @param name nom du nœud recherché
	 * @return le nœud ou <code>null</code>
	 */
	INode findNode(String name);
}
