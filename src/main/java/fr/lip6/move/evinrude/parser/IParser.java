package fr.lip6.move.evinrude.parser;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IAssignement;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IBlock;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICall;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICfg;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICondition;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IExpressionComplex;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IFunction;

/**
 * Interface du parser. Cette interface est utilisé principalement par les modules.
 */
public interface IParser {
	/**
	 * @return le ICfg courrant, si la méthode extractCfg n'a pas été appelé le ICfg est vide.
	 */
	ICfg getCfg();

	/**
	 * @return numero de ligne de l'instruction courrante
	 */
	int getCurrentLine();

	/**
	 * @param line mise à jour de la ligne courrante
	 */
	void setCurrentLine(int line);

	/**
	 * @return la fonction courrante ou <code>null</code>
	 */
	IFunction getCurrentFunction();

	/**
	 * @param function nouvelle fonction courrante
	 */
	void setCurrentFunction(IFunction function);

	/**
	 * @return le block courrant ou <code>null</code>
	 */
	IBlock getCurrentBlock();

	/**
	 * @param block nouveau block courrant
	 */
	void setCurrentBlock(IBlock block);

	/**
	 * @return the currentAssignement
	 */
	IAssignement getCurrentAssignement();

	/**
	 * @param currentAssignement the currentAssignement to set
	 */
	void setCurrentAssignement(IAssignement currentAssignement);

	/**
	 * @return the currentCondition
	 */
	ICondition getCurrentCondition();

	/**
	 * @param currentCondition the currentCondition to set
	 */
	void setCurrentCondition(ICondition currentCondition);

	/**
	 * @return the currentExpr
	 */
	IExpressionComplex getCurrentExpr();

	/**
	 * @param currentExpr the currentExpr to set
	 */
	void setCurrentExpr(IExpressionComplex currentExpr);

	/**
	 * @param call the current call to set
	 */
	void setCurrentCall(ICall call);

	/**
	 * @return the current call
	 */
	ICall getCurrentCall();

	/**
	 * @return Numero de ligne du CFG en cours de lecture
	 */
	int getCfgLine();
}
