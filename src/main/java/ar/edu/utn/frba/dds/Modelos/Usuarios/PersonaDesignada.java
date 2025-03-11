package ar.edu.utn.frba.dds.Modelos.Usuarios;

import ar.edu.utn.frba.dds.Modelos.Usuarios.Usuario;
import ar.edu.utn.frba.dds.Persistencia.EntidadPersistente;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "persona_designada")
public class PersonaDesignada extends EntidadPersistente {
  @Getter
  @Setter
  @Column
  private String nombre;
  @Getter
  @Setter
  @Column
  private String apellido;
  @Getter
  @Setter
  @Embedded
  private Usuario usuario;
  public PersonaDesignada(String nombre, String apellido, Usuario usuario) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.usuario = usuario;
  }

  public PersonaDesignada() {

  }
}
