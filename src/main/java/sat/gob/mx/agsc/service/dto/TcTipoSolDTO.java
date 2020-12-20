package sat.gob.mx.agsc.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link sat.gob.mx.agsc.domain.TcTipoSol} entity.
 */
public class TcTipoSolDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String clave;

    @NotNull
    private String descripcion;

    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    private Integer isr;

    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    private Integer iva;

    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    private Integer efirma;

    private Instant fechaInicio;

    private Instant fechaFin;

    private String usuario;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIsr() {
        return isr;
    }

    public void setIsr(Integer isr) {
        this.isr = isr;
    }

    public Integer getIva() {
        return iva;
    }

    public void setIva(Integer iva) {
        this.iva = iva;
    }

    public Integer getEfirma() {
        return efirma;
    }

    public void setEfirma(Integer efirma) {
        this.efirma = efirma;
    }

    public Instant getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Instant fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Instant getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Instant fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TcTipoSolDTO)) {
            return false;
        }

        return id != null && id.equals(((TcTipoSolDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TcTipoSolDTO{" +
            "id=" + getId() +
            ", clave='" + getClave() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", isr=" + getIsr() +
            ", iva=" + getIva() +
            ", efirma=" + getEfirma() +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", usuario='" + getUsuario() + "'" +
            "}";
    }
}
