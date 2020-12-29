/**
 * 
 */
package sat.gob.mx.agsc.exception.acuse;

/**
 * @author BABJ827J
 */
public class ExceptionAcuse extends Exception {

	/**
	 * serialVersionUID = 8869806170674132522L;
	 */
	private static final long serialVersionUID = 8869806170674132522L;

	/**
	 * Constructor sin argumentos
	 */
	public ExceptionAcuse() {
		super();
	}

	/**
	 * @param message
	 */
	public ExceptionAcuse(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ExceptionAcuse(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ExceptionAcuse(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ExceptionAcuse(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
