package sat.gob.mx.agsc.repository;

import sat.gob.mx.agsc.domain.TcTipoImp;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TcTipoImp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TcTipoImpRepository extends JpaRepository<TcTipoImp, Long> {
}
