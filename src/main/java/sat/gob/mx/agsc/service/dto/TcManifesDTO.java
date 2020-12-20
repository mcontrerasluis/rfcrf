package sat.gob.mx.agsc.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link sat.gob.mx.agsc.domain.TcManifes} entity.
 */
public class TcManifesDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String clave;

    @NotNull
    private String descripcion;

    @NotNull
    private Integer moral;

    @NotNull
    private Integer fisica;

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
    private Integer rfnorte;

    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    private Integer rfsur;

    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    private Integer s01;

    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    private Integer s02;

    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    private Integer s03;

    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    private Integer s04;

    private Instant fecha;

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

    public Integer getRfnorte() {
        return rfnorte;
    }

    public void setRfnorte(Integer rfnorte) {
        this.rfnorte = rfnorte;
    }

    public Integer getRfsur() {
        return rfsur;
    }

    public void setRfsur(Integer rfsur) {
        this.rfsur = rfsur;
    }

    public Integer gets01() {
        return s01;
    }

    public void sets01(Integer s01) {
        this.s01 = s01;
    }

    public Integer gets02() {
        return s02;
    }

    public void sets02(Integer s02) {
        this.s02 = s02;
    }

    public Integer gets03() {
        return s03;
    }

    public void sets03(Integer s03) {
        this.s03 = s03;
    }

    public Integer gets04() {
        return s04;
    }

    public void sets04(Integer s04) {
        this.s04 = s04;
    }

    public Instant getFecha() {
        return fecha;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
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
        if (!(o instanceof TcManifesDTO)) {
            return false;
        }

        return id != null && id.equals(((TcManifesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TcManifesDTO{" +
            "id=" + getId() +
            ", clave='" + getClave() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", moral=" + getMoral() +
            ", fisica=" + getFisica() +
            ", isr=" + getIsr() +
            ", iva=" + getIva() +
            ", rfnorte=" + getRfnorte() +
            ", rfsur=" + getRfsur() +
            ", s01=" + gets01() +
            ", s02=" + gets02() +
            ", s03=" + gets03() +
            ", s04=" + gets04() +
            ", fecha='" + getFecha() + "'" +
            ", usuario='" + getUsuario() + "'" +
            "}";
    }
}
