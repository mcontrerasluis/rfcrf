/**
 * 
 */
package sat.gob.mx.agsc;

import java.util.LinkedHashMap;
import java.util.Map;

import sat.gob.mx.agsc.acuse.AbstractAcuse;
import sat.gob.mx.agsc.exception.acuse.ExceptionAcuseConfig;
import sat.gob.mx.agsc.vo.AcuseZonaFronterizaVO;

/**
 * @author BABJ827J
 */
public class GeneraAcuseZonaFronteriza extends AbstractAcuse<AcuseZonaFronterizaVO> {
	
	private AcuseZonaFronterizaVO dataAcusetramite;
	
	/**
	 * constructor sin argumentos
	 */
	public GeneraAcuseZonaFronteriza() {
		super();
	}
	
	/**
	 * Permite llenar y/o configurar el acuse del template en base a la información del vo.
	 * @return {@link Map <{@link String}, {@link Object}> con el mapa de la configuración del template.
	 */
	public Map<String, Object> doConfigParamsAcuse(TIPO_ACUSE tipoSolicitud) throws ExceptionAcuseConfig {
		Map<String, Object> paramsJRXML = new LinkedHashMap<String, Object>();
		paramsJRXML.clear();
		doConfigTitulos(paramsJRXML, tipoSolicitud);
		doConfigLogos(paramsJRXML);
        paramsJRXML.put("numFolio", getDataAcusetramite().getNumFolio());
        paramsJRXML.put("fechaCreacion", getDataAcusetramite().getFechaCreacion());
        paramsJRXML.put("razonSocial", getDataAcusetramite().getRazonSocial());
        paramsJRXML.put("rfc", getDataAcusetramite().getRfc());
        paramsJRXML.put("fechaPresentacion", getDataAcusetramite().getFechaPresentacion());
        paramsJRXML.put("tipoSolicitud", getDataAcusetramite().getTipoSolicitud());
        paramsJRXML.put("region", getDataAcusetramite().getRegion());
        paramsJRXML.put("impuesto", getDataAcusetramite().getImpuesto());
        paramsJRXML.put("ejercicio", getDataAcusetramite().getEjercicio());
        paramsJRXML.put("manifestaciones", getDataAcusetramite().getManifestaciones());
        paramsJRXML.put("cadenaOriginal", getDataAcusetramite().getCadenaOriginal());
        paramsJRXML.put("selloDigital", getDataAcusetramite().getSelloDigital());
        return paramsJRXML;
	}
	
	/**
	 * Información del template para llenar el pdf
	 * @return dataAcusetramite
	 */
	private AcuseZonaFronterizaVO getDataAcusetramite() {
		return dataAcusetramite;
	}
	
	/**
	 * @param dataAcusetramite información del vo asociado al template
	 */
	public void setDataAcuse(AcuseZonaFronterizaVO dataAcusetramite) {
		this.dataAcusetramite = dataAcusetramite;
	}

	/**
	 * @param nombreTemplate nombre del template, el cual esta asociado al 
	 * acuse de la zona fronteriza para generar el pdf.
	 */
	public void setNombreTemplate(String nombreTemplate) {
		this.nombreTemplate = nombreTemplate;		
	}
}
