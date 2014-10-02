package fr.lip6.move.evinrude.builder.perspectives.synchros;

import fr.lip6.move.evinrude.builder.AbstractPerspective;
import fr.lip6.move.evinrude.builder.IPerspective;

/**
 * Définition de la perspective en charge de la gestion des synchronisations<br>
 *
 * @author Jean-Baptiste Voron
 */
public class SynchroPerspective extends AbstractPerspective implements IPerspective {
	static final String RESOURCE_THREADS_SEMAPHORES = "res_pthread_semaphores";
	/**
	 * Constructeur<br>
	 */
	public SynchroPerspective() {
		super("Synchro Perspective");

		// Ajout des règles
		addRule(new Synchro0());
		addRule(new Synchro1());
		addRule(new Synchro2());
		addRule(new Synchro3());
	}
}
