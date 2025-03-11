package ar.edu.utn.frba.dds.Modelos.DTOServicio1;

import ar.edu.utn.frba.dds.Modelos.Servicio;

import java.util.List;
import java.util.stream.Collectors;

public class ComunidadDTO {
  private int id;
  private String nombre;
  private List<ServicioParticularObservadoDTO> serviciosParticularesObservados;
  private Integer gradoDeConfianza;

  public ComunidadDTO() {}

  public ComunidadDTO(int id, String nombre, List<Servicio> serviciosDeInteres, Double gradoDeConfianza) {
    this.id = id;
    this.nombre = nombre;
    this.serviciosParticularesObservados = serviciosDeInteres.stream()
        .map(servicio ->
            new ServicioParticularObservadoDTO(
                new ServicioDTO((int) servicio.getId(), servicio.getNombre()),
                new EstablecimientoDTO((int) servicio.getEstablecimiento().getId(), servicio.getEstablecimiento().getNombre()))
        ).collect(Collectors.toList());
    this.gradoDeConfianza =  gradoDeConfianza.intValue();
  }

  public ComunidadDTO(ComunidadDTO body) {
    this.id = body.id;
    this.nombre = body.nombre;
    this.serviciosParticularesObservados = body.serviciosParticularesObservados;
    this.gradoDeConfianza = body.gradoDeConfianza;
  }


  public int getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public List<ServicioParticularObservadoDTO> getServiciosParticularesObservados() {
    return serviciosParticularesObservados;
  }

  public Integer getGradoDeConfianza() {
    return gradoDeConfianza;
  }
}
