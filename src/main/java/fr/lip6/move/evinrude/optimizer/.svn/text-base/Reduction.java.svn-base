package fr.lip6.move.evinrude.optimizer;

import fr.lip6.move.evinrude.commons.exceptions.EvinrudeException;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IModel;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.INode;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.IPlace;
import fr.lip6.move.evinrude.commons.model.petri.interfaces.ISubModel;
import fr.lip6.move.evinrude.optimizer.conditions.ICondition;

import java.util.ArrayList;
import java.util.List;

/**
 * Définition d'une règle de réduction<br>
 * Une réduction ne peut être appliquée que si toutes les conditions sont satisfaites.<br>
 * <i>Elle s'applique sur un noeud mais peut impacter d'autres noeuds...</i>
 *
 * @author Jean-Baptiste Voron
 */
public abstract class Reduction implements IReduction {
	/** Le nom de la réduction */
	private String name;

	/** Type de la réduction */
	private int type = 0;

	/** La liste des conditions pour que la règle puisse s'appliquer */
	private List<ICondition> conditions;

	/**
	 * Constructeur
	 * @param name Le nom de la réduction
	 */
	public Reduction(String name) {
		this.name = name;
		// Initialisation de la liste
		this.conditions = new ArrayList<ICondition>();
	}

	/**
	 * Mise en place de la réduction
	 * @param model Le modèle sur lequel on agit
	 * @param node Le noeud qui satisfait toutes les contraintes
	 * @throws EvinrudeException Si une erreur se produit
	 */
	public abstract void performReduction(IModel model, INode node) throws EvinrudeException;

	/**
	 * {@inheritDoc}
	 */
	public final String getName() {
		return this.name;
	}

	/**
	 * Ajout d'une condition de réduction
	 * @param condition La condition de réduction à ajouter
	 * @return La condition qui vient d'être attachée
	 */
	protected final ICondition addCondition(ICondition condition) {
		this.conditions.add(condition);
		return condition;
	}

	/**
	 * Trouve un noeud pour lequel la réduction est applicable<br>
	 * Cette recherche se fait par parcours de tous les noeuds et vérification des conditions d'application
	 * @param model Le modèle qui doit être réduit (dont les noeuds vont être parcourus)
	 * @return <code>true</code> si le noeud est elligible, <code>null</code> sinon.
	 */
	private INode findReducable(ISubModel model) {
		boolean flag;

		// Parcours de tous les nœuds
		for (INode node : model.getNodes()) {
			flag = true;

			// Parcours des conditions de réduction
			for (ICondition cond : this.conditions) {
				if (!cond.isVerify(node)) {
					flag = false; break;
				}
			}
			if (flag) {	return node; }
		}

		// Parcours des place contenant des sous-modèles
		for (IPlace node : model.getPlaces()) {
			if (node.getSubModels().size() > 0) {
				for (ISubModel sub : node.getSubModels()) {
					INode res = findReducable(sub);
					if (res != null) { return res; }
				}
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean apply(IModel model) {
		boolean hasPerformReduction = false;
		INode node = null;
		while ((node = findReducable(model)) != null) {
			try {
				performReduction(model, node);
				hasPerformReduction = true;
			} catch (EvinrudeException e) {
				System.err.println("Echec lors de la réduction " + getName());
				return false;
			}
		}
		return hasPerformReduction;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final boolean isTyped(int flag) {
		return (this.type & flag) == flag;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final void setType(int type) {
		this.type = type;
	}
}
