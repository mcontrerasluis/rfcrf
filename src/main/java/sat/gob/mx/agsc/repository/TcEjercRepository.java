package sat.gob.mx.agsc.repository;

import sat.gob.mx.agsc.domain.TcEjerc;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TcEjerc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TcEjercRepository extends JpaRepository<TcEjerc, Long> {
}
