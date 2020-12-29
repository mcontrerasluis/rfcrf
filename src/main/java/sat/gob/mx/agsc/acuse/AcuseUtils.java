/**
 * 
 */
package sat.gob.mx.agsc.acuse;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import sat.gob.mx.agsc.exception.acuse.ExceptionAcuse;


/**
 * @author jbatis
 */
public final class AcuseUtils {

	/**
	 * Nombre del properties
	 */
	private static final String FILE_ACUSE_CONFIG_NAME = "acuse-config";
	private static final Logger log = Logger.getLogger("AcuseUtils");
	
	/**
	 * Constructor sin argumentos
	 */
	private AcuseUtils() {
		super();
	}

	/***
	 * Permite realizar la propiedad del properties
	 * @param propertie
	 * @return detalle del properties
	 * @throws ExceptionAcuse
	 */
	public static String getPropertyBy(String propertie) throws ExceptionAcuse {
        try {
            return ResourceBundle.getBundle(FILE_ACUSE_CONFIG_NAME).getString(propertie);
        } catch (MissingResourceException e) {
        	log.error(new StringBuilder("No se encontró el archivo acuse-confi.properties; causa: ").append(e.getMessage()));
            throw new ExceptionAcuse("No se encontró el archivo ", e);
        }
	}
	
}
