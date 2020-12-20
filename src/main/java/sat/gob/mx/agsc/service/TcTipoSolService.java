package sat.gob.mx.agsc.service;

import sat.gob.mx.agsc.domain.TcTipoSol;
import sat.gob.mx.agsc.repository.TcTipoSolRepository;
import sat.gob.mx.agsc.service.dto.TcTipoSolDTO;
import sat.gob.mx.agsc.service.mapper.TcTipoSolMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TcTipoSol}.
 */
@Service
@Transactional
public class TcTipoSolService {

    private final Logger log = LoggerFactory.getLogger(TcTipoSolService.class);

    private final TcTipoSolRepository tcTipoSolRepository;

    private final TcTipoSolMapper tcTipoSolMapper;

    public TcTipoSolService(TcTipoSolRepository tcTipoSolRepository, TcTipoSolMapper tcTipoSolMapper) {
        this.tcTipoSolRepository = tcTipoSolRepository;
        this.tcTipoSolMapper = tcTipoSolMapper;
    }

    /**
     * Save a tcTipoSol.
     *
     * @param tcTipoSolDTO the entity to save.
     * @return the persisted entity.
     */
    public TcTipoSolDTO save(TcTipoSolDTO tcTipoSolDTO) {
        log.debug("Request to save TcTipoSol : {}", tcTipoSolDTO);
        TcTipoSol tcTipoSol = tcTipoSolMapper.toEntity(tcTipoSolDTO);
        tcTipoSol = tcTipoSolRepository.save(tcTipoSol);
        return tcTipoSolMapper.toDto(tcTipoSol);
    }

    /**
     * Get all the tcTipoSols.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TcTipoSolDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TcTipoSols");
        return tcTipoSolRepository.findAll(pageable)
            .map(tcTipoSolMapper::toDto);
    }


    /**
     * Get one tcTipoSol by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TcTipoSolDTO> findOne(Long id) {
        log.debug("Request to get TcTipoSol : {}", id);
        return tcTipoSolRepository.findById(id)
            .map(tcTipoSolMapper::toDto);
    }

    /**
     * Delete the tcTipoSol by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TcTipoSol : {}", id);
        tcTipoSolRepository.deleteById(id);
    }
}
