package ar.edu.utn.frba.dds.Modelos.DTOServicio1;

public class EstablecimientoDTO {
  private int id;
  private String nombre;

  public EstablecimientoDTO() {
  }

  public EstablecimientoDTO(int id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }

  public int getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }
}
