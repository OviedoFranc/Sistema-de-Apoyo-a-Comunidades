package ar.edu.utn.frba.dds.Controllers;

import ar.edu.utn.frba.dds.Modelos.Comunidades.Comunidad;
import ar.edu.utn.frba.dds.Modelos.Comunidades.Membresia;
import ar.edu.utn.frba.dds.Modelos.Incidente;
import ar.edu.utn.frba.dds.Modelos.Servicio;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositorioComunidades;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositorioEstablecimiento;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositorioIncidentes;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositorioMembresias;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositorioServicios;
import ar.edu.utn.frba.dds.Server.Utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServiciosController extends Controller implements ICrudViewsHandler {

  @Override
  public void index(Context context) {
    RepositorioServicios.getInstance().clean();
    List todos = RepositorioServicios.getInstance().all();
    Comunidad comunidad = RepositorioComunidades.getInstance().get(Long.parseLong(context.pathParam("id")));
    List servicios = todos.stream().filter(s -> !comunidad.getServiciosDeInteres().contains(s)).toList();
    List establecimientos = RepositorioEstablecimiento.getInstance().all();

    Map<String, Object> model = new HashMap<>();
    model.put("servicios", servicios);
    model.put("comunidad", context.pathParam("id"));
    model.put("establecimientos", establecimientos);

    context.render("servicios/agregar_servicio.hbs", model);
  }

  @Override
  public void show(Context context) {
    RepositorioServicios.getInstance().clean();
    List<Incidente> incidentes = RepositorioIncidentes.getInstance().incidentesDeServicioYComunidad(Long.parseLong(context.pathParam("idServicio")), Long.parseLong(context.pathParam("id")));
    List<Incidente> incidentesResueltos = incidentes.stream().filter(incidente -> incidente.getEstaResuelto() == true).collect(Collectors.toList());
    List<Incidente> incidentesAbiertos = incidentes.stream().filter(incidente -> incidente.getEstaResuelto() == false).collect(Collectors.toList());
    Servicio servicio = RepositorioServicios.getInstance().get(Long.parseLong(context.pathParam("idServicio")));

    Map<String, Object> model = new HashMap<>();
    model.put("servicio", servicio);
    model.put("comunidad", context.pathParam("id"));
    model.put("incidentesAbiertos", incidentesAbiertos);
    model.put("incidentesResueltos", incidentesResueltos);

    context.render("incidentes/incidentes.hbs", model);
  }

  @Override
  public void create(Context context) {
  }

  @Override
  public void save(Context context) {
    Servicio servicio = new Servicio(context.formParam("nombreServicio"),
        context.formParam("descripcionServicio"),
        RepositorioEstablecimiento.getInstance().get(Long.parseLong(context.formParam("establecimiento"))));

    Comunidad comunidad = RepositorioComunidades.getInstance().get(Long.parseLong(context.pathParam("id")));
    comunidad.agregarServicioDeInteres(servicio);

    RepositorioComunidades.getInstance().update(comunidad);

    context.status(HttpStatus.CREATED);
    context.redirect("/comunidades/".concat(context.pathParam("id")).concat("/servicios"));
  }

  @Override
  public void edit(Context context) {

  }

  @Override
  public void update(Context context) {

  }

  @Override
  public void delete(Context context) {

  }
}
