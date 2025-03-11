package ar.edu.utn.frba.dds.Persistencia;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class EntidadPersistente {
  @Setter
  @Getter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

}
