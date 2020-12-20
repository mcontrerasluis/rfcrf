package sat.gob.mx.agsc.service;

import sat.gob.mx.agsc.domain.TcValida;
import sat.gob.mx.agsc.repository.TcValidaRepository;
import sat.gob.mx.agsc.service.dto.TcValidaDTO;
import sat.gob.mx.agsc.service.mapper.TcValidaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TcValida}.
 */
@Service
@Transactional
public class TcValidaService {

    private final Logger log = LoggerFactory.getLogger(TcValidaService.class);

    private final TcValidaRepository tcValidaRepository;

    private final TcValidaMapper tcValidaMapper;

    public TcValidaService(TcValidaRepository tcValidaRepository, TcValidaMapper tcValidaMapper) {
        this.tcValidaRepository = tcValidaRepository;
        this.tcValidaMapper = tcValidaMapper;
    }

    /**
     * Save a tcValida.
     *
     * @param tcValidaDTO the entity to save.
     * @return the persisted entity.
     */
    public TcValidaDTO save(TcValidaDTO tcValidaDTO) {
        log.debug("Request to save TcValida : {}", tcValidaDTO);
        TcValida tcValida = tcValidaMapper.toEntity(tcValidaDTO);
        tcValida = tcValidaRepository.save(tcValida);
        return tcValidaMapper.toDto(tcValida);
    }

    /**
     * Get all the tcValidas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TcValidaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TcValidas");
        return tcValidaRepository.findAll(pageable)
            .map(tcValidaMapper::toDto);
    }


    /**
     * Get one tcValida by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TcValidaDTO> findOne(Long id) {
        log.debug("Request to get TcValida : {}", id);
        return tcValidaRepository.findById(id)
            .map(tcValidaMapper::toDto);
    }

    /**
     * Delete the tcValida by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TcValida : {}", id);
        tcValidaRepository.deleteById(id);
    }
}
