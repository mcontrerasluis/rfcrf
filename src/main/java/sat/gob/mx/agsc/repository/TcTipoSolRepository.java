package sat.gob.mx.agsc.repository;

import sat.gob.mx.agsc.domain.TcTipoSol;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TcTipoSol entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TcTipoSolRepository extends JpaRepository<TcTipoSol, Long> {
}
