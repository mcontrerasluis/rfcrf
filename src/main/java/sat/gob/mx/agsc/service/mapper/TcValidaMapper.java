package sat.gob.mx.agsc.service.mapper;


import sat.gob.mx.agsc.domain.*;
import sat.gob.mx.agsc.service.dto.TcValidaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TcValida} and its DTO {@link TcValidaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TcValidaMapper extends EntityMapper<TcValidaDTO, TcValida> {


    @Mapping(target = "generals", ignore = true)
    @Mapping(target = "removeGeneral", ignore = true)
    TcValida toEntity(TcValidaDTO tcValidaDTO);

    default TcValida fromId(Long id) {
        if (id == null) {
            return null;
        }
        TcValida tcValida = new TcValida();
        tcValida.setId(id);
        return tcValida;
    }
}
