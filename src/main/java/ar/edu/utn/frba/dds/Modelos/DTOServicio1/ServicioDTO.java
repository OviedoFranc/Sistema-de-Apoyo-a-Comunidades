package ar.edu.utn.frba.dds.Modelos.DTOServicio1;

public class ServicioDTO {
  private int id;
  private String nombre;
  public ServicioDTO() {
  }
  public ServicioDTO(int id, String nombre) {
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
