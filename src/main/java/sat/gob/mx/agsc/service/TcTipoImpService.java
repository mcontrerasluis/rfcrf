package sat.gob.mx.agsc.service;

import sat.gob.mx.agsc.domain.TcTipoImp;
import sat.gob.mx.agsc.repository.TcTipoImpRepository;
import sat.gob.mx.agsc.service.dto.TcTipoImpDTO;
import sat.gob.mx.agsc.service.mapper.TcTipoImpMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TcTipoImp}.
 */
@Service
@Transactional
public class TcTipoImpService {

    private final Logger log = LoggerFactory.getLogger(TcTipoImpService.class);

    private final TcTipoImpRepository tcTipoImpRepository;

    private final TcTipoImpMapper tcTipoImpMapper;

    public TcTipoImpService(TcTipoImpRepository tcTipoImpRepository, TcTipoImpMapper tcTipoImpMapper) {
        this.tcTipoImpRepository = tcTipoImpRepository;
        this.tcTipoImpMapper = tcTipoImpMapper;
    }

    /**
     * Save a tcTipoImp.
     *
     * @param tcTipoImpDTO the entity to save.
     * @return the persisted entity.
     */
    public TcTipoImpDTO save(TcTipoImpDTO tcTipoImpDTO) {
        log.debug("Request to save TcTipoImp : {}", tcTipoImpDTO);
        TcTipoImp tcTipoImp = tcTipoImpMapper.toEntity(tcTipoImpDTO);
        tcTipoImp = tcTipoImpRepository.save(tcTipoImp);
        return tcTipoImpMapper.toDto(tcTipoImp);
    }

    /**
     * Get all the tcTipoImps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TcTipoImpDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TcTipoImps");
        return tcTipoImpRepository.findAll(pageable)
            .map(tcTipoImpMapper::toDto);
    }


    /**
     * Get one tcTipoImp by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TcTipoImpDTO> findOne(Long id) {
        log.debug("Request to get TcTipoImp : {}", id);
        return tcTipoImpRepository.findById(id)
            .map(tcTipoImpMapper::toDto);
    }

    /**
     * Delete the tcTipoImp by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TcTipoImp : {}", id);
        tcTipoImpRepository.deleteById(id);
    }
}
