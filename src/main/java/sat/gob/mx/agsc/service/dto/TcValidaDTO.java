package sat.gob.mx.agsc.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link sat.gob.mx.agsc.domain.TcValida} entity.
 */
public class TcValidaDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String clave;

    @NotNull
    private String descripcion;

    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    private Integer moral;

    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    private Integer fisica;

    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    private Integer isr;

    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    private Integer iva;

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

    public Integer getMoral() {
        return moral;
    }

    public void setMoral(Integer moral) {
        this.moral = moral;
    }

    public Integer getFisica() {
        return fisica;
    }

    public void setFisica(Integer fisica) {
        this.fisica = fisica;
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
        if (!(o instanceof TcValidaDTO)) {
            return false;
        }

        return id != null && id.equals(((TcValidaDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TcValidaDTO{" +
            "id=" + getId() +
            ", clave='" + getClave() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", moral=" + getMoral() +
            ", fisica=" + getFisica() +
            ", isr=" + getIsr() +
            ", iva=" + getIva() +
            ", usuario='" + getUsuario() + "'" +
            "}";
    }
}
