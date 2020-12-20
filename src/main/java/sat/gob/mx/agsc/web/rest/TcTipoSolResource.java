package sat.gob.mx.agsc.web.rest;

import sat.gob.mx.agsc.service.TcTipoSolService;
import sat.gob.mx.agsc.web.rest.errors.BadRequestAlertException;
import sat.gob.mx.agsc.service.dto.TcTipoSolDTO;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link sat.gob.mx.agsc.domain.TcTipoSol}.
 */
@RestController
@RequestMapping("/api")
public class TcTipoSolResource {

    private final Logger log = LoggerFactory.getLogger(TcTipoSolResource.class);

    private static final String ENTITY_NAME = "tcTipoSol";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TcTipoSolService tcTipoSolService;

    public TcTipoSolResource(TcTipoSolService tcTipoSolService) {
        this.tcTipoSolService = tcTipoSolService;
    }

    /**
     * {@code POST  /tc-tipo-sols} : Create a new tcTipoSol.
     *
     * @param tcTipoSolDTO the tcTipoSolDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tcTipoSolDTO, or with status {@code 400 (Bad Request)} if the tcTipoSol has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tc-tipo-sols")
    public ResponseEntity<TcTipoSolDTO> createTcTipoSol(@Valid @RequestBody TcTipoSolDTO tcTipoSolDTO) throws URISyntaxException {
        log.debug("REST request to save TcTipoSol : {}", tcTipoSolDTO);
        if (tcTipoSolDTO.getId() != null) {
            throw new BadRequestAlertException("A new tcTipoSol cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TcTipoSolDTO result = tcTipoSolService.save(tcTipoSolDTO);
        return ResponseEntity.created(new URI("/api/tc-tipo-sols/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tc-tipo-sols} : Updates an existing tcTipoSol.
     *
     * @param tcTipoSolDTO the tcTipoSolDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tcTipoSolDTO,
     * or with status {@code 400 (Bad Request)} if the tcTipoSolDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tcTipoSolDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tc-tipo-sols")
    public ResponseEntity<TcTipoSolDTO> updateTcTipoSol(@Valid @RequestBody TcTipoSolDTO tcTipoSolDTO) throws URISyntaxException {
        log.debug("REST request to update TcTipoSol : {}", tcTipoSolDTO);
        if (tcTipoSolDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TcTipoSolDTO result = tcTipoSolService.save(tcTipoSolDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tcTipoSolDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tc-tipo-sols} : get all the tcTipoSols.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tcTipoSols in body.
     */
    @GetMapping("/tc-tipo-sols")
    public ResponseEntity<List<TcTipoSolDTO>> getAllTcTipoSols(Pageable pageable) {
        log.debug("REST request to get a page of TcTipoSols");
        Page<TcTipoSolDTO> page = tcTipoSolService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tc-tipo-sols/:id} : get the "id" tcTipoSol.
     *
     * @param id the id of the tcTipoSolDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tcTipoSolDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tc-tipo-sols/{id}")
    public ResponseEntity<TcTipoSolDTO> getTcTipoSol(@PathVariable Long id) {
        log.debug("REST request to get TcTipoSol : {}", id);
        Optional<TcTipoSolDTO> tcTipoSolDTO = tcTipoSolService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tcTipoSolDTO);
    }

    /**
     * {@code DELETE  /tc-tipo-sols/:id} : delete the "id" tcTipoSol.
     *
     * @param id the id of the tcTipoSolDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tc-tipo-sols/{id}")
    public ResponseEntity<Void> deleteTcTipoSol(@PathVariable Long id) {
        log.debug("REST request to delete TcTipoSol : {}", id);
        tcTipoSolService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
