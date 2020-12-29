/**
 * 
 */
package sat.gob.mx.agsc.vo;

/**
 * @author jbatis
 */
public class AcuseZonaFronterizaVO extends AbstractManifestacionDetail implements AcuseFormatDetail {
	
	/**
	 * Constructor sin argumentos
	 */
	public AcuseZonaFronterizaVO() {
		super();
	}
	
	public String getCadenaOriginal() {
		return new StringBuilder(getRfc()).append("|")
				.append(getFechaCreacion()).append("|")
				.append(getNumFolio()).append("|")
				.append(getTipoSolicitud()).append("|")
				
				.toString();
	}
}
