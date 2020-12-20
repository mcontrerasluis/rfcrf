package sat.gob.mx.agsc.service.mapper;


import sat.gob.mx.agsc.domain.*;
import sat.gob.mx.agsc.service.dto.TcTipoImpDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TcTipoImp} and its DTO {@link TcTipoImpDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TcTipoImpMapper extends EntityMapper<TcTipoImpDTO, TcTipoImp> {



    default TcTipoImp fromId(Long id) {
        if (id == null) {
            return null;
        }
        TcTipoImp tcTipoImp = new TcTipoImp();
        tcTipoImp.setId(id);
        return tcTipoImp;
    }
}
