package fr.lip6.move.evinrude.commons.model.cfg.interfaces;

/**
 * Définition des comportements publics d'une instruction de bloc.<br>
 * On definit principalement 3 types d'insutructions :
 * <ul>
 * 	<li>Un appel de fonction (interne/externe)</li>
 * 	<li>Une conditionnelle</li>
 * 	<li>Une assignation (affectation)</li>
 * </ul>
 *
 * @author Jean-Baptiste Voron
 */
public interface IInstruction {
	/**
	 * @return le numéro de ligne dans le CFG de l'instruction
	 */
	int getCfgLine();

	/**
	 * @return le numéro de ligne de l'instruction
	 */
	int getLineNumber();

	/**
	 * @return <code>true</code> si l'instruction est un appel de fonction (interne/externe)
	 */
	boolean isCallSite();

	/**
	 * @return <code>true</code> si l'instruction est une structure conditionnelle
	 */
	boolean isConditionnal();

	/**
	 * @return <code>true</code> si l'instruction est une affectation
	 */
	boolean isAssignement();

	/**
	 * @return un appel de fonction ({@link ICall}) s'il existe, ou <code>null</code> sinon
	 */
	ICall getCallSite();

	/**
	 * @return bloc contenant cette instruction
	 */
	IBlock getBlock();

	/**
	 * @return la description XMl de l'opérande
	 * @param level Niveau d'indentation
	 */
	StringBuffer toXML(int level);
}
