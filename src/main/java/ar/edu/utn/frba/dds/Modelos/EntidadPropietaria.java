package ar.edu.utn.frba.dds.Modelos;

import ar.edu.utn.frba.dds.Modelos.Usuarios.PersonaDesignada;
import ar.edu.utn.frba.dds.Persistencia.EntidadPersistente;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "entidad_propietaria")
public class EntidadPropietaria extends EntidadPersistente {
  @Getter
  @Column
  private String nombre;
  @Getter
  @Column
  @Type(type = "text")
  private String descripcion;
  @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private PersonaDesignada encargado;

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "organismoDeControl_id", referencedColumnName = "id")
  @Setter
  private OrganismoDeControl organismoDeControl;

  public EntidadPropietaria(String nombre, String descripcion, PersonaDesignada encargado, OrganismoDeControl organismoDeControl) {
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.encargado = encargado;
    this.organismoDeControl = organismoDeControl;
  }

  public EntidadPropietaria() {

  }
}
