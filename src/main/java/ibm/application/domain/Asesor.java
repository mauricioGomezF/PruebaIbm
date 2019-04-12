package ibm.application.domain;



import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Asesor.
 */
@Entity
@Table(name = "asesor")
public class Asesor implements Serializable {

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
    @Size(max = 50)
    @Pattern(regexp = "[A-Z]+")
    @Column(name = "especialidad", length = 50, nullable = false)
    private String especialidad;

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

    public Asesor nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public Asesor especialidad(String especialidad) {
        this.especialidad = especialidad;
        return this;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
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
        Asesor asesor = (Asesor) o;
        if (asesor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), asesor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Asesor{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", especialidad='" + getEspecialidad() + "'" +
            "}";
    }
}
