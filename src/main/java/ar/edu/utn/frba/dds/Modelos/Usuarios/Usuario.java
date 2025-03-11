package ar.edu.utn.frba.dds.Modelos.Usuarios;

import ar.edu.utn.frba.dds.Persistencia.EntidadPersistente;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Embeddable
public class Usuario {
  @Getter
  @Setter
  @Column
  private String username;
  @Getter
  @Setter
  @Column
  private String contrasenia;
  @Getter
  @Enumerated(EnumType.STRING)
  @Column(name = "tipo")
  private Rol rol;

  public Usuario(String username, String contrasenia, Rol rol) {
    this.username = username;
    this.contrasenia = contrasenia;
    this.rol = rol;
  }

  public Usuario() {

  }
}