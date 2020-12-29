/**
 * 
 */
package sat.gob.mx.agsc.sellado;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;

import sat.gob.mx.agsc.exception.sellado.SelladoException;
import sat.gob.mx.agsc.sellado.service.SelladorService;

/**
 * @author jbatis
 *
 */
public class SelladorImp implements SelladorService {
	
	/**
	 * Logger
	 */
	private final Logger log = Logger.getLogger(this.getClass());

	/**
	 * Ruta de la selladora service
	 */
	private String pathSelladoraService;
	
	/**
	 * Detalla el tipo de sellado
	 */
	private int tipoSellado;
	
	/**
	 * Constructor sin argumentos
	 */
	public SelladorImp() {
		super();
	}

	/**
	 * Ruta del servicio de la selladora
	 */
	public void setSelladoraService(String pathSelladoraService) {
		this.pathSelladoraService = pathSelladoraService;
	}
	
	/**
	 * @return Ruta del servicio de la selladora
	 */
	public String getPathSelladoraService() {
		return pathSelladoraService;
	}
	
	/**
	 * Detalla el tipo de sellado
	 */
	public void setTipoSellado(int tipoSellado) {
		this.tipoSellado = tipoSellado;
	}
	
	/**
	 * Detalla el tipo de sellado
	 * @return
	 */
	public int getTipoSellado() {
		return tipoSellado;
	}
	
	public String doSellado(String cadenaOriginal) throws SelladoException {
		Socket conexionSelladora = null;
		try {
			conexionSelladora = new Socket(getPathSelladoraService(), getTipoSellado());
			OutputStream buffer = conexionSelladora.getOutputStream();
		 	buffer.write(cadenaOriginal.getBytes());
		 	byte[] respuesta = new byte[1024*32];
		 	int leidos = 0;
		 	InputStream input = conexionSelladora.getInputStream();
		 	int offset = 0;
		 	leidos = input.read(respuesta, offset, respuesta.length-offset);
		 	if ( leidos > 0 ) {
		 		offset += leidos;
		 	}
		 	while(input.available() != 0){
		 		leidos = input.read(respuesta,offset,respuesta.length-offset);
		 		offset += leidos;
		 	}
		 	log.debug( new StringBuilder( "Se recibió respuesta de la selladora: ").append(" tamaño: ").append(offset));
		 	return new String(respuesta, 0, offset);
		 } catch (IOException e) {
			 log.warn( new StringBuilder("Error al tratar de realizar el sellado, causa: ").append(e.getMessage()));
			 throw new SelladoException("Error al tratar de realizar el sellado.", e.getCause());
		 }
	}

}
