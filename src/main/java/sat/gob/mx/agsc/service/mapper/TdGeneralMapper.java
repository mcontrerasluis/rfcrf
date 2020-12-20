package sat.gob.mx.agsc.service.mapper;


import sat.gob.mx.agsc.domain.*;
import sat.gob.mx.agsc.service.dto.TdGeneralDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TdGeneral} and its DTO {@link TdGeneralDTO}.
 */
@Mapper(componentModel = "spring", uses = {TdRegFrontMapper.class})
public interface TdGeneralMapper extends EntityMapper<TdGeneralDTO, TdGeneral> {

    @Mapping(source = "tipoSolicitud.id", target = "tipoSolicitudId")
    TdGeneralDTO toDto(TdGeneral tdGeneral);

    @Mapping(source = "tipoSolicitudId", target = "tipoSolicitud")
    TdGeneral toEntity(TdGeneralDTO tdGeneralDTO);

    default TdGeneral fromId(Long id) {
        if (id == null) {
            return null;
        }
        TdGeneral tdGeneral = new TdGeneral();
        tdGeneral.setId(id);
        return tdGeneral;
    }
}
