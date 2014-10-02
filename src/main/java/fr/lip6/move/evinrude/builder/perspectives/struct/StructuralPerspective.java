package fr.lip6.move.evinrude.builder.perspectives.struct;

import fr.lip6.move.evinrude.builder.AbstractPerspective;
import fr.lip6.move.evinrude.builder.IPerspective;

/**
 * Définition de la perspective structurelle<br>
 * C'est la base de tout le travail.<br>
 * Après le passage de cette perspective, le squelette du RdP est construit<br>
 *
 * @author Jean-Baptiste Voron
 */
public class StructuralPerspective extends AbstractPerspective implements IPerspective {
	public static final String GLOBAL_EXIT = "Global_EXIT";

	/**
	 * Constructeur<br>
	 */
	public StructuralPerspective() {
		super("Structural Perspective");

		// Ajout des règles
		addRule(new Struct0());
		addRule(new Struct1());
		addRule(new Struct2());
		addRule(new Struct31());
		addRule(new Struct32());
		addRule(new Struct4());
		addRule(new Struct6());
	}
}
