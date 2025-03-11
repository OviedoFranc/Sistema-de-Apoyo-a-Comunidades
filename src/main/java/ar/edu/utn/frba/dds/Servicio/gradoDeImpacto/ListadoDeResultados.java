package ar.edu.utn.frba.dds.Servicio.gradoDeImpacto;

import ar.edu.utn.frba.dds.Servicio.EntidadesGeoRef.PageInterface;
import lombok.Getter;
import java.util.List;
import java.util.Optional;

public class ListadoDeResultados extends PageInterface {
  @Getter
  public List<EntidadValor> entidadValor;

  public EntidadValor valorDeEntidad(long id){
    return this.entidadValor.stream()
        .filter(l -> l.getEntidad_id() == id)
        .findFirst().get();
  }

  public ListadoDeResultados(List<EntidadValor> entidadValor) {
    this.entidadValor = entidadValor;
  }
}
