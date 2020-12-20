package sat.gob.mx.agsc.repository;

import sat.gob.mx.agsc.domain.TcValida;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TcValida entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TcValidaRepository extends JpaRepository<TcValida, Long> {
}
