package sat.gob.mx.agsc.web.rest;

import sat.gob.mx.agsc.service.TcManifesService;
import sat.gob.mx.agsc.web.rest.errors.BadRequestAlertException;
import sat.gob.mx.agsc.service.dto.TcManifesDTO;

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
import org.springframework.data.domain.PageRequest;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link sat.gob.mx.agsc.domain.TcManifes}.
 */
@RestController
@RequestMapping("/api")
public class TcManifesResource {

    private final Logger log = LoggerFactory.getLogger(TcManifesResource.class);

    private static final String ENTITY_NAME = "tcManifes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TcManifesService tcManifesService;

    public TcManifesResource(TcManifesService tcManifesService) {
        this.tcManifesService = tcManifesService;
    }

    /**
     * {@code POST  /tc-manifes} : Create a new tcManifes.
     *
     * @param tcManifesDTO the tcManifesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tcManifesDTO, or with status {@code 400 (Bad Request)} if the tcManifes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tc-manifes")
    public ResponseEntity<TcManifesDTO> createTcManifes(@Valid @RequestBody TcManifesDTO tcManifesDTO) throws URISyntaxException {
        log.debug("REST request to save TcManifes : {}", tcManifesDTO);
        if (tcManifesDTO.getId() != null) {
            throw new BadRequestAlertException("A new tcManifes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TcManifesDTO result = tcManifesService.save(tcManifesDTO);
        return ResponseEntity.created(new URI("/api/tc-manifes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tc-manifes} : Updates an existing tcManifes.
     *
     * @param tcManifesDTO the tcManifesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tcManifesDTO,
     * or with status {@code 400 (Bad Request)} if the tcManifesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tcManifesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tc-manifes")
    public ResponseEntity<TcManifesDTO> updateTcManifes(@Valid @RequestBody TcManifesDTO tcManifesDTO) throws URISyntaxException {
        log.debug("REST request to update TcManifes : {}", tcManifesDTO);
        if (tcManifesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TcManifesDTO result = tcManifesService.save(tcManifesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tcManifesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tc-manifes} : get all the tcManifes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tcManifes in body.
     */
    @GetMapping("/tc-manifes")
    public ResponseEntity<List<TcManifesDTO>> getAllTcManifes(Pageable pageable) {
        log.debug("REST request to get a page of TcManifes");
        Page<TcManifesDTO> page = tcManifesService.findAll(PageRequest.of(0, 25));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tc-manifes/:id} : get the "id" tcManifes.
     *
     * @param id the id of the tcManifesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tcManifesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tc-manifes/{id}")
    public ResponseEntity<TcManifesDTO> getTcManifes(@PathVariable Long id) {
        log.debug("REST request to get TcManifes : {}", id);
        Optional<TcManifesDTO> tcManifesDTO = tcManifesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tcManifesDTO);
    }

    /**
     * {@code DELETE  /tc-manifes/:id} : delete the "id" tcManifes.
     *
     * @param id the id of the tcManifesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tc-manifes/{id}")
    public ResponseEntity<Void> deleteTcManifes(@PathVariable Long id) {
        log.debug("REST request to delete TcManifes : {}", id);
        tcManifesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
