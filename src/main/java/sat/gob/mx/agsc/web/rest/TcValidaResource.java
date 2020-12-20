package sat.gob.mx.agsc.web.rest;

import sat.gob.mx.agsc.service.TcValidaService;
import sat.gob.mx.agsc.web.rest.errors.BadRequestAlertException;
import sat.gob.mx.agsc.service.dto.TcValidaDTO;

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
 * REST controller for managing {@link sat.gob.mx.agsc.domain.TcValida}.
 */
@RestController
@RequestMapping("/api")
public class TcValidaResource {

    private final Logger log = LoggerFactory.getLogger(TcValidaResource.class);

    private static final String ENTITY_NAME = "tcValida";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TcValidaService tcValidaService;

    public TcValidaResource(TcValidaService tcValidaService) {
        this.tcValidaService = tcValidaService;
    }

    /**
     * {@code POST  /tc-validas} : Create a new tcValida.
     *
     * @param tcValidaDTO the tcValidaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tcValidaDTO, or with status {@code 400 (Bad Request)} if the tcValida has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tc-validas")
    public ResponseEntity<TcValidaDTO> createTcValida(@Valid @RequestBody TcValidaDTO tcValidaDTO) throws URISyntaxException {
        log.debug("REST request to save TcValida : {}", tcValidaDTO);
        if (tcValidaDTO.getId() != null) {
            throw new BadRequestAlertException("A new tcValida cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TcValidaDTO result = tcValidaService.save(tcValidaDTO);
        return ResponseEntity.created(new URI("/api/tc-validas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tc-validas} : Updates an existing tcValida.
     *
     * @param tcValidaDTO the tcValidaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tcValidaDTO,
     * or with status {@code 400 (Bad Request)} if the tcValidaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tcValidaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tc-validas")
    public ResponseEntity<TcValidaDTO> updateTcValida(@Valid @RequestBody TcValidaDTO tcValidaDTO) throws URISyntaxException {
        log.debug("REST request to update TcValida : {}", tcValidaDTO);
        if (tcValidaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TcValidaDTO result = tcValidaService.save(tcValidaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tcValidaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tc-validas} : get all the tcValidas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tcValidas in body.
     */
    @GetMapping("/tc-validas")
    public ResponseEntity<List<TcValidaDTO>> getAllTcValidas(Pageable pageable) {
        log.debug("REST request to get a page of TcValidas");
        Page<TcValidaDTO> page = tcValidaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tc-validas/:id} : get the "id" tcValida.
     *
     * @param id the id of the tcValidaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tcValidaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tc-validas/{id}")
    public ResponseEntity<TcValidaDTO> getTcValida(@PathVariable Long id) {
        log.debug("REST request to get TcValida : {}", id);
        Optional<TcValidaDTO> tcValidaDTO = tcValidaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tcValidaDTO);
    }

    /**
     * {@code DELETE  /tc-validas/:id} : delete the "id" tcValida.
     *
     * @param id the id of the tcValidaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tc-validas/{id}")
    public ResponseEntity<Void> deleteTcValida(@PathVariable Long id) {
        log.debug("REST request to delete TcValida : {}", id);
        tcValidaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
