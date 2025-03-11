package ar.edu.utn.frba.dds.Controllers;

import ar.edu.utn.frba.dds.Modelos.Rankings.RankingIncidentes;
import ar.edu.utn.frba.dds.Modelos.Usuarios.Rol;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositorioPersonas;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositorioPersonasDesignadas;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositoriosItemsRankings;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.Map;

public class LoginController extends Controller {

  public void get(Context context) {
    context.sessionAttribute("id", null);
    context.sessionAttribute("tipo_rol", null);
    context.render("login.hbs");
  }

  public void post(Context context){
    Map<String, Object> model = new HashMap<>();
    model.put("failed", true);
    if (RepositorioPersonas.getInstance().usuarioYContraseniaCorrectas(context.formParam("username"), context.formParam("password"))) {
      long idUser = RepositorioPersonas.getInstance().getId(context.formParam("username"), context.formParam("password"));
      context.sessionAttribute("id", idUser);
      context.sessionAttribute("tipo_rol", RepositorioPersonas.getInstance().get(idUser).getUsuario().getRol().name());
      context.redirect("/comunidades");
    } else if (RepositorioPersonasDesignadas.getInstance().usuarioYContraseniaCorrectas(context.formParam("username"), context.formParam("password"))) {
      long idUser = RepositorioPersonasDesignadas.getInstance().getId(context.formParam("username"), context.formParam("password"));
      context.sessionAttribute("id", idUser);
      context.sessionAttribute("tipo_rol", RepositorioPersonasDesignadas.getInstance().get(idUser).getUsuario().getRol().name());
      context.redirect("/rankings");
      //TODO:ALGO HARDCODEADO TOTALMENTE MAL
    } else if (context.formParam("username").equals("admin") && context.formParam("password").equals("abc123")) {
      context.sessionAttribute("tipo_rol", Rol.ADMINISTRADOR.name());
      context.redirect("/csv");
    }
    else {
      context.render("login.hbs", model);
    }
  }
}
