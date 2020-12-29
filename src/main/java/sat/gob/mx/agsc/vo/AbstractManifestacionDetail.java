package sat.gob.mx.agsc.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author jbatis
 */
public abstract class AbstractManifestacionDetail {

	private String numFolio;
	private String fechaCreacion;
	private String razonSocial;
	private String rfc;
	private String fechaPresentacion;
	private String tipoSolicitud;
	private String region;
	private String impuesto;
	private String ejercicio;
	
	private String cadenaOriginal;
	private String selloDigital;
	
	private List<String> manifestaciones = new ArrayList<String>();
	
	public AbstractManifestacionDetail() {
		super();
	}

	/**
	 * @return the numFolio
	 */
	public String getNumFolio() {
		return numFolio;
	}

	/**
	 * @param numFolio the numFolio to set
	 */
	public void setNumFolio(String numFolio) {
		this.numFolio = numFolio;
	}

	/**
	 * @return the fechaCreacion
	 */
	public String getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @return the razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}

	/**
	 * @param razonSocial the razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	/**
	 * @return the rfc
	 */
	public String getRfc() {
		return rfc;
	}

	/**
	 * @param rfc the rfc to set
	 */
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	/**
	 * @return the fechaPresentacion
	 */
	public String getFechaPresentacion() {
		return fechaPresentacion;
	}

	/**
	 * @param fechaPresentacion the fechaPresentacion to set
	 */
	public void setFechaPresentacion(String fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	/**
	 * @return the tipoSolicitud
	 */
	public String getTipoSolicitud() {
		return tipoSolicitud;
	}

	/**
	 * @param tipoSolicitud the tipoSolicitud to set
	 */
	public void setTipoSolicitud(String tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * @return the impuesto
	 */
	public String getImpuesto() {
		return impuesto;
	}

	/**
	 * @param impuesto the impuesto to set
	 */
	public void setImpuesto(String impuesto) {
		this.impuesto = impuesto;
	}

	/**
	 * @return the ejercicio
	 */
	public String getEjercicio() {
		return ejercicio;
	}

	/**
	 * @param ejercicio the ejercicio to set
	 */
	public void setEjercicio(String ejercicio) {
		this.ejercicio = ejercicio;
	}

	/**
	 * @return the manifestaciones
	 */
	public List<String> getManifestaciones() {
		return manifestaciones;
	}

	/**
	 * @param manifestaciones the manifestaciones to set
	 */
	public void setManifestaciones(List<String> manifestaciones) {
		this.manifestaciones = manifestaciones;
	}

	/**
	 * @return the cadenaOriginal
	 */
	public String getCadenaOriginal() {
		return cadenaOriginal;
	}

	/**
	 * @param cadenaOriginal the cadenaOriginal to set
	 */
	public void setCadenaOriginal(String cadenaOriginal) {
		this.cadenaOriginal = cadenaOriginal;
	}

	/**
	 * @return the selloDigital
	 */
	public String getSelloDigital() {
		return selloDigital;
	}

	/**
	 * @param selloDigital the selloDigital to set
	 */
	public void setSelloDigital(String selloDigital) {
		this.selloDigital = selloDigital;
	}

}
