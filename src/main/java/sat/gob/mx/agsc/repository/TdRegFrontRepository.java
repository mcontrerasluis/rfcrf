package sat.gob.mx.agsc.repository;

import sat.gob.mx.agsc.domain.TdRegFront;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the TdRegFront entity.
 */
@Repository
public interface TdRegFrontRepository extends JpaRepository<TdRegFront, Long> {

    @Query(value = "select distinct tdRegFront from TdRegFront tdRegFront left join fetch tdRegFront.manifestacions left join fetch tdRegFront.validacions",
        countQuery = "select count(distinct tdRegFront) from TdRegFront tdRegFront")
    Page<TdRegFront> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct tdRegFront from TdRegFront tdRegFront left join fetch tdRegFront.manifestacions left join fetch tdRegFront.validacions")
    List<TdRegFront> findAllWithEagerRelationships();

    @Query("select tdRegFront from TdRegFront tdRegFront left join fetch tdRegFront.manifestacions left join fetch tdRegFront.validacions where tdRegFront.id =:id")
    Optional<TdRegFront> findOneWithEagerRelationships(@Param("id") Long id);
}
