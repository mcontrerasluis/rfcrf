/**
 * 
 */
package mx.gob.sat.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import sat.gob.mx.agsc.GeneraAcuseZonaFronteriza;
import sat.gob.mx.agsc.acuse.AbstractAcuse;
import sat.gob.mx.agsc.acuse.AcuseService.TIPO_ACUSE;
import sat.gob.mx.agsc.exception.acuse.ExceptionAcuseConfig;
import sat.gob.mx.agsc.vo.AcuseZonaFronterizaVO;

/**
 * @author BABJ827J
 */
public class Main {

	private static final Logger log = Logger.getLogger(Main.class);
	
	private AcuseZonaFronterizaVO acuseVo;
	
	/**
	 * Constructor sin argumentos
	 */
	public Main() {
		super();
	}

	private void doConfigAcuse() {
		this.acuseVo = new AcuseZonaFronterizaVO();
		this.acuseVo.setNumFolio("ERF202122888310");
		this.acuseVo.setFechaCreacion("11 de Diciembre del 2020");
		this.acuseVo.setRfc("OPO140101E76");
		this.acuseVo.setRazonSocial("OPERACION DE PADRONES ORIGINAL SA DE CV");
		this.acuseVo.setFechaPresentacion("09 de enero de 2021");
		this.acuseVo.setTipoSolicitud("Incorporación al padrón de beneficiarios");
		this.acuseVo.setRegion("Norte");
		this.acuseVo.setImpuesto("ISR");
		this.acuseVo.setEjercicio("2021");
		this.acuseVo.setCadenaOriginal("OPO140101E76|20210109|ERF202122888310|S01|E03");
		this.acuseVo.setSelloDigital("yBv5DUh0/IR4sDxqVnOT8X94dODuWtFg8Um3UY4jhmG6J9UUZvIrdPlmH/qbzBWwFkt0DyJlESJglvcZcaJsty72ouhHOL5kIcBZQkG81xfk076J4RM8YRiLJJ0Q9MJVxbW5wE9EEwSCBzMLHn6mEauHkwM0Pk1eLpIXRS5aUvA=");
		
		List<String> manifestaciones =  new ArrayList<String>();
		manifestaciones.add("No he realizado operaciones con contribuyentes que hayan sido publicados en los listados a que se refiere el artículo 69-B, cuarto párrafo del CFF.");
        manifestaciones.add("No he interpuesto algún medio de defensa en contra de la resolución a través de la cual se concluyó que no se acreditó la materialidad de las operaciones y/o en contra de la determinación de créditos fiscales del ISR e IVA que deriven de la aplicación del Decreto.");
        manifestaciones.add("Los socios o accionistas registrados ante el SAT; de la empresa que suscribe, no se encuentran en los supuestos del artículo 69-B del CFF.");        
        this.acuseVo.setManifestaciones(manifestaciones);
		
	}

	private void runTestPDF(String nombreTemplate) {
		doConfigAcuse();
		try {
			AbstractAcuse<AcuseZonaFronterizaVO> service = new GeneraAcuseZonaFronteriza();
			service.setNombreTemplate(nombreTemplate);
			service.setDataAcuse(this.acuseVo);
			// se aguantan los 3 tipos de acuses.
			service.doGeneraAcuseStreamPDF(TIPO_ACUSE.SOLICITUD);
			service.doGeneraAcusePDF(TIPO_ACUSE.SOLICITUD);
			log.info("Informacion del pdf generado...");
		} catch (ExceptionAcuseConfig e) {
			log.info(e.getMessage());
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Main main = new Main();
		main.runTestPDF("AcuseZonaFronteriza.jrxml");
		
	}
	
}
