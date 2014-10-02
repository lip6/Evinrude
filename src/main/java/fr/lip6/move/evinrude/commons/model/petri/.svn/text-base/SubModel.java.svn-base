package fr.lip6.move.evinrude.commons.model.petri;

import fr.lip6.move.evinrude.commons.exceptions.EvinrudeException;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IArc;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ISubModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ITransition;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 */
public class SubModel implements ISubModel {
	/** Le logger */
	private static Logger LOG = Logger.getLogger(SubModel.class.getName());

	private final IPlace parentPlace;
	private final int instructionNumber;

	private final Map<String, IPlace> places = new HashMap<String, IPlace>();
	private final Map<String, ITransition> transitions = new HashMap<String, ITransition>();

	/**
	 * @param parent place à laquelle est attachée ce sous modèle
	 * @param instructionNumber Instruction liée à ce sous-modèle
	 */
	SubModel(IPlace parent, int instructionNumber) {
		this.parentPlace = parent;
		this.instructionNumber = instructionNumber;
		LOG.finest("\t\t" + this);
	}

	/** {@inheritDoc}
	 */
	@Override
	public final IArc createArc(INode source, INode target, int type) throws EvinrudeException {
		if (source.isPlace() == target.isPlace()) {
			throw new EvinrudeException("Impossible de créer un arc entre " + source + " et " + target);
		} else if (!source.getSubModelContainer().equals(target.getSubModelContainer())) {
			throw new EvinrudeException("Impossible de créer un arc entre des nœuds contenu dans des sous-modèles différents");
		}
		IArc arc = new Arc(source, target, type);
		((Node) source).addOutgoingArc(arc);
		((Node) target).addIncomingArc(arc);
		return arc;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final IArc createArc(INode source, INode target) throws EvinrudeException {
		return createArc(source, target, IArc.NORMAL);
	}

	/** {@inheritDoc}
	 */
	@Override
	public final IPlace createPlace(String name, int type) throws EvinrudeException {
		if (getTopModel().findNode(name) != null) {
			LOG.severe("A node with this name already exist: " + name);
			throw new EvinrudeException("A node with this name already exist: " + name);
		}

		IPlace place = new Place(this, name, type);
		places.put(name, place);
		((Model) getTopModel()).addCachedNode(place);
		return place;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final IPlace createPlace(String name, String targetName) throws EvinrudeException {
		if (getTopModel().findNode(name) != null) {
			LOG.severe("A node with this name already exist: " + name);
			throw new EvinrudeException("A node with this name already exist: " + name);
		}

		IPlace place = new Place(this, name, targetName);
		places.put(name, place);
		((Model) getTopModel()).addCachedNode(place);
		return place;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final IPlace createPlace(IPlace place) throws EvinrudeException {
		if (getTopModel().findNode(place.getName()) != null) {
			LOG.severe("A node with this name already exist: " + place.getName());
			throw new EvinrudeException("A node with this name already exist: " + place.getName());
		}

		IPlace newPlace = new Place(this, place);
		places.put(place.getName(), newPlace);
		((Model) getTopModel()).addCachedNode(newPlace);
		return newPlace;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final ITransition createTransition(String name, int type) throws EvinrudeException {
		if (getTopModel().findNode(name) != null) {
			LOG.severe("A node with this name already exist: " + name);
			throw new EvinrudeException("A node with this name already exist: " + name);
		}

		ITransition transition = new Transition(this, name, type);
		transitions.put(name, transition);
		((Model) getTopModel()).addCachedNode(transition);
		return transition;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final ITransition createTransition(ITransition transition) throws EvinrudeException {
		if (getTopModel().findNode(transition.getName()) != null) {
			LOG.severe("A node with this name already exist: " + transition.getName());
			throw new EvinrudeException("A node with this name already exist: " + transition.getName());
		}
		((Model) getTopModel()).setDirty();

		ITransition newTransition = new Transition(this, transition);
		transitions.put(transition.getName(), newTransition);
		((Model) getTopModel()).addCachedNode(newTransition);
		return newTransition;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Collection<IArc> getArcs() {
		Set<IArc> arcs = new HashSet<IArc>();
		for (IPlace place : places.values()) {
			arcs.addAll(place.getOutgoingArcs());
		}
		for (ITransition transition : transitions.values()) {
			arcs.addAll(transition.getOutgoingArcs());
		}
		return arcs;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Collection<IArc> getArcs(INode source, INode target) {
		Set<IArc> arcs = new HashSet<IArc>();
		for (IArc a : source.getOutgoingArcs()) {
			if (a.getTarget().equals(target)) {
				arcs.add(a);
			}
		}
		return Collections.unmodifiableSet(arcs);
	}

	/** {@inheritDoc}
	 */
	@Override
	public final IArc getFirstArc(INode source, INode target) {
		Collection<IArc> arcs = getArcs(source, target);
		if (arcs.isEmpty()) {
			return null;
		} else {
			return arcs.iterator().next();
		}
	}

	/** {@inheritDoc}
	 */
	@Override
	public final IModel getTopModel() {
		// Si on est déjà dans le modèle de haut-niveau
		if (this instanceof IModel) { return (IModel) this; }
		// Sinon, on le trouve récursivement
		return this.parentPlace.getSubModelContainer().getTopModel();
	}

	/** {@inheritDoc}
	 */
	@Override
	public final IPlace getParentPlace() {
		return parentPlace;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final IPlace getPlace(String name) {
		return places.get(name);
	}

	/** {@inheritDoc}
	 */
	@Override
	public final Collection<IPlace> getPlaces() {
		return Collections.unmodifiableCollection(places.values());
	}

	/** {@inheritDoc}
	 */
	@Override
	public final ITransition getTransition(String name) {
		return transitions.get(name);
	}

	/** {@inheritDoc}
	 */
	@Override
	public final Collection<ITransition> getTransitions() {
		return Collections.unmodifiableCollection(transitions.values());
	}

	/**
	 * {@inheritDoc}
	 */
	public final INode getNode(String name) {
		// Recherche parmi les places du sous-modèle
		INode node = getPlace(name);
		if (node != null) { return node; }

		// Recherche parmi les transitions du sous-modèle
		node = getTransition(name);
		if (node != null) { return node; }

		return null;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final Collection<INode> getNodes() {
		// Cette classe anonyme permet d'éviter de copier la liste des places et des transitions
		return new AbstractCollection<INode>() {

			@Override
			public Iterator<INode> iterator() {
				return new Iterator<INode>() {
					private final Iterator<IPlace> itPlaces = places.values().iterator();
					private final Iterator<ITransition> itTransitions = transitions.values().iterator();

					@Override
					public boolean hasNext() {
						return itPlaces.hasNext() || itTransitions.hasNext();
					}

					@Override
					public INode next() {
						if (itPlaces.hasNext()) {
							return itPlaces.next();
						}
						return itTransitions.next();
					}

					@Override
					public void remove() { throw new UnsupportedOperationException(); }
				};
			}

			@Override
			public int size() {
				return places.size() + transitions.size();
			}

		};
	}

	/** {@inheritDoc}
	 */
	@Override
	public final int getInstructionNumber() {
		return instructionNumber;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final IPlace getInputPlace() {
		for (IPlace place : places.values()) {
			if (place.isTyped(IPlace.INPUT)) {
				return place;
			}
		}
		return null;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final List<IPlace> getOutputPlaces() {
		List<IPlace> list = new ArrayList<IPlace>();
		for (IPlace place : places.values()) {
			if (place.isTyped(IPlace.OUTPUT)) {
				list.add(place);
			}
		}
		return list;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final INode moveNode(INode node) {
		node.getSubModelContainer().removeNode(node);
		if (node instanceof IPlace) {
			return createPlace((IPlace) node);
		} else if (node instanceof ITransition) {
			return createTransition((ITransition) node);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void integrateSubModel(ISubModel submodel) throws EvinrudeException {
		List<IArc> arcs = new ArrayList<IArc>(submodel.getArcs());

		// Recopie des nœuds
		for (INode node : new ArrayList<INode>(submodel.getNodes())) {
			moveNode(node);
		}

		// Recopie des arcs (source, target, type et valuation)
		for (IArc arc : arcs) {
			INode source = getNode(arc.getSource().getName());
			INode target = getNode(arc.getTarget().getName());
			IArc newArc = createArc(source, target,	arc.getType());
			newArc.setValuation(arc.getValuation());
		}
	}

	/** {@inheritDoc}
	 */
	@Override
	public final INode removeNode(INode node) {
		if (node.isPlace() && ((IPlace) node).getSubModels().size() != 0) {
			return null;
		}
		if (places.remove(node.getName()) == null && transitions.remove(node.getName()) == null) {
			return null;
		}
		for (IArc arc : node.getIncomingArcs()) {
			((Node) arc.getSource()).removeOutgoingArc(arc);
		}
		for (IArc arc : node.getOutgoingArcs()) {
			((Node) arc.getTarget()).removeIncomingArc(arc);
		}
		((Node) node).clearIncomingArc();
		((Node) node).clearOutgoingArc();
		((Model) getTopModel()).removeCachedNode(node);
		return node;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final IArc removeArc(IArc arc) {
		INode source = arc.getSource();
		INode target = arc.getTarget();

		if (((Node) source).removeOutgoingArc(arc) && ((Node) target).removeIncomingArc(arc)) {
			return arc;
		}
		return null;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final boolean equals(Object obj) {
		if (!(obj instanceof SubModel)) {
			return false;
		}
		SubModel subModel = (SubModel) obj;
		if (parentPlace == null) {
			return super.equals(obj);
		} else {
			return instructionNumber == subModel.instructionNumber && parentPlace.equals(subModel.getParentPlace());
		}
	}

	/** {@inheritDoc}
	 */
	@Override
	public final int hashCode() {
		if (parentPlace == null) {
			return super.hashCode();
		}
		return Integer.valueOf(instructionNumber).hashCode() * parentPlace.hashCode();
	}

	/** {@inheritDoc}
	 */
	@Override
	public final String toString() {
		if (instructionNumber == -1) { return "SubModel(top-level)"; }
		return "SubModel(" + instructionNumber + ", linked to [" + parentPlace + "])";
	}
}
