package fr.lip6.move.evinrude.commons.utils.log;

import java.util.Calendar;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Classe responsable du format des logs
 */
public final class EvinrudeLogFormatter extends Formatter {

	/**
	 * Singleton sur le formatter
	 */
	private static final EvinrudeLogFormatter INSTANCE = new EvinrudeLogFormatter();

	private int indent = 0;
	private String state = "";

	/**
	 * Constructor
	 */
	private EvinrudeLogFormatter() {
	}

	/**
	 * @param level niveau d'indentation
	 */
	public void setIndent(int level) {
		indent = level;
	}

	/**
	 * Incrémente le niveau d'indentation
	 */
	public void increaseIndent() {
		indent++;
	}

	/**
	 * Décrémente le niveau d'indentation
	 */
	public void decreaseIndent() {
		indent = Math.max(0, indent - 1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String format(LogRecord record) {
		Calendar c = Calendar.getInstance();
		String name = ""; //$NON-NLS-1$

		StringBuilder l = new StringBuilder();

		l.append(c.get(Calendar.DAY_OF_MONTH) + "/"); //$NON-NLS-1$
		l.append(c.get(Calendar.MONTH) + "/"); //$NON-NLS-1$
		l.append(c.get(Calendar.YEAR) + " "); //$NON-NLS-1$

		l.append(c.get(Calendar.HOUR) + ":"); //$NON-NLS-1$
		l.append(c.get(Calendar.MINUTE) + ":"); //$NON-NLS-1$
		l.append(c.get(Calendar.SECOND) + " "); //$NON-NLS-1$

		l.append("(" + name + ") "); //$NON-NLS-1$ //$NON-NLS-2$
		l.append(" [" + record.getLevel() + "] "); //$NON-NLS-1$ //$NON-NLS-2$
		l.append(record.getSourceClassName().substring(record.getSourceClassName().lastIndexOf(".") + 1) + " -> "); //$NON-NLS-1$ //$NON-NLS-2$
		l.append(record.getMessage());
		l.append("\n"); //$NON-NLS-1$

		return l.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getHead(Handler h) {
		return "-------- EVINRUDE --------\n\n"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * @return the instance
	 */
	public static EvinrudeLogFormatter getInstance() {
		return INSTANCE;
	}

	/**
	 * @return the indent
	 */
	public String getIndentString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < indent; i++) {
			sb.append("  ");
		}
		return sb.toString();
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
}
