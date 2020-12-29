/**
 * 
 */
package sat.gob.mx.agsc.acuse;

import static sat.gob.mx.agsc.acuse.AcuseUtils.getPropertyBy;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import sat.gob.mx.agsc.exception.acuse.ExceptionAcuse;
import sat.gob.mx.agsc.exception.acuse.ExceptionAcuseConfig;

/**
 * @author jbatis
 */
public abstract class AbstractAcuse<T> implements AcuseService {

	private static final String PROPERTY_PATH_ACUSES = "PATH_ACUSES";
	private static final String PROPERTY_PATH_IMG_SHCP = "PATH_IMG_SHCP";
	private static final String PROPERTY_PATH_IMG_SAT = "PATH_IMG_SAT";
	private static final String PROPERTY_PATH_IMG_MEXICO = "PATH_IMG_MEXICO";
	private static final Logger log = Logger.getLogger("AcuseUtils");
	
	protected String nombreTemplate = StringUtils.EMPTY;
	
	/**
	 * Constructor sin argumentos
	 */
	public AbstractAcuse() {
		super();
	}
	
	/**
	 * @param dataAcusetramite
	 */
	public abstract void setDataAcuse(T dataAcusetramite);
	
	/**
	 * Permite configurar los titulos del acuse.
	 * @param paramsJRXML
	 * @param tipoAcuse
	 * @throws ExceptionAcuseConfig
	 */
	protected void doConfigTitulos(Map<String, Object> paramsJRXML, TIPO_ACUSE tipoAcuse) throws ExceptionAcuseConfig  {
		try {
			final String tipoAcusePresentar = tipoAcuse.toString().toLowerCase();
			paramsJRXML.put("titlePrincipal", getPropertyBy(String.format("acuse.%s.titlePrincipal", tipoAcusePresentar)));
	        paramsJRXML.put("detailAcuse", getPropertyBy(String.format("acuse.%s.detailAcuse", tipoAcusePresentar)));
	        paramsJRXML.put("descriptionAcuse", getPropertyBy(String.format("acuse.%s.descriptionAcuse", tipoAcusePresentar)));
	        paramsJRXML.put("informationAcuse", getPropertyBy(String.format("acuse.%s.informationAcuse", tipoAcusePresentar)));
		} catch (ExceptionAcuse e) {
			log.warn( new StringBuilder("Error al configurar los titulos del acuse, causa: ").append(e.getMessage()));
			log.warn( new StringBuilder("Checa de que exista la propiedad en el archivo de configuracion, para: ").append(tipoAcuse));
			throw new ExceptionAcuseConfig("Error al configurar los titulos del acuse.");
		}
	}
	
	/**
	 * Permite configurar los logos del template
	 * @param paramsJRXML
	 * @throws ExceptionAcuseConfig
	 */
	protected void doConfigLogos(Map<String, Object> paramsJRXML) throws ExceptionAcuseConfig {
        try {
	        paramsJRXML.put("logoSHCP", ImageIO.read(new File(getPropertyBy(PROPERTY_PATH_IMG_SHCP))));
            paramsJRXML.put("logoSAT", ImageIO.read(new File(getPropertyBy(PROPERTY_PATH_IMG_SAT))));
            paramsJRXML.put("logoMexico", ImageIO.read(new File(getPropertyBy(PROPERTY_PATH_IMG_MEXICO))));
		} catch (ExceptionAcuse e) {
			log.warn( new StringBuilder("Error al configurar la ruta de los logos del acuse, causa: ").append(e.getMessage()));
			throw new ExceptionAcuseConfig("Error al configurar la ruta de los logos del acuse.");
		} catch (IOException e) {
			log.warn( new StringBuilder("Error al configurar los logos del acuse, causa: ").append(e.getMessage()));
			throw new ExceptionAcuseConfig("Error al configurar el reader del acuse.");
		}
	}
	
	/**
	 * Permite generar el acuse del pdf
	 * @param TIPO_ACUSE tipo de acuse para implementar
	 * @return {@link ByteArrayOutputStream}
	 */
	public ByteArrayOutputStream doGeneraAcuseStreamPDF(TIPO_ACUSE tipoSolicitud) throws ExceptionAcuseConfig {
		ByteArrayOutputStream acuse = new ByteArrayOutputStream();
		try {
			final String pathFileTemplate = new StringBuilder(getPropertyBy(PROPERTY_PATH_ACUSES)).append(getNombreTemplate()).toString();
			Map<String, Object> paramsJRXML = doConfigParamsAcuse(tipoSolicitud);
			JasperReport jasperReport = JasperCompileManager.compileReport(pathFileTemplate);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramsJRXML, new JREmptyDataSource());
            JasperExportManager.exportReportToPdfStream(jasperPrint, acuse);
		} catch (ExceptionAcuse e) {
			log.warn( new StringBuffer("No fue posible realizar la lectura del template, causa: ").append(e.getMessage()).toString());
			throw new ExceptionAcuseConfig("Error realizar la lectura del template.");
		} catch (ExceptionAcuseConfig e) {
			log.warn( new StringBuffer("No fue posible configurar los parametros del acuse, causa: ").append(e.getMessage()).toString());
			throw new ExceptionAcuseConfig("Error al configurar los parametros del acuse.");
		} catch (JRException e) {
			log.warn( new StringBuffer("No fue posible generar el acuse, causa: ").append(e.getMessage()).toString());
			throw new ExceptionAcuseConfig("Error al generar el acuse.");
        }
		return acuse;
	}
	
	/**
	 * Permite generar el pdf del acuse
	 * @param tipoSolicitud
	 * @throws ExceptionAcuseConfig
	 */
	public void doGeneraAcusePDF(TIPO_ACUSE tipoSolicitud) throws ExceptionAcuseConfig {
		try {
			final String pathFileTemplate = new StringBuilder(getPropertyBy(PROPERTY_PATH_ACUSES)).append(getNombreTemplate()).toString();
			Map<String, Object> paramsJRXML = doConfigParamsAcuse(tipoSolicitud);
			JasperReport jasperReport = JasperCompileManager.compileReport(pathFileTemplate);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramsJRXML, new JREmptyDataSource());
            String destFileName = "reportExample.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, destFileName);
		} catch (ExceptionAcuse e) {
			log.warn( new StringBuffer("No fue posible realizar la lectura del template, causa: ").append(e.getMessage()).toString());
			throw new ExceptionAcuseConfig("Error realizar la lectura del template.");
		} catch (ExceptionAcuseConfig e) {
			log.warn( new StringBuffer("No fue posible configurar los parametros del acuse, causa: ").append(e.getMessage()).toString());
			throw new ExceptionAcuseConfig("Error al configurar los parametros del acuse.");
		} catch (JRException e) {
			log.warn( new StringBuffer("No fue posible generar el acuse, causa: ").append(e.getMessage()).toString());
			throw new ExceptionAcuseConfig("Error al generar el acuse.");
        }
	}
	
	private String getNombreTemplate() {
		return nombreTemplate;
	}
}
