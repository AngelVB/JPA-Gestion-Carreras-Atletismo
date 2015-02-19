/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Angel
 */
@Entity
@Table(name = "carrera")
@NamedQueries({
    @NamedQuery(name = "Carrera.findAll", query = "SELECT c FROM Carrera c"),
    @NamedQuery(name = "Carrera.findByIdCarrera", query = "SELECT c FROM Carrera c WHERE c.idCarrera = :idCarrera"),
    @NamedQuery(name = "Carrera.findByFecha", query = "SELECT c FROM Carrera c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "Carrera.findByPoblacion", query = "SELECT c FROM Carrera c WHERE c.poblacion = :poblacion"),
    @NamedQuery(name = "Carrera.findByOrganiza", query = "SELECT c FROM Carrera c WHERE c.organiza = :organiza"),
    @NamedQuery(name = "Carrera.findByLugarSalida", query = "SELECT c FROM Carrera c WHERE c.lugarSalida = :lugarSalida"),
    @NamedQuery(name = "Carrera.findByDistancia", query = "SELECT c FROM Carrera c WHERE c.distancia = :distancia"),
    @NamedQuery(name = "Carrera.findByModalidad", query = "SELECT c FROM Carrera c WHERE c.modalidad = :modalidad")})
public class Carrera implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_carrera")
    private Integer idCarrera;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "poblacion")
    private String poblacion;
    @Basic(optional = false)
    @Column(name = "organiza")
    private String organiza;
    @Basic(optional = false)
    @Column(name = "lugar_salida")
    private String lugarSalida;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "distancia")
    private BigDecimal distancia;
    @Basic(optional = false)
    @Column(name = "modalidad")
    private String modalidad;
    @OneToMany(mappedBy = "idCarrera")
    private List<Marca> marcaList;

    public Carrera() {
    }

    public Carrera(Integer idCarrera) {
        this.idCarrera = idCarrera;
    }

    public Carrera(Integer idCarrera, Date fecha, String poblacion, String organiza, String lugarSalida, BigDecimal distancia, String modalidad) {
        this.idCarrera = idCarrera;
        this.fecha = fecha;
        this.poblacion = poblacion;
        this.organiza = organiza;
        this.lugarSalida = lugarSalida;
        this.distancia = distancia;
        this.modalidad = modalidad;
    }

    public Integer getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(Integer idCarrera) {
        this.idCarrera = idCarrera;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getOrganiza() {
        return organiza;
    }

    public void setOrganiza(String organiza) {
        this.organiza = organiza;
    }

    public String getLugarSalida() {
        return lugarSalida;
    }

    public void setLugarSalida(String lugarSalida) {
        this.lugarSalida = lugarSalida;
    }

    public BigDecimal getDistancia() {
        return distancia;
    }

    public void setDistancia(BigDecimal distancia) {
        this.distancia = distancia;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public List<Marca> getMarcaList() {
        return marcaList;
    }

    public void setMarcaList(List<Marca> marcaList) {
        this.marcaList = marcaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCarrera != null ? idCarrera.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Carrera)) {
            return false;
        }
        Carrera other = (Carrera) object;
        if ((this.idCarrera == null && other.idCarrera != null) || (this.idCarrera != null && !this.idCarrera.equals(other.idCarrera))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Carrera[ idCarrera=" + idCarrera + " ]";
    }
    
}
