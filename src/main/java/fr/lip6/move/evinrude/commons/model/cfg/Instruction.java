package fr.lip6.move.evinrude.commons.model.cfg;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IAssignement;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IBlock;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICall;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICondition;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IInstruction;

/**
 * Définition d'une <b>instruction</b> d'un bloc.<br>
 * Une instruction est associée à un numéro de ligne.
 * C'est une des choses importante qui permet le backtracking d'une erreur...
 *
 * @author Jean-Baptiste Voron
 */
public abstract class Instruction implements IInstruction {
	/** Le numero de ligne de l'instruction */
	private int lineNumber;

	/** Le numero de ligne dans le cfg */
	private int cfgLine;

	/** Block contenant cette instruction*/
	private IBlock block;

	/**
	 * Constructeur d'une instruction
	 * @param cfgLine Le numero de ligne dans le cfg
	 * @param lineNumber Le numéro de ligne associé à l'instruction
	 * @param block Block contenant cette instruction
	 */
	public Instruction(int cfgLine, int lineNumber, IBlock block) {
		this.cfgLine = cfgLine;
		this.lineNumber = lineNumber;
		this.block = block;
	}

	/**
	 * {@inheritDoc}
	 */
	public final int getLineNumber() {
		return this.lineNumber;
	}

	/** {@inheritDoc}
	 */
	public final IBlock getBlock() {
		return block;
	}

	/**
	 * {@inheritDoc}
	 */
	public final ICall getCallSite() {
		// Si l'instruction est un appel de fonction elle-même
		if (this instanceof ICall) {
			return (ICall) this;
		}

		// Si l'instruction est une affectation et que le membre droit est un appel de fonction
		if (this instanceof IAssignement) {
			IAssignement assignement = (IAssignement) this;
			if (assignement.getRight() instanceof ICall) {
				return (ICall) assignement.getRight();
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean isCallSite() {
		if (this instanceof ICall || (this instanceof IAssignement && ((IAssignement) this).getRight() instanceof ICall)) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean isConditionnal() {
		if (this instanceof ICondition) { return true; }
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean isAssignement() {
		if (this instanceof IAssignement) { return true; }
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public abstract StringBuffer toXML(int level);

	/** {@inheritDoc}
	 */
	@Override
	public final int getCfgLine() {
		return cfgLine;
	}
}
