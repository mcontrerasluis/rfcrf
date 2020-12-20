package sat.gob.mx.agsc.service.mapper;


import sat.gob.mx.agsc.domain.*;
import sat.gob.mx.agsc.service.dto.TcTipoSolDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TcTipoSol} and its DTO {@link TcTipoSolDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TcTipoSolMapper extends EntityMapper<TcTipoSolDTO, TcTipoSol> {



    default TcTipoSol fromId(Long id) {
        if (id == null) {
            return null;
        }
        TcTipoSol tcTipoSol = new TcTipoSol();
        tcTipoSol.setId(id);
        return tcTipoSol;
    }
}
