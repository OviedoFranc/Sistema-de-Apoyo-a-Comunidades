package ar.edu.utn.frba.dds.Modelos.DTOServicio1;

import ar.edu.utn.frba.dds.Servicio.gradoDeImpacto.EntidadValor;
import lombok.Getter;
import java.util.List;

public class ListaPropuestas {
  @Getter
  public List<PropuestaDeFusionDTO> propuestas;

  public ListaPropuestas(List<PropuestaDeFusionDTO> propuestas) {
    this.propuestas = propuestas;
  }
}
