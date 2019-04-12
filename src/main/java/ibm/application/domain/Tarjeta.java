package ibm.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Tarjeta.
 */
@Entity
@Table(name = "tarjeta")
public class Tarjeta implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = "^(\\d{4}[ ]){3}\\d{4}")
    @Column(name = "numero_tarjeta", nullable = false)
    private String numeroTarjeta;

    @NotNull
    @Pattern(regexp = "^([0-9]{3,4})$")
    @Column(name = "ccv", nullable = false)
    private String ccv;

    @NotNull
    @Size(max = 50)
    @Pattern(regexp = "[A-Z]+")
    @Column(name = "tipo_tarjeta", length = 50, nullable = false)
    private String tipoTarjeta;

    @ManyToOne
    @JsonIgnoreProperties("tarjetas")
    private Cliente cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public Tarjeta numeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
        return this;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getCcv() {
        return ccv;
    }

    public Tarjeta ccv(String ccv) {
        this.ccv = ccv;
        return this;
    }

    public void setCcv(String ccv) {
        this.ccv = ccv;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public Tarjeta tipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
        return this;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Tarjeta cliente(Cliente cliente) {
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
        Tarjeta tarjeta = (Tarjeta) o;
        if (tarjeta.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tarjeta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tarjeta{" +
            "id=" + getId() +
            ", numeroTarjeta='" + getNumeroTarjeta() + "'" +
            ", ccv='" + getCcv() + "'" +
            ", tipoTarjeta='" + getTipoTarjeta() + "'" +
            "}";
    }
}
