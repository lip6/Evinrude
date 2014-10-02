package fr.lip6.move.evinrude.builder.perspectives.threads;

import fr.lip6.move.evinrude.builder.AbstractPerspective;
import fr.lip6.move.evinrude.builder.IPerspective;

/**
 * Définition de la perspective en charge de la gestion des threads<br>
 *
 * @author Jean-Baptiste Voron
 */
public class ThreadsPerspective extends AbstractPerspective implements IPerspective {
	static final String RESOURCE_ATTACHED_THREADS = "res_attached_threads";
	/**
	 * Constructeur<br>
	 */
	public ThreadsPerspective() {
		super("Threads Perspective");

		// Ajout des règles
		addRule(new Threads0());
		addRule(new Threads1());
		addRule(new Threads2());
		addRule(new Threads3());
	}
}
