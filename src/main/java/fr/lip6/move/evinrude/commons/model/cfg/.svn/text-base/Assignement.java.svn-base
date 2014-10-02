package fr.lip6.move.evinrude.commons.model.cfg;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IAssignement;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IBlock;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IOperand;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IVariable;

/**
 * Définition d'une affectation.<br>
 * Une affectation se compose obligatoirement :
 * <ul>
 * <li>d'un membre gauche qui est une variable;</li>
 * <li>d'un membre droit qui est une operande.</li>
 * </ul>
 * @author Jean-Baptiste Voron
 */
public class Assignement extends Instruction implements IAssignement {

	/** Membre gauche : Variable ({@link IVariable}) */
	private IVariable left;
	/** Membre droit : Opérande ({@link IOperand}) */
	private IOperand right;

	/**
	 * Constructeur d'une affectation
	 * @param cfgLine Le numero de ligne dans le cfg
	 * @param lineNumber Le numéro de ligne associée à l'affectation
	 * @param block Block contenant cette instruction
	 */
	public Assignement(int cfgLine, int lineNumber, IBlock block) {
		super(cfgLine, lineNumber, block);
	}

	/**
	 * {@inheritDoc}
	 */
	public final IVariable getLeft() {
		return left;
	}

	/**
	 * {@inheritDoc}
	 */
	public final IOperand getRight() {
		return right;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void setLeft(IVariable left) {
		this.left = left;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void setRight(IOperand right) {
		this.right = right;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		StringBuffer pretty = new StringBuffer();
		pretty.append("Assignement : [").append(left).append("] = [").append(right).append("]");
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

		// Description globale
		toReturn.append(indentation).append("<assignement line=\"").append(this.getCfgLine()).append("\">\n");
		// Membre de gauche
		toReturn.append(indentation).append("\t<left>\n");
		toReturn.append(this.left.toXML(level + 2));
		toReturn.append(indentation).append("\t</left>\n");
		// Membre de droite
		toReturn.append(indentation).append("\t<right>\n");
		toReturn.append(this.right.toXML(level + 2));
		toReturn.append(indentation).append("\t</right>\n");

		toReturn.append(indentation).append("</assignement>\n");
		return toReturn;
	}
}
