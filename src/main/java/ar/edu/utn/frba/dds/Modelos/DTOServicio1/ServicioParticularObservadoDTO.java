package ar.edu.utn.frba.dds.Modelos.DTOServicio1;

public class ServicioParticularObservadoDTO {
  private int id;
  private ServicioDTO servicio;
  private EstablecimientoDTO establecimiento;

  public ServicioParticularObservadoDTO() {
  }
  public ServicioParticularObservadoDTO(ServicioDTO servicio, EstablecimientoDTO establecimiento) {
    this.id = servicio.getId();
    this.servicio = servicio;
    this.establecimiento = establecimiento;
  }

  public ServicioDTO getServicio() {
    return servicio;
  }

  public EstablecimientoDTO getEstablecimiento() {
    return establecimiento;
  }
}
