package fr.lip6.move.evinrude.parser.modules;

import fr.lip6.move.evinrude.parser.AbstractModule;
import fr.lip6.move.evinrude.parser.IParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Module qui gère les lignes "inutiles" du fichier CFG comme les lignes vides
 */
public class ToIgnoreModule extends AbstractModule {

	private final List<String> toIgnore = new ArrayList<String>();

	/**
	 * Constructeur
	 * @param parser Objet parser permettant de récupérer certains paramètres courrant
	 */
	public ToIgnoreModule(IParser parser) {
		super(parser);
		toIgnore.add("");
		toIgnore.add("\\{");
		toIgnore.add("\\}");
		toIgnore.add("else");
		toIgnore.add("<L\\d+>:;");
		toIgnore.add(".*goto\\s<.*\\d+>.*;");
		toIgnore.add(".*return;");
		toIgnore.add("Merging blocks \\d+ and \\d+");
		toIgnore.add("Removing basic block \\d+");
		toIgnore.add(";; Function (.*) \\(\\1\\)");
		toIgnore.add(".* [\\w\\.]+:;");
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean match(String line) {
		String trimLine = line.trim();
		for (String rule : toIgnore) {
			if (trimLine.matches(rule)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean process(String line) {
		return match(line);
	}

}
