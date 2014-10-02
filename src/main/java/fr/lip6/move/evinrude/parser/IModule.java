package fr.lip6.move.evinrude.parser;


/**
 * Interface définissant un module du parser.
 */
public interface IModule {
	/**
	 * Permet de vérifier si le module peut effectuer des traitements sur une ligne.
	 * @param line ligne à vérifier
	 * @return <code>true</code> si la ligne peut être traitée <code>false</code> sinon
	 */
	boolean match(String line);

	/**
	 * Traitement de la ligne sur le ICfg
	 * @param line ligne à traiter
	 * @return <code>true</code> si la ligne a été parsé <code>false</code> sinon
	 */
	boolean process(String line);
}
