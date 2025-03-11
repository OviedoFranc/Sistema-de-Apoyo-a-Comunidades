package ar.edu.utn.frba.dds.Servicio.gradoDeImpacto;

import java.util.List;

public class ListadoDeValores {
  List<ValoresFormula> valoresPorEntidad;

  public ListadoDeValores(List<ValoresFormula> valoresPorEntidad) {
    this.valoresPorEntidad = valoresPorEntidad;
  }
}
