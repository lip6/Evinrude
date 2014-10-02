package fr.lip6.move.evinrude.commons.model.petri;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICfg;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IExecutable;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ISubModel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;

/**
 */
public class Model extends SubModel implements IModel {
	private final IExecutable executable;
	private final ICfg head;

	private final Set<ICfg> processedCfg = new HashSet<ICfg>();
	private final Queue<ICfg> queuedCfg = new LinkedList<ICfg>();

	private boolean dirty = true;
	private Map<String, INode> cache = new HashMap<String, INode>();

	/**
	 * Constructeur du modèle
	 * @param exe L'exécutable qui est en cours de modélisation
	 */
	public Model(IExecutable exe) {
		super(null, 0);
		this.executable = exe;
		this.executable.setModel(this);
		this.head = exe.getMain().getCfg();
		enqueueNewCfg(head);
	}

	/** {@inheritDoc}
	 */
	@Override
	public final void enqueueNewCfg(ICfg cfg) {
		if (!processedCfg.contains(cfg) && !queuedCfg.contains(cfg)) {
			executable.addCfg(cfg);
			queuedCfg.add(cfg);
		}
	}

	/** {@inheritDoc}
	 */
	@Override
	public final ICfg getHead() {
		return head;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final boolean hasNextNewCfg() {
		return !queuedCfg.isEmpty();
	}

	/** {@inheritDoc}
	 */
	@Override
	public final ICfg nextNewCfg() throws NoSuchElementException {
		ICfg cfg = queuedCfg.remove();
		processedCfg.add(cfg);
		return cfg;
	}

	/**
	 * Notification d'un changement dans le modèle pour la gestion du cache de nœud.
	 * Cette modèle doit être prévenu des ajouts/suppression de nœud par les sous-modèle.
	 */
	final void setDirty() {
		dirty = true;
	}

	/**
	 * @param node nœud à ajouter au cache
	 */
	final void addCachedNode(INode node) {
		cache.put(node.getName(), node);
	}

	/**
	 * @param node nœud à supprimer du cache
	 */
	final void removeCachedNode(INode node) {
		cache.remove(node.getName());
	}

	/**
	 * Mise à jour du cache de nœud à partir de subModel.
	 * @param subModel sous-modèle
	 */
	private void updateCache(ISubModel subModel) {
		for (INode node : subModel.getNodes()) {
			cache.put(node.getName(), node);
		}
		for (IPlace place : subModel.getPlaces()) {
			for (ISubModel childModel : place.getSubModels()) {
				updateCache(childModel);
			}
		}
	}

	/** {@inheritDoc}
	 */
	@Override
	public final INode findNode(String name) {
		// Mise à jour du cache
		if (dirty) {
			cache.clear();
			updateCache(this);
			dirty = false;
		}
		return cache.get(name);
	}

	/** {@inheritDoc}
	 */
	@Override
	public final IExecutable getExecutable() {
		return executable;
	}
}
