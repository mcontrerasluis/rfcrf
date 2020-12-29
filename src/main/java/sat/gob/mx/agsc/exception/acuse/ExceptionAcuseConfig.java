/**
 * 
 */
package sat.gob.mx.agsc.exception.acuse;

/**
 * @author jbatis
 */
public class ExceptionAcuseConfig extends Exception {

	/**
	 * serialVersionUID = -6693047516590072352L;
	 */
	private static final long serialVersionUID = -6693047516590072352L;

	/**
	 * Constructor sin argumentos
	 */
	public ExceptionAcuseConfig() {
		super();
	}

	/**
	 * @param message
	 */
	public ExceptionAcuseConfig(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ExceptionAcuseConfig(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ExceptionAcuseConfig(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ExceptionAcuseConfig(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
