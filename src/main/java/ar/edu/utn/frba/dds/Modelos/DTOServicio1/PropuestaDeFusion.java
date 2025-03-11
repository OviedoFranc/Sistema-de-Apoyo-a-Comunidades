package ar.edu.utn.frba.dds.Modelos.DTOServicio1;

import ar.edu.utn.frba.dds.Modelos.Comunidades.Comunidad;
import lombok.Getter;

public class PropuestaDeFusion {
  @Getter
  private Comunidad unaComunidad;
  @Getter
  private Comunidad otraComunidad;

  public PropuestaDeFusion(Comunidad unaComunidad, Comunidad otraComunidad) {
    this.unaComunidad = unaComunidad;
    this.otraComunidad = otraComunidad;
  }
}
