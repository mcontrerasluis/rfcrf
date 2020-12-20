package sat.gob.mx.agsc.service;

import sat.gob.mx.agsc.domain.TcEjerc;
import sat.gob.mx.agsc.repository.TcEjercRepository;
import sat.gob.mx.agsc.service.dto.TcEjercDTO;
import sat.gob.mx.agsc.service.mapper.TcEjercMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TcEjerc}.
 */
@Service
@Transactional
public class TcEjercService {

    private final Logger log = LoggerFactory.getLogger(TcEjercService.class);

    private final TcEjercRepository tcEjercRepository;

    private final TcEjercMapper tcEjercMapper;

    public TcEjercService(TcEjercRepository tcEjercRepository, TcEjercMapper tcEjercMapper) {
        this.tcEjercRepository = tcEjercRepository;
        this.tcEjercMapper = tcEjercMapper;
    }

    /**
     * Save a tcEjerc.
     *
     * @param tcEjercDTO the entity to save.
     * @return the persisted entity.
     */
    public TcEjercDTO save(TcEjercDTO tcEjercDTO) {
        log.debug("Request to save TcEjerc : {}", tcEjercDTO);
        TcEjerc tcEjerc = tcEjercMapper.toEntity(tcEjercDTO);
        tcEjerc = tcEjercRepository.save(tcEjerc);
        return tcEjercMapper.toDto(tcEjerc);
    }

    /**
     * Get all the tcEjercs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TcEjercDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TcEjercs");
        return tcEjercRepository.findAll(pageable)
            .map(tcEjercMapper::toDto);
    }


    /**
     * Get one tcEjerc by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TcEjercDTO> findOne(Long id) {
        log.debug("Request to get TcEjerc : {}", id);
        return tcEjercRepository.findById(id)
            .map(tcEjercMapper::toDto);
    }

    /**
     * Delete the tcEjerc by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TcEjerc : {}", id);
        tcEjercRepository.deleteById(id);
    }
}
