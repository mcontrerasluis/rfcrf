package sat.gob.mx.agsc.service;

import sat.gob.mx.agsc.domain.TdRegFront;
import sat.gob.mx.agsc.repository.TdRegFrontRepository;
import sat.gob.mx.agsc.service.dto.TdRegFrontDTO;
import sat.gob.mx.agsc.service.mapper.TdRegFrontMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link TdRegFront}.
 */
@Service
@Transactional
public class TdRegFrontService {

    private final Logger log = LoggerFactory.getLogger(TdRegFrontService.class);

    private final TdRegFrontRepository tdRegFrontRepository;

    private final TdRegFrontMapper tdRegFrontMapper;

    public TdRegFrontService(TdRegFrontRepository tdRegFrontRepository, TdRegFrontMapper tdRegFrontMapper) {
        this.tdRegFrontRepository = tdRegFrontRepository;
        this.tdRegFrontMapper = tdRegFrontMapper;
    }

    /**
     * Save a tdRegFront.
     *
     * @param tdRegFrontDTO the entity to save.
     * @return the persisted entity.
     */
    public TdRegFrontDTO save(TdRegFrontDTO tdRegFrontDTO) {
        log.debug("Request to save TdRegFront : {}", tdRegFrontDTO);
        TdRegFront tdRegFront = tdRegFrontMapper.toEntity(tdRegFrontDTO);
        tdRegFront = tdRegFrontRepository.save(tdRegFront);
        return tdRegFrontMapper.toDto(tdRegFront);
    }

    /**
     * Get all the tdRegFronts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TdRegFrontDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TdRegFronts");
        return tdRegFrontRepository.findAll(pageable)
            .map(tdRegFrontMapper::toDto);
    }


    /**
     * Get all the tdRegFronts with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TdRegFrontDTO> findAllWithEagerRelationships(Pageable pageable) {
        return tdRegFrontRepository.findAllWithEagerRelationships(pageable).map(tdRegFrontMapper::toDto);
    }


    /**
     *  Get all the tdRegFronts where General is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<TdRegFrontDTO> findAllWhereGeneralIsNull() {
        log.debug("Request to get all tdRegFronts where General is null");
        return StreamSupport
            .stream(tdRegFrontRepository.findAll().spliterator(), false)
            .filter(tdRegFront -> tdRegFront.getGeneral() == null)
            .map(tdRegFrontMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one tdRegFront by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TdRegFrontDTO> findOne(Long id) {
        log.debug("Request to get TdRegFront : {}", id);
        return tdRegFrontRepository.findOneWithEagerRelationships(id)
            .map(tdRegFrontMapper::toDto);
    }

    /**
     * Delete the tdRegFront by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TdRegFront : {}", id);
        tdRegFrontRepository.deleteById(id);
    }
}
