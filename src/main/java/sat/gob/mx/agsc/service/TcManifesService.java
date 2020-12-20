package sat.gob.mx.agsc.service;

import sat.gob.mx.agsc.domain.TcManifes;
import sat.gob.mx.agsc.repository.TcManifesRepository;
import sat.gob.mx.agsc.service.dto.TcManifesDTO;
import sat.gob.mx.agsc.service.mapper.TcManifesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TcManifes}.
 */
@Service
@Transactional
public class TcManifesService {

    private final Logger log = LoggerFactory.getLogger(TcManifesService.class);

    private final TcManifesRepository tcManifesRepository;

    private final TcManifesMapper tcManifesMapper;

    public TcManifesService(TcManifesRepository tcManifesRepository, TcManifesMapper tcManifesMapper) {
        this.tcManifesRepository = tcManifesRepository;
        this.tcManifesMapper = tcManifesMapper;
    }

    /**
     * Save a tcManifes.
     *
     * @param tcManifesDTO the entity to save.
     * @return the persisted entity.
     */
    public TcManifesDTO save(TcManifesDTO tcManifesDTO) {
        log.debug("Request to save TcManifes : {}", tcManifesDTO);
        TcManifes tcManifes = tcManifesMapper.toEntity(tcManifesDTO);
        tcManifes = tcManifesRepository.save(tcManifes);
        return tcManifesMapper.toDto(tcManifes);
    }

    /**
     * Get all the tcManifes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TcManifesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TcManifes");
        return tcManifesRepository.findAll(pageable)
            .map(tcManifesMapper::toDto);
    }


    /**
     * Get one tcManifes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TcManifesDTO> findOne(Long id) {
        log.debug("Request to get TcManifes : {}", id);
        return tcManifesRepository.findById(id)
            .map(tcManifesMapper::toDto);
    }

    /**
     * Delete the tcManifes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TcManifes : {}", id);
        tcManifesRepository.deleteById(id);
    }
}
