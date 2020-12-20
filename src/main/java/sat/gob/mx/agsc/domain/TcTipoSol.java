package sat.gob.mx.agsc.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A TcTipoSol.
 */
@Entity
@Table(name = "tc_tipo_sol")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TcTipoSol implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "clave", nullable = false)
    private String clave;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "isr", nullable = false)
    private Integer isr;

    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "iva", nullable = false)
    private Integer iva;

    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "efirma", nullable = false)
    private Integer efirma;

    @Column(name = "fecha_inicio")
    private Instant fechaInicio;

    @Column(name = "fecha_fin")
    private Instant fechaFin;

    @Column(name = "usuario")
    private String usuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public TcTipoSol clave(String clave) {
        this.clave = clave;
        return this;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public TcTipoSol descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIsr() {
        return isr;
    }

    public TcTipoSol isr(Integer isr) {
        this.isr = isr;
        return this;
    }

    public void setIsr(Integer isr) {
        this.isr = isr;
    }

    public Integer getIva() {
        return iva;
    }

    public TcTipoSol iva(Integer iva) {
        this.iva = iva;
        return this;
    }

    public void setIva(Integer iva) {
        this.iva = iva;
    }

    public Integer getEfirma() {
        return efirma;
    }

    public TcTipoSol efirma(Integer efirma) {
        this.efirma = efirma;
        return this;
    }

    public void setEfirma(Integer efirma) {
        this.efirma = efirma;
    }

    public Instant getFechaInicio() {
        return fechaInicio;
    }

    public TcTipoSol fechaInicio(Instant fechaInicio) {
        this.fechaInicio = fechaInicio;
        return this;
    }

    public void setFechaInicio(Instant fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Instant getFechaFin() {
        return fechaFin;
    }

    public TcTipoSol fechaFin(Instant fechaFin) {
        this.fechaFin = fechaFin;
        return this;
    }

    public void setFechaFin(Instant fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getUsuario() {
        return usuario;
    }

    public TcTipoSol usuario(String usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TcTipoSol)) {
            return false;
        }
        return id != null && id.equals(((TcTipoSol) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TcTipoSol{" +
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
