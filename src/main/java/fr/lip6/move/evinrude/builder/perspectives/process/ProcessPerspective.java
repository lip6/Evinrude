package fr.lip6.move.evinrude.builder.perspectives.process;

import fr.lip6.move.evinrude.builder.AbstractPerspective;
import fr.lip6.move.evinrude.builder.IPerspective;

/**
 * Définition de la perspective en charge de la gestion des appels système liés aux processus<br>
 * @author Jean-Baptiste Voron
 */
public class ProcessPerspective extends AbstractPerspective implements IPerspective {
	/**
	 * Constructeur<br>
	 */
	public ProcessPerspective() {
		super("Process Perspective");

		// Ajout des règles
		addRule(new Process0());
		addRule(new Process1());
		addRule(new Process2());
	}
}
