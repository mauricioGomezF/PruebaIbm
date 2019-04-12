package ibm.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Pattern(regexp = "[A-Z]+")
    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @NotNull
    @Size(max = 100)
    @Column(name = "direccion", length = 100, nullable = false)
    private String direccion;

    @NotNull
    @Size(max = 30)
    @Column(name = "ciudad", length = 30, nullable = false)
    private String ciudad;

    @NotNull
    @Size(max = 20)
    @Pattern(regexp = "[0-9]+")
    @Column(name = "telefono", length = 20, nullable = false)
    private String telefono;

    @OneToMany(mappedBy = "cliente")
    private Set<Tarjeta> tarjetas = new HashSet<>();
    @OneToMany(mappedBy = "cliente")
    private Set<Consumo> consumos = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Cliente nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public Cliente direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public Cliente ciudad(String ciudad) {
        this.ciudad = ciudad;
        return this;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTelefono() {
        return telefono;
    }

    public Cliente telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Set<Tarjeta> getTarjetas() {
        return tarjetas;
    }

    public Cliente tarjetas(Set<Tarjeta> tarjetas) {
        this.tarjetas = tarjetas;
        return this;
    }

    public Cliente addTarjeta(Tarjeta tarjeta) {
        this.tarjetas.add(tarjeta);
        tarjeta.setCliente(this);
        return this;
    }

    public Cliente removeTarjeta(Tarjeta tarjeta) {
        this.tarjetas.remove(tarjeta);
        tarjeta.setCliente(null);
        return this;
    }

    public void setTarjetas(Set<Tarjeta> tarjetas) {
        this.tarjetas = tarjetas;
    }

    public Set<Consumo> getConsumos() {
        return consumos;
    }

    public Cliente consumos(Set<Consumo> consumos) {
        this.consumos = consumos;
        return this;
    }

    public Cliente addConsumo(Consumo consumo) {
        this.consumos.add(consumo);
        consumo.setCliente(this);
        return this;
    }

    public Cliente removeConsumo(Consumo consumo) {
        this.consumos.remove(consumo);
        consumo.setCliente(null);
        return this;
    }

    public void setConsumos(Set<Consumo> consumos) {
        this.consumos = consumos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cliente cliente = (Cliente) o;
        if (cliente.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cliente.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cliente{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", ciudad='" + getCiudad() + "'" +
            ", telefono='" + getTelefono() + "'" +
            "}";
    }
}
