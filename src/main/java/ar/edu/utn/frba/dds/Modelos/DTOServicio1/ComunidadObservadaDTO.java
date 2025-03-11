package ar.edu.utn.frba.dds.Modelos.DTOServicio1;

import java.util.List;

public class ComunidadObservadaDTO {
  private List<ComunidadDTO> comunidades;

  public ComunidadObservadaDTO(List<ComunidadDTO> comunidadesObservadas) {
    this.comunidades = comunidadesObservadas;
  }

  public List<ComunidadDTO> getComunidades() {
    return comunidades;
  }

  public void setComunidades(List<ComunidadDTO> comunidades) {
    this.comunidades = comunidades;
  }
}
