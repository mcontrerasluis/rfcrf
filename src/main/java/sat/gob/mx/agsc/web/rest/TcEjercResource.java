package sat.gob.mx.agsc.web.rest;

import sat.gob.mx.agsc.service.TcEjercService;
import sat.gob.mx.agsc.web.rest.errors.BadRequestAlertException;
import sat.gob.mx.agsc.service.dto.TcEjercDTO;

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
 * REST controller for managing {@link sat.gob.mx.agsc.domain.TcEjerc}.
 */
@RestController
@RequestMapping("/api")
public class TcEjercResource {

    private final Logger log = LoggerFactory.getLogger(TcEjercResource.class);

    private static final String ENTITY_NAME = "tcEjerc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TcEjercService tcEjercService;

    public TcEjercResource(TcEjercService tcEjercService) {
        this.tcEjercService = tcEjercService;
    }

    /**
     * {@code POST  /tc-ejercs} : Create a new tcEjerc.
     *
     * @param tcEjercDTO the tcEjercDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tcEjercDTO, or with status {@code 400 (Bad Request)} if the tcEjerc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tc-ejercs")
    public ResponseEntity<TcEjercDTO> createTcEjerc(@Valid @RequestBody TcEjercDTO tcEjercDTO) throws URISyntaxException {
        log.debug("REST request to save TcEjerc : {}", tcEjercDTO);
        if (tcEjercDTO.getId() != null) {
            throw new BadRequestAlertException("A new tcEjerc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TcEjercDTO result = tcEjercService.save(tcEjercDTO);
        return ResponseEntity.created(new URI("/api/tc-ejercs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tc-ejercs} : Updates an existing tcEjerc.
     *
     * @param tcEjercDTO the tcEjercDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tcEjercDTO,
     * or with status {@code 400 (Bad Request)} if the tcEjercDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tcEjercDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tc-ejercs")
    public ResponseEntity<TcEjercDTO> updateTcEjerc(@Valid @RequestBody TcEjercDTO tcEjercDTO) throws URISyntaxException {
        log.debug("REST request to update TcEjerc : {}", tcEjercDTO);
        if (tcEjercDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TcEjercDTO result = tcEjercService.save(tcEjercDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tcEjercDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tc-ejercs} : get all the tcEjercs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tcEjercs in body.
     */
    @GetMapping("/tc-ejercs")
    public ResponseEntity<List<TcEjercDTO>> getAllTcEjercs(Pageable pageable) {
        log.debug("REST request to get a page of TcEjercs");
        Page<TcEjercDTO> page = tcEjercService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tc-ejercs/:id} : get the "id" tcEjerc.
     *
     * @param id the id of the tcEjercDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tcEjercDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tc-ejercs/{id}")
    public ResponseEntity<TcEjercDTO> getTcEjerc(@PathVariable Long id) {
        log.debug("REST request to get TcEjerc : {}", id);
        Optional<TcEjercDTO> tcEjercDTO = tcEjercService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tcEjercDTO);
    }

    /**
     * {@code DELETE  /tc-ejercs/:id} : delete the "id" tcEjerc.
     *
     * @param id the id of the tcEjercDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tc-ejercs/{id}")
    public ResponseEntity<Void> deleteTcEjerc(@PathVariable Long id) {
        log.debug("REST request to delete TcEjerc : {}", id);
        tcEjercService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
