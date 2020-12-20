package sat.gob.mx.agsc.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link sat.gob.mx.agsc.domain.TdGeneral} entity.
 */
public class TdGeneralDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String rfc;

    @NotNull
    private Instant fecha;

    @NotNull
    private String tipoSolicitudd;

    @NotNull
    private String folio;

    @NotNull
    private Integer estatus;


    private Long tipoSolicitudId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public Instant getFecha() {
        return fecha;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getTipoSolicitudd() {
        return tipoSolicitudd;
    }

    public void setTipoSolicitudd(String tipoSolicitudd) {
        this.tipoSolicitudd = tipoSolicitudd;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public Long getTipoSolicitudId() {
        return tipoSolicitudId;
    }

    public void setTipoSolicitudId(Long tdRegFrontId) {
        this.tipoSolicitudId = tdRegFrontId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TdGeneralDTO)) {
            return false;
        }

        return id != null && id.equals(((TdGeneralDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TdGeneralDTO{" +
            "id=" + getId() +
            ", rfc='" + getRfc() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", tipoSolicitudd='" + getTipoSolicitudd() + "'" +
            ", folio='" + getFolio() + "'" +
            ", estatus=" + getEstatus() +
            ", tipoSolicitudId=" + getTipoSolicitudId() +
            "}";
    }
}
