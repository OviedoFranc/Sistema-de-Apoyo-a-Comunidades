package ar.edu.utn.frba.dds.Servicio.gradoDeImpacto;

public class ValoresFormula {
  long entidad_id;
  int tiempoResolucionIncidente;
  int cantIncidentesNoResueltos;
  int cnf;
  int totalPersonasImpactadas;

  public ValoresFormula(long entidad_id, int tiempoResolucionIncidente, int cantIncidentesNoResueltos, int cnf, int totalPersonasImpactadas) {
    this.entidad_id = entidad_id;
    this.tiempoResolucionIncidente = tiempoResolucionIncidente;
    this.cantIncidentesNoResueltos = cantIncidentesNoResueltos;
    this.cnf = cnf;
    this.totalPersonasImpactadas = totalPersonasImpactadas;
  }
}
