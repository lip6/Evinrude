package fr.lip6.move.evinrude.commons.model.petri;

import fr.lip6.move.evinrude.commons.model.petri.interfaces.IGuard;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ISubModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ITransition;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 */
public class Transition extends Node implements ITransition {
	/** Le logger */
	private static Logger LOG = Logger.getLogger(Transition.class.getName());
	/** La liste des guardes associées à la transition */
	private List<IGuard> guards = new ArrayList<IGuard>();

	/**
	 * Constructeur d'une nouvelle transition
	 * @param subModel sous-modèle contenant ce nœud
	 * @param name nom de la transition
	 * @param type type de la transition
	 */
	Transition(ISubModel subModel, String name, int type) {
		super(subModel, name, type);
		this.guards.add(new Guard());
		LOG.finest("\t\tTransition " + this.toString());
	}

	/**
	 * Constructeur d'une transition par copie
	 * @param subModel sous-modèle contenant ce nœud
	 * @param transition transition à copier
	 */
	Transition(SubModel subModel, ITransition transition) {
		super(subModel, transition);
		addGuards(transition.getGuards());
		LOG.finest("\t\tTransition " + this.toString());
	}

	/**
	 * {@inheritDoc}
	 */
	public final void addGuard(IGuard guard) {
		this.guards.add(guard);
	}

	/**
	 * {@inheritDoc}
	 */
	public final void addGuards(List<IGuard> guards) {
		this.guards.addAll(guards);
	}

	/**
	 * {@inheritDoc}
	 */
	public final List<IGuard> getGuards() {
		return this.guards;
	}

	/**
	 * {@inheritDoc}
	 */
	public final IGuard getEventGuard() {
		for (IGuard guard : guards) {
			if (guard.getType() == IGuard.EVENT_GUARD) {
				return guard;
			}
		}
		return null;
	}
}
