package sat.gob.mx.agsc.service.mapper;


import sat.gob.mx.agsc.domain.*;
import sat.gob.mx.agsc.service.dto.TdRegFrontDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TdRegFront} and its DTO {@link TdRegFrontDTO}.
 */
@Mapper(componentModel = "spring", uses = {TcTipoSolMapper.class, TcTipoImpMapper.class, TcEjercMapper.class, TcManifesMapper.class, TcValidaMapper.class})
public interface TdRegFrontMapper extends EntityMapper<TdRegFrontDTO, TdRegFront> {

    @Mapping(source = "tipoSolicitud.id", target = "tipoSolicitudId")
    @Mapping(source = "tipoSolicitud.descripcion", target = "tipoSolicitudDescripcion")
    @Mapping(source = "tipoImpuesto.id", target = "tipoImpuestoId")
    @Mapping(source = "tipoImpuesto.descripcion", target = "tipoImpuestoDescripcion")
    @Mapping(source = "ejercicio.id", target = "ejercicioId")
    @Mapping(source = "ejercicio.valor", target = "ejercicioValor")
    TdRegFrontDTO toDto(TdRegFront tdRegFront);

    @Mapping(source = "tipoSolicitudId", target = "tipoSolicitud")
    @Mapping(source = "tipoImpuestoId", target = "tipoImpuesto")
    @Mapping(source = "ejercicioId", target = "ejercicio")
    @Mapping(target = "removeManifestacion", ignore = true)
    @Mapping(target = "removeValidacion", ignore = true)
    @Mapping(target = "general", ignore = true)
    TdRegFront toEntity(TdRegFrontDTO tdRegFrontDTO);

    default TdRegFront fromId(Long id) {
        if (id == null) {
            return null;
        }
        TdRegFront tdRegFront = new TdRegFront();
        tdRegFront.setId(id);
        return tdRegFront;
    }
}
