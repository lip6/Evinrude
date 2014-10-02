package fr.lip6.move.evinrude.commons.exceptions;

/**
 * Exception dédiée à Evinrude<br>
 * Un message doit être associé à l'exception.
 *
 * @author Jean-Baptiste Voron
 */
public class EvinrudeException extends RuntimeException {

	/** Pour la compatibilité */
	private static final long serialVersionUID = 1L;

	/** Le message associé à l'exception */
	private String message;

	/**
	 * Constructeur
	 * @param message Le message associé à 'exception
	 */
	public EvinrudeException(String message) {
		this.message = message;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String getMessage() {
		return this.message;
	}

}
