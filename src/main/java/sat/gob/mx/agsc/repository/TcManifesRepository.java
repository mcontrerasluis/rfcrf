package sat.gob.mx.agsc.repository;

import sat.gob.mx.agsc.domain.TcManifes;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TcManifes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TcManifesRepository extends JpaRepository<TcManifes, Long> {
}
