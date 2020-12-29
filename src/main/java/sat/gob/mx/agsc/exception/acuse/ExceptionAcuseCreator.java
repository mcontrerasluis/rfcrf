/**
 * 
 */
package sat.gob.mx.agsc.exception.acuse;

/**
 * @author jbatis
 */
public class ExceptionAcuseCreator extends Exception {

	/**
	 * serialVersionUID = 3119050855760102766L;
	 */
	private static final long serialVersionUID = 3119050855760102766L;

	/**
	 * Constructor sin argumentos
	 */
	public ExceptionAcuseCreator() {
		super();
	}

	/**
	 * @param message
	 */
	public ExceptionAcuseCreator(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ExceptionAcuseCreator(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ExceptionAcuseCreator(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ExceptionAcuseCreator(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
