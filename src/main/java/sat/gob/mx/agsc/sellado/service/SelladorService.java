/**
 * 
 */
package sat.gob.mx.agsc.sellado.service;

import sat.gob.mx.agsc.exception.sellado.SelladoException;

/**
 * @author jbatis
 *
 */
public interface SelladorService {

	/**
	 * @param cadenaOriginal
	 * @return cadena sellada a patir de la cadena original.
	 * @throws SelladoException
	 */
	public String doSellado(String cadenaOriginal) throws SelladoException;
	
	/**
	 * Ruta para configurar el servicio de la selladora.
	 * @param pathSelladoraService
	 */
	public void setSelladoraService(String pathSelladoraService);
	
	/**
	 * Detalla el tipo de sellado
	 * @param tipoSellado
	 */
	public void setTipoSellado(int tipoSellado);
}
