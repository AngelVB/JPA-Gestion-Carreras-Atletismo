/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
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
@Table(name = "corredor")
@NamedQueries({
    @NamedQuery(name = "Corredor.findAll", query = "SELECT c FROM Corredor c"),
    @NamedQuery(name = "Corredor.findByIdCorredor", query = "SELECT c FROM Corredor c WHERE c.idCorredor = :idCorredor"),
    @NamedQuery(name = "Corredor.findByNombre", query = "SELECT c FROM Corredor c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Corredor.findByApellidos", query = "SELECT c FROM Corredor c WHERE c.apellidos = :apellidos"),
    @NamedQuery(name = "Corredor.findByDni", query = "SELECT c FROM Corredor c WHERE c.dni = :dni"),
    @NamedQuery(name = "Corredor.findByFechaNac", query = "SELECT c FROM Corredor c WHERE c.fechaNac = :fechaNac"),
    @NamedQuery(name = "Corredor.findByClub", query = "SELECT c FROM Corredor c WHERE c.club = :club")})
public class Corredor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_corredor")
    private Integer idCorredor;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "apellidos")
    private String apellidos;
    @Basic(optional = false)
    @Column(name = "dni")
    private String dni;
    @Basic(optional = false)
    @Column(name = "fecha_nac")
    @Temporal(TemporalType.DATE)
    private Date fechaNac;
    @Basic(optional = false)
    @Column(name = "club")
    private String club;
    @OneToMany(mappedBy = "idCorredor")
    private List<Marca> marcaList;

    public Corredor() {
    }

    public Corredor(Integer idCorredor) {
        this.idCorredor = idCorredor;
    }

    public Corredor(Integer idCorredor, String nombre, String apellidos, String dni, Date fechaNac, String club) {
        this.idCorredor = idCorredor;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.fechaNac = fechaNac;
        this.club = club;
    }

    public Integer getIdCorredor() {
        return idCorredor;
    }

    public void setIdCorredor(Integer idCorredor) {
        this.idCorredor = idCorredor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
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
        hash += (idCorredor != null ? idCorredor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Corredor)) {
            return false;
        }
        Corredor other = (Corredor) object;
        if ((this.idCorredor == null && other.idCorredor != null) || (this.idCorredor != null && !this.idCorredor.equals(other.idCorredor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Corredor[ idCorredor=" + idCorredor + " ]";
    }
    
}
