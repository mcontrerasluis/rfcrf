package sat.gob.mx.agsc.service.mapper;


import sat.gob.mx.agsc.domain.*;
import sat.gob.mx.agsc.service.dto.TcManifesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TcManifes} and its DTO {@link TcManifesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TcManifesMapper extends EntityMapper<TcManifesDTO, TcManifes> {


    @Mapping(target = "generals", ignore = true)
    @Mapping(target = "removeGeneral", ignore = true)
    TcManifes toEntity(TcManifesDTO tcManifesDTO);

    default TcManifes fromId(Long id) {
        if (id == null) {
            return null;
        }
        TcManifes tcManifes = new TcManifes();
        tcManifes.setId(id);
        return tcManifes;
    }
}
