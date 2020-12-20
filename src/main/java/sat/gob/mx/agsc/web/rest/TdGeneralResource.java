package sat.gob.mx.agsc.web.rest;

import sat.gob.mx.agsc.service.TdGeneralService;
import sat.gob.mx.agsc.web.rest.errors.BadRequestAlertException;
import sat.gob.mx.agsc.service.dto.TdGeneralDTO;

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
 * REST controller for managing {@link sat.gob.mx.agsc.domain.TdGeneral}.
 */
@RestController
@RequestMapping("/api")
public class TdGeneralResource {

    private final Logger log = LoggerFactory.getLogger(TdGeneralResource.class);

    private static final String ENTITY_NAME = "tdGeneral";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TdGeneralService tdGeneralService;

    public TdGeneralResource(TdGeneralService tdGeneralService) {
        this.tdGeneralService = tdGeneralService;
    }

    /**
     * {@code POST  /td-generals} : Create a new tdGeneral.
     *
     * @param tdGeneralDTO the tdGeneralDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tdGeneralDTO, or with status {@code 400 (Bad Request)} if the tdGeneral has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/td-generals")
    public ResponseEntity<TdGeneralDTO> createTdGeneral(@Valid @RequestBody TdGeneralDTO tdGeneralDTO) throws URISyntaxException {
        log.debug("REST request to save TdGeneral : {}", tdGeneralDTO);
        if (tdGeneralDTO.getId() != null) {
            throw new BadRequestAlertException("A new tdGeneral cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TdGeneralDTO result = tdGeneralService.save(tdGeneralDTO);
        return ResponseEntity.created(new URI("/api/td-generals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /td-generals} : Updates an existing tdGeneral.
     *
     * @param tdGeneralDTO the tdGeneralDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tdGeneralDTO,
     * or with status {@code 400 (Bad Request)} if the tdGeneralDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tdGeneralDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/td-generals")
    public ResponseEntity<TdGeneralDTO> updateTdGeneral(@Valid @RequestBody TdGeneralDTO tdGeneralDTO) throws URISyntaxException {
        log.debug("REST request to update TdGeneral : {}", tdGeneralDTO);
        if (tdGeneralDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TdGeneralDTO result = tdGeneralService.save(tdGeneralDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tdGeneralDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /td-generals} : get all the tdGenerals.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tdGenerals in body.
     */
    @GetMapping("/td-generals")
    public ResponseEntity<List<TdGeneralDTO>> getAllTdGenerals(Pageable pageable) {
        log.debug("REST request to get a page of TdGenerals");
        Page<TdGeneralDTO> page = tdGeneralService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /td-generals/:id} : get the "id" tdGeneral.
     *
     * @param id the id of the tdGeneralDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tdGeneralDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/td-generals/{id}")
    public ResponseEntity<TdGeneralDTO> getTdGeneral(@PathVariable Long id) {
        log.debug("REST request to get TdGeneral : {}", id);
        Optional<TdGeneralDTO> tdGeneralDTO = tdGeneralService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tdGeneralDTO);
    }

    /**
     * {@code DELETE  /td-generals/:id} : delete the "id" tdGeneral.
     *
     * @param id the id of the tdGeneralDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/td-generals/{id}")
    public ResponseEntity<Void> deleteTdGeneral(@PathVariable Long id) {
        log.debug("REST request to delete TdGeneral : {}", id);
        tdGeneralService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
