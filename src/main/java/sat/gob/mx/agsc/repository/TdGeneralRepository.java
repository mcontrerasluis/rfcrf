package sat.gob.mx.agsc.repository;

import sat.gob.mx.agsc.domain.TdGeneral;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TdGeneral entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TdGeneralRepository extends JpaRepository<TdGeneral, Long> {
}
