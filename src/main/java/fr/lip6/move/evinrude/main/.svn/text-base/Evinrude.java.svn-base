package fr.lip6.move.evinrude.main;

import fr.lip6.move.evinrude.builder.Builder;
import fr.lip6.move.evinrude.builder.Flattener;
import fr.lip6.move.evinrude.commons.exceptions.EvinrudeException;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IApplication;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IExecutable;
import fr.lip6.move.evinrude.commons.utils.DotExport;
import fr.lip6.move.evinrude.commons.utils.log.ConsoleHandler;
import fr.lip6.move.evinrude.commons.utils.log.EvinrudeLogFormatter;
import fr.lip6.move.evinrude.commons.utils.log.EvinrudeLogHandler;
import fr.lip6.move.evinrude.generator.Generator;
import fr.lip6.move.evinrude.optimizer.IReduction;
import fr.lip6.move.evinrude.optimizer.Reducer;
import fr.lip6.move.evinrude.parser.IFile;
import fr.lip6.move.evinrude.parser.Parser;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Classe principale du projet <b>Evinrude</b>
 * @author Jean-Baptiste Voron
 * @author Clément Démoulins
 */
public final class Evinrude {
	/** Le logger */
	private static Logger LOG;

	/** Constructeur privé */
	private Evinrude() { }

	/**
	 * Entrée principale du module <b>builder</b> du projet<br>
	 * Les paramètres nécessaires sont les suivants :
	 * <ul>
	 * <li>Une indication sur le traitement à effectuer</li>
	 * <li>Le répertoire où sont stockés les CFG de l'application</li>
	 * </ul>
	 * @param args Les arguments fourni au programme en ligne de commande
	 * @throws EvinrudeException Erreur lors de l'éxecution d'evinrude
	 * @throws IOException erreur d'entrée/sortie
	 * @throws InterruptedException inturreption alors que j'attends la fin de l'execution d'une commande
	 * @throws ParseException Erreur lors de l'analyse des arguments
	 */
	public static void main(String[] args) throws EvinrudeException, IOException, InterruptedException, ParseException {
		// Option de la ligne de commande
		Options options = new Options();
		OptionBuilder.withArgName("path name");
		OptionBuilder.hasArgs(2);
		OptionBuilder.withValueSeparator(' ');
		OptionBuilder.withDescription("Analyze a local application");
		options.addOption(OptionBuilder.create("analyze"));
		options.addOption("benchs", false, "Use the application's repository");
		options.addOption("negate", false, "In benchs mode, remove the argument of the applications proccess list");
		options.addOption("config", true, "Configuration file, defaut use resources/config.properties");

		GnuParser gnuParser = new GnuParser();
		CommandLine cli = gnuParser.parse(options, args);

		// Initialisation
		String configFilePath = "resources/config.properties";
		if (cli.hasOption("config")) {
			configFilePath = cli.getOptionValue("config");
		}
		initializeLogger(configFilePath);
		LOG.config("Starting Evinrude : << Failure is always between the chair and the screen >>");
		initializeProperties(configFilePath);

		// ————————————————————————————————————————————
		// ** 1 **  Chargement des applications
		EvinrudeLogFormatter.getInstance().setState("P");
		LOG.info("Step 1 : Load applications sources");
		Parser parser = new Parser();
		Iterator<IApplication> apps;

		//    1.a ** Utilisation du dépôt de benchs
		File benchsResult = new File(System.getProperty("evinrude.outputdir"), "benchs.result");
		if (cli.hasOption("benchs")) {
			if (benchsResult.exists()) { benchsResult.delete(); }
			apps = parser.parseBenchs(Arrays.asList(cli.getArgs()), cli.hasOption("negate"));

		//    1.b ** Utilisation de sources locales
		} else if (cli.hasOption("analyze")) {
			// Récupération du répertoire contenant tous les fichiers XML contenant la description du CFG
			List<IFile> files = Browser.buildFileList(cli.getOptionValues("analyze")[0]);
			apps = Collections.singletonList(parser.parse(cli.getOptionValues("analyze")[1], files.iterator())).iterator();

		//    1.c ** Erreur dans les arguments
		} else {
			new HelpFormatter().printHelp("evinrude", "", options, "One of the two options : -analyze|-benchs must be selected");
			return;
		}

		// Pour chaque application on lance le processus de construction de l'IDS
		while (apps.hasNext()) {
			EvinrudeLogFormatter.getInstance().setState("P");
			IApplication app = apps.next();

			Builder builder = new Builder(app);
			Reducer reducer = new Reducer(app);
			Flattener flattener = new Flattener();
			Generator generator = new Generator(app);
			fr.lip6.move.evinrude.compiler.Compiler compiler = new fr.lip6.move.evinrude.compiler.Compiler();

			for (IExecutable executable : app.getExecutables()) {

				// ————————————————————————————————————————————
				// ** 2 **  Construction du réseaux de Petri
				EvinrudeLogFormatter.getInstance().setState("B");
				builder.process(executable);
				//  2.5 ** Réduction hiérarchique
				EvinrudeLogFormatter.getInstance().setState("RH");
				reducer.reduc(executable, IReduction.HIERARCHICAL);
				//  2.9 **  Dump de contrôle dans un fichier DOT
				DotExport.dumpDotRepresentation(executable.getModel(), "pre-flatten", executable.getFolder());

				// ————————————————————————————————————————————
				// ** 3 **  Aplatissement des réseaux de Petri
				EvinrudeLogFormatter.getInstance().setState("F");
				flattener.flatten(executable);
				//  3.5 ** Réduction non hiérarchique
				EvinrudeLogFormatter.getInstance().setState("RF");
				reducer.reduc(executable, IReduction.FLAT);
				//  3.9 **  Dump de contrôle dans un fichier DOT
				DotExport.dumpDotRepresentation(executable.getModel(), "final", executable.getFolder());

				// ————————————————————————————————————————————
				// ** 4 ** Génération de code (RdP dans Bianca)
				EvinrudeLogFormatter.getInstance().setState("G");
				generator.generateNets(executable);

				// ————————————————————————————————————————————
				// ** 5 ** Compilation et tests de bianca
				EvinrudeLogFormatter.getInstance().setState("C");
				int result = compiler.compile(executable);
				String resultString = "OK";
				if (result != 0) {
					resultString = "KO";
				}

				if (cli.hasOption("benchs")) {
					boolean result2 = false;
					if (result == 0) {
						LOG.info("Test IDS for " + executable.getApplication().getName() + "/" + executable.getName());
						EvinrudeLogFormatter.getInstance().increaseIndent();
						result2 = testIDS(executable);
						EvinrudeLogFormatter.getInstance().decreaseIndent();
					}
					String resultString2 = "OK";
					if (!result2) {
						resultString2 = "KO";
					}

					// Écriture du tableau de bord (fichier 'benchs.result')
					BufferedWriter writer = new BufferedWriter(new FileWriter(benchsResult, true));
					writer.append(String.format("%-50s %s %s", app.getName() + "/" + executable.getName(), resultString, resultString2));
					writer.newLine();
					writer.close();
				}

				// Permettre au GC de faire son boulot
				executable.setModel(null);
			}
//			StatsExport.dumpStats(app, new File(System.getProperty("evinrude.outputdir") + "/" + app.getName()));
		}
	}

	/**
	 * Recupère les traces disponibles sur le dépôt puis les tests sur l'IDS généré pour cet executable
	 * @param executable executable
	 * @return résultat du test, <code>true</code> si toutes les traces sont passées <code>false</code> sinon
	 */
	private static boolean testIDS(IExecutable executable) {
		File replay = new File(executable.getBiancaFolder() + "/replay");
		if (!replay.isFile()) {
			LOG.warning("replay executable doesn't exist in [" + executable.getBiancaFolder() + "]");
			return false;
		} else if (!replay.canExecute()) {
			LOG.warning("replay is not executable [" + executable.getBiancaFolder() + "]");
			return false;
		}

		// Pour chaque trace on test l'IDS
		Iterator<IFile> iterator = Browser.findIDSTrace(executable);
		while (iterator.hasNext()) {
			IFile trace = iterator.next();
			String[] split = trace.getFilename().split("\\.");
			int resultExpected = Integer.valueOf(split[split.length - 2]);

			LOG.fine("Test de la trace : " + trace.getFilename());
			EvinrudeLogFormatter.getInstance().increaseIndent();
			try {
				// Création d'un fichier temporaire
				File traceFile = File.createTempFile(trace.getFilename(), "" + System.currentTimeMillis());
				traceFile.deleteOnExit();

				// Copie de la trace dans un fichier temporaire
				BufferedInputStream bis = new BufferedInputStream(trace.getInputStream());
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(traceFile));
				int c;
				while ((c = bis.read()) != -1) {
					bos.write(c);
				}
				bis.close();
				bos.close();

				try {
					// Lancement du test de l'IDS
					ProcessBuilder pb = new ProcessBuilder(replay.getAbsolutePath(), traceFile.getAbsolutePath());
					Process replayProcess = pb.start();
					int result = replayProcess.waitFor();

					// Résultat du test
					if (result != resultExpected) {
						LOG.warning("Test with trace '" + trace.getFilename() + "' failed, expected: " + resultExpected + " returned: " + result);
						return false;
					} else {
						LOG.fine("\tsucceed");
					}
				} catch (InterruptedException e) {
					LOG.warning("replayProcess interrupted : " + e.getMessage());
				}
			} catch (IOException e) {
				LOG.warning("Impossible de créer le fichier temporaire pour les traces [" + e.getMessage() + "]");
			}
			EvinrudeLogFormatter.getInstance().decreaseIndent();
		}
		return true;
	}

	/**
	 * Initialisation du logger
	 * @throws IOException Problème de chargement des propriétés du logger
	 * @param configFilePath Fichier de configuration à utiliser
	 */
	private static void initializeLogger(String configFilePath) throws IOException {
		// Chargement des propriétés du logger
		InputStream is = new FileInputStream(configFilePath);
		LogManager.getLogManager().readConfiguration(is);
		is.close();

		LOG = Logger.getLogger("fr.lip6.move.evinrude"); //$NON-NLS-1$
		LOG.setUseParentHandlers(false);

		// Les logs sont affiches dans la console
		LOG.addHandler(new ConsoleHandler());

		// Les logs sont enregistres dans un fichier.
		try {
			EvinrudeLogHandler handler = EvinrudeLogHandler.getInstance();
			EvinrudeLogFormatter format = EvinrudeLogFormatter.getInstance();
			handler.setFormatter(format);
			LOG.addHandler(handler);
		} catch (IOException ioe) {
			LOG.warning("FileHandler cannot be instanciated... Please contact the dev team"); //$NON-NLS-1$
		} catch (SecurityException se) {
			LOG.warning("FileHandler cannot be instanciated... Please contact the dev team"); //$NON-NLS-1$
		}
	}

	/**
	 * Initialisation des propriétés définies dans le fichier <code>resources/config.properties</code>
	 * @param configFilePath Fichier de configuration à utiliser
	 */
	private static void initializeProperties(String configFilePath) {
		try {
			Properties config = System.getProperties();
			config.load(new FileInputStream(configFilePath));
			for (String prop : config.stringPropertyNames()) {
				if (prop.startsWith("evinrude")) {
					LOG.config(prop + " = " + config.getProperty(prop));
				}
			}
			System.setProperties(config);
		} catch (IOException e) {
			LOG.severe("Config file not loaded [" + e.getMessage() + "]");
		}
	}
}
