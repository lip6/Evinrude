package fr.lip6.move.evinrude.commons.utils.log;

import java.io.PrintStream;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Handler permettant d'afficher les log sur la sortie standard.<br>
 * Voici la description d'une ligne de log : <br>
 * [NIVEAU] MESSAGE_DE_LOG (PACKAGE.CLASSE.METHODE)<br>
 * <br>
 * Ce Handler n'utilise pas de Formatter.
 */
public class ConsoleHandler extends Handler {
	/** {@inheritDoc} */
	@Override
	public void close() throws SecurityException { }

	/** {@inheritDoc} */
	@Override
	public void flush() { }

	/** {@inheritDoc} */
	@Override
	public final void publish(LogRecord record) {
		String level = "[" + record.getLevel() + "]"; //$NON-NLS-1$ //$NON-NLS-2$
		String state = "(" + EvinrudeLogFormatter.getInstance().getState() + ")";
		String indent = EvinrudeLogFormatter.getInstance().getIndentString();

		PrintStream stream = System.out;
		if (record.getLevel().intValue() > Level.INFO.intValue()) {
			System.out.flush();
			stream = System.err;
		}
		stream.println(String.format("%-10s%-5s%s%s", level, state, indent, record.getMessage())); //$NON-NLS-1$
	}
}
