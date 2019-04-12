package ibm.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "consumo")
public class Consumo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private Instant fecha;

    @NotNull
    @Size(max = 100)
    @Column(name = "descripcion", length = 100, nullable = false)
    private String descripcion;

    @DecimalMax(value = "999999999999")
    @Column(name = "monto")
    private Double monto;

    @ManyToOne
    @JsonIgnoreProperties("consumos")
    private Cliente cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFecha() {
        return fecha;
    }

    public Consumo fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Consumo descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getMonto() {
        return monto;
    }

    public Consumo monto(Double monto) {
        this.monto = monto;
        return this;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Consumo cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
        Consumo consumo = (Consumo) o;
        if (consumo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), consumo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Consumo{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", monto=" + getMonto() +
            "}";
    }
}
