package ar.edu.utn.frba.dds.Modelos.Rankings;

import ar.edu.utn.frba.dds.Modelos.Entidad;
import ar.edu.utn.frba.dds.Persistencia.EntidadPersistente;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import java.io.IOException;
import java.util.List;

@Entity
@Table(name = "rankings")
//@DiscriminatorColumn(name = "nombre", discriminatorType = DiscriminatorType.STRING)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class MetodosRanking extends EntidadPersistente {
  @Getter
  @Column
  protected String nombre;

  public List<ItemRanking> generarRanking(List<Entidad> entidades) throws IOException, InterruptedException {

    return null;
  }
}
