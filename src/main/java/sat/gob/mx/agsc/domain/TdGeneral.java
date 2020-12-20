package sat.gob.mx.agsc.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A TdGeneral.
 */
@Entity
@Table(name = "td_general")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TdGeneral implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "rfc", nullable = false)
    private String rfc;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private Instant fecha;

    @NotNull
    @Column(name = "tipo_solicitudd", nullable = false)
    private String tipoSolicitudd;

    @NotNull
    @Column(name = "folio", nullable = false)
    private String folio;

    @NotNull
    @Column(name = "estatus", nullable = false)
    private Integer estatus;

    @OneToOne
    @JoinColumn(unique = true)
    private TdRegFront tipoSolicitud;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRfc() {
        return rfc;
    }

    public TdGeneral rfc(String rfc) {
        this.rfc = rfc;
        return this;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public Instant getFecha() {
        return fecha;
    }

    public TdGeneral fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getTipoSolicitudd() {
        return tipoSolicitudd;
    }

    public TdGeneral tipoSolicitudd(String tipoSolicitudd) {
        this.tipoSolicitudd = tipoSolicitudd;
        return this;
    }

    public void setTipoSolicitudd(String tipoSolicitudd) {
        this.tipoSolicitudd = tipoSolicitudd;
    }

    public String getFolio() {
        return folio;
    }

    public TdGeneral folio(String folio) {
        this.folio = folio;
        return this;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public TdGeneral estatus(Integer estatus) {
        this.estatus = estatus;
        return this;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public TdRegFront getTipoSolicitud() {
        return tipoSolicitud;
    }

    public TdGeneral tipoSolicitud(TdRegFront tdRegFront) {
        this.tipoSolicitud = tdRegFront;
        return this;
    }

    public void setTipoSolicitud(TdRegFront tdRegFront) {
        this.tipoSolicitud = tdRegFront;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TdGeneral)) {
            return false;
        }
        return id != null && id.equals(((TdGeneral) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TdGeneral{" +
            "id=" + getId() +
            ", rfc='" + getRfc() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", tipoSolicitudd='" + getTipoSolicitudd() + "'" +
            ", folio='" + getFolio() + "'" +
            ", estatus=" + getEstatus() +
            "}";
    }
}
