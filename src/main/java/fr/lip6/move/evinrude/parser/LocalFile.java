package fr.lip6.move.evinrude.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Descripteur de fichier locaux implémentant l'interface IFile. L'implémentation est basé directement sur les
 * java.io.File
 */
public class LocalFile implements IFile {

	private File file;

	/**
	 * @param file fichier classique
	 */
	public LocalFile(File file) {
		this.file = file;
	}

	/**
	 * {@inheritDoc}
	 */
	public final String getFilename() {
		return file.getName();
	}

	/**
	 * {@inheritDoc}
	 */
	public final InputStream getInputStream() throws IOException {
		return new FileInputStream(file);
	}

	/**
	 * {@inheritDoc}
	 */
	public final String getPath() {
		return file.getAbsolutePath();
	}

}
