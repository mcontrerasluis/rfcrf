package sat.gob.mx.agsc.service.mapper;


import sat.gob.mx.agsc.domain.*;
import sat.gob.mx.agsc.service.dto.TcEjercDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TcEjerc} and its DTO {@link TcEjercDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TcEjercMapper extends EntityMapper<TcEjercDTO, TcEjerc> {



    default TcEjerc fromId(Long id) {
        if (id == null) {
            return null;
        }
        TcEjerc tcEjerc = new TcEjerc();
        tcEjerc.setId(id);
        return tcEjerc;
    }
}
