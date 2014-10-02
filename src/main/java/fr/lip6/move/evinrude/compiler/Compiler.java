package fr.lip6.move.evinrude.compiler;

import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IExecutable;
import fr.lip6.move.evinrude.commons.utils.log.EvinrudeLogFormatter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe se chargeant de la compilation des IDS d'une IApplication.
 *
 * @author Clément Démoulins
 * @author Jean-Baptiste Voron
 */
public class Compiler {
	/** Le logger */
	private static final Logger LOG = Logger.getLogger(Compiler.class.getName());

	private String compileFilePath;

	/**
	 * Classe lisant un flux et écrivant dans un logger
	 * @author Clément Démoulins
	 * @author Jean-Baptiste Voron
	 */
	public static class StreamToLogRunnable implements Runnable {
		private Logger logger;
		private Level level;
		private BufferedReader reader;

		/**
		 * @param logger logger
		 * @param level niveau de sortie des logs
		 * @param input flux à lire
		 */
		public StreamToLogRunnable(Logger logger, Level level, InputStream input) {
			this.logger = logger;
			this.level = level;
			this.reader = new BufferedReader(new InputStreamReader(input));
		}

		/** {@inheritDoc}
		 */
		@Override
		public final void run() {
			String line;
			try {
				while ((line = reader.readLine()) != null) {
					logger.log(level, line);
				}
			} catch (IOException e) {
				logger.warning(e.getMessage());
			}
		}
	}

	/**
	 * Constructor
	 */
	public Compiler() {
		this.compileFilePath = System.getProperty("user.dir") + "/resources/compile.sh";
	}

	/**
	 * Lancement de la compilation, la méthode est synchrone.
	 * @param executable Compilation de cet executables
	 * @return valeur de retour de la compilation
	 * @throws IOException Impossible de rendre le script de compilation
	 * @throws InterruptedException Intérruption lors de l'exécution d'une commande
	 */
	public final int compile(IExecutable executable) throws IOException, InterruptedException {
		EvinrudeLogFormatter.getInstance().setIndent(0);
		LOG.log(Level.INFO, "Compilation for " + executable.getName());
		EvinrudeLogFormatter.getInstance().increaseIndent();

		// chmod +x compile.sh
		ProcessBuilder chmod = new ProcessBuilder("chmod", "+x", compileFilePath);
		Process chmodProcess = chmod.start();
		LOG.fine("chmod return " + chmodProcess.waitFor());

		// make bianca
		ProcessBuilder compile = new ProcessBuilder(compileFilePath, executable.getBiancaFolder().getAbsolutePath());
		final Process compileProcess = compile.start();
		EvinrudeLogFormatter.getInstance().increaseIndent();

		// stdout
		new Thread(new StreamToLogRunnable(LOG, Level.FINE, compileProcess.getInputStream())).start();

		// stderr
		new Thread(new StreamToLogRunnable(LOG, Level.WARNING, compileProcess.getErrorStream())).start();

		// On attend que la compilation soit finie
		int exitValue = compileProcess.waitFor();
		EvinrudeLogFormatter.getInstance().decreaseIndent();

		LOG.fine("compile.sh return " + exitValue);
		EvinrudeLogFormatter.getInstance().decreaseIndent();
		LOG.info("End of compilation of " + executable.getName());

		return exitValue;
	}
}
