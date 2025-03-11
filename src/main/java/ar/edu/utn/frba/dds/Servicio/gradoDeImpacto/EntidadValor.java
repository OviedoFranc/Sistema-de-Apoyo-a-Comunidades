package ar.edu.utn.frba.dds.Servicio.gradoDeImpacto;

import lombok.Getter;

@Getter
public class EntidadValor {
  public long entidad_id;
  public double resultadoGradoImpacto;

  public EntidadValor(long entidad_id, double resultadoGradoImpacto) {
    this.entidad_id = entidad_id;
    this.resultadoGradoImpacto = resultadoGradoImpacto;
  }
}
