package fr.lip6.move.evinrude.parser.modules;

import fr.lip6.move.evinrude.commons.model.cfg.Application;
import fr.lip6.move.evinrude.commons.model.cfg.Cfg;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IAssignement;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IBlock;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICall;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICfg;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICondition;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IExpressionComplex;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IFunction;
import fr.lip6.move.evinrude.commons.utils.IdGenerator;
import fr.lip6.move.evinrude.parser.IParser;

/**
 * Objet simulant un parser.
 */
public class MockParser implements IParser {
	private int currentLine = -1;
	private IFunction currentFunction;
	private IBlock currentBlock;
	private ICfg currentCfg = new Cfg(new Application("app"), "cfg", new IdGenerator());
	private IAssignement currentAssignement;
	private ICondition currentCondition;
	private IExpressionComplex currentExpr;
	private ICall call;

	/**
	 * {@inheritDoc}
	 */
	public final ICfg getCfg() {
		return currentCfg;
	}

	/**
	 * {@inheritDoc}
	 */
	public final IAssignement getCurrentAssignement() {
		return currentAssignement;
	}

	/**
	 * {@inheritDoc}
	 */
	public final IBlock getCurrentBlock() {
		return currentBlock;
	}

	/**
	 * {@inheritDoc}
	 */
	public final ICondition getCurrentCondition() {
		return currentCondition;
	}

	/**
	 * {@inheritDoc}
	 */
	public final IExpressionComplex getCurrentExpr() {
		return currentExpr;
	}

	/**
	 * {@inheritDoc}
	 */
	public final IFunction getCurrentFunction() {
		return currentFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	public final int getCurrentLine() {
		return currentLine;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void setCurrentAssignement(IAssignement currentAssignement) {
		this.currentAssignement = currentAssignement;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void setCurrentBlock(IBlock block) {
		this.currentBlock = block;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void setCurrentCondition(ICondition currentCondition) {
		this.currentCondition = currentCondition;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void setCurrentExpr(IExpressionComplex currentExpr) {
		this.currentExpr = currentExpr;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void setCurrentFunction(IFunction function) {
		this.currentFunction = function;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void setCurrentLine(int line) {
		this.currentLine = line;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final int getCfgLine() {
		return 0;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final ICall getCurrentCall() {
		return call;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final void setCurrentCall(ICall call) {
		this.call = call;
	}

}
