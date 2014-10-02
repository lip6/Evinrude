package fr.lip6.move.evinrude.parser;

import fr.lip6.move.evinrude.commons.model.cfg.Application;
import fr.lip6.move.evinrude.commons.model.cfg.Cfg;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IApplication;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IAssignement;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IBlock;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICall;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICfg;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.ICondition;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IExpressionComplex;
import fr.lip6.move.evinrude.commons.model.cfg.interfaces.IFunction;
import fr.lip6.move.evinrude.commons.utils.ApplicationStatsExport;
import fr.lip6.move.evinrude.commons.utils.IdGenerator;
import fr.lip6.move.evinrude.commons.utils.StatsExport;
import fr.lip6.move.evinrude.commons.utils.log.EvinrudeLogFormatter;
import fr.lip6.move.evinrude.main.Browser;
import fr.lip6.move.evinrude.parser.modules.BlockModule;
import fr.lip6.move.evinrude.parser.modules.EmptyBlockModule;
import fr.lip6.move.evinrude.parser.modules.FunctionModule;
import fr.lip6.move.evinrude.parser.modules.InstructionModule;
import fr.lip6.move.evinrude.parser.modules.PredecessorModule;
import fr.lip6.move.evinrude.parser.modules.SuccessorModule;
import fr.lip6.move.evinrude.parser.modules.ToIgnoreModule;
import fr.lip6.move.evinrude.parser.modules.VariableDeclarationModule;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/**
 * Classe dédiée à l'analyse syntaxique d'un fichier CFG fourni par GCC.<br>
 * L'analyse permet la reconnaissances des tokens suivants :
 * <ul>
 * <li>Fonctions</li>
 * <li>Blocs</li>
 * <li>Prédécesseurs et Successeurs de blocs</li>
 * <li>Mots-Clé</li>
 * <li>Variable // Appels de fonctions // Operations sur la memoire</li>
 * </ul>
 * La construction aboutie à un objet ICFG ({@link Cfg})
 * @author Jean-Baptiste Voron
 * @author Clement Desmoulins
 */
public class Parser implements IParser {

	/** Le logger */
	private static Logger LOG = Logger.getLogger(Parser.class.getName());

	private int cfgLine;
	private int currentLine = -1;
	private IFunction currentFunction;
	private IBlock currentBlock;
	private ICfg currentCfg;
	private IAssignement currentAssignement;
	private ICondition currentCondition;
	private IExpressionComplex currentExpr;
	private ICall currentCall;

	private IdGenerator idGenerator = new IdGenerator();

	private final List<IModule> modules = new ArrayList<IModule>();

	/**
	 * Constructeur
	 */
	public Parser() {
		modules.add(new EmptyBlockModule(this));
		modules.add(new FunctionModule(this));
		modules.add(new VariableDeclarationModule(this));
		modules.add(new BlockModule(this));
		modules.add(new PredecessorModule(this));
		modules.add(new SuccessorModule(this));
		modules.add(new InstructionModule(this));
		modules.add(new ToIgnoreModule(this));
	}

	/**
	 * Chargement des sources CFG d'une application
	 * @param name nom de l'application
	 * @param files iterateur de fichiers CFG
	 * @return IApplication regroupant toutes les informations contenus dans les CFG
	 */
	public final IApplication parse(String name, Iterator<IFile> files) {
		// Creation de l'application qui va contenir toutes les informations
		IApplication application = new Application(name);
		application.setTimer(StatsExport.TIMER_BEGIN, System.currentTimeMillis());

		LOG.info("Step 1.1 : Source code parsing...");
		EvinrudeLogFormatter.getInstance().increaseIndent();

		// Chaque fichier source produit un CFG qui est associé a l'application
		application.setTimer(StatsExport.TIMER_PARSE_START, System.currentTimeMillis());
		while (files.hasNext()) {
			IFile file = files.next();
			LOG.fine("Parsing file : " + file.getFilename());
			ICfg cfg = extractCFG(application, file);
			application.addCfg(cfg);
		}
		application.setTimer(StatsExport.TIMER_PARSE_STOP, System.currentTimeMillis());

		// Nettoyage des fonctions GNU
		application.removeGnuFunctions();

		// Extraction de statistiques
		dumpParseStats(application);
		EvinrudeLogFormatter.getInstance().decreaseIndent();

		// Sauvegarde de l'application dans un fichier XML
		LOG.info("Step 1.2 : Backup parsing results into an XML file...");
		EvinrudeLogFormatter.getInstance().increaseIndent();

		try {
			FileWriter writer = new FileWriter(application.getFolder().getAbsolutePath() + "/" + application.getName() + ".xml", false);
			application.toXML(writer);
			writer.close();
		} catch (IOException e) {
			LOG.warning("Erreur lors de la sauvegarde XML de l'application...");
			System.err.println("Erreur lors de la sauvegarde XML de l'application " + e.getMessage());
			e.printStackTrace();
		}
		LOG.config("XML Backup is available in: " + application.getName() + ".xml");
		EvinrudeLogFormatter.getInstance().decreaseIndent();

		return application;
	}

	/**
	 * Lancement d'Evinrude sur tous les benchs
	 * @param paramApps Liste des applications du dépot de bench à traiter, si la liste est vide toutes les applications sont traitées.
	 * @param negate si <code>true</code>, paramApps sera traitée comme une liste d'application à ne pas traiter
	 * @return Liste des applications trouvé sur le dépot de bench.
	 */
	public final Iterator<IApplication> parseBenchs(List<String> paramApps, boolean negate) {
		final List<String> appsName = Browser.listBenchApplications();

		if (negate) {
			for (String name : paramApps) {
				if (!appsName.remove(name)) {
					LOG.warning("Application : " + name + " doesn't exist ! Can't remove it.");
				}
			}
		} else {
			for (String name : paramApps) {
				if (!appsName.contains(name)) {
					LOG.warning("Application : " + name + " doesn't exist ! Can't process it.");
				}
			}
			appsName.retainAll(paramApps);
		}
		LOG.info("  Benchs : " + appsName);

		// Création d'un iterator pour éviter de charger toutes les applications à la fois
		return new Iterator<IApplication>() {
			private Iterator<String> itAppsName = appsName.iterator();
			@Override
			public boolean hasNext() {
				return itAppsName.hasNext();
			}
			@Override
			public IApplication next() {
				String app = itAppsName.next();
				LOG.info("    Analyze application [" + app + "]");
				IApplication application = parse(app, Browser.findBenchCfg(app));
				return application;
			}
			@Override
			public void remove() { throw new UnsupportedOperationException(); }
		};
	}

	/**
	 * Extraction de statistiques basiques sur l'application qui va être analysée
	 * @param application L'application considérée
	 */
	private static void dumpParseStats(IApplication application) {
		StringBuffer stats = new StringBuffer();

		stats.append("Application's name:\t\t").append(application.getName());
		stats.append(" (").append(application.getCfgs().size()).append(" files)\n\n");
		stats.append(ApplicationStatsExport.computeStatistics(application)).append("\n");
		stats.append(ApplicationStatsExport.computeSymbolsTable(application));

		try {
			PrintWriter applicationStats = new PrintWriter(new BufferedWriter(new FileWriter(application.getFolder().getAbsolutePath() + "/" + application.getName() + ".parse_stats")));
			applicationStats.print(stats);
			applicationStats.close();
		} catch (IOException e) {
			LOG.warning("Erreur lors de la sauvegarde des statistiques de l'application...");
			System.err.println("Erreur lors de la sauvegarde des statistiques de l'application " + e.getMessage());
			e.printStackTrace();
		}
		LOG.config("\tStats file is available in: " + application.getName() + ".stats");

		try {
			PrintWriter applicationDependencies = new PrintWriter(new BufferedWriter(new FileWriter(application.getFolder().getAbsolutePath() + "/" + application.getName() + "_dependencies.dot")));
			applicationDependencies.print(ApplicationStatsExport.computeDependenciesGraph(application));
			applicationDependencies.close();
		} catch (IOException e) {
			LOG.warning("Erreur lors du calcul des dépendances de l'application...");
			System.err.println("Erreur lors du calcul des dépendances de l'application " + e.getMessage());
			e.printStackTrace();
		}
		LOG.config("\tDependencies graph is available in: " + application.getName() + "_dependencies.dot");
	}

	/**
	 * Analyse d'un fichier
	 * @param application L'application donc le CFG dépend
	 * @param file Le fichier qui doit être analysé
	 * @return Le CFG produit ou <code>null</code> si l'opération s'est mal déroulée
	 */
	public final ICfg extractCFG(IApplication application, IFile file) {
		currentCfg = new Cfg(application, file.getFilename(), idGenerator);

		try {
			String line;
			cfgLine = 0;
			int parsedLineNumber = 0;
			BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
			BufferedWriter writer = new BufferedWriter(new FileWriter(System.getProperty("evinrude.outputdir") + "/" + application.getName() + "/"
					+ file.getFilename() + ".unread"));
			while ((line = reader.readLine()) != null) {
				cfgLine++;
				boolean processed = false;
				for (IModule module : modules) {
					try {
					if (module.match(line)) {
						processed = processed || module.process(line);
					}
					} catch (StackOverflowError e) {
						LOG.severe("A line is too long for our parser : " + line);
					}
				}
				if (!processed) {
					LOG.warning(file.getFilename() + ":" + cfgLine + " {" + line + "}");
					writer.write("[" + String.format("%6d", cfgLine) + "] " + line + "\n");
				} else {
					parsedLineNumber++;
				}
			}
			currentCfg.setLineNb(cfgLine);
			currentCfg.setCoveredLineNb(parsedLineNumber);

			reader.close();
			writer.flush();
			writer.close();
		} catch (IOException e) {
			LOG.warning("Erreur lors de la lecture du fichier : " + file.getFilename());
			e.printStackTrace();
		}

		return currentCfg;
	}

	/**
	 * {@inheritDoc}
	 */
	public final IBlock getCurrentBlock() {
		return currentBlock;
	}

	/**
	 * {@inheritDoc}
	 */
	public final ICfg getCfg() {
		return currentCfg;
	}

	/**
	 * {@inheritDoc}
	 */
	public final IFunction getCurrentFunction() {
		return currentFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void setCurrentBlock(IBlock block) {
		this.currentBlock = block;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void setCurrentFunction(IFunction function) {
		this.currentFunction = function;
	}

	/**
	 * {@inheritDoc}
	 */
	public final int getCurrentLine() {
		return currentLine;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void setCurrentLine(int line) {
		currentLine = line;
	}

	/**
	 * {@inheritDoc}
	 */
	public final IAssignement getCurrentAssignement() {
		return currentAssignement;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void setCurrentAssignement(IAssignement currentAssignement) {
		this.currentAssignement = currentAssignement;
	}

	/**
	 * {@inheritDoc}
	 */
	public final ICondition getCurrentCondition() {
		return currentCondition;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void setCurrentCondition(ICondition currentCondition) {
		this.currentCondition = currentCondition;
	}

	/**
	 * {@inheritDoc}
	 */
	public final IExpressionComplex getCurrentExpr() {
		return currentExpr;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void setCurrentExpr(IExpressionComplex currentExpr) {
		this.currentExpr = currentExpr;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final void setCurrentCall(ICall call) {
		this.currentCall = call;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final ICall getCurrentCall() {
		return currentCall;
	}

	/** {@inheritDoc}
	 */
	@Override
	public final int getCfgLine() {
		return cfgLine;
	}

}
