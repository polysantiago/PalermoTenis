package com.palermotenis.model.beans;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Locale;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.palermotenis.model.beans.geograficos.Pais;

@Entity
@Table(name = "monedas")
@NamedQueries(
    { @NamedQuery(name = "Moneda.findAll", query = "SELECT m FROM Moneda m"),
            @NamedQuery(name = "Moneda.findById", query = "SELECT m FROM Moneda m WHERE m.id = :id"),
            @NamedQuery(name = "Moneda.findByCodigo", query = "SELECT m FROM Moneda m WHERE m.codigo = :codigo"),
            @NamedQuery(name = "Moneda.findByNombre", query = "SELECT m FROM Moneda m WHERE m.nombre = :nombre") })
public class Moneda implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "Simbolo")
    private String simbolo;

    @Column(name = "Codigo")
    private String codigo;

    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;

    @JoinColumn(name = "contrario")
    @OneToOne(fetch = FetchType.EAGER)
    private Moneda contrario;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "moneda")
    private Collection<Pais> paises;

    @Transient
    private transient Locale locale;

    @Transient
    private transient NumberFormat formatter;

    public Moneda() {
    }

    public Moneda(Integer id) {
        this.id = id;
    }

    public Moneda(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Moneda(String simbolo, String codigo, String nombre) {
        this.simbolo = simbolo;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public Moneda(String simbolo, String codigo, String nombre, Moneda contrario) {
        this.simbolo = simbolo;
        this.codigo = codigo;
        this.nombre = nombre;
        this.contrario = contrario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Collection<Pais> getPaises() {
        return paises;
    }

    public void setPaises(Collection<Pais> paises) {
        this.paises = paises;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Moneda)) {
            return false;
        }
        Moneda other = (Moneda) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "test.Moneda[id=" + id + "]";
    }

    public String formatear(Double valor, Pais pais) {
        if (getLocale() == null) {
            setLocale(new Locale(pais.getIdioma().getCodigo(), pais.getCodigo()));
        }
        if (getFormatter() == null) {
            setFormatter(NumberFormat.getInstance(getLocale()));
        }
        getFormatter().setMinimumFractionDigits(2);
        return getFormatter().getCurrency().getSymbol() + " " + getFormatter().format(valor);
    }

    /**
     * @return the locale
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * @param locale
     *            the locale to set
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * @return the formatter
     */
    public NumberFormat getFormatter() {
        return formatter;
    }

    /**
     * @param formatter
     *            the formatter to set
     */
    public void setFormatter(NumberFormat formatter) {
        this.formatter = formatter;
    }

    /**
     * @return the simbolo
     */
    public String getSimbolo() {
        return simbolo;
    }

    /**
     * @param simbolo
     *            the simbolo to set
     */
    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo
     *            the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the contrario
     */
    public Moneda getContrario() {
        return contrario;
    }

    /**
     * @param contrario
     *            the contrario to set
     */
    public void setContrario(Moneda contrario) {
        this.contrario = contrario;
    }

}
