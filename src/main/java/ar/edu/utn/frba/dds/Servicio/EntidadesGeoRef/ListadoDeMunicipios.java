
package ar.edu.utn.frba.dds.Servicio.EntidadesGeoRef;

import ar.edu.utn.frba.dds.Modelos.UbicacionDTO.Municipalidad;

import java.util.List;

public class ListadoDeMunicipios extends PageInterface {
  public List<Municipalidad> municipalidades;
  public Parametro parametros;
  private class Parametro {
  public List<String> campos;
  public int max;
  public List<String> provincia;
  }
}
