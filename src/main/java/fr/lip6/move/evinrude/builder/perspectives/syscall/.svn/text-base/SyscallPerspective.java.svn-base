package fr.lip6.move.evinrude.builder.perspectives.syscall;

import fr.lip6.move.evinrude.builder.AbstractPerspective;
import fr.lip6.move.evinrude.builder.IPerspective;

/**
 * Définition de la perspective en charge de la gestion des appels système<br>
 *
 * @author Jean-Baptiste Voron
 */
public class SyscallPerspective extends AbstractPerspective implements IPerspective {
	/**
	 * Constructeur<br>
	 */
	public SyscallPerspective() {
		super("Syscall Perspective");

		// Ajout des règles
		addRule(new Syscall0());
		addRule(new Syscall1());
		addRule(new Syscall2());
		addRule(new Syscall3());
		addRule(new Syscall4());
		addRule(new Syscall5());
		addRule(new Syscall6());
	}
}
