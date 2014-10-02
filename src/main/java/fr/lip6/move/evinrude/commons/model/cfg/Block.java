package fr.lip6.move.evinrude.commons.model.cfg;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IAssignement;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IBlock;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICall;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICondition;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IFunction;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IInstruction;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Définition d'un bloc de CFG<br>
 * Un tel bloc contient une liste d'éléments clé ainsi que des information sur ses liens (prédécesseurs et successeurs)
 * @author Jean-Baptiste Voron
 */
public class Block implements IBlock {
	/** Le logger */
	private static Logger LOG = Logger.getLogger(Block.class.getName());

	/** L'identifiant du bloc */
	private String id;

	/** Ligne dans le fichier source où commence le block */
	private int startingLine;

	/** La fonction parente du bloc */
	private IFunction parent;

	/** La liste des blocs fils */
	private Set<IBlock> successors;

	/** La liste des blocs parents */
	private Set<IBlock> predecessors;

	/** La liste des affectations */
	private Map<Integer, IInstruction> instructions;

	/** La condition de passage aux blocs suivants */
	private ICondition condition = null;

	/**
	 * Constructeur
	 * @param parent Fonction qui contient le bloc à créer
	 * @param id L'identifiant du bloc à créer
	 * @param startingLine ligne dans le fichier source où commence le block
	 */
	Block(IFunction parent, String id, int startingLine) {
		if (parent == null) { LOG.severe("Block's parent is null !"); }
		this.parent = parent;
		this.id = id;
		this.startingLine = startingLine;
		this.successors = new HashSet<IBlock>();
		this.predecessors = new HashSet<IBlock>();
		this.instructions = new HashMap<Integer, IInstruction>();
	}

	/**
	 * {@inheritDoc}
	 */
	public final String getId() {
		return this.id;
	}

	/**
	 * {@inheritDoc}
	 */
	public final int getStartingLine() {
		return startingLine;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void setStartingLine(int startingLine) {
		if (this.startingLine != -1) {
			throw new InvalidParameterException("A starting line for the current block has already been set ! [old:" + this.startingLine + ", new:" + startingLine + "]");
		}
		this.startingLine = startingLine;
	}

	/**
	 * {@inheritDoc}
	 */
	public final IFunction getFunction() {
		return this.parent;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void addSuccessor(String id) {
		IBlock succ = this.parent.getBlock(id);
		this.successors.add(succ);
	}

	/**
	 * {@inheritDoc}
	 */
	public final void addPredecessor(String id) {
		IBlock pred = this.parent.getBlock(id);
		this.predecessors.add(pred);
	}

	/**
	 * {@inheritDoc}
	 */
	public final void addAssignement(IAssignement assignement) {
		if (this.instructions.containsKey(assignement.getCfgLine())) {
			throw new InvalidParameterException("An instruction is already set for this lineNumber");
		}
		this.instructions.put(assignement.getCfgLine(), assignement);
	}

	/**
	 * {@inheritDoc}
	 */
	public final void addCall(ICall call) {
		this.instructions.put(call.getCfgLine(), call);
	}

	/**
	 * {@inheritDoc}
	 */
	public final void addConditionnal(ICondition condition) {
		if (this.condition != null) {
			throw new InvalidParameterException("A condition has already been set");
		}
		this.condition = condition;
	}

	/**
	 * {@inheritDoc}
	 */
	public final Collection<IBlock> getPredecessors() {
		return this.predecessors;
	}

	/**
	 * {@inheritDoc}
	 */
	public final Collection<IBlock> getSuccessors() {
		return this.successors;
	}

	/**
	 * {@inheritDoc}
	 */
	public final Map<Integer, IFunction> getLocalFunctionCalls() {
		Map<Integer, IFunction> applicationCalls = new HashMap<Integer, IFunction>();

		// Parcours de toutes les instructions du bloc
		for (Integer instructionNumber : instructions.keySet()) {
			// Recherche un appel de fonction dans cette instruction
			ICall call = instructions.get(instructionNumber).getCallSite();
			if (call == null) {	continue; }

			// On traite un appel de fonction... Est-ce un appel à une bibliothèque ?
			List<IFunction> calledList = parent.getCfg().getApplication().getFunction(call.getFunctionName());
			IFunction called = null;
			if (calledList.size() < 1) {
				LOG.finer("Undefined function : " + call.getFunctionName());
				continue;
				// S'il y a une seule fonction ui s'appelle comme çà
			} else if (calledList.size() == 1) {
				// On cherche d'abord une fonction homonyme locale
				called = parent.getCfg().getFunction(call.getFunctionName());
				if (called == null) {
					LOG.fine("The function " + call.getFunctionName() + " is outside the CFG (--> " + calledList.get(0).getCfg().getName() + ")");
					called = calledList.get(0);
				}
				// S'il y a plusieurs résultats
			} else {
				LOG.warning("Ambiguity upon the function's name : " + call.getFunctionName() + " (considering the first occurence)");
				// On cherche d'abord une fonction homonyme locale
				called = parent.getCfg().getFunction(call.getFunctionName());
				if (called == null) {
					called = calledList.get(0);
				}
			}

			// L'appel de fonction est local à l'application... on l'ajoute à la liste
			applicationCalls.put(instructionNumber, called);
		}
		return applicationCalls;
	}

	/**
	 * {@inheritDoc}
	 */
	public final Map<Integer, ICall> getLibraryCalls() {
		Map<Integer, ICall> externalCall = new HashMap<Integer, ICall>();

		// Parcours de toutes les instructions du bloc
		for (Integer instructionNumber : instructions.keySet()) {
			// Recherche un appel de fonction dans cette instruction
			ICall call = instructions.get(instructionNumber).getCallSite();
			if (call == null) { continue; }

			// On traite un appel de fonction... Mais est-il externe ?
			if (parent.getCfg().getApplication().getFunction(call.getFunctionName()).size() > 0) { continue; }

			// L'appel de fonction n'est pas local... On ajoute à la liste
			externalCall.put(instructionNumber, call);
		}
		return externalCall;
	}

	/**
	 * {@inheritDoc}
	 */
	public final Map<Integer, IInstruction> getInstructions() {
		return this.instructions;
	}

	/**
	 * {@inheritDoc}
	 */
	public final ICondition getCondition() {
		return this.condition;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		StringBuffer pretty = new StringBuffer();
		pretty.append("\tBlock " + id + " starting at line " + startingLine + "\n");

		pretty.append("\t\tListe des predecesseurs : ");
		for (IBlock pred : this.predecessors) {
			pretty.append(pred.getId()).append(" ");
		}
		pretty.append("\n");
		pretty.append("\t\tListe des successeurs : ");
		for (IBlock succ : this.successors) {
			pretty.append(succ.getId()).append(" ");
		}
		pretty.append("\n");
		if (condition != null) {
			pretty.append("\t\t").append(condition.toString()).append("\n");
		}
		pretty.append("\t\tListe des instructions : \n");
		for (Integer key : this.instructions.keySet()) {
			pretty.append("\t\t\t").append(key).append(" -> ").append(this.instructions.get(key).toString());
			pretty.append("\n");
		}
		return pretty.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	public final StringBuffer toXML(int level) {
		int i = 0;
		StringBuffer toReturn = new StringBuffer();
		StringBuffer indentation = new StringBuffer();

		// Indentation
		for (i = 0; i < level; i++) {
			indentation.append("\t");
		}

		toReturn.append(indentation).append("<block id=\"").append(this.id).append("\" ");
		toReturn.append("startingLine=\"").append(this.startingLine).append("\" ");

		// Liste des prédecesseurs
		toReturn.append("predecessors=\"");
		for (IBlock pred : this.predecessors) {
			toReturn.append(pred.getId()).append(" ");
		}
		toReturn.append("\" ");

		// Liste des successeurs
		toReturn.append("successors=\"");
		for (IBlock succ : this.successors) {
			toReturn.append(succ.getId()).append(" ");
		}
		toReturn.append("\">");

		// Liste des instructions
		if (this.instructions.size() > 0) {
			toReturn.append("\n");
			toReturn.append(indentation).append("\t<instructions>\n");
			for (IInstruction instruction : this.instructions.values()) {
				toReturn.append(instruction.toXML(level + 2));
			}
			toReturn.append(indentation).append("\t</instructions>\n");
			toReturn.append(indentation);
		}

		toReturn.append("</block>\n");
		return toReturn;
	}
}
