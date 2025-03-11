package ar.edu.utn.frba.dds.Modelos;

import ar.edu.utn.frba.dds.Modelos.UbicacionDTO.Localidad;
import ar.edu.utn.frba.dds.Persistencia.EntidadPersistente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "servicio")
public class Servicio extends EntidadPersistente {
  @Getter
  @Column
  private String nombre;
  @Column
  @Type(type = "text")
  @Getter
  private String descripcion;
  @JsonIgnore
  @Setter
  @Getter
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "establecimiento_id", referencedColumnName = "id")
  private Establecimiento establecimiento;

  public Servicio(String nombre, String descripcion, Establecimiento establecimiento) {
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.establecimiento = establecimiento;
  }

  public Servicio() {

  }
}
