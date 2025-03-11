package ar.edu.utn.frba.dds.Modelos.Rankings;

import ar.edu.utn.frba.dds.Modelos.Entidad;
import ar.edu.utn.frba.dds.Modelos.Incidente;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositorioIncidentes;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositorioRankings;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositoriosItemsRankings;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MayorCantidadIncidentes extends MetodosRanking {
  //public LocalDateTime horarioActual;
  /*
  @Getter
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "ranking")
  private List<ItemRanking> rankingCantidadIncidentes;
   */

  public MayorCantidadIncidentes() {
    this.nombre = "Mayor cantidad de incidentes en la semana";
    //this.rankingCantidadIncidentes = new ArrayList<>();
  }

  @Override
  public List<ItemRanking> generarRanking(List<Entidad> entidades) {
    List<ItemRanking> rankingCantidadIncidentes = new ArrayList<>();
    for (Entidad entidad : entidades) {
      List<Incidente> incidentesEntidad = RepositorioIncidentes.getInstance().incidentesDeEntidad(entidad);
      long cantidadIncidentes = incidentesEntidad.stream().filter(incidente -> sonDelPeriodo(incidente)).count();
      double cantidad = (double) cantidadIncidentes;
      ItemRanking item = new ItemRanking(entidad, cantidad, LocalDateTime.now(), this);
      rankingCantidadIncidentes.add(item);
      RepositoriosItemsRankings.getInstance().add(item);
    }
    return rankingCantidadIncidentes;
  }

  private Boolean sonDelPeriodo(Incidente incidente) {
    long tiempo = ChronoUnit.DAYS.between(incidente.getFechaHoraApertura(), LocalDateTime.now());
    return tiempo < 7;
  }


}
