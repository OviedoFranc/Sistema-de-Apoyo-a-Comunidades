package ar.edu.utn.frba.dds.Modelos.DTOServicio1;

public class PropuestaDeFusionDTO {
  private ComunidadDTO unaComunidad;
  private ComunidadDTO otraComunidad;

  public PropuestaDeFusionDTO() {}

  public PropuestaDeFusionDTO(ComunidadDTO unaComunidad, ComunidadDTO otraComunidad) {
    this.unaComunidad = unaComunidad;
    this.otraComunidad = otraComunidad;
  }

  // Getters y setters

  public ComunidadDTO getUnaComunidad() {
    return unaComunidad;
  }

  public void setUnaComunidad(ComunidadDTO unaComunidad) {
    this.unaComunidad = unaComunidad;
  }

  public ComunidadDTO getOtraComunidad() {
    return otraComunidad;
  }

  public void setOtraComunidad(ComunidadDTO otraComunidad) {
    this.otraComunidad = otraComunidad;
  }
}