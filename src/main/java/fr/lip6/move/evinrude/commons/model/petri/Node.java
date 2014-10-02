package fr.lip6.move.evinrude.commons.model.petri;

import fr.lip6.move.evinrude.commons.model.petri.interfaces.IArc;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ISubModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ITransition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 */
public abstract class Node implements INode {

	private final ISubModel subModelContainer;

	private final String name;
	private final List<IArc> incoming = new ArrayList<IArc>();
	private final List<IArc> outgoing = new ArrayList<IArc>();

	private int type;

	/**
	 * @param subModel sous-modèle contenant ce nœud.
	 * @param name Nom de l'attribut
	 * @param type Type du nœud, voir {@link IPlace} et {@link ITransition}
	 */
	Node(ISubModel subModel, String name, int type) {
		this.subModelContainer = subModel;
		this.name = name;
		this.type = type;
	}

	/**
	 * @param subModel sous-modèle contenant ce nœud
	 * @param node Nœud à copier
	 */
	Node(ISubModel subModel, INode node) {
		this(subModel, node.getName(), node.getType());
	}

	/** {@inheritDoc}
	 */
	public final ISubModel getSubModelContainer() {
		return subModelContainer;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final String getName() {
		return name;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final List<IArc> getIncomingArcs() {
		return Collections.unmodifiableList(incoming);
	}

	/** {@inheritDoc}
	 */
	@Override
	public final List<IArc> getOutgoingArcs() {
		return Collections.unmodifiableList(outgoing);
	}

	/**
	 * @param arc ajoute cet arc aux arcs entrant
	 */
	final void addIncomingArc(IArc arc) {
		incoming.add(arc);
	}

	/**
	 * @param arc ajoute cet arc aux arcs sortant
	 */
	final void addOutgoingArc(IArc arc) {
		outgoing.add(arc);
	}

	/**
	 * @param arc retire cet arc aux arcs entrant
	 * @return <code>true</code> si l'arc a été enlevé
	 */
	final boolean removeIncomingArc(IArc arc) {
		return incoming.remove(arc);
	}

	/**
	 * @param arc retire cet arc aux arcs sortant
	 * @return <code>true</code> si l'arc a été enlevé
	 */
	final boolean removeOutgoingArc(IArc arc) {
		return outgoing.remove(arc);
	}

	/**
	 * Retire tous les arcs sortants
	 */
	final void clearOutgoingArc() {
		outgoing.clear();
	}

	/**
	 * Retire tous les arcs entrants
	 */
	final void clearIncomingArc() {
		incoming.clear();
	}

	/** {@inheritDoc}
	 */
	@Override
	public final List<INode> getNextNodes() {
		List<INode> nodes = new ArrayList<INode>();
		for (IArc arc : outgoing) {
			nodes.add(arc.getTarget());
		}
		return nodes;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final List<INode> getPreviousNodes() {
		List<INode> nodes = new ArrayList<INode>();
		for (IArc arc : incoming) {
			nodes.add(arc.getSource());
		}
		return nodes;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void setType(int newType) {
		this.type = newType;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final boolean isTyped(int flag) {
		if (flag == 0) {
			return this.type == 0;
		}
		return (this.type & flag) == flag;
	}

	@Override
	public final boolean isNotTyped(int flag) {
		if (flag == 0) {
			return type != 0;
		}
		return ((Integer.MAX_VALUE ^ type) & flag) == flag;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final int getType() {
		return this.type;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final boolean isPlace() {
		return this instanceof IPlace;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final boolean isTransition() {
		return this instanceof ITransition;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final boolean equals(Object obj) {
		if (!(obj instanceof Node)) {
			return false;
		}
		Node node = (Node) obj;
		return subModelContainer.equals(node.getSubModelContainer()) && name.equals(node.getName());
	}

	/** {@inheritDoc}
	 */
	@Override
	public final int hashCode() {
		return name.hashCode() * subModelContainer.hashCode();
	}

	/** {@inheritDoc}
	 */
	@Override
	public final String toString() {
		return name;
	}

}
