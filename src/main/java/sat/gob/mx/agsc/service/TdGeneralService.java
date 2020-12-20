package sat.gob.mx.agsc.service;

import sat.gob.mx.agsc.domain.TdGeneral;
import sat.gob.mx.agsc.repository.TdGeneralRepository;
import sat.gob.mx.agsc.service.dto.TdGeneralDTO;
import sat.gob.mx.agsc.service.mapper.TdGeneralMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TdGeneral}.
 */
@Service
@Transactional
public class TdGeneralService {

    private final Logger log = LoggerFactory.getLogger(TdGeneralService.class);

    private final TdGeneralRepository tdGeneralRepository;

    private final TdGeneralMapper tdGeneralMapper;

    public TdGeneralService(TdGeneralRepository tdGeneralRepository, TdGeneralMapper tdGeneralMapper) {
        this.tdGeneralRepository = tdGeneralRepository;
        this.tdGeneralMapper = tdGeneralMapper;
    }

    /**
     * Save a tdGeneral.
     *
     * @param tdGeneralDTO the entity to save.
     * @return the persisted entity.
     */
    public TdGeneralDTO save(TdGeneralDTO tdGeneralDTO) {
        log.debug("Request to save TdGeneral : {}", tdGeneralDTO);
        TdGeneral tdGeneral = tdGeneralMapper.toEntity(tdGeneralDTO);
        tdGeneral = tdGeneralRepository.save(tdGeneral);
        return tdGeneralMapper.toDto(tdGeneral);
    }

    /**
     * Get all the tdGenerals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TdGeneralDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TdGenerals");
        return tdGeneralRepository.findAll(pageable)
            .map(tdGeneralMapper::toDto);
    }


    /**
     * Get one tdGeneral by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TdGeneralDTO> findOne(Long id) {
        log.debug("Request to get TdGeneral : {}", id);
        return tdGeneralRepository.findById(id)
            .map(tdGeneralMapper::toDto);
    }

    /**
     * Delete the tdGeneral by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TdGeneral : {}", id);
        tdGeneralRepository.deleteById(id);
    }
}
