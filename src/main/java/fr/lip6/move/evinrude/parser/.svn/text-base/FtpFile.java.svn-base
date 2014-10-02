package fr.lip6.move.evinrude.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import org.apache.commons.net.ftp.FTPClient;

/**
 * Descripteur d'un fichier cfg. Permet juste de lire le fichier.
 */
public class FtpFile implements IFile {

	/** Le logger */
	private static Logger LOG = Logger.getLogger(FtpFile.class.getName());

	private String filename;
	private String path;
	private FTPClient ftp;

	/**
	 * @param ftp Client ftp connecté.
	 * @param path Chemin complet vers le fichier distant
	 */
	public FtpFile(FTPClient ftp, String path) {
		this.filename = path.replaceAll("/.*/", "");
		this.path = path;
		this.ftp = ftp;
	}

	/**
	 * {@inheritDoc}
	 */
	public final String getFilename() {
		return filename;
	}

	/**
	 * {@inheritDoc}
	 */
	public final String getPath() {
		return path;
	}

	/**
	 * {@inheritDoc}
	 */
	public final InputStream getInputStream() throws IOException {
		return new FtpInputStream(ftp, path);
	}

	/**
	 * Classe interne pour gérer les InputStream retourné par le client ftp et les fermés correctement.
	 */
	private class FtpInputStream extends InputStream {
		private InputStream is;
		private FTPClient ftp;

		/**
		 * @param ftp Connexion ouverte sur un ftp
		 * @param remote Fichier à lire
		 * @throws IOException @see {@link FTPClient.retrieveFileStream}
		 */
		public FtpInputStream(FTPClient ftp, String remote) throws IOException {
			this.ftp = ftp;
			this.is = ftp.retrieveFileStream(remote);
			if (is == null) {
				LOG.warning("Problème lors de la lecture du fichier " + remote);
				is = new ByteArrayInputStream(new byte[0]);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void close() throws IOException {
			ftp.completePendingCommand();
			is.close();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int read() throws IOException {
			return is.read();
		}

	}

}
