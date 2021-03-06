package sat.gob.mx.agsc.web.rest;

import sat.gob.mx.agsc.service.TdRegFrontService;
import sat.gob.mx.agsc.web.rest.errors.BadRequestAlertException;
import sat.gob.mx.agsc.service.dto.TdRegFrontDTO;
import sat.gob.mx.agsc.service.dto.TcManifesDTO;

import sat.gob.mx.agsc.service.UserService;
import sat.gob.mx.agsc.service.TcTipoSolService;
import sat.gob.mx.agsc.service.TcTipoImpService;
import sat.gob.mx.agsc.service.dto.UserDTO;
import sat.gob.mx.agsc.service.dto.TcTipoImpDTO;
import sat.gob.mx.agsc.service.dto.TcTipoSolDTO;
import java.time.format.DateTimeFormatter;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Locale;
import java.util.stream.StreamSupport;
import java.time.Instant;
import java.time.format.FormatStyle;
import java.time.ZoneId;

import java.util.ArrayList;
import java.util.Base64;

import sat.gob.mx.agsc.GeneraAcuseZonaFronteriza;
import sat.gob.mx.agsc.acuse.AbstractAcuse;
import sat.gob.mx.agsc.acuse.AcuseService.TIPO_ACUSE;
import sat.gob.mx.agsc.exception.acuse.ExceptionAcuseConfig;
import sat.gob.mx.agsc.vo.AcuseZonaFronterizaVO;

import org.springframework.http.MediaType;
import java.security.Principal;


/**
 * REST controller for managing {@link sat.gob.mx.agsc.domain.TdRegFront}.
 */
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TdRegFrontResource {

    private final Logger log = LoggerFactory.getLogger(TdRegFrontResource.class);

    private static final String ENTITY_NAME = "tdRegFront";
    
	
	private AcuseZonaFronterizaVO acuseVo;    

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TdRegFrontService tdRegFrontService;

    private final UserService userService;

    private final TcTipoSolService tcTipoSolService;

    private final TcTipoImpService tcTipoImpService;

    public TdRegFrontResource(TdRegFrontService tdRegFrontService, UserService userService, TcTipoSolService tcTipoSolService, TcTipoImpService tcTipoImpService) {
        this.tdRegFrontService = tdRegFrontService;
        this.userService = userService;
        this.tcTipoSolService= tcTipoSolService;
        this.tcTipoImpService = tcTipoImpService;
    }

    /**
     * {@code POST  /td-reg-fronts} : Create a new tdRegFront.
     *
     * @param tdRegFrontDTO the tdRegFrontDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tdRegFrontDTO, or with status {@code 400 (Bad Request)} if the tdRegFront has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/td-reg-fronts")
    public ResponseEntity<TdRegFrontDTO> createTdRegFront(@Valid @RequestBody TdRegFrontDTO tdRegFrontDTO,Principal principal) throws URISyntaxException {
        log.debug("REST request to save TdRegFront : {}", tdRegFrontDTO);
        
        UserDTO usuario = userService.getUserFromAuthentication((AbstractAuthenticationToken) principal);
        tdRegFrontDTO.setRfc(usuario.getRfc());
        tdRegFrontDTO.setUsuario(usuario.getFirstName());
        tdRegFrontDTO.setFecha(Instant.now());

        TcTipoSolDTO tcTipoSolDTO = tcTipoSolService.findOne(tdRegFrontDTO.getTipoSolicitudId()).get();
        

        if (tdRegFrontDTO.getId() != null) {
            throw new BadRequestAlertException("A new tdRegFront cannot already have an ID", ENTITY_NAME, "idexists");
        } 
        TdRegFrontDTO result = tdRegFrontService.save(tdRegFrontDTO);
        result.setFolio("ERF"+"2021"+ String.format("%06d", result.getId()));        

        TdRegFrontDTO resultFinal = tdRegFrontService.save(result);
        
        return ResponseEntity.created(new URI("/api/td-reg-fronts/" + resultFinal.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, resultFinal.getId().toString()))
            .body(result);
    }

    @PostMapping(
    value = "/getpdf",
    produces = MediaType.TEXT_PLAIN_VALUE)
    public @ResponseBody String getPDFP(@Valid @RequestBody TdRegFrontDTO tdRegFrontDTO, Principal principal) throws URISyntaxException {
        byte[] contents = null;

        TcTipoSolDTO tcTipoSolDTO = tcTipoSolService.findOne(tdRegFrontDTO.getTipoSolicitudId()).get();
        TcTipoImpDTO tcTipoImpDTO = tcTipoImpService.findOne(tdRegFrontDTO.getTipoImpuestoId()).get();
        UserDTO usuario = userService.getUserFromAuthentication((AbstractAuthenticationToken) principal);
        

        Instant instant = Instant.now();

        // format instant to string
        String dtStr = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG).withLocale(new Locale("es", "ES"))
        .withZone(ZoneId.systemDefault())
        .format(instant);

        System.out.println("fecha" + dtStr);
        this.acuseVo = new AcuseZonaFronterizaVO();
		this.acuseVo.setNumFolio("");
		this.acuseVo.setFechaCreacion(dtStr);
		this.acuseVo.setRfc(usuario.getRfc());
		this.acuseVo.setRazonSocial(usuario.getFirstName());
		this.acuseVo.setFechaPresentacion(dtStr);
		this.acuseVo.setTipoSolicitud(tcTipoSolDTO.getDescripcion());
		this.acuseVo.setRegion(tdRegFrontDTO.getRegion());
		this.acuseVo.setImpuesto(tcTipoImpDTO.getDescripcion());
		this.acuseVo.setEjercicio("2021");
		this.acuseVo.setCadenaOriginal("");
		this.acuseVo.setSelloDigital("");
		
		List<String> manifestaciones =  new ArrayList<String>();
        for (TcManifesDTO i : tdRegFrontDTO.getManifestacions()) {
            manifestaciones.add(i.getDescripcion());
        }		
        this.acuseVo.setManifestaciones(manifestaciones);


		try {
            log.debug("REST request to generate Acuse : {}", tdRegFrontDTO);
			AbstractAcuse<AcuseZonaFronterizaVO> service = new GeneraAcuseZonaFronteriza();
			service.setNombreTemplate("AcuseZonaFronteriza.jrxml");
			service.setDataAcuse(this.acuseVo);
			// se aguantan los 3 tipos de acuses.
            service.doGeneraAcusePDF(TIPO_ACUSE.SOLICITUD);
			contents = service.doGeneraAcuseStreamPDF(TIPO_ACUSE.SOLICITUD).toByteArray();
            
            log.info("Informacion del pdf generado...");
            
			
		} catch (ExceptionAcuseConfig e) {
			log.info(e.getMessage());
		}
        HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            // Here you have to set the actual filename of your pdf
            String filename = "output.pdf";
            
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return Base64.getEncoder().encodeToString(contents);			
        
    }

    @GetMapping(
    value = "/getpdfFinal/{id}",
    produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]>  getPDFPFinal (@PathVariable Long id, Principal principal) throws URISyntaxException {
        byte[] contents = null;

        TdRegFrontDTO tdRegFrontDTO = tdRegFrontService.findOne(id).get();

        TcTipoSolDTO tcTipoSolDTO = tcTipoSolService.findOne(tdRegFrontDTO.getTipoSolicitudId()).get();
        TcTipoImpDTO tcTipoImpDTO = tcTipoImpService.findOne(tdRegFrontDTO.getTipoImpuestoId()).get();
        UserDTO usuario = userService.getUserFromAuthentication((AbstractAuthenticationToken) principal);
        

        Instant instant = Instant.now();

        // format instant to string
        String dtStr = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG).withLocale(new Locale("es", "ES"))
        .withZone(ZoneId.systemDefault())
        .format(tdRegFrontDTO.getFecha());
        
        this.acuseVo = new AcuseZonaFronterizaVO();
		this.acuseVo.setNumFolio(tdRegFrontDTO.getFolio());
		this.acuseVo.setFechaCreacion(dtStr);
		this.acuseVo.setRfc(usuario.getRfc());
		this.acuseVo.setRazonSocial(usuario.getFirstName());
		this.acuseVo.setFechaPresentacion(dtStr);
		this.acuseVo.setTipoSolicitud(tcTipoSolDTO.getDescripcion());
		this.acuseVo.setRegion(tdRegFrontDTO.getRegion());
		this.acuseVo.setImpuesto(tcTipoImpDTO.getDescripcion());
		this.acuseVo.setEjercicio("2021");
		this.acuseVo.setCadenaOriginal(tdRegFrontDTO.getCadena());
		this.acuseVo.setSelloDigital(tdRegFrontDTO.getSello());
		
		List<String> manifestaciones =  new ArrayList<String>();
        for (TcManifesDTO i : tdRegFrontDTO.getManifestacions()) {
            manifestaciones.add(i.getDescripcion());
        }		
        this.acuseVo.setManifestaciones(manifestaciones);


		try {
            log.debug("REST request to generate Acuse : {}", tdRegFrontDTO);
			AbstractAcuse<AcuseZonaFronterizaVO> service = new GeneraAcuseZonaFronteriza();
			service.setNombreTemplate("AcuseZonaFronteriza.jrxml");
			service.setDataAcuse(this.acuseVo);
			// se aguantan los 3 tipos de acuses.
            service.doGeneraAcusePDF(TIPO_ACUSE.SOLICITUD);
			contents = service.doGeneraAcuseStreamPDF(TIPO_ACUSE.SOLICITUD).toByteArray();
            
            log.info("Informacion del pdf generado...");
            
			
		} catch (ExceptionAcuseConfig e) {
			log.info(e.getMessage());
		}
        HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            // Here you have to set the actual filename of your pdf
            String filename = "output.pdf";
            
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;
        
    }


    @GetMapping("/getpdf")
    public ResponseEntity<byte[]> getPDF() {
        byte[] contents = null;
        doConfigAcuse();
        
		try {
			AbstractAcuse<AcuseZonaFronterizaVO> service = new GeneraAcuseZonaFronteriza();
			service.setNombreTemplate("AcuseZonaFronteriza.jrxml");
			service.setDataAcuse(this.acuseVo);
			// se aguantan los 3 tipos de acuses.
            service.doGeneraAcusePDF(TIPO_ACUSE.SOLICITUD);
			contents = service.doGeneraAcuseStreamPDF(TIPO_ACUSE.SOLICITUD).toByteArray();
            
            log.info("Informacion del pdf generado...");
            
			
		} catch (ExceptionAcuseConfig e) {
			log.info(e.getMessage());
		}
        HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            // Here you have to set the actual filename of your pdf
            String filename = "output.pdf";
            
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;			
        
    }

    /**
     * {@code PUT  /td-reg-fronts} : Updates an existing tdRegFront.
     *
     * @param tdRegFrontDTO the tdRegFrontDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tdRegFrontDTO,
     * or with status {@code 400 (Bad Request)} if the tdRegFrontDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tdRegFrontDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/td-reg-fronts")
    public ResponseEntity<TdRegFrontDTO> updateTdRegFront(@Valid @RequestBody TdRegFrontDTO tdRegFrontDTO) throws URISyntaxException {
        log.debug("REST request to update TdRegFront : {}", tdRegFrontDTO);
        if (tdRegFrontDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TdRegFrontDTO result = tdRegFrontService.save(tdRegFrontDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tdRegFrontDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /td-reg-fronts} : get all the tdRegFronts.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tdRegFronts in body.
     */
    @GetMapping("/td-reg-fronts")
    public ResponseEntity<List<TdRegFrontDTO>> getAllTdRegFronts(Pageable pageable, @RequestParam(required = false) String filter, @RequestParam(required = false, defaultValue = "false") boolean eagerload, Principal principal) {
        if ("general-is-null".equals(filter)) {
            log.debug("REST request to get all TdRegFronts where general is null");
            return new ResponseEntity<>(tdRegFrontService.findAllWhereGeneralIsNull(),
                    HttpStatus.OK);
        }
        UserDTO usuario = userService.getUserFromAuthentication((AbstractAuthenticationToken) principal);        
        log.debug("REST request to get a page of TdRegFronts");
        Page<TdRegFrontDTO> page;
        if (eagerload) {
            page = tdRegFrontService.findAllWithEagerRelationshipsByRfc(pageable, usuario.getRfc());
        } else {
            page = tdRegFrontService.findAllWithEagerRelationshipsByRfc(pageable, usuario.getRfc());
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /td-reg-fronts/:id} : get the "id" tdRegFront.
     *
     * @param id the id of the tdRegFrontDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tdRegFrontDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/td-reg-fronts/{id}")
    public ResponseEntity<TdRegFrontDTO> getTdRegFront(@PathVariable Long id) {
        log.debug("REST request to get TdRegFront : {}", id);
        Optional<TdRegFrontDTO> tdRegFrontDTO = tdRegFrontService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tdRegFrontDTO);
    }

    /**
     * {@code DELETE  /td-reg-fronts/:id} : delete the "id" tdRegFront.
     *
     * @param id the id of the tdRegFrontDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/td-reg-fronts/{id}")
    public ResponseEntity<Void> deleteTdRegFront(@PathVariable Long id) {
        log.debug("REST request to delete TdRegFront : {}", id);
        tdRegFrontService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
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
}
