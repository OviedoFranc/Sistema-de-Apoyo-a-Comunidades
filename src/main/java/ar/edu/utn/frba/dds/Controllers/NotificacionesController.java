package ar.edu.utn.frba.dds.Controllers;

import ar.edu.utn.frba.dds.Modelos.Notificaciones.Notificacion.Notificacion;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositorioNotificaciones;
import ar.edu.utn.frba.dds.Server.Utils.ICrudViewsHandler;
import io.javalin.http.Context;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificacionesController extends Controller implements ICrudViewsHandler {

  @Override
  public void index(Context context) {
    Map<String, Object> model = new HashMap<>();
    RepositorioNotificaciones.getInstance().clean();

    long id = context.sessionAttribute("id");
    List<Notificacion> notificaciones = RepositorioNotificaciones.getInstance().getNotificacionesDeUsuario(id);
    notificaciones.sort(Comparator.comparing(notificacion -> notificacion.getIncidente().getFechaHoraApertura()));
    Collections.reverse(notificaciones);

    model.put("notificaciones", notificaciones);

    context.render("notificaciones.hbs",model);
  }

  @Override
  public void show(Context context) {

  }

  @Override
  public void create(Context context) {

  }

  @Override
  public void save(Context context) {

  }

  @Override
  public void edit(Context context) {

  }

  @Override
  public void update(Context context) {

  }

  @Override
  public void delete(Context context) {
    long idNotificacion = Long.parseLong(context.formParam("idNotificacion"));
    Notificacion notificacion = RepositorioNotificaciones.getInstance().get(idNotificacion);
    RepositorioNotificaciones.getInstance().delete(notificacion);
    context.redirect("/notificaciones");
  }
}
