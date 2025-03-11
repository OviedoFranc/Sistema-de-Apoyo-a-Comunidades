package ar.edu.utn.frba.dds.Modelos;

import ar.edu.utn.frba.dds.Modelos.Usuarios.PersonaDesignada;
import ar.edu.utn.frba.dds.Persistencia.EntidadPersistente;
import lombok.Getter;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "organismo_de_control")
public class OrganismoDeControl extends EntidadPersistente {
  @Getter
  @Column
  private String nombre;
  @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private PersonaDesignada encargado;

  public OrganismoDeControl(String nombre, PersonaDesignada encargado) {
    this.nombre = nombre;
    this.encargado = encargado;
  }

  public OrganismoDeControl() {

  }
}
