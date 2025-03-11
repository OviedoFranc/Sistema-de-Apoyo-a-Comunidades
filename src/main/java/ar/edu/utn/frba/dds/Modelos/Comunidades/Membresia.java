package ar.edu.utn.frba.dds.Modelos.Comunidades;

import ar.edu.utn.frba.dds.Modelos.Usuarios.Persona;
import ar.edu.utn.frba.dds.Persistencia.EntidadPersistente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "membresia")
public class Membresia extends EntidadPersistente {
  @JsonIgnore
  @Getter
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private Comunidad comunidad;
  @JsonIgnore
  @Getter
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private Persona miembro;
  @Getter
  @Enumerated(EnumType.STRING)
  private RolComunidad tipoDeUsuario;
  @Getter
  @Enumerated(EnumType.STRING)
  private CargoComunidad cargoDentroDeComunidad;


  public Membresia(Comunidad comunidad, RolComunidad tipoDeUsuario, Persona miembro, CargoComunidad cargoComunidad) {
    this.comunidad = comunidad;
    this.tipoDeUsuario = tipoDeUsuario;
    this.miembro = miembro;
    this.cargoDentroDeComunidad = cargoComunidad;
  }

  public Membresia() {

  }

  public void cambiarRol(){
    if (tipoDeUsuario.equals(RolComunidad.AFECTADO)){
      this.tipoDeUsuario = RolComunidad.OBSERVADOR;
    } else {
      this.tipoDeUsuario = RolComunidad.AFECTADO;
    }
  }

  public void cambiarCargo(CargoComunidad cargo){
    this.cargoDentroDeComunidad = cargo;
  }

}
