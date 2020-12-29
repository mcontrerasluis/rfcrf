/**
 * 
 */
package sat.gob.mx.agsc.acuse;

import java.util.Map;

import sat.gob.mx.agsc.exception.acuse.ExceptionAcuseConfig;

/**
 * @author BABJ827J
 */
public interface AcuseService {

	enum TIPO_ACUSE {
		SOLICITUD, RESOLUCION, BAJA
	}
	
	public void setNombreTemplate(String nombreTemplate);
	
	public Map<String, Object> doConfigParamsAcuse(TIPO_ACUSE tipoSolicitud) throws ExceptionAcuseConfig;
	
}
