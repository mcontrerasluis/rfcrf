package sat.gob.mx.agsc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A TcManifes.
 */
@Entity
@Table(name = "tc_manifes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TcManifes implements Serializable {

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
    @Column(name = "moral", nullable = false)
    private Integer moral;

    @NotNull
    @Column(name = "fisica", nullable = false)
    private Integer fisica;

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
    @Column(name = "rfnorte", nullable = false)
    private Integer rfnorte;

    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "rfsur", nullable = false)
    private Integer rfsur;

    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "s_01", nullable = false)
    private Integer s01;

    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "s_02", nullable = false)
    private Integer s02;

    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "s_03", nullable = false)
    private Integer s03;

    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "s_04", nullable = false)
    private Integer s04;

    @Column(name = "fecha")
    private Instant fecha;

    @Column(name = "usuario")
    private String usuario;

    @ManyToMany(mappedBy = "manifestacions")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<TdRegFront> generals = new HashSet<>();

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

    public TcManifes clave(String clave) {
        this.clave = clave;
        return this;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public TcManifes descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getMoral() {
        return moral;
    }

    public TcManifes moral(Integer moral) {
        this.moral = moral;
        return this;
    }

    public void setMoral(Integer moral) {
        this.moral = moral;
    }

    public Integer getFisica() {
        return fisica;
    }

    public TcManifes fisica(Integer fisica) {
        this.fisica = fisica;
        return this;
    }

    public void setFisica(Integer fisica) {
        this.fisica = fisica;
    }

    public Integer getIsr() {
        return isr;
    }

    public TcManifes isr(Integer isr) {
        this.isr = isr;
        return this;
    }

    public void setIsr(Integer isr) {
        this.isr = isr;
    }

    public Integer getIva() {
        return iva;
    }

    public TcManifes iva(Integer iva) {
        this.iva = iva;
        return this;
    }

    public void setIva(Integer iva) {
        this.iva = iva;
    }

    public Integer getRfnorte() {
        return rfnorte;
    }

    public TcManifes rfnorte(Integer rfnorte) {
        this.rfnorte = rfnorte;
        return this;
    }

    public void setRfnorte(Integer rfnorte) {
        this.rfnorte = rfnorte;
    }

    public Integer getRfsur() {
        return rfsur;
    }

    public TcManifes rfsur(Integer rfsur) {
        this.rfsur = rfsur;
        return this;
    }

    public void setRfsur(Integer rfsur) {
        this.rfsur = rfsur;
    }

    public Integer gets01() {
        return s01;
    }

    public TcManifes s01(Integer s01) {
        this.s01 = s01;
        return this;
    }

    public void sets01(Integer s01) {
        this.s01 = s01;
    }

    public Integer gets02() {
        return s02;
    }

    public TcManifes s02(Integer s02) {
        this.s02 = s02;
        return this;
    }

    public void sets02(Integer s02) {
        this.s02 = s02;
    }

    public Integer gets03() {
        return s03;
    }

    public TcManifes s03(Integer s03) {
        this.s03 = s03;
        return this;
    }

    public void sets03(Integer s03) {
        this.s03 = s03;
    }

    public Integer gets04() {
        return s04;
    }

    public TcManifes s04(Integer s04) {
        this.s04 = s04;
        return this;
    }

    public void sets04(Integer s04) {
        this.s04 = s04;
    }

    public Instant getFecha() {
        return fecha;
    }

    public TcManifes fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public TcManifes usuario(String usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Set<TdRegFront> getGenerals() {
        return generals;
    }

    public TcManifes generals(Set<TdRegFront> tdRegFronts) {
        this.generals = tdRegFronts;
        return this;
    }

    public TcManifes addGeneral(TdRegFront tdRegFront) {
        this.generals.add(tdRegFront);
        tdRegFront.getManifestacions().add(this);
        return this;
    }

    public TcManifes removeGeneral(TdRegFront tdRegFront) {
        this.generals.remove(tdRegFront);
        tdRegFront.getManifestacions().remove(this);
        return this;
    }

    public void setGenerals(Set<TdRegFront> tdRegFronts) {
        this.generals = tdRegFronts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TcManifes)) {
            return false;
        }
        return id != null && id.equals(((TcManifes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TcManifes{" +
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
