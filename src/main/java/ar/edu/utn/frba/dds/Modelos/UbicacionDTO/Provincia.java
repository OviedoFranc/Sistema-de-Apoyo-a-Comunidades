package ar.edu.utn.frba.dds.Modelos.UbicacionDTO;

import lombok.Getter;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class Provincia {
  @Getter
  @Transient
  public long id;
  @Getter
  @Column
  public String nombre;
}
