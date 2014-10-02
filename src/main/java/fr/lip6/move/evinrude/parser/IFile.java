package fr.lip6.move.evinrude.parser;

import java.io.IOException;
import java.io.InputStream;

/**
 * Interface d'un descripteur de fichier. L'interface est minimaliste, elle ne permet de récupérer que le nom du fichier
 * et de le lire.
 */
public interface IFile {
	/**
	 * @return nom du fichier
	 */
	String getFilename();

	/**
	 * @return chemin complet vers le fichier : /path/to/filename
	 */
	String getPath();

	/**
	 * @return InputStream permettant de lire le fichier. Attention, il faut fermer le flux avant de passer au suivant.
	 * @throws IOException En cas d'erreur lors de la création de l'InputStream
	 */
	InputStream getInputStream() throws IOException;
}
