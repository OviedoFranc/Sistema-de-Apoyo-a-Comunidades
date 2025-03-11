package ar.edu.utn.frba.dds.Controllers;

import ar.edu.utn.frba.dds.Modelos.Incidente;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositorioComunidades;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositorioIncidentes;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositorioPersonas;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositorioServicios;
import ar.edu.utn.frba.dds.Server.Utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import javax.persistence.criteria.CriteriaBuilder;

public class IncidentesController extends Controller implements ICrudViewsHandler {
  @Override
  public void index(Context context) {

  }

  @Override
  public void show(Context context) {

  }

  @Override
  public void create(Context context) {

  }

  @Override
  public void save(Context context) {
    int cantIncidentes = RepositorioIncidentes.getInstance().incidentesDeServicioYComunidad(Long.parseLong(context.pathParam("idServicio")), Long.parseLong(context.pathParam("id"))).size();
    Incidente incidente = new Incidente("Incidente "+(cantIncidentes+1),
        context.formParam("descripcion"),
        RepositorioPersonas.getInstance().get(context.sessionAttribute("id")),
        RepositorioServicios.getInstance().get(Long.parseLong(context.pathParam("idServicio"))),
        RepositorioComunidades.getInstance().get(Long.parseLong(context.pathParam("id"))));

    incidente.getComunidad().informarNuevoIncidente(incidente);
    RepositorioIncidentes.getInstance().add(incidente);

    context.status(HttpStatus.CREATED);
    context.redirect("/comunidades/"+context.pathParam("id")+"/servicios/"+context.pathParam("idServicio"));
  }

  @Override
  public void edit(Context context) {
    Incidente incidente = RepositorioIncidentes.getInstance().get(Long.parseLong(context.formParam("idIncidente")));

    incidente.getComunidad().informarIncidenteResuelto(incidente);
    RepositorioIncidentes.getInstance().update(incidente);

    context.status(HttpStatus.OK);
    context.redirect("/comunidades/"+context.pathParam("id")+"/servicios/"+context.pathParam("idServicio"));
  }

  @Override
  public void update(Context context) {

  }

  @Override
  public void delete(Context context) {

  }
}
