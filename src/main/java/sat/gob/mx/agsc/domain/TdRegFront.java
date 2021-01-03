package sat.gob.mx.agsc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A TdRegFront.
 */
@Entity
@Table(name = "td_reg_front")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TdRegFront implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "region", nullable = false)
    private String region;

    @NotNull
    @Column(name = "domicilio_region", nullable = false)
    private String domicilioRegion;

    @NotNull
    @Column(name = "sucursal_region", nullable = false)
    private String sucursalRegion;

    @Column(name = "tipo_impuestod")
    private String tipoImpuestod;

    @Column(name = "tipo_solicitudd")
    private String tipoSolicitudd;

    @Column(name = "ejerciciod")
    private Integer ejerciciod;

    @NotNull
    @Column(name = "estatus", nullable = false)
    private String estatus;

    @Column(name = "folio")
    private String folio;

    @Column(name = "rfc")
    private String rfc;

    @Column(name = "cadena")
    private String cadena;

    @Column(name = "sello")
    private String sello;

    @Column(name = "fecha")
    private Instant fecha;

    @Column(name = "usuario")
    private String usuario;

    @ManyToOne
    @JsonIgnoreProperties(value = "tdRegFronts", allowSetters = true)
    private TcTipoSol tipoSolicitud;

    @ManyToOne
    @JsonIgnoreProperties(value = "tdRegFronts", allowSetters = true)
    private TcTipoImp tipoImpuesto;

    @ManyToOne
    @JsonIgnoreProperties(value = "tdRegFronts", allowSetters = true)
    private TcEjerc ejercicio;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "td_reg_front_manifestacion",
               joinColumns = @JoinColumn(name = "td_reg_front_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "manifestacion_id", referencedColumnName = "id"))
    private Set<TcManifes> manifestacions = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "td_reg_front_validacion",
               joinColumns = @JoinColumn(name = "td_reg_front_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "validacion_id", referencedColumnName = "id"))
    private Set<TcValida> validacions = new HashSet<>();

    @OneToOne(mappedBy = "tipoSolicitud")
    @JsonIgnore
    private TdGeneral general;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public TdRegFront region(String region) {
        this.region = region;
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDomicilioRegion() {
        return domicilioRegion;
    }

    public TdRegFront domicilioRegion(String domicilioRegion) {
        this.domicilioRegion = domicilioRegion;
        return this;
    }

    public void setDomicilioRegion(String domicilioRegion) {
        this.domicilioRegion = domicilioRegion;
    }

    public String getSucursalRegion() {
        return sucursalRegion;
    }

    public TdRegFront sucursalRegion(String sucursalRegion) {
        this.sucursalRegion = sucursalRegion;
        return this;
    }

    public void setSucursalRegion(String sucursalRegion) {
        this.sucursalRegion = sucursalRegion;
    }

    public String getTipoImpuestod() {
        return tipoImpuestod;
    }

    public TdRegFront tipoImpuestod(String tipoImpuestod) {
        this.tipoImpuestod = tipoImpuestod;
        return this;
    }

    public void setTipoImpuestod(String tipoImpuestod) {
        this.tipoImpuestod = tipoImpuestod;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public TdRegFront cadena(String cadena){
        this.cadena = cadena;
        
        return this;
    }

    public String getSello() {
        return sello;
    }

    public void setSello(String sello) {
        this.sello = sello;
    }

    public TdRegFront sello(String sello){
        this.sello = sello;
        
        return this;
    }


    public String getTipoSolicitudd() {
        return tipoSolicitudd;
    }

    public TdRegFront tipoSolicitudd(String tipoSolicitudd) {
        this.tipoSolicitudd = tipoSolicitudd;
        return this;
    }

    public void setTipoSolicitudd(String tipoSolicitudd) {
        this.tipoSolicitudd = tipoSolicitudd;
    }

    public Integer getEjerciciod() {
        return ejerciciod;
    }

    public TdRegFront ejerciciod(Integer ejerciciod) {
        this.ejerciciod = ejerciciod;
        return this;
    }

    public void setEjerciciod(Integer ejerciciod) {
        this.ejerciciod = ejerciciod;
    }

    public String getEstatus() {
        return estatus;
    }

    public TdRegFront estatus(String estatus) {
        this.estatus = estatus;
        return this;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getFolio() {
        return folio;
    }

    public TdRegFront folio(String folio) {
        this.folio = folio;
        return this;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getRfc() {
        return rfc;
    }

    public TdRegFront rfc(String rfc) {
        this.rfc = rfc;
        return this;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public Instant getFecha() {
        return fecha;
    }

    public TdRegFront fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public TdRegFront usuario(String usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public TcTipoSol getTipoSolicitud() {
        return tipoSolicitud;
    }

    public TdRegFront tipoSolicitud(TcTipoSol tcTipoSol) {
        this.tipoSolicitud = tcTipoSol;
        return this;
    }

    public void setTipoSolicitud(TcTipoSol tcTipoSol) {
        this.tipoSolicitud = tcTipoSol;
    }

    public TcTipoImp getTipoImpuesto() {
        return tipoImpuesto;
    }

    public TdRegFront tipoImpuesto(TcTipoImp tcTipoImp) {
        this.tipoImpuesto = tcTipoImp;
        return this;
    }

    public void setTipoImpuesto(TcTipoImp tcTipoImp) {
        this.tipoImpuesto = tcTipoImp;
    }

    public TcEjerc getEjercicio() {
        return ejercicio;
    }

    public TdRegFront ejercicio(TcEjerc tcEjerc) {
        this.ejercicio = tcEjerc;
        return this;
    }

    public void setEjercicio(TcEjerc tcEjerc) {
        this.ejercicio = tcEjerc;
    }

    public Set<TcManifes> getManifestacions() {
        return manifestacions;
    }

    public TdRegFront manifestacions(Set<TcManifes> tcManifes) {
        this.manifestacions = tcManifes;
        return this;
    }

    public TdRegFront addManifestacion(TcManifes tcManifes) {
        this.manifestacions.add(tcManifes);
        tcManifes.getGenerals().add(this);
        return this;
    }

    public TdRegFront removeManifestacion(TcManifes tcManifes) {
        this.manifestacions.remove(tcManifes);
        tcManifes.getGenerals().remove(this);
        return this;
    }

    public void setManifestacions(Set<TcManifes> tcManifes) {
        this.manifestacions = tcManifes;
    }

    public Set<TcValida> getValidacions() {
        return validacions;
    }

    public TdRegFront validacions(Set<TcValida> tcValidas) {
        this.validacions = tcValidas;
        return this;
    }

    public TdRegFront addValidacion(TcValida tcValida) {
        this.validacions.add(tcValida);
        tcValida.getGenerals().add(this);
        return this;
    }

    public TdRegFront removeValidacion(TcValida tcValida) {
        this.validacions.remove(tcValida);
        tcValida.getGenerals().remove(this);
        return this;
    }

    public void setValidacions(Set<TcValida> tcValidas) {
        this.validacions = tcValidas;
    }

    public TdGeneral getGeneral() {
        return general;
    }

    public TdRegFront general(TdGeneral tdGeneral) {
        this.general = tdGeneral;
        return this;
    }

    public void setGeneral(TdGeneral tdGeneral) {
        this.general = tdGeneral;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TdRegFront)) {
            return false;
        }
        return id != null && id.equals(((TdRegFront) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TdRegFront{" +
            "id=" + getId() +
            ", region='" + getRegion() + "'" +
            ", domicilioRegion='" + getDomicilioRegion() + "'" +
            ", sucursalRegion='" + getSucursalRegion() + "'" +
            ", tipoImpuestod='" + getTipoImpuestod() + "'" +
            ", tipoSolicitudd='" + getTipoSolicitudd() + "'" +
            ", ejerciciod=" + getEjerciciod() +
            ", estatus='" + getEstatus() + "'" +
            ", folio='" + getFolio() + "'" +
            ", rfc='" + getRfc() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", usuario='" + getUsuario() + "'" +
            "}";
    }
}
