package fr.lip6.move.evinrude.commons.model.petri;

import fr.lip6.move.evinrude.commons.exceptions.EvinrudeException;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ISubModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ITransition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 */
public class Place extends Node implements IPlace {
	/** Le logger */
	private static Logger LOG = Logger.getLogger(Place.class.getName());

	private String initialMarking = "";
	private NavigableMap<Integer, ISubModel> subModels = new TreeMap<Integer, ISubModel>();

	private String targetName;

	/**
	 * @param subModel sous-modèle contenant ce nœud
	 * @param name nom de la place
	 * @param type type de place
	 */
	Place(ISubModel subModel, String name, int type) {
		super(subModel, name, type);
		LOG.finest("\t\tPlace " + this.toString());
	}

	/**
	 * @param subModel sous-modèle contenant ce nœud
	 * @param name nom de la place
	 * @param targetName nom de la place cible
	 */
	Place(ISubModel subModel, String name, String targetName) {
		super(subModel, name, IPlace.VIRTUAL);
		this.targetName = targetName;
		LOG.finest("\t\tPlace " + this.toString() + "(ref:" + targetName + ")");
	}

	/**
	 * Les sous-modèle attaché à la place ne sont pas recopié
	 * @param subModel sous-modèle contenant ce nœud
	 * @param place place à copier
	 */
	Place(ISubModel subModel, IPlace place) {
		super(subModel, place);
		this.targetName = place.getTargetName();
		this.initialMarking = place.getInitialMarking();
		LOG.finest("\t\tPlace " + this.toString());
	}

	/** {@inheritDoc}
	 */
	@Override
	public final String getInitialMarking() {
		return initialMarking;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final void setInitialMarking(String initialMarking) {
		this.initialMarking = initialMarking;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final List<ITransition> getNext() {
		List<ITransition> transitions = new ArrayList<ITransition>();
		for (INode node : getNextNodes()) {
			if (node instanceof ITransition) {
				transitions.add((ITransition) node);
			}
		}
		return transitions;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final List<ITransition> getPrevious() {
		List<ITransition> transitions = new ArrayList<ITransition>();
		for (INode node : getPreviousNodes()) {
			if (node instanceof ITransition) {
				transitions.add((ITransition) node);
			}
		}
		return transitions;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final ISubModel createSubModel(int instructionNumber) throws EvinrudeException {
		if (subModels.containsKey(instructionNumber)) {
			LOG.severe("A sub-model have already this instruction number");
			throw new EvinrudeException("This instruction already taken: " + instructionNumber);
		}

		ISubModel subModel = new SubModel(this, instructionNumber);
		subModels.put(instructionNumber, subModel);

		return subModel;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final ISubModel getSubModel(int instructionNumber) {
		return subModels.get(instructionNumber);
	}

	/** {@inheritDoc}
	 */
	@Override
	public final Collection<ISubModel> getSubModels() {
		return Collections.unmodifiableCollection(subModels.values());
	}

	/** {@inheritDoc}
	 */
	@Override
	public final String getTargetName() {
		return targetName;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final void removeSubModels() {
		subModels.clear();
	}

	/** {@inheritDoc}
	 */
	public final void removeSubModel(int instructionNumber) {
		subModels.remove(instructionNumber);
	}

}
