package sat.gob.mx.agsc.web.rest;

import sat.gob.mx.agsc.service.TcTipoImpService;
import sat.gob.mx.agsc.web.rest.errors.BadRequestAlertException;
import sat.gob.mx.agsc.service.dto.TcTipoImpDTO;

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
 * REST controller for managing {@link sat.gob.mx.agsc.domain.TcTipoImp}.
 */
@RestController
@RequestMapping("/api")
public class TcTipoImpResource {

    private final Logger log = LoggerFactory.getLogger(TcTipoImpResource.class);

    private static final String ENTITY_NAME = "tcTipoImp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TcTipoImpService tcTipoImpService;

    public TcTipoImpResource(TcTipoImpService tcTipoImpService) {
        this.tcTipoImpService = tcTipoImpService;
    }

    /**
     * {@code POST  /tc-tipo-imps} : Create a new tcTipoImp.
     *
     * @param tcTipoImpDTO the tcTipoImpDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tcTipoImpDTO, or with status {@code 400 (Bad Request)} if the tcTipoImp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tc-tipo-imps")
    public ResponseEntity<TcTipoImpDTO> createTcTipoImp(@Valid @RequestBody TcTipoImpDTO tcTipoImpDTO) throws URISyntaxException {
        log.debug("REST request to save TcTipoImp : {}", tcTipoImpDTO);
        if (tcTipoImpDTO.getId() != null) {
            throw new BadRequestAlertException("A new tcTipoImp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TcTipoImpDTO result = tcTipoImpService.save(tcTipoImpDTO);
        return ResponseEntity.created(new URI("/api/tc-tipo-imps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tc-tipo-imps} : Updates an existing tcTipoImp.
     *
     * @param tcTipoImpDTO the tcTipoImpDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tcTipoImpDTO,
     * or with status {@code 400 (Bad Request)} if the tcTipoImpDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tcTipoImpDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tc-tipo-imps")
    public ResponseEntity<TcTipoImpDTO> updateTcTipoImp(@Valid @RequestBody TcTipoImpDTO tcTipoImpDTO) throws URISyntaxException {
        log.debug("REST request to update TcTipoImp : {}", tcTipoImpDTO);
        if (tcTipoImpDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TcTipoImpDTO result = tcTipoImpService.save(tcTipoImpDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tcTipoImpDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tc-tipo-imps} : get all the tcTipoImps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tcTipoImps in body.
     */
    @GetMapping("/tc-tipo-imps")
    public ResponseEntity<List<TcTipoImpDTO>> getAllTcTipoImps(Pageable pageable) {
        log.debug("REST request to get a page of TcTipoImps");
        Page<TcTipoImpDTO> page = tcTipoImpService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tc-tipo-imps/:id} : get the "id" tcTipoImp.
     *
     * @param id the id of the tcTipoImpDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tcTipoImpDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tc-tipo-imps/{id}")
    public ResponseEntity<TcTipoImpDTO> getTcTipoImp(@PathVariable Long id) {
        log.debug("REST request to get TcTipoImp : {}", id);
        Optional<TcTipoImpDTO> tcTipoImpDTO = tcTipoImpService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tcTipoImpDTO);
    }

    /**
     * {@code DELETE  /tc-tipo-imps/:id} : delete the "id" tcTipoImp.
     *
     * @param id the id of the tcTipoImpDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tc-tipo-imps/{id}")
    public ResponseEntity<Void> deleteTcTipoImp(@PathVariable Long id) {
        log.debug("REST request to delete TcTipoImp : {}", id);
        tcTipoImpService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
